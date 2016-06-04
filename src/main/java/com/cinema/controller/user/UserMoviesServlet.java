package com.cinema.controller.user;

import com.cinema.Constants;
import com.cinema.Factories;
import com.cinema.dto.MovieDTO;
import com.cinema.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * \
 */
@WebServlet(urlPatterns = {"/user/movies/*"})
public class UserMoviesServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(UserMoviesServlet.class);
    Factories factories = Factories.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        populateMovieReqAttr(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger = getLogger(this.getClass().getName());
        populateMovieReqAttr(req, resp);


    }

    private void populateMovieReqAttr(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<MovieDTO> movies = null;
        try {
            movies = factories.getMovieService().findAllMovies();
        } catch (ServiceException e) {
            resp.sendError(500, e.getMessage());
        }

        req.setAttribute("movies", movies);
        req.getRequestDispatcher("/resources/jsp/user/movies.jsp").forward(req, resp);
    }


    private void setErrorMessage(HttpServletRequest req, String msg) {
        req.setAttribute("errorMessage", msg);
    }

}
