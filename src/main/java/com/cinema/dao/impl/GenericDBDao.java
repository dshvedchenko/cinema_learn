package com.cinema.dao.impl;

import com.cinema.dao.annotations.Column;
import com.cinema.dao.annotations.Entity;
import com.cinema.dao.annotations.Id;
import com.cinema.exception.DAOExceptions;
import com.cinema.dao.api.GenericDao;
import com.cinema.datasource.DataSource;
import com.cinema.exception.IllegalOperationException;
import com.cinema.model.GenericModel;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by dshvedchenko on 06.04.16.
 * generic DB operations
 */
abstract class GenericDBDao<E extends GenericModel, K> implements GenericDao<E, K> {
    private Class type;

    protected Logger logger = null;

    @lombok.Getter
    protected String tableName;

    @lombok.Getter
    protected String idFieldName;

    protected DataSource dataSource;
    private Set<Field> fields;
    private Set<Field> mutableFields;
    private Field idField;

    private final String SELECT_ALL = "SELECT * FROM %s";
    private final String FIND_BY_ID = "SELECT * FROM %s WHERE %s = ?";
    private final String FIND_BY_FK_ID = "SELECT * FROM %s WHERE %s = ?";
    private final String FIND_BY_PARAMS = "SELECT * FROM %s where %s";
    private final String INSERT = "INSERT INTO %s (%s) values (%s)";
    private final String UPDATE = "UPDATE %s SET %s WHERE %s = ?";
    private final String DELETE_BY_ID = "DELETE FROM %s WHERE %s = ?";
    private final String DELETE_ALL = "DELETE FROM %s";

    //private Map<String,PreparedStatement> preparedStatements = null;

    public GenericDBDao(Class<E> type) {
        this.type = type;
        dataSource = DataSource.getInstance();
        hanldeFields(this.type);
        handleTable(this.type);
        logger = getLogger(this.getClass().getName());
    }


    @Override
    public K add(E entity) throws DAOExceptions {

        K result = null;
        String sql = getInsertStatement(INSERT, getTableName(), mutableFields);

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("ADD Entity", e);
            throw new DAOExceptions("Can not connect DB", e);
        }
        PreparedStatement preparedStatement = null;


        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            populatePrepStatementParams(entity, preparedStatement);

            int rows = preparedStatement.executeUpdate();

            setGeneratedKey(entity, preparedStatement, rows);

