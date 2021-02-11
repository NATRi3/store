package by.epam.store.pool;

import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;
import java.util.TimerTask;
@Log4j2
public class CheckConnectionTimerTask extends TimerTask {
    @Override
    public void run() {
        CustomConnectionPool instance = CustomConnectionPool.getInstance();
        CustomConnectionPool.lockConnection.lock();
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
        CustomConnectionPool.lockConnection.unlock();
    }
}
