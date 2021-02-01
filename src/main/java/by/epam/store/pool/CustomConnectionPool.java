package by.epam.store.pool;

import lombok.extern.log4j.Log4j2;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
public class CustomConnectionPool {
    private static CustomConnectionPool instance;
    private static final AtomicBoolean isInitialized = new AtomicBoolean(true);
    private final BlockingQueue<Connection> freeConnections;
    private final Queue<Connection> givenAwayConnection;
    private static final Lock locking = new ReentrantLock();
    static final Lock lockConnection = new ReentrantLock();
    static final Condition condition = lockConnection.newCondition();
    static final AtomicBoolean timeTaskIsWork = new AtomicBoolean(false);
    static final int POOL_SIZE = 8;
    private static final Timer timerTask = new Timer();
    private CustomConnectionPool(){
        freeConnections = new LinkedBlockingQueue<>();
        givenAwayConnection = new LinkedList<>();
        try{
            for(int i = 0; i<POOL_SIZE;i++){
                freeConnections.add(new ProxyConnection(ConnectionCreator.getConnection()));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException();
        }
        timerTask.schedule(new CheckConnectionTimerTask(), /*TimeUnit.HOURS.toMillis(2)*/1,2000);
    }

    public static CustomConnectionPool getInstance(){
        if(isInitialized.get()){
            locking.lock();
            if(instance == null) {
                instance = new CustomConnectionPool();
                isInitialized.set(false);
            }
            locking.unlock();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if(timeTaskIsWork.get()){
            lockConnection.lock();
            if(timeTaskIsWork.get()) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    log.warn(e);
                }
            }
            lockConnection.unlock();
        }
        try {
            Connection connection = freeConnections.take();
            givenAwayConnection.add(connection);
            return connection;
        } catch (InterruptedException e) {
            log.info(e);
            throw new SQLException();
        }
    }

    public void closeConnection(Connection connection) throws SQLException {
        if(timeTaskIsWork.get()){
            lockConnection.lock();
            if(timeTaskIsWork.get()) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    log.warn(e);
                }
            }
            lockConnection.unlock();
        }
        if(!(connection instanceof ProxyConnection)) {
            log.warn("Connection is not proxy or null!");
            throw new SQLException("Connection is not proxy or null!");
        }
        givenAwayConnection.remove(connection);
        freeConnections.add(connection);
    }

    public void closePool(){
        for(int i=0; i<getSize(); i++){
            try {
                ProxyConnection proxyConnection = (ProxyConnection) freeConnections.take();
                proxyConnection.reallyClose();
            } catch (InterruptedException | SQLException e) {
               log.warn(e);
            }
        }
    }

    int getSize(){
        return freeConnections.size()+givenAwayConnection.size();
    }

}