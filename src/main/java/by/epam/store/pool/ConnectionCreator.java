package by.epam.store.pool;

import com.mysql.cj.jdbc.Driver;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;

@Log4j2
public class ConnectionCreator {
    public static final String DB_PROPERTIES_FILE = "property/config.properties";
    private static final String DB_URL;
    public static final String user;
    public static final String pass;
    public static final Properties property = new Properties();
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
            log.error(e);
            throw new RuntimeException();
        }
    }
    private ConnectionCreator(){
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,user,pass);
    }
}
