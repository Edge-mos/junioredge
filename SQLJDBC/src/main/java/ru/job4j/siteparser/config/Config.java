package ru.job4j.siteparser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Класс для работы с файлом конфигураций.
 * @since 02.12.2018.
 */
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private final String filePath;
    private Properties properties = new Properties();
    public static final String URL = "db.url";
    public static final String LOGIN = "db.login";
    public static final String PASSWORD = "db.password";
    public static final String INITIAL = "db.initial";
    public static final String DROP = "db.drop";
    public static final String GETALL = "db.getAll";
    public static final String INSERT = "db.insert";
    public static final String SITE_URL = "site.url";
    public static final String PATTERN = "parse.pattern";
    public static final String LASTSEARCH = "db.lastSearch";

    public Config(String filePath) {
        this.filePath = filePath;
    }

    public synchronized String getProperty(String arg) {
        if (this.properties.isEmpty()) {
            try (InputStream is = Config.class.getClassLoader().getResourceAsStream(this.filePath)) {
                this.properties.load(is);
            } catch (Exception e) {
                LOGGER.error("Config problem!!!", e);
            }
        }
        return this.properties.getProperty(arg);
    }
}
