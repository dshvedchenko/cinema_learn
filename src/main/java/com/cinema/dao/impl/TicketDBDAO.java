package com.cinema.dao.impl;

import com.cinema.Factories;
import com.cinema.dao.api.SessionDAO;
import com.cinema.dao.api.TicketDAO;
import com.cinema.dao.api.UserDAO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import com.cinema.model.Ticket;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dshvedchenko on 11.04.16.
 */
public class TicketDBDAO extends GenericDBDao<Ticket, Integer> implements TicketDAO {


    private volatile static TicketDAO instance;
    private SessionDAO sessionDAO = null;
    private UserDAO userDAO = null;

    public static TicketDAO getInstance() {
        if (instance == null) {
            synchronized (UserDBDAO.class) {
                if (instance == null) {

                    instance = new TicketDBDAO(Ticket.class);
                }
            }
        }

        return instance;
    }


    public TicketDBDAO(Class<Ticket> classs) {
        super(classs);

    }

    @Override
    public List<Ticket> findAllTickets() throws DAOExceptions {
        return listAll();
    }

    @Override
    public List<Ticket> findAllTicketsBySessionId(Integer sessionId) throws DAOExceptions {
        List<Ticket> result = null;
        try {
            result = findByFK("session_id", sessionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Ticket> findAllTicketsByUserId(Integer userId) throws DAOExceptions {

        List<Ticket> result = null;
        try {
            result = findByFK("booked_by_user_id", userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer purchaseTicket(Ticket ticket) {
        Integer ticketId = null;
        try {
            ticketId = add(ticket);
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        return ticketId;
    }

    @Override
    public void returnTicket(Ticket ticket) throws DAOExceptions {
        remove(ticket);
    }

    @Override
    public void deleteAllTicketsBySessionId(Integer sessionId) throws DAOExceptions {
        removeByForeignKey("session_id", sessionId);
    }

}
