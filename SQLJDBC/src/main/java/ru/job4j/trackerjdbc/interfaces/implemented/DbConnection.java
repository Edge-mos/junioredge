package ru.job4j.trackerjdbc.interfaces.implemented;

import ru.job4j.trackerjdbc.interfaces.Connect;
import ru.job4j.trackerjdbc.interfaces.InitConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 01.10.2018.
 */
public class DbConnection implements Connect {

    private final InitConfig config;
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public DbConnection(InitConfig config) {
        this.config = config;
        this.URL = this.config.getProperty("db.url");
        this.USERNAME = this.config.getProperty("db.login");
        this.PASSWORD = this.config.getProperty("db.password");
    }

    @Override
    public Connection getConnection() {
        Connection con = null;
        try {
           con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Connect failed!");
            e.printStackTrace();
        }
        return con;
    }
}
