package ru.job4j.xmlxslt.connection;

import ru.job4j.xmlxslt.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Неиспользуется в программе!
 * @since 29.11.2018.
 */
public class DbConnectionImpl implements Access{
    private final String URL;

    public DbConnectionImpl() {
        this.URL = Config.getProperty(Config.DB_URL);
    }

    @Override
    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(this.URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
