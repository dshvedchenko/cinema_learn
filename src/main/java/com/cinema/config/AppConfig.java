package com.cinema.config;

import lombok.Data;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Created by dshvedchenko on 17.03.16.
 */
@Data
public class AppConfig {
    static volatile AppConfig instance;
    private boolean isInMemoryDB = true;

    private String dbPassword = null;
    private String dbUsername = null;
    private String dbUrl = null;
    private Integer dbPoolSize = null;
    private String DriverClassName = null;

    private Properties pros = null;

    private AppConfig() {
        pros = new Properties();
        InputStream is = null;
        try {
            String propFile = "application.property";

            if (new File(propFile).exists()) {
                is = new FileInputStream(propFile);
            } else {
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile);
            }
            pros.load(is);

            loadConfigFromProps();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException e) {
            }
        }

    }

    private void loadConfigFromProps() {
        if (pros.getProperty("db.mysql") != null) {
            setInMemoryDB(pros.getProperty("db.mysql").equalsIgnoreCase("false"));
        }

        if (pros.getProperty("db.username") != null) {
            setDbUsername(pros.getProperty("db.username"));
        }

        if (pros.getProperty("db.password") != null) {
            setDbPassword(pros.getProperty("db.password"));
        }

        if (pros.getProperty("db.url") != null) {
            setDbUrl(pros.getProperty("db.url"));
        }

        setDbPoolSize((Integer) pros.getOrDefault("db.maxpoolsize", 5));

        if (pros.getProperty("db.driverclassname") != null) {
            setDriverClassName(pros.getProperty("db.driverclassname"));
        }
    }


    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }
}
