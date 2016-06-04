package com.cinema.service.api;

import java.util.List;

import com.cinema.dto.SessionDTO;
import com.cinema.dto.TicketDTO;
import com.cinema.dto.UserDTO;
import com.cinema.exception.ServiceException;

/**
 * Created by dshvedchenko on 28.03.16.
 */
public interface TicketService {
    List<TicketDTO> findAllTicketsBySession(SessionDTO sessionDTO) throws ServiceException;

    void purchaseTicket(TicketDTO place, UserDTO user, SessionDTO session) throws ServiceException;

    void returnTicketById(Integer id) throws ServiceException;

    List<TicketDTO> findAllTicketsByUser(UserDTO user) throws ServiceException;

    List<TicketDTO> findAllNonExpiredTicketsByUser(UserDTO user) throws ServiceException;

    List<TicketDTO> findAllTicketsByUserId(Integer userId) throws ServiceException;

    List<TicketDTO> convertCheckboxIdsToPlaces(String[] checkboxIds);

    void returnTicketsByStringIds(String[] ticketIds) throws ServiceException;
}
