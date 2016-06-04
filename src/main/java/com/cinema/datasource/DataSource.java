package com.cinema.datasource;

import com.cinema.config.AppConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Data;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by dshvedchenko on 31.03.16.
 */
public class DataSource {

    private ComboPooledDataSource cdps = null;
    private static AppConfig config = AppConfig.getInstance();

    private static DataSource instance = null;

    private DataSource() {
        cdps = new ComboPooledDataSource();
        try {
            cdps.setDriverClass(config.getDriverClassName());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cdps.setJdbcUrl(config.getDbUrl());
        cdps.setUser(config.getDbUsername());
        cdps.setPassword(config.getDbPassword());
        cdps.setInitialPoolSize(5);
        cdps.setMinPoolSize(config.getDbPoolSize());
        cdps.setMaxConnectionAge(600);
        cdps.setDataSourceName("MYSQL_CINEMA");

    }

    public static DataSource getInstance() {
        if (instance == null) {
            synchronized (DataSource.class) {
                if (instance == null) {
                    instance = new DataSource();
                }
            }
        }

        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        connection = cdps.getConnection();
        return connection;
    }

    public void destroy() {
        cdps.close();
    }
}
