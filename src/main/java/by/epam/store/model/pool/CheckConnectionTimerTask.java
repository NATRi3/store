package by.epam.store.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.TimerTask;

/**
 * The type Check connection timer task.
 * Timer task which generate lost connections
 */
public class CheckConnectionTimerTask extends TimerTask {
    private static final Logger log = LogManager.getLogger(CheckConnectionTimerTask.class);

    @Override
    public void run() {
        CustomConnectionPool instance = CustomConnectionPool.getInstance();
        instance.timerTaskLocker.writeLock().lock();
        try {
            while (CustomConnectionPool.getInstance().getSize() < CustomConnectionPool.POOL_SIZE) {
                try {
                    ProxyConnection newConnection = new ProxyConnection(ConnectionCreator.getConnection());
                    instance.closeConnection(newConnection);
                } catch (SQLException e) {
                    log.warn(e);

                }
            }
        } finally {
            instance.timerTaskLocker.writeLock().unlock();
        }
    }
}
