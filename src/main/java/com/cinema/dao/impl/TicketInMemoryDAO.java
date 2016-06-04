package com.cinema.dao.impl;

import com.cinema.dao.api.TicketDAO;
import com.cinema.model.Ticket;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dshvedchenko on 25.03.16.
 */
public class TicketInMemoryDAO extends InMemoryDao<Ticket, Integer> implements TicketDAO {


    private volatile static TicketDAO instance;


    public static TicketDAO getInstance() {
        if (instance == null) {
            synchronized (HallInMemoryDAO.class) {
                if (instance == null) {
                    instance = new TicketInMemoryDAO();
                }
            }
        }

        return instance;
    }

    @Override
    public List<Ticket> findAllTickets() {
        return listAll();
    }

    @Override
    public List<Ticket> findAllTicketsBySessionId(Integer sessionId) {
        return listAll().stream().filter(item -> item.getSessionId() == sessionId).collect(Collectors.toList());
    }

    @Override
    public Integer purchaseTicket(Ticket ticket) {
        return add(ticket);
    }

    @Override
    public void returnTicket(Ticket ticket) {
        remove(ticket);
    }

    @Override
    public void deleteAllTicketsBySessionId(Integer sessionId) {
        synchronized (TicketInMemoryDAO.class) {
            List<Ticket> toRemove = findAllTicketsBySessionId(sessionId);
            toRemove.forEach(item -> remove(item));
        }
    }


    @Override
    void initKeySequence() {
        if (keySequence == null) {
            synchronized (TicketInMemoryDAO.class) {
                keySequence = 0;
            }
        }
    }

    @Override
    public Integer injectKey(Ticket entity) {
        Integer localKey = -1;
        synchronized (instance) {
            localKey = keySequence++;
            entity.setId(keySequence);
        }
        return localKey;
    }


    @Override
    public Integer getIdByEntity(Ticket entity) {
        return entity.getId();
    }

    @Override
    public List<Ticket> findAllTicketsByUserId(Integer userId) {
        return listAll().stream().filter(item -> item.getBookedByUserId() == userId).collect(Collectors.toList());
    }
}
