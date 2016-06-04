package com.cinema.dao.impl;

import com.cinema.Factories;
import com.cinema.dao.api.*;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import com.cinema.model.Hall;
import com.cinema.model.Movie;
import com.cinema.model.Session;
import com.cinema.model.Ticket;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dshvedchenko on 11.04.16.
 */
public class SessionDBDAO extends GenericDBDao<Session, Integer> implements SessionDAO {


    private volatile static SessionDAO instance;

    private MovieDAO movieDAO = null;
    private TicketDAO ticketDAO = null;
    private HallDAO hallDAO = null;

    private SessionDBDAO(Class<Session> type) {
        super(type);

    }

    public static SessionDAO getInstance() {
        if (instance == null) {
            synchronized (SessionDBDAO.class) {
                if (instance == null) {
                    instance = new SessionDBDAO(Session.class);
                }
            }
        }
        return instance;
    }

//    @Override
//    public boolean populateReferences(Session entity) throws IllegalOperationException, DAOExceptions {
//        boolean result = false;
//
//        movieDAO = Factories.getInstance().getMovieDao();
//        ticketDAO = Factories.getInstance().getTicketDoa();
//        hallDAO = Factories.getInstance().getHallDao();
//
//        List<Ticket> tickets = ticketDAO.findAllTicketsBySessionId(entity.getId());
//        entity.setTickets(tickets);
//
//        Hall hall = hallDAO.findById(entity.getHall_Id());
//        entity.setHall(hall);
//
//        Movie movie = movieDAO.findById(entity.getMovie_Id());
//        entity.setMovie(movie);
//
//        return true;
//    }

//    @Override
//    public boolean deleteReferences(Session entity) throws IllegalOperationException, DAOExceptions {
//        boolean result = false;
//        ticketDAO = Factories.getInstance().getTicketDoa();
//        for(Ticket ticket: entity.getTickets()) {
//            ticketDAO.remove(ticket);
//        }
//        result = true;
//        return result;
//    }

    public List<Session> findByHallId(Integer hall_id) throws DAOExceptions {
        List<Session> sessions = null;
        try {
            sessions = findByFK("hall_id", hall_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    public List<Session> findByMovieId(Integer movie_id) throws DAOExceptions {
        List<Session> sessions = null;
        try {
            sessions = findByFK("movie_id", movie_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    @Override
    public boolean deleteSessionById(Integer sessionId) throws DAOExceptions {
        boolean result = false;
        Session session = findById(sessionId);
        if (session != null) {
            remove(session);
            result = true;
        }

        return result;
    }

}
