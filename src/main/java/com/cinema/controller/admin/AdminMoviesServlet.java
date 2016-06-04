package com.cinema.controller.admin;

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
 * servlet for implementing movie operation from admin perspective
 * 1. show movies in cinema
 * 2. add movie
 * 3. edit movie
 * 4. delete movie
 */
@WebServlet(urlPatterns = {"/admin/movies/*"})
public class AdminMoviesServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(AdminMoviesServlet.class);
    Factories factories = Factories.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().endsWith(Constants.ADMIN_UPDATE_MOVIE_URI_SFX)) {
            doGetUpdateMovieForm(req, resp);
            return;
        }

        populateMovieReqAttr(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger = getLogger(this.getClass().getName());


        if (req.getRequestURI().endsWith(Constants.ADMIN_DELETE_MOVIE_BY_ID_URI_SFX)) {
            processDeleteById(req, resp);
        }

        if (req.getRequestURI().endsWith(Constants.ADMIN_ADD_MOVIE_URI_SFX)) {
            processAddMovie(req, resp);
        }

        if (req.getRequestURI().endsWith(Constants.ADMIN_UPDATE_MOVIE_URI_SFX)) {
            processUpdateMovie(req, resp);
        }

        populateMovieReqAttr(req, resp);


    }

    private void processUpdateMovie(HttpServletRequest req, HttpServletResponse resp) {
        MovieDTO movie = new MovieDTO();
        Integer movieId = Integer.valueOf(req.getParameter(Constants.FORM_PARAM_MOVIE_ID));
        if (movieId != null) {
            movie.setId(movieId);
            movieFormToMovieDTO(req, movie);
            factories.getMovieService().updateMovie(movie);
            req.removeAttribute("movie");
        }
    }

    private void populateMovieReqAttr(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<MovieDTO> movies = null;
        try {
            movies = factories.getMovieService().findAllMovies();
        } catch (ServiceException e) {
            resp.sendError(500, e.getMessage());
        }

        req.setAttribute("movies", movies);
        req.getRequestDispatcher("/resources/jsp/admin/movies.jsp").forward(req, resp);
    }

    private void processAddMovie(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MovieDTO movie = new MovieDTO();
        movieFormToMovieDTO(req, movie);

        factories.getMovieService().createMovie(movie);

        if (movie.getId() == null) {
            resp.sendError(500, "Troubles in create Movie");
        }
    }

    private void movieFormToMovieDTO(HttpServletRequest req, MovieDTO movie) {
        movie.setTitle(req.getParameter(Constants.FORM_PARAM_MOVIE_TITLE));
        movie.setDescription(req.getParameter(Constants.FORM_PARAM_MOVIE_DESCRIPTION));
        movie.setDuration(Integer.valueOf(req.getParameter(Constants.FORM_PARAM_MOVIE_DURATION)));
    }

    private void processDeleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean result = factories.getMovieService().deleteMoviesByStrListIds(req.getParameterValues(Constants.ADMIN_DELETE_MOVIE_CHECKBOX_GROUP));
        if (!result) setErrorMessage(req, "can not perform delete operation");
    }

    private void doGetUpdateMovieForm(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Integer movieId = Integer.valueOf(req.getParameter("id"));
        if (movieId != null) {
            MovieDTO movie = null;
            try {
                movie = factories.getMovieService().findMovieById(movieId);
            } catch (ServiceException e) {
                resp.sendError(500, "Troubles in prepare update movie form");
            }
            if (movie != null) {
                req.setAttribute("movie", movie);
                req.getRequestDispatcher("/resources/jsp/admin/updatemovie.jsp").forward(req, resp);
            } else {
                setErrorMessage(req, "Movie Not Found");
                resp.sendRedirect(getServletContext().getContextPath() + "/admin/movies");
            }
        } else {
            resp.sendError(500, "Specify id parameter");
        }
    }

    private void setErrorMessage(HttpServletRequest req, String msg) {
        req.setAttribute("errorMessage", msg);
    }

}
