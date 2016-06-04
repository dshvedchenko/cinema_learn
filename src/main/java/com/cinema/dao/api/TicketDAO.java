package com.cinema.dao.api;

import com.cinema.exception.DAOExceptions;
import com.cinema.model.Ticket;

import java.util.List;

/**
 * Created by dshvedchenko on 21.03.16.
 */
public interface TicketDAO extends GenericDao<Ticket, Integer> {

    List<Ticket> findAllTickets() throws DAOExceptions;

    List<Ticket> findAllTicketsBySessionId(Integer sessionId) throws DAOExceptions;

    List<Ticket> findAllTicketsByUserId(Integer userId) throws DAOExceptions;

    Integer purchaseTicket(Ticket ticket);

    void returnTicket(Ticket ticket) throws DAOExceptions;

    void deleteAllTicketsBySessionId(Integer sessionId) throws DAOExceptions;


}
