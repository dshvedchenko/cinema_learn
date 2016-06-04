package com.cinema.exception;

import javax.servlet.ServletContextListener;

/**
 * Created by dshvedchenko on 18.04.16.
 */
public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(String cause) {
        super(cause);
    }

    public ServiceException(String cause, Exception e) {
        super(cause + ":\r\n" + (e != null ? e.getMessage() : ""), e);
    }
}
