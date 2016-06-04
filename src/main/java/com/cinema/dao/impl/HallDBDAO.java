package com.cinema.dao.impl;

import com.cinema.Factories;
import com.cinema.dao.api.HallDAO;
import com.cinema.dao.api.SessionDAO;
import com.cinema.dao.api.UserDAO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import com.cinema.model.Hall;
import com.cinema.model.Session;
import com.cinema.model.User;

import java.util.List;

/**
 * Created by dshvedchenko on 10.04.16.
 */
public class HallDBDAO extends GenericDBDao<Hall, Integer> implements HallDAO {

    private volatile static HallDAO instance;

    private SessionDAO sessionDAO = null;

    private HallDBDAO(Class<Hall> type) {
        super(type);
    }

    public static HallDAO getInstance() {
        if (instance == null) {
            synchronized (HallDBDAO.class) {
                if (instance == null) {
                    instance = new HallDBDAO(Hall.class);
                }
            }
        }

        return instance;
    }

//    @Override
//    public boolean populateReferences(Hall entity) throws IllegalOperationException, DAOExceptions {
//        sessionDAO = Factories.getInstance().getSessionDao();
//        List<Session> sessions = sessionDAO.findByHallId(entity.getId());
//        sessions.stream().forEach(item -> item.setHall(entity));
//        entity.setSessions(sessions);
//        return false;
//    }
//
//    @Override
//    public boolean deleteReferences(Hall entity) throws IllegalOperationException, DAOExceptions {
//        return false;
//    }
}
