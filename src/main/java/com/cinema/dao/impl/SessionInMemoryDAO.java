package com.cinema.dao.impl;

import com.cinema.dao.api.SessionDAO;
import com.cinema.exception.IllegalOperationException;
import com.cinema.model.Session;

import java.util.List;

/**
 * Created by dshvedchenko on 25.03.16.
 */
public class SessionInMemoryDAO extends InMemoryDao<Session, Integer> implements SessionDAO {

    private volatile static SessionDAO instance;


    public static SessionDAO getInstance() {
        if (instance == null) {
            synchronized (HallInMemoryDAO.class) {
                if (instance == null) {
                    instance = new SessionInMemoryDAO();
                }
            }
        }

        return instance;
    }

    @Override
    public Integer injectKey(Session entity) {
        Integer localKey = -1;
        synchronized (keySequence) {
            localKey = keySequence++;
            entity.setId(keySequence);
        }
        return localKey;
    }

    @Override
    void initKeySequence() {
        if (keySequence == null) {
            synchronized (SessionInMemoryDAO.class) {
                keySequence = 0;
            }
        }
    }


    @Override
    public Integer getIdByEntity(Session entity) {
        return entity.getId();
    }


    @Override
    public List<Session> findByHallId(Integer hall_id) throws IllegalOperationException {
        throw new IllegalOperationException("not supported in InMemory");
    }

    @Override
    public List<Session> findByMovieId(Integer movie_id) throws IllegalOperationException {
        throw new IllegalOperationException("not supported in InMemory");
    }

    @Override
    public boolean deleteSessionById(Integer sessionId) {
        boolean result = false;
        Session session = findById(sessionId);
        if (session != null) {
            remove(session);
            result = true;
        }

        return result;
    }
}

