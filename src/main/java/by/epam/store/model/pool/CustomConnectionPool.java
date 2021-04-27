package by.epam.store.model.pool;

import by.epam.store.exception.InitializationException;
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

/**
 * The type Custom connection pool.
 */
public class CustomConnectionPool implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(CustomConnectionPool.class);
    private static final Lock locking = new ReentrantLock();
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static CustomConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnection;
    /**
     * The Pool size.
     */
    static final int POOL_SIZE = 4;
    /**
     * The Timer task locker.
     */
    final ReentrantReadWriteLock timerTaskLocker = new ReentrantReadWriteLock();

    private CustomConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>();
        givenAwayConnection = new LinkedList<>();
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = ConnectionCreator.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            }
        } catch (SQLException e) {
            log.fatal(e);
            throw new InitializationException("Error in initialization connection pool");
        }
        Timer timerTask = new Timer();
        timerTask.schedule(new CheckConnectionTimerTask(), 14400000, 14400000);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CustomConnectionPool getInstance() {
        if (!isInitialized.get()) {
            try {
                locking.lock();
                if (instance == null) {
                    instance = new CustomConnectionPool();
                    isInitialized.set(true);
                }
            } finally {
                locking.unlock();
            }
        }
        return instance;
    }

    /**
     * Gets connection from connection pool.
     *
     * @return the connection
     */
    public Connection getConnection() {
        timerTaskLocker.readLock().lock();
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnection.add(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e);
        } finally {
            timerTaskLocker.readLock().unlock();
        }
        return connection;
    }

    /**
     * Return connection to connection pool.
     *
     * @param connection the connection
     */
    void closeConnection(Connection connection) {
        timerTaskLocker.readLock().lock();
        try {
            if (connection instanceof ProxyConnection) {
                givenAwayConnection.remove(connection);
                freeConnections.add((ProxyConnection) connection);
            } else {
                log.warn("Connection is not proxy or null!");
            }
        } finally {
            timerTaskLocker.readLock().unlock();
        }

    }

    /**
     * Close pool.
     */
    @Override
    public void close() {
        for (int i = 0; i < getSize(); i++) {
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

    /**
     * Gets size.
     *
     * @return the size
     */
    int getSize() {
        return freeConnections.size() + givenAwayConnection.size();
    }

}
