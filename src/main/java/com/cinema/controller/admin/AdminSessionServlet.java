package com.cinema.controller.admin;

import com.cinema.Constants;
import com.cinema.Factories;
import com.cinema.dto.HallDTO;
import com.cinema.dto.MovieDTO;
import com.cinema.dto.SessionAction;
import com.cinema.dto.SessionDTO;
import com.cinema.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(urlPatterns = {"/admin/sessions/*"})
public class AdminSessionServlet extends HttpServlet {
    Logger logger = getLogger(AdminSessionServlet.class);
    Factories factories = Factories.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().endsWith(Constants.ADMIN_UPDATE_SESSION_URI_SFX)) {
            doGetUpdateSessionForm(req, resp);
            return;
        }

        populateSessionReqAttr(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().endsWith(Constants.ADMIN_DELETE_SESSION_BY_ID_URI_SFX)) {
            processDeleteById(req, resp);
        }

        if (req.getRequestURI().endsWith(Constants.ADMIN_ADD_SESSION_URI_SFX)) {
            processAddSession(req, resp);
        }

        if (req.getRequestURI().endsWith(Constants.ADMIN_UPDATE_SESSION_URI_SFX)) {
            processUpdateSession(req, resp);
        }

        populateSessionReqAttr(req, resp);


    }

    private void populateSessionReqAttr(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SessionDTO> sessionDTOs = null;
        List<MovieDTO> movieDTOs = null;
        List<HallDTO> hallDTOs = null;
        Map<Integer, SessionAction> sessionActions = null;
        try {
            sessionDTOs = factories.getSessionService().findAllSessions();
            hallDTOs = factories.getHallService().findAllHalls();
            movieDTOs = factories.getMovieService().findAllMovies();
            sessionActions = factories.getSessionService().sessions_TO_SessionActions(sessionDTOs);
        } catch (ServiceException e) {
            setErrorMessage(req, e.getMessage());
        }

        req.setAttribute("sessions", sessionDTOs);
        req.setAttribute("sessionActions", sessionActions);
        req.setAttribute("movies", movieDTOs);
        req.setAttribute("halls", hallDTOs);
        req.getRequestDispatcher("/resources/jsp/admin/sessions.jsp").forward(req, resp);
    }

    private void setErrorMessage(HttpServletRequest req, String msg) {
        req.setAttribute("errorMessage", msg);
    }

    private void processAddSession(HttpServletRequest req, HttpServletResponse resp) {
        SessionDTO sessionDTO = new SessionDTO();
        try {
            sessionFormToSessionDTO(req, sessionDTO);

            factories.getSessionService().createSession(sessionDTO);
        } catch (ServiceException se) {
            setErrorMessage(req, "Unable to create session  : " + se.getMessage());
        }


    }

    private void processDeleteById(HttpServletRequest req, HttpServletResponse resp) {
        boolean result = factories.getSessionService().deleteSessionsByStrListIds(req.getParameterValues(Constants.ADMIN_DELETE_SESSION_CHECKBOX_GROUP));
        if (!result) setErrorMessage(req, "can not perform delete operation");

    }

    private void doGetUpdateSessionForm(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer sessionId = Integer.valueOf(req.getParameter(Constants.FORM_PARAM_SESSION_ID));
        SessionDTO sessionDTO = null;
        List<MovieDTO> movieDTOs = null;
        List<HallDTO> hallDTOs = null;
        SessionAction sessionAction = null;
        if (sessionId != null) {
            try {
                sessionDTO = factories.getSessionService().findById(sessionId);
                movieDTOs = factories.getMovieService().findAllMovies();
                hallDTOs = factories.getHallService().findAllHalls();
                sessionAction = factories.getSessionService().getAllowableSessionActions(sessionDTO);
            } catch (ServiceException e) {
                resp.sendError(500, e.getMessage());
                return;
            }

            if (sessionDTO != null) {
                req.setAttribute("session", sessionDTO);
                req.setAttribute("sessionAction", sessionAction);
                req.setAttribute("movies", movieDTOs);
                req.setAttribute("halls", hallDTOs);
                req.getRequestDispatcher("/resources/jsp/admin/updatesession.jsp").forward(req, resp);
            } else {
                setErrorMessage(req, "User Not Found");
                resp.sendRedirect(getServletContext().getContextPath() + "/admin/users");
            }
        }
    }

    private void processUpdateSession(HttpServletRequest req, HttpServletResponse resp) {
        SessionDTO session = new SessionDTO();
        Integer sessionId = Integer.valueOf(req.getParameter(Constants.FORM_PARAM_SESSION_ID));
        if (sessionId != null) {
            try {
                session.setId(sessionId);
                sessionFormToSessionDTO(req, session);
                factories.getSessionService().updateSession(session);
                req.removeAttribute("session");
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }


    private void sessionFormToSessionDTO(HttpServletRequest req, SessionDTO session) throws ServiceException {
        Integer movieId = Integer.valueOf(req.getParameter(Constants.FORM_PARAM_SESSION_MOVIE_ID));
        MovieDTO movieDTO = factories.getMovieService().findMovieById(movieId);
        session.setMovie(movieDTO);

        Integer hallId = Integer.valueOf(req.getParameter(Constants.FORM_PARAM_SESSION_HALL_ID));
        HallDTO hallDTO = factories.getHallService().findHallById(hallId);

        session.setHall(hallDTO);
        LocalDateTime sessionDateTime = LocalDateTime.parse(req.getParameter(Constants.FORM_PARAM_SESSION_DATETIME));
        session.setSessionDateTime(sessionDateTime);
    }

}
