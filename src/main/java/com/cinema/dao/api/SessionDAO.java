package com.cinema.dao.api;

import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import com.cinema.model.Session;

import java.util.List;

/**
 * Created by dshvedchenko on 14.03.16.
 */
public interface SessionDAO extends GenericDao<Session, Integer> {

    public List<Session> findByHallId(Integer hall_id) throws IllegalOperationException, DAOExceptions;

    public List<Session> findByMovieId(Integer movie_id) throws IllegalOperationException, DAOExceptions;

    public boolean deleteSessionById(Integer sessionId) throws DAOExceptions;

}
