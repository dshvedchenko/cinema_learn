package com.cinema.dao.api;

/**
 * Created by dshvedchenko on 25.03.16.
 */

import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;

import java.util.List;

public interface GenericDao<E, K> {

    /**
     * Add entity into DB
     *
     * @param entity
     * @return
     * @throws DAOExceptions
     */
    K add(E entity) throws DAOExceptions;

    /**
     * Updates entity in DB,
     *
     * @param entity
     */
    void update(E entity);

    /**
     * Remove entity from DB
     *
     * @param entity
     * @throws DAOExceptions
     */
    void remove(E entity) throws DAOExceptions;

    /**
     * Remove Entotyt from DB by ID
     *
     * @param id
     * @throws DAOExceptions
     */
    void removeById(K id) throws DAOExceptions;

    /**
     * Remove all entities of this type from DB
     *
     * @throws DAOExceptions
     */
    void removeAll() throws DAOExceptions;

    /**
     * Find entity by Key( id )
     *
     * @param id
     * @return
     * @throws DAOExceptions
     */
    E findById(K id) throws DAOExceptions;

    /**
     * Check if entity exists in DB
     *
     * @param entity
     * @return
     */
    boolean exists(E entity);

    /**
     * Extract ID from entity, needed for inMemory rezliations
     *
     * @param entity
     * @return
     * @throws IllegalOperationException
     */
    K getIdByEntity(E entity) throws IllegalOperationException;

    /**
     * List all entity of this type from DB
     *
     * @return
     * @throws DAOExceptions
     */
    List<E> listAll() throws DAOExceptions;

}
