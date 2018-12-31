package ru.job4j.siteparser.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.siteparser.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Имплементация соединения для БД.
 * @since 02.12.2018.
 */
public class DbConnectionImpl implements Access{
    private final Config config;
    private static final Logger LOGGER = LoggerFactory.getLogger(DbConnectionImpl.class);

    public DbConnectionImpl(Config config) {
        this.config = config;
    }

    @Override
    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(this.config.getProperty(Config.URL),
                    this.config.getProperty(Config.LOGIN),
                    this.config.getProperty(Config.PASSWORD));
        } catch (SQLException e) {
            LOGGER.error("Connection failed!", e);
        }
        return con;
    }
}