            result = (K) getKeyValue(entity, idField);
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("ADD Entity", e1);
            }
            logger.error("ADD Entity", e);
            throw new DAOExceptions("Can not add user", e);
        } catch (IllegalOperationException e) {
            logger.error("ADD Entity. prepare data", e);
            throw new DAOExceptions("Can not prepare data", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }
        return result;
    }

    private void setGeneratedKey(E entity, PreparedStatement preparedStatement, int rows) throws SQLException, DAOExceptions {
        if (rows > 0) {
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                setFieldValueFromDAO(entity, idField, rs, 1);
            }
        }
    }

    private void populatePrepStatementParams(E entity, PreparedStatement preparedStatement) throws SQLException {
        int i = 0;
        for (Field field : mutableFields) {
            i++;
            Object value = getFieldValueForDAO(entity, field);
            if (field.getType() == Integer.class) {
                preparedStatement.setInt(i, (Integer) value);
            } else if (field.getType() == LocalDate.class) {
                if (value != null) {
                    value = java.sql.Date.valueOf((LocalDate) value);
                }
                preparedStatement.setDate(i, (java.sql.Date) value);

            } else if (field.getType() == LocalDateTime.class) {
                if (value != null) {
                    value = java.sql.Timestamp.valueOf((LocalDateTime) value);
                }
                preparedStatement.setTimestamp(i, (java.sql.Timestamp) value);
            } else
                preparedStatement.setObject(i, value);
        }
    }


    protected String getInsertStatement(String template, String tableName, Set<Field> fields) {
        return String.format(template, getTableName(), getCsvFieldsList(fields), getParamPlaces(fields.size()));
    }


    protected String getCsvFieldsList(Set<Field> fields) {
        StringBuilder tmp = new StringBuilder();
        boolean first = true;
        for (Field field : fields) {
            if (first) first = false;
            else tmp.append(", ");
            tmp.append(getColumnForField(field));
        }
        return tmp.toString();
    }

    protected String getParamPlaces(int count) {
        StringBuilder tmp = new StringBuilder("");
        for (int i = 0; i < count; i++) {
            if (i > 0) tmp.append(", ");
            tmp.append("?");
        }

        return tmp.toString();
    }


    @Override
    public void update(E entity) {
        K result = null;
        String sql = getUpdateStatement(UPDATE, getTableName(), mutableFields, idField);

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;


        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            populatePrepStatementParams(entity, preparedStatement);
            preparedStatement.setObject(getKeyIndexInUpdateStatement(mutableFields), getIdByEntity(entity));

            int rows = preparedStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            logger.error("UPDATE Entity", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("ROLLBACK UPDATE Entity", e1);
            }
        } catch (IllegalOperationException e) {
            logger.error("prepare data UPDATE Entity", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }
        // return result;
    }

    protected String getUpdateStatement(String template, String tableName, Set<Field> fields, Field idKey) {
        return String.format(template, getTableName(), getUpdateSetFieldsPart(fields), getIdFieldName());
    }


    private String getUpdateSetFieldsPart(Set<Field> fields) {
        StringBuilder tmp = new StringBuilder();
        boolean first = true;
        for (Field field : fields) {
            if (first) first = false;
            else tmp.append(", ");
            tmp.append(getColumnForField(field))
                    .append(" = ? ");
        }
        return tmp.toString();
    }

    /**
     * Assume that in update WHERE has only one key
     *
     * @param mutablefields
     * @return
     */
    private Integer getKeyIndexInUpdateStatement(Set<Field> mutablefields) {
        return mutablefields.size() + 1;
    }


    @Override
    public void removeById(K id) throws DAOExceptions {
        String sql = String.format(DELETE_BY_ID, getTableName(), getIdFieldName());

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            int rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("REMOVE Entity", e);
            throw new DAOExceptions("REMOVE Entity", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }

    }

    @Override
    public void remove(E entity) throws DAOExceptions {
        try {
            removeById(getIdByEntity(entity));
        } catch (IllegalOperationException e) {
            logger.error("Remove Entity", e);
            ;
        }

    }

    @Override
    public void removeAll() throws DAOExceptions {
        String sql = String.format(DELETE_ALL, getTableName());

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            logger.error("Remove ALL Entity", e);
            throw new DAOExceptions("Remove All Entity", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }

    }


    public void removeByForeignKey(String fkColumn, Object key) throws DAOExceptions {
        String sql = String.format(DELETE_BY_ID, getTableName(), fkColumn);

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, key);
            int rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Remove by Foreaign Key", e);
            throw new DAOExceptions("Remove by Foreign key", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }


    }

    @Override
    public E findById(K id) throws DAOExceptions {

        String sql = String.format(FIND_BY_ID, getTableName(), getIdFieldName());

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        E result = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = createEntity(resultSet);
            }

        } catch (SQLException e) {
            logger.error("Find By Key Entity", e);
            throw new DAOExceptions("Find By Key", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }


        return result;

    }

    public List<E> findByParams(LinkedHashMap<String, Object> paramSet) throws SQLException, DAOExceptions {

        String sql = String.format(FIND_BY_PARAMS, getTableName(), SqlHelper.getConditionPartFromMapParams(paramSet));

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        List<E> result = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int i = 1;
            for (Map.Entry<String, Object> entry : paramSet.entrySet()) {
                preparedStatement.setObject(i, entry.getValue());
                i++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            result = readAll(resultSet);

        } catch (SQLException e) {
            logger.error("Find By params", e);
            throw new DAOExceptions("Find by Params", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }


        return result;

    }

    public List<E> findByFK(String fkColumn, Object key) throws SQLException, DAOExceptions {

        String sql = String.format(FIND_BY_FK_ID, getTableName(), fkColumn);

        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = null;
        List<E> result = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();
            result = readAll(resultSet);

        } catch (SQLException e) {
            logger.error("Find bY FK", e);
            throw new DAOExceptions("Find By FK", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }


        return result;

    }

    @Override
    public boolean exists(E entity) {
        K key = null;
        boolean result = false;
        try {
            key = getIdByEntity(entity);
        } catch (IllegalOperationException e) {
            e.printStackTrace();
        }

        if (key != null) {
            E entFromDb = null;
            try {
                entFromDb = findById(key);
            } catch (DAOExceptions daoExceptions) {
                daoExceptions.printStackTrace();
            }
            if (entFromDb != null) result = true;
        }

        return result;
    }

    @Override
    public K getIdByEntity(E entity) throws IllegalOperationException {
        return (K) getKeyValue(entity, idField);
    }

    @Override
    public List<E> listAll() throws DAOExceptions {


        String sql = String.format(SELECT_ALL, getTableName());

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        List result = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = readAll(resultSet);
        } catch (SQLException e) {
            logger.error("listALL", e);
            throw new DAOExceptions("List ALl", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }
        return result;
    }


    protected List<E> readAll(ResultSet resultSet) throws SQLException, DAOExceptions {
        List<E> result = new LinkedList<>();
        while (resultSet.next()) {
            E entity = createEntity(resultSet);
            result.add((E) entity);
        }
        return result;
    }

    protected E createEntity(ResultSet resultSet) throws SQLException, DAOExceptions {

        E entity = null;

        if (!resultSet.isAfterLast()) {
            try {
                entity = (E) this.type.newInstance();
            } catch (InstantiationException e) {
                logger.error("Create Entity from resultSet", e);
                throw new DAOExceptions("Create ENtity from ResultSet", e);
            } catch (IllegalAccessException e) {
                logger.error("Set Entity fields", e);
                throw new RuntimeException(e);
            }

            for (Field field : fields) {
                // Object o = resultSet.getObject();
                if (!setFieldValueFromDAO(entity, field, resultSet, getColumnForField(field)))
                    throw new UnsupportedOperationException(String.format("%s field set is BAD", field.getName()));
            }
        }

        return entity;
    }


    /**
     * Finds all model fields annotated with Column annotations and @Id
     * and set fields property and idField property
     *
     * @param classs
     */
    public void hanldeFields(Class<?> classs) {
        Set<Field> set = new LinkedHashSet<>();
        mutableFields = new LinkedHashSet<>();
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {

                    if (field.isAnnotationPresent(Id.class)) {
                        idField = field;
                        this.idFieldName = getColumnForField(field);

                    }
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        //copy all fields except idField
        mutableFields = set.stream().filter(item -> !item.equals(idField)).collect(Collectors.toSet());
        fields = set;
    }

    private void handleTable(Class type) {
        if (!type.isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException("Can not use types not annotated with Entity.class");

        Entity entity = (Entity) type.getAnnotation(Entity.class);
        this.tableName = entity.tableName();
    }

    /**
     * Returns table column name if defined in Column annotation, if omit  -use lowercase field name
     *
     * @param field
     * @return supposed column name
     */
    private String getColumnForField(Field field) {
        String result = field.getName().toLowerCase();
        Column column = (Column) field.getAnnotation(Column.class);
        if (column.name().length() > 0) result = column.name().toLowerCase();
        return result;
    }

    void cleaunpConnectionsResources(PreparedStatement preparedStatement, Connection connection) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public boolean setFieldValueFromDAO(E entity, Field field, ResultSet resultSet, String columnLabel) throws DAOExceptions {
        boolean result = false;
        try {
            Integer valueIndex = resultSet.findColumn(columnLabel);
            result = setFieldValueFromDAO(entity, field, resultSet, valueIndex);
        } catch (SQLException e) {
            logger.error("set fields From DAO", e);
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Perform general getting data from ResultSet according to destination field types
     *
     * @param entity
     * @param field
     * @param resultSet
     * @param valueIndex
     * @return
     */
    public boolean setFieldValueFromDAO(E entity, Field field, ResultSet resultSet, Integer valueIndex) throws DAOExceptions {
        boolean result = false;
        Class<?> c = entity.getClass();
        try {
            Object value = null;
            field.setAccessible(true);
            if (field.getType() == Integer.class) {
                value = resultSet.getInt(valueIndex);
            } else if (field.getType() == LocalDate.class) {
                value = resultSet.getDate(valueIndex);
                if (value != null) {
                    value = ((java.sql.Date) value).toLocalDate();
                }

            } else if (field.getType() == LocalDateTime.class) {
                value = resultSet.getTimestamp(valueIndex);
                if (value != null) {
                    value = ((java.sql.Timestamp) value).toLocalDateTime();
                }
            } else
                value = resultSet.getObject(valueIndex);

            field.setAccessible(true);
            field.set(entity, value);
            result = true;
        } catch (IllegalAccessException e) {
            logger.error("Set Field Value from DAO", e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("Set Field Value From DAO", e);
            throw new DAOExceptions("Set Field Value From DAO", e);
        }

        return result;
    }

    public K getKeyValue(E entity, Field field) throws IllegalOperationException {
        K result = null;
        Class<?> c = entity.getClass();
        try {
            field.setAccessible(true);
            Object o = field.get(entity);
            result = (K) o;
        } catch (IllegalAccessException e) {
            logger.error("Get Key Value", e);
            throw new RuntimeException(e);
        }

        return result;
    }

    public Object getFieldValueForDAO(E entity, Field field) {
        Object result = null;
        Class<?> c = entity.getClass();
        try {
            field.setAccessible(true);
            result = field.get(entity);
        } catch (IllegalAccessException e) {
            logger.error("ADD Entity", e);
            throw new RuntimeException(e);
        }

        return result;
    }


}
