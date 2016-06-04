package com.cinema.dto;

/**
 * Created by dshvedchenko on 21.03.16.
 */
public enum UserRole {
    REGULAR(0),
    ADMIN(1);

    private final int value;

    UserRole(int i) {
        value = i;
    }

    public static UserRole of(int i) {
        return values()[i];
    }
}
