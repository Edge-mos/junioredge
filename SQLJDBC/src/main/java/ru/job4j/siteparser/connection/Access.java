package ru.job4j.siteparser.connection;

import java.sql.Connection;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Интерфейс для получения данных.
 * @since 02.12.2018.
 */
public interface Access {
    Connection getConnection();
}
