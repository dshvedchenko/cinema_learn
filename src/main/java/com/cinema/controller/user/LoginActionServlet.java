package com.cinema.controller.user;

/**
 * Created by dshvedchenko on 15.04.16.
 */

import com.cinema.Factories;
import com.cinema.dto.UserDTO;
import com.cinema.dto.UserRole;
import com.cinema.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginActionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/resources/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Factories factories = Factories.getInstance();
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDTO user = null;
        try {
            user = factories.getUserService().findUserByLoginPassword(username, password);
        } catch (ServiceException e) {

        }

        if (user != null) {

            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", user.getRole() == UserRole.ADMIN);

            req.getRequestDispatcher("/resources/jsp/home.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorMessage", "Password is wrong or user not exists !");
            req.getRequestDispatcher("/resources/jsp/login.jsp").forward(req, resp);
        }

    }
}
