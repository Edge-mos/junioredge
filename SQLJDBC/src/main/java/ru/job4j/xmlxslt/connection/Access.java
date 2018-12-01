package ru.job4j.xmlxslt.connection;

import ru.job4j.xmlxslt.config.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Статический метод в интерфейсе!!
 * @since 29.11.2018.
 */
public interface Access {
    Connection getConnection();

    static Connection getConnectionDB() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(Config.getProperty(Config.DB_URL));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
