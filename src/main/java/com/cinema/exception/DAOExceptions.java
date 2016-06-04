package com.cinema.exception;

/**
 * Created by dshvedchenko on 18.04.16.
 */
public class DAOExceptions extends Exception {
    public DAOExceptions() {
    }

    public DAOExceptions(String cause) {
        super(cause);
    }

    public DAOExceptions(String cause, Exception e) {
        super(cause + ":\r\n" + (e != null ? e.getMessage() : ""), e);

    }
}
