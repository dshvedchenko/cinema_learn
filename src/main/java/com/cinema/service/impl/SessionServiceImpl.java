package com.cinema.service.impl;

import com.cinema.Factories;
import com.cinema.Transformer;
import com.cinema.dao.api.SessionDAO;
import com.cinema.dto.HallDTO;
import com.cinema.dto.SessionAction;
import com.cinema.dto.SessionDTO;
import com.cinema.dto.TicketDTO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import com.cinema.exception.ServiceException;
import com.cinema.model.Session;
import com.cinema.service.api.SessionService;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author dshvedchenko on 28.03.16.
 */
public class SessionServiceImpl implements SessionService {

    private SessionDAO dao = Factories.getInstance().getSessionDao();

    private static SessionService instance;

    private Logger logger = null;

    private SessionServiceImpl() {
        logger = getLogger(this.getClass().getName());
    }

    public static SessionService getInstance() {
        if (instance == null) {
            synchronized (SessionServiceImpl.class) {
                if (instance == null) {
                    instance = new SessionServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<SessionDTO> findAllSessions() throws ServiceException {
        try {
            return Transformer.listSession_TO_SessionDTO(dao.listAll());
        } catch (DAOExceptions daoExceptions) {
            logger.error("findAllSessions", daoExceptions);
            throw new ServiceException("findAllSessions", daoExceptions);
        }
    }

    @Override
    public void createSession(SessionDTO sessionDTO) throws ServiceException {
        Session session = Transformer.sessionDTO_TO_Session(sessionDTO);
        try {
            dao.add(session);
        } catch (DAOExceptions daoExceptions) {
            logger.error("Create Session error", daoExceptions);
            throw new ServiceException("Create Session Error", daoExceptions);
        }
        if (session.getId() != null) {
            sessionDTO.setId(session.getId());
        }

    }

    @Override
    public void deleteSession(Integer sessionId) throws ServiceException {
        try {
            dao.deleteSessionById(sessionId);
        } catch (DAOExceptions daoExceptions) {
            logger.error("deleteMovieById", daoExceptions);
            throw new ServiceException("deleteMovieById", daoExceptions);
        }

    }

    @Override
    public boolean deleteSessionsByStrListIds(String[] strSessionsIds) {
        boolean result = false;
        try {
            Integer[] sessionsIds = Arrays.asList(strSessionsIds).stream().map(item -> Integer.parseInt(item)).toArray(Integer[]::new);
            for (Integer sessionsFormId : sessionsIds) {
                deleteSession(sessionsFormId);
            }
            result = true;
        } catch (ServiceException e) {
            logger.error("can not delete session : ", e);
        }
        return result;
    }

    @Override
    public SessionDTO findById(Integer sessionId) throws ServiceException {
        SessionDTO result = null;
        try {
            result = Transformer.session_TO_SessionDTO(dao.findById(sessionId));
        } catch (DAOExceptions daoExceptions) {
            logger.error("can find session by Id : ", daoExceptions);
            throw new ServiceException("", daoExceptions);
        }
        return result;
    }

    @Override
    /**
     * Set allowable actions for current session
     */
    public SessionAction getAllowableSessionActions(SessionDTO sessionDTO) {
        SessionAction sessionAction = new SessionAction();
        sessionAction.setId(sessionDTO.getId());
        sessionAction.setRemovable(detectSessionRemovable(sessionDTO));
        sessionAction.setActive(detectSessionActive(sessionDTO));
        return sessionAction;
    }

    /**
     * Allow to remove session if no tickets sold
     *
     * @param sessionDTO
     * @return
     */
    private boolean detectSessionRemovable(SessionDTO sessionDTO) {
        return sessionDTO.getTickets().size() == 0;
    }

    /**
     * Set session is active for ticket booking if not in past and not all tickets sold
     *
     * @param sessionDTO
     * @return
     */
    private boolean detectSessionActive(SessionDTO sessionDTO) {
        return sessionDTO.getSessionDateTime().compareTo(LocalDateTime.now()) >= 0 && sessionDTO.getTickets().size() < sessionDTO.getHall().getSeatCols() * sessionDTO.getHall().getSeatRows();
    }

    @Override
    public Map<Integer, SessionAction> sessions_TO_SessionActions(List<SessionDTO> sessionDTOs) {
        return sessionDTOs.stream().map(it -> getAllowableSessionActions(it)).collect(Collectors.toMap(SessionAction::getId, it -> it));
    }

    @Override
    public List<SessionDTO> findSessionsForMovieId(Integer movieId) throws ServiceException {
        try {
            return Transformer.listSession_TO_SessionDTO(dao.findByMovieId(movieId));
        } catch (DAOExceptions daoExceptions) {
            logger.error("findSessionsForMovieId", daoExceptions);
            throw new ServiceException("findSessionsForMovieId", daoExceptions);
        } catch (ServiceException e) {
            logger.error("findSessionsForMovieId", e);
            throw new ServiceException("findSessionsForMovieId", e);
        } catch (IllegalOperationException e) {
            logger.error("findSessionsForMovieId", e);
            throw new ServiceException("findSessionsForMovieId", e);
        }
    }

    @Override
    public void updateSession(SessionDTO session) throws ServiceException {
        List<TicketDTO> tickets = Factories.getInstance().getTicketService().findAllTicketsBySession(session);
        checkIfNewSessionHallSuitableForTicketsSold(tickets, session.getHall());
        dao.update(Transformer.sessionDTO_TO_Session(session));
    }

    private void checkIfNewSessionHallSuitableForTicketsSold(List<TicketDTO> tickets, HallDTO hall) throws ServiceException {
        if (tickets.stream().filter(it -> it.getRow() > hall.getSeatRows() || it.getSeat() > hall.getSeatCols()).findFirst().isPresent())
            throw new ServiceException("There is tickets which not suitable for new Hall. update declined");
    }


}
