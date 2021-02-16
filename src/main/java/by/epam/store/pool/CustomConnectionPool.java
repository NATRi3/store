package by.epam.store.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

public class CustomConnectionPool {
    private static final Logger log = LogManager.getLogger(CustomConnectionPool.class);
    private static CustomConnectionPool instance;
    private static final AtomicBoolean isInitialized = new AtomicBoolean(true);
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnection;
    private static final Lock locking = new ReentrantLock();
    static final Lock lockConnection = new ReentrantLock();
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
        timerTask.schedule(new CheckConnectionTimerTask(), 1,2000);
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

            lockConnection.unlock();
        }
        try {
            ProxyConnection connection = freeConnections.take();
            givenAwayConnection.add(connection);
            return connection;
        } catch (InterruptedException e) {
            log.info(e);
            throw new SQLException();
        }
    }

    public void closeConnection(Connection connection) {
        if(timeTaskIsWork.get()){
            lockConnection.lock();
            lockConnection.unlock();
        }
        if(connection instanceof ProxyConnection) {
            givenAwayConnection.remove(connection);
            freeConnections.add((ProxyConnection) connection);
        } else {
        log.warn("Connection is not proxy or null!");
        }
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
