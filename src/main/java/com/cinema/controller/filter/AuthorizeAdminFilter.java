package com.cinema.controller.filter;

import com.cinema.dto.UserDTO;
import com.cinema.dto.UserRole;
import com.cinema.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dshvedchenko on 13.04.16.
 */
public class AuthorizeAdminFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AutzAdminFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        boolean allowAdmin = false;

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::" + uri);

        HttpSession session = req.getSession(false);

        if (session != null) {
            UserDTO user = (UserDTO) req.getSession(false).getAttribute("user");
            if (user != null && user.getRole() == UserRole.ADMIN) {
                allowAdmin = true;
            }
        }


        if (allowAdmin) {
            chain.doFilter(request, response);
        } else {
            this.context.log("Unauthorized access request");
            res.sendRedirect(req.getContextPath() + "/resources/jsp/home.jsp");
        }

    }


    public void destroy() {
        //close any resources here
    }

}