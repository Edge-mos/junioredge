package ru.job4j.xmlxslt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 29.11.2018.
 */
public class Config {
    public static final String DB_URL = "db.url";
    public static final String DB_INITIAL = "db.initial";
    public static final String DB_DROP = "db.drop";
    public static final String DB_INSERT = "db.insert";
    public static final String DB_LOAD_VALS = "db.loadVals";
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static Properties properties = new Properties();

    public synchronized static String getProperty(String arg) {
        if (properties.isEmpty()) {
            try (InputStream is = Config.class.getClassLoader().getResourceAsStream("dao.properties")) {
                properties.load(is);
            } catch (Exception e) {
                logger.error("Config load problem", e);
            }
        }
        return properties.getProperty(arg);
    }
}
