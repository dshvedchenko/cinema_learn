package com.cinema.controller.user;

import com.cinema.Constants;
import com.cinema.Factories;
import com.cinema.dto.*;
import com.cinema.exception.ServiceException;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(urlPatterns = {"/user/session/*"})
public class UserSessionServlet extends HttpServlet {
    Logger logger = getLogger(UserSessionServlet.class);
    Factories factories = Factories.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if (getSessionIdFromRequest(req) == null) {
            setErrorMessage(req, " requires session id");
        } else {
            populateSessionReqAttr(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().endsWith(Constants.BUY_TICKET_ON_SEESION_URI_SFX)) {
            processBuyTicketRequest(req, resp);
        }

        populateSessionReqAttr(req, resp);

    }

    private void processBuyTicketRequest(HttpServletRequest req, HttpServletResponse resp) {
        String[] selectedPlaces = req.getParameterValues(Constants.BUY_TICKET_CHECKBOX);
        SessionDTO sessionDTO = null;
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        try {
            sessionDTO = getSession(req);
            processPurcahsingAllSelectedPlaces(selectedPlaces, userDTO, sessionDTO);
        } catch (ServiceException se) {
        }

    }

    private void populateSessionReqAttr(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionDTO sessionDTO = null;
        SessionAction sessionAction = null;
        List<TicketDTO> ticketDTOs;
        boolean[][] bookedPlaces = null;
        try {
            sessionDTO = getSession(req);
            sessionAction = factories.getSessionService().getAllowableSessionActions(sessionDTO);
            ticketDTOs = sessionDTO.getTickets();
            bookedPlaces = getBookedPlaces(ticketDTOs, sessionDTO.getHall());

            req.setAttribute("session", sessionDTO);
            req.setAttribute("sessionAction", sessionAction);
            req.setAttribute("bookedPlaces", bookedPlaces);

        } catch (ServiceException e) {
            setErrorMessage(req, e.getMessage());
        }


        req.getRequestDispatcher("/resources/jsp/user/session.jsp").forward(req, resp);
    }

    private boolean[][] getBookedPlaces(List<TicketDTO> ticketDTOs, HallDTO hall) {
        boolean[][] result = new boolean[hall.getSeatRows()][hall.getSeatCols()];
        ticketDTOs.stream().forEach(it -> result[it.getRow() - 1][it.getSeat() - 1] = true);
        return result;
    }

    private void setErrorMessage(HttpServletRequest req, String msg) {
        req.setAttribute("errorMessage", msg);
    }

    private Integer getSessionIdFromRequest(HttpServletRequest req) {
        return Integer.valueOf(req.getParameter("id"));
    }


    private SessionDTO getSession(HttpServletRequest req) throws ServiceException {
        SessionDTO result = null;
        Integer sessionId = getSessionIdFromRequest(req);
        if (sessionId != null) {
            result = factories.getSessionService().findById(sessionId);
        }
        return result;
    }

    private boolean processPurcahsingAllSelectedPlaces(String[] selectedPlaces, UserDTO user, SessionDTO session) throws ServiceException {
        List<TicketDTO> tickets = factories.getTicketService().convertCheckboxIdsToPlaces(selectedPlaces);
        for (TicketDTO ticket : tickets) {
            factories.getTicketService().purchaseTicket(ticket, user, session);
        }
        return true;
    }
}
