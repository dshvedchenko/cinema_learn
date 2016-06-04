package com.cinema.controller.user;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by dshvedchenko on 17.04.16.
 */
public class RegisterActionServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/resources/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RegisterBean registerBean = new RegisterBean(req);

        if (validateRequest(req, registerBean)) {

            LocalDate birthDate = LocalDate.parse(registerBean.strBirthDate);
            UserDTO udto = new UserDTO(null, registerBean.login, registerBean.password, registerBean.firstName, registerBean.lastName, birthDate, UserRole.REGULAR, registerBean.email);

            Factories factories = Factories.getInstance();
            try {
                factories.getUserService().createUser(udto);
                HttpSession session = req.getSession();
                session.setAttribute("user", udto);
                req.getRequestDispatcher("/resources/jsp/home.jsp").forward(req, resp);

            } catch (ServiceException e) {
                req.setAttribute("errorMessage", e.getMessage());
                e.printStackTrace();
                req.getRequestDispatcher("/resources/jsp/register.jsp").forward(req, resp);
            }


        } else {
            req.getRequestDispatcher("/resources/jsp/register.jsp").forward(req, resp);
        }


    }

    /**
     * Validate registration parameters and set validation error messages
     *
     * @param req
     * @param registerBean
     * @return
     */
    private boolean validateRequest(HttpServletRequest req, RegisterBean registerBean) {
        boolean result = true;

        if (!registerBean.password.equals(registerBean.password_c)) {
            result = false;
            req.setAttribute("passwordMismatch", "Passwords do not match");
        }

        if (registerBean.strBirthDate == null) {
            result = false;
            req.setAttribute("birthDateMessage", "BirthDate have to be specified");
        } else {
//            LocalDate.parse(registerBean.strBirthDate);
        }

        return result;
    }

    class RegisterBean {
        String firstName;
        String lastName;
        String email;
        String login;
        String password;
        String password_c;
        String strBirthDate;

        RegisterBean(HttpServletRequest req) {
            this.firstName = req.getParameter("firstName");
            this.lastName = req.getParameter("lastName");
            this.email = req.getParameter("email");
            this.login = req.getParameter("login");
            this.password = req.getParameter("password");
            this.password_c = req.getParameter("password_c");
            this.strBirthDate = req.getParameter("birthDate");
        }

    }
}
