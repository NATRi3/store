package by.epam.store.pool;

import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;
import java.util.TimerTask;
@Log4j2
public class CheckConnectionTimerTask extends TimerTask {
    @Override
    public void run() {
        while (CustomConnectionPool.getInstance().getSize()<CustomConnectionPool.POOL_SIZE) {
            try {
                CustomConnectionPool.getInstance().closeConnection(new ProxyConnection(ConnectionCreator.getConnection()));
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        CustomConnectionPool.lockConnection.lock();
        CustomConnectionPool.condition.signalAll();
        CustomConnectionPool.lockConnection.unlock();
    }
}
