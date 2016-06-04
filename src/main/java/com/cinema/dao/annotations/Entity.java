package com.cinema.dao.annotations;

import jdk.nashorn.internal.ir.annotations.Reference;

import java.lang.annotation.*;

/**
 * Created by dshvedchenko on 06.04.16.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    String tableName();
}
