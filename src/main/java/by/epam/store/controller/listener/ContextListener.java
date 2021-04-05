package by.epam.store.controller.listener;

import by.epam.store.model.pool.CustomConnectionPool;
import by.epam.store.model.pool.NoSQLConnectionPool;
import by.epam.store.annotation.DependencyInjector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(ContextListener.class);
    public static final String APPLICATION_CONTAINER = "ApplicationContainer";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(APPLICATION_CONTAINER, DependencyInjector.getApplicationContainer());
        log.info("Dependency inject successful");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        CustomConnectionPool.getInstance().closePool();
        NoSQLConnectionPool.getInstance().close();
        log.info("Connection SQL and NoSQL pool closed successful");
    }
}
