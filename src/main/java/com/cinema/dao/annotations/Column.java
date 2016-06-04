package com.cinema.dao.annotations;

import java.lang.annotation.*;

/**
 * Created by dshvedchenko on 06.04.16.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name() default "";
}
