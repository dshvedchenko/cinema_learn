package com.cinema.controller.user;

import com.cinema.Constants;
import com.cinema.Factories;
import com.cinema.dto.UserDTO;
import com.cinema.dto.UserRole;
import com.cinema.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


@WebServlet(urlPatterns = {"/user/profile/*"})
public class UserProfileServlet extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(UserProfileServlet.class);
    Factories factories = Factories.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        populateUsersReqAttr(req, resp);
    }


    private void setErrorMessage(HttpServletRequest req, String msg) {
        req.setAttribute("errorMessage", msg);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger = getLogger(this.getClass().getName());

        if (req.getRequestURI().endsWith(Constants.USER_RETURN_TICKETS_URI_SFX)) {
            try {
                processReturnTickets(req, req);
            } catch (ServiceException e) {
                setErrorMessage(req, "cat not remove some tickets : " + e.getMessage());
            }
        }

        populateUsersReqAttr(req, resp);

    }

    private void processReturnTickets(HttpServletRequest req, HttpServletRequest req1) throws ServiceException {
        String[] ticketsToReturn = req.getParameterValues(Constants.USER_RETURN_TICKETS_CHECKBOX);
        factories.getTicketService().returnTicketsByStringIds(ticketsToReturn);
    }

    private void populateUsersReqAttr(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserDTO user = null;
        try {
            UserDTO sessionUser = (UserDTO) req.getSession().getAttribute("user");
            user = factories.getUserService().findUserById(sessionUser.getId());
            user.setTickets(factories.getTicketService().findAllNonExpiredTicketsByUser(user));
        } catch (ServiceException e) {
            setErrorMessage(req, e.getMessage());
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("/resources/jsp/user/profile.jsp").forward(req, resp);
    }

}
