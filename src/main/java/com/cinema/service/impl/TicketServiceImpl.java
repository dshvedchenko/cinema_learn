package com.cinema.service.impl;

import com.cinema.Factories;
import com.cinema.Transformer;
import com.cinema.dao.api.TicketDAO;
import com.cinema.dto.SessionDTO;
import com.cinema.dto.TicketDTO;
import com.cinema.dto.UserDTO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.ServiceException;
import com.cinema.model.Ticket;
import com.cinema.service.api.TicketService;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by dshvedchenko on 03.04.16.
 */
public class TicketServiceImpl implements TicketService {

    Logger logger = null;

    private static TicketDAO dao = Factories.getInstance().getTicketDoa();

    private static TicketService instance;

    private TicketServiceImpl() {
        logger = getLogger(this.getClass().getName());
    }

    public static TicketService getInstance() {
        if (instance == null) {
            synchronized (SessionServiceImpl.class) {
                if (instance == null) {
                    instance = new TicketServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<TicketDTO> findAllTicketsBySession(SessionDTO sessionDTO) throws ServiceException {
        try {
            return Transformer.listTickets_TO_TicketDTO_ResolvingReferences(dao.findAllTicketsBySessionId(sessionDTO.getId()));
        } catch (DAOExceptions daoExceptions) {
            logger.error("findAllTicket By Session ", daoExceptions);
            throw new ServiceException("findAllTicket", daoExceptions);
        }
    }

    //TODO
    @Override
    public void purchaseTicket(TicketDTO ticket, UserDTO user, SessionDTO session) throws ServiceException {
        if (user == null) throw new ServiceException("User can not be null");
        if (session == null) throw new ServiceException("Session can not be null");
        ticket.setBookedByUser(user);
        ticket.setSession(session);
        dao.purchaseTicket(Transformer.ticketDTO_TO_ticket(ticket));
    }


    @Override
    public void returnTicketById(Integer id) throws ServiceException {
        try {
            TicketDTO ticketDTO = Transformer.ticket_TO_ticketDTO_ResolvingReferences(dao.findById(id));
            if (ticketDTO.getSession().getSessionDateTime().isAfter(LocalDateTime.now())) {
                dao.removeById(id);
            } else {
                throw new ServiceException("can not return tickets for past Session");
            }
        } catch (DAOExceptions daoExceptions) {
            logger.error("returnTicket", daoExceptions);
            throw new ServiceException("returnTicket", daoExceptions);
        }
    }

    @Override
    public List<TicketDTO> findAllTicketsByUser(UserDTO user) throws ServiceException {
        return findAllTicketsByUserId(user.getId());
    }

    @Override
    public List<TicketDTO> findAllNonExpiredTicketsByUser(UserDTO user)
            throws ServiceException {
        return findAllTicketsByUser(user).stream().filter(ticket -> ticket.getSession().getSessionDateTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());

    }

    @Override
    public List<TicketDTO> findAllTicketsByUserId(Integer userId) throws ServiceException {
        try {
            return Transformer.listTickets_TO_TicketDTO_ResolvingReferences(dao.findAllTicketsByUserId(userId));
        } catch (DAOExceptions daoExceptions) {
            logger.error("returnTicket", daoExceptions);
            throw new ServiceException("returnTicket", daoExceptions);
        }
    }

    @Override
    public List<TicketDTO> convertCheckboxIdsToPlaces(String[] checkboxIds) {
        List<TicketDTO> ticketsDTOs = new ArrayList();
        ticketsDTOs = Arrays.stream(checkboxIds).map(it -> TicketDTO.of(it)).collect(Collectors.toList());
        return ticketsDTOs;
    }

    public void returnTicketsByStringIds(String[] inTicketdIds) throws ServiceException {
        try {
            Integer[] ticketIds = Arrays.asList(inTicketdIds).stream().map(item -> Integer.parseInt(item)).toArray(Integer[]::new);
            for (Integer ticketId : ticketIds) {
                returnTicketById(ticketId);
            }
        } catch (ServiceException e) {
            logger.error("can not delete movie : ", e);
            throw e;
        }
    }

}
