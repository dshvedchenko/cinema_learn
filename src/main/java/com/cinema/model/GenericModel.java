package com.cinema.model;

import com.cinema.dao.annotations.Entity;
import com.cinema.exception.IllegalOperationException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by dshvedchenko on 06.04.16.
 */
@Entity(tableName = "")
abstract public class GenericModel<K> {

    public GenericModel() {
    }

//
//    public boolean setFieldValueFromDAO(Field field, ResultSet resultSet, String columnLabel) {
//        boolean result = false;
//        try {
//            Integer valueIndex = resultSet.findColumn(columnLabel);
//            result = setFieldValueFromDAO(field,resultSet, valueIndex);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public boolean setFieldValueFromDAO(Field field, ResultSet resultSet, Integer valueIndex) {
//        boolean result = false;
//        Class<?> c = this.getClass();
//        try {
//            Object value = null;
//            field.setAccessible(true);
//            if (field.getType() == Integer.class ) {
//                value = resultSet.getInt(valueIndex);
//            } else if ( field.getType() == LocalDate.class ) {
//                value = resultSet.getTimestamp(valueIndex).toLocalDateTime().toLocalDate();
//            } else
//                value = resultSet.getObject(valueIndex);
//
//
//            field.setAccessible(true);
//            field.set(this, value);
//            result = true;
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//    public K getKeyValue(Field field) throws IllegalOperationException {
//        K result = null;
//        Class<?> c = this.getClass();
//        try {
//            field.setAccessible(true);
//            Object o = field.get(this);
//            result = (K) o;
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public Object getFieldValueForDAO(Field field) {
//        Object result = null;
//        Class<?> c = this.getClass();
//        try {
//            field.setAccessible(true);
//            result = field.get(this);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

}
