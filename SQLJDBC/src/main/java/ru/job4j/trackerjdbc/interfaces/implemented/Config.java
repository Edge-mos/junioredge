package ru.job4j.trackerjdbc.interfaces.implemented;

import ru.job4j.trackerjdbc.interfaces.InitConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 01.10.2018.
 */
public class Config implements InitConfig{
   private final Properties config;

    public Config() {
        this.config = this.loadProperties();
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream path = new FileInputStream(
                "/home/edge/MEGAsync/job4jProjects/module2junior/SQLJDBC/src/main/resources/configTracker.properties")) {
            properties.load(path);
        } catch (IOException e) {
            System.err.println("Config load error!");
            e.printStackTrace();
        }
        return properties;
    }

    @Override
    public String getProperty(String property) {
        return this.config.getProperty(property);
    }
}
