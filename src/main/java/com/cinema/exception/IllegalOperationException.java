package com.cinema.exception;

/**
 * Created by dshvedchenko on 07.04.16.
 */
public class IllegalOperationException extends Exception {
    //Parameterless Constructor
    public IllegalOperationException() {
    }

    //Constructor that accepts a message
    public IllegalOperationException(String message) {
        super(message);
    }
}
