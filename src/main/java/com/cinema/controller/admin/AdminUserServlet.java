package com.cinema.controller.admin;

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


@WebServlet(urlPatterns = {"/admin/users/*"})
public class AdminUserServlet extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(AdminUserServlet.class);
    Factories factories = Factories.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().endsWith(Constants.ADMIN_UPDATE_USER_URI_SFX)) {
            doGetUpdateUsersForm(req, resp);
            return;
        }

        populateUsersReqAttr(req, resp);

    }

    private void doGetUpdateUsersForm(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer id = Integer.valueOf(req.getParameter(Constants.FORM_PARAM_USER_ID));
        UserDTO user = null;
        if (id != null) {
            try {
                user = factories.getUserService().findUserById(id);
            } catch (ServiceException e) {
                resp.sendError(500, e.getMessage());
                return;
            }

            if (user != null) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("/resources/jsp/admin/updateuser.jsp").forward(req, resp);
            } else {
                setErrorMessage(req, "User Not Found");
                resp.sendRedirect(getServletContext().getContextPath() + "/admin/users");
            }
        }

    }

    private void setErrorMessage(HttpServletRequest req, String msg) {
        req.setAttribute("errorMessage", msg);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger = getLogger(this.getClass().getName());


        if (req.getRequestURI().endsWith(Constants.ADMIN_DELETE_USER_BY_ID_URI_SFX)) {
            processDeleteById(req, resp);
        }

        if (req.getRequestURI().endsWith(Constants.ADMIN_ADD_USER_URI_SFX)) {
            processAddUser(req, resp);
        }

        if (req.getRequestURI().endsWith(Constants.ADMIN_UPDATE_USER_URI_SFX)) {
            processUpdateUser(req, resp);
        }

        populateUsersReqAttr(req, resp);
    }

    private void populateUsersReqAttr(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<UserDTO> users = null;
        try {
            users = factories.getUserService().findAllUsers();
        } catch (ServiceException e) {
            setErrorMessage(req, e.getMessage());
        }

        req.setAttribute("users", users);
        req.getRequestDispatcher("/resources/jsp/admin/users.jsp").forward(req, resp);
    }

    private void processUpdateUser(HttpServletRequest req, HttpServletResponse resp) {
        UserDTO user = new UserDTO();
        Integer userId = Integer.valueOf(req.getParameter(Constants.FORM_PARAM_USER_ID));
        user.setId(userId);
        userFormToUserDTO(req, user);

        try {
            factories.getUserService().updateUserByAdmin(user);
        } catch (ServiceException e) {
            setErrorMessage(req, "Troubles in Update User" + e.getMessage());
        }

    }

    private void processAddUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDTO user = new UserDTO();
        userFormToUserDTO(req, user);

        try {
            factories.getUserService().createUser(user);
        } catch (ServiceException e) {
            setErrorMessage(req, "Troubles in create User" + e.getMessage());

        }

    }

    private void processDeleteById(HttpServletRequest req, HttpServletResponse resp) {
        boolean result = factories.getUserService().deleteUsersByStrListIds(req.getParameterValues(Constants.ADMIN_DELETE_SESSION_CHECKBOX_GROUP));
        if (!result) setErrorMessage(req, "can not perform delete operation");
    }

    private void userFormToUserDTO(HttpServletRequest req, UserDTO user) {
        user.setLogin(req.getParameter(Constants.FORM_PARAM_USER_LOGIN));
        user.setPassword(req.getParameter(Constants.FORM_PARAM_USER_PASSWORD));
        user.setFirstName(req.getParameter(Constants.FORM_PARAM_USER_FIRSTNAME));
        user.setLastName(req.getParameter(Constants.FORM_PARAM_USER_LASTNAME));
        user.setBirthDate(LocalDate.parse(req.getParameter(Constants.FORM_PARAM_USER_BIRTHDATE)));
        user.setRole(UserRole.valueOf(req.getParameter(Constants.FORM_PARAM_USER_ROLE)));
        user.setEmail(req.getParameter(Constants.FORM_PARAM_USER_EMAIL));

    }
}
