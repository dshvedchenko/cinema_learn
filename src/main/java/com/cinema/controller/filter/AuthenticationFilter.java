package com.cinema.controller.filter;

import com.cinema.dto.UserDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dshvedchenko on 13.04.16.
 */
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::" + uri);

        HttpSession session = req.getSession(false);
        UserDTO user = null;
        if (session != null) {
            user = (UserDTO) session.getAttribute("user");
        }

        if (user == null && !(uri.endsWith("html") || uri.endsWith("LoginServlet"))) {
            this.context.log("Unauthorized access request");
            res.sendRedirect(req.getContextPath() + "/resources/jsp/login.jsp");
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }


    }


    public void destroy() {
        //close any resources here
    }

}