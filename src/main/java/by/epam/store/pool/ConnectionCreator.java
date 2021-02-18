package by.epam.store.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    private static final Logger log = LogManager.getLogger(ConnectionCreator.class);
    private static final String DB_PROPERTIES_FILE = "property/config.properties";
    private static final Properties property = new Properties();
    private static final String DB_URL;
    private static final String user;
    private static final String pass;

    static {
        try {
            ClassLoader classLoader = ConnectionCreator.class.getClassLoader();
            property.load(classLoader.getResourceAsStream(DB_PROPERTIES_FILE));
            String driverName = property.getProperty("driver");
            Class.forName(driverName);
            DB_URL = property.getProperty("url");
            user = property.getProperty("user");
            pass = property.getProperty("pass");
        } catch (IOException | ClassNotFoundException e) {
            log.fatal(e);
            throw new RuntimeException();
        }
    }

    private ConnectionCreator(){
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,user,pass);
    }
}
