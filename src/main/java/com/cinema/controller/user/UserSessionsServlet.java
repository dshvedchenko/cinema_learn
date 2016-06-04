package com.cinema.controller.user;

import com.cinema.Constants;
import com.cinema.Factories;
import com.cinema.dto.HallDTO;
import com.cinema.dto.MovieDTO;
import com.cinema.dto.SessionAction;
import com.cinema.dto.SessionDTO;
import com.cinema.exception.ServiceException;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(urlPatterns = {"/user/sessions/*"})
public class UserSessionsServlet extends HttpServlet {
    Logger logger = getLogger(UserSessionsServlet.class);
    Factories factories = Factories.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().endsWith(Constants.USER_MOVIE_SESSIONS_URI_SFX)) {
            Integer movieIdForSessions = Integer.valueOf(req.getParameter("id"));
            req.setAttribute("movieIdForSessions", movieIdForSessions);
        }

        populateSessionReqAttr(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        populateSessionReqAttr(req, resp);

    }

    private void populateSessionReqAttr(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SessionDTO> sessionDTOs = null;
        List<MovieDTO> movieDTOs = null;
        List<HallDTO> hallDTOs = null;
        Map<Integer, SessionAction> sessionActions = null;
        try {
            sessionDTOs = getSessions(req);
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
        req.getRequestDispatcher("/resources/jsp/user/sessions.jsp").forward(req, resp);
    }

    private void setErrorMessage(HttpServletRequest req, String msg) {
        req.setAttribute("errorMessage", msg);
    }


    private List<SessionDTO> getSessions(HttpServletRequest req) throws ServiceException {
        List<SessionDTO> result = null;
        Integer movieIdForSessions = (Integer) req.getAttribute("movieIdForSessions");
        if (movieIdForSessions != null) {
            result = factories.getSessionService().findSessionsForMovieId(movieIdForSessions);
        } else {
            result = factories.getSessionService().findAllSessions();
        }
        return result;
    }
}
