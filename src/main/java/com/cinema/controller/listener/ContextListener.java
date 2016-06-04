package com.cinema.controller.listener;

import com.cinema.Constants;
import com.cinema.Factories;
import com.cinema.datasource.DataSource;
import com.cinema.dto.UserRole;
import org.slf4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by dshvedchenko on 14.04.16.
 */
public class ContextListener implements ServletContextListener {

    Logger logger = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // to init all singletons on app start
        sce.getServletContext().setAttribute("Factories", Factories.getInstance());
        // init connection pool
        //TODO switch to use Tomcat managed AppPool
        sce.getServletContext().setAttribute("DataSource", DataSource.getInstance());

        sce.getServletContext().setAttribute("userRoles", UserRole.values());

        System.setProperty("org.slf4j.simpleLogger.showDateTime", "true");
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
        System.setProperty("org.slf4j.simpleLogger.dateTimeFormat", "yyyy-MM-dd::HH-mm-ss-SSS");
        logger = getLogger(this.getClass().getName());
        logger.error("I have hit contextInitialized: " + sce.getServletContext().toString());

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSource ds = (DataSource) sce.getServletContext().getAttribute("DataSource");
        ds.destroy();
        logger.debug("I have hit contextDestroyed.");
    }
}
