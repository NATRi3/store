package by.epam.store.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CustomConnectionPool {
    private static final Logger log = LogManager.getLogger(CustomConnectionPool.class);
    private static CustomConnectionPool instance;
    private static final AtomicBoolean isInitialized = new AtomicBoolean(true);
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnection;
    private static final Lock locking = new ReentrantLock();
    static final Lock lockConnection = new ReentrantLock();
    static final AtomicBoolean timeTaskIsWork = new AtomicBoolean(false);
    static final int POOL_SIZE = 4;
    private final Timer timerTask = new Timer();
    final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private CustomConnectionPool(){
        freeConnections = new LinkedBlockingQueue<>();
        givenAwayConnection = new LinkedList<>();
        try{
            for(int i = 0; i<POOL_SIZE;i++){
                Connection connection = ConnectionCreator.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            }
        } catch (SQLException e) {
            log.fatal(e);
            throw new RuntimeException();
        }
        timerTask.schedule(new CheckConnectionTimerTask(), 14400000,14400000);
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

    public Connection getConnection(){
        readWriteLock.readLock().lock();
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnection.add(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e);
        }finally {
            readWriteLock.readLock().unlock();
        }
        return connection;
    }

    void closeConnection(Connection connection) {
        readWriteLock.readLock().lock();
        try {
            if (connection instanceof ProxyConnection) {
                givenAwayConnection.remove(connection);
                freeConnections.add((ProxyConnection) connection);
            } else {
                log.warn("Connection is not proxy or null!");
            }
        } finally {
            readWriteLock.readLock().unlock();
        }

    }

    public void closePool(){
        for(int i=0; i<getSize(); i++){
            try {
                ProxyConnection proxyConnection = freeConnections.take();
                proxyConnection.reallyClose();
            } catch (InterruptedException | SQLException e) {
                log.error(e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException exp) {
                log.error(exp);
            }
        }
    }

    int getSize(){
        return freeConnections.size()+givenAwayConnection.size();
    }

}
