package com.cinema.service.api;

import com.cinema.dto.SessionAction;
import com.cinema.dto.SessionDTO;
import com.cinema.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by dshvedchenko on 21.03.16.
 */
public interface SessionService {
    List<SessionDTO> findAllSessions() throws ServiceException;

    void createSession(SessionDTO session) throws ServiceException;

    void updateSession(SessionDTO session) throws ServiceException;

    void deleteSession(Integer sessionId) throws ServiceException;

    boolean deleteSessionsByStrListIds(String[] parameterValues);

    SessionDTO findById(Integer sessionId) throws ServiceException;

    SessionAction getAllowableSessionActions(SessionDTO sessionDTO);

    Map<Integer, SessionAction> sessions_TO_SessionActions(List<SessionDTO> sessionDTOs);

    List<SessionDTO> findSessionsForMovieId(Integer movieId) throws ServiceException;
}
