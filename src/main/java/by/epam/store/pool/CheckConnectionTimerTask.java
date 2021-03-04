package by.epam.store.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.TimerTask;

public class CheckConnectionTimerTask extends TimerTask {
    private static final Logger log = LogManager.getLogger(CheckConnectionTimerTask.class);
    @Override
    public void run() {
        CustomConnectionPool instance = CustomConnectionPool.getInstance();
        CustomConnectionPool.timeTaskIsWork.set(true);
        while (CustomConnectionPool.getInstance().getSize()<CustomConnectionPool.POOL_SIZE) {
            try {
                ProxyConnection newConnection = new ProxyConnection(ConnectionCreator.getConnection());
                instance.closeConnection(newConnection);
            } catch (SQLException e) {
                log.warn(e);

            }
        }
        CustomConnectionPool.timeTaskIsWork.set(false);
    }
}
