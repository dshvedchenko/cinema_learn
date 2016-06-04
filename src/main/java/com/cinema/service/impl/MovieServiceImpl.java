package com.cinema.service.impl;

import com.cinema.Factories;
import com.cinema.Transformer;
import com.cinema.dao.api.MovieDAO;
import com.cinema.dto.MovieDTO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import com.cinema.exception.ServiceException;
import com.cinema.model.Movie;
import com.cinema.service.api.MovieService;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by dshvedchenko on 14.03.16.
 */
public class MovieServiceImpl implements MovieService {

    Logger logger = null;

    private MovieDAO dao = Factories.getInstance().getMovieDao();

    private static volatile MovieService instance;


    private MovieServiceImpl() {
        logger = getLogger(this.getClass().getName());
    }

    public static MovieService getInstance() {
        if (instance == null) {
            synchronized (MovieServiceImpl.class) {
                if (instance == null) {
                    instance = new MovieServiceImpl();
                }
            }
        }

        return instance;
    }

    @Override
    public List<MovieDTO> findAllMovies() throws ServiceException {
        try {
            return Transformer.listMovie_To_ListMovieDTO(dao.getAllMovies());
        } catch (DAOExceptions daoExceptions) {
            logger.error("findAllMovies", daoExceptions);
            throw new ServiceException("findAllMovies", daoExceptions);
        } catch (ServiceException e) {
            logger.error("findAllMovies", e);
            throw new ServiceException("findAllMovies", e);
        }
    }

    @Override
    public MovieDTO findMovieById(Integer id) throws ServiceException {
        MovieDTO movie = null;
        try {
            movie = Transformer.movie_To_MovieDTO(dao.findById(id), true);
        } catch (ServiceException e) {
            logger.error("findMovieById", e);
            throw new ServiceException("findMovieById", e);
        } catch (DAOExceptions daoExceptions) {
            logger.error("findMovieById", daoExceptions);
            throw new ServiceException("findMovieById", daoExceptions);
        }

        return movie;
    }

    @Override
    public void createMovie(MovieDTO movie) {
        Movie me = Transformer.movieDTO_To_Movie(movie);
        dao.createMovie(me);
        if (me.getId() != null) {
            movie.setId(me.getId());
        }
    }

    @Override
    public void deleteMovieById(Integer id) throws ServiceException {
        try {
            dao.deleteMovieById(id);
        } catch (DAOExceptions daoExceptions) {
            logger.error("deleteMovieById", daoExceptions);
            throw new ServiceException("deleteMovieById", daoExceptions);
        }
    }

    @Override
    public void deleteMovie(MovieDTO movie) throws ServiceException {
        try {

            dao.deleteMovie(Transformer.movieDTO_To_Movie(movie));
        } catch (DAOExceptions daoExceptions) {
            logger.error("deleteMovie", daoExceptions);
            throw new ServiceException("deleteMovie", daoExceptions);
        }
    }

    @Override
    public void updateMovie(MovieDTO movie) {
        dao.update(Transformer.movieDTO_To_Movie(movie));
    }

    @Override
    public void clearMovies() throws ServiceException {
        try {
            dao.removeAll();
        } catch (DAOExceptions daoExceptions) {
            logger.error("clearMovies", daoExceptions);
            throw new ServiceException("clearMovies", daoExceptions);
        }
    }

    @Override
    public List<MovieDTO> findMoviesByTitle(String title) throws ServiceException {
        List<Movie> movies = null;
        List<MovieDTO> mdto = null;
        try {
            movies = dao.findAllByTitle(title);
        } catch (DAOExceptions daoExceptions) {
            logger.error("findMoviesByTitle", daoExceptions);
            throw new ServiceException("findMoviesByTitle", daoExceptions);
        }
        if (movies != null) try {
            mdto = Transformer.listMovie_To_ListMovieDTO(movies);
        } catch (DAOExceptions daoExceptions) {
            logger.error("findMoviesByTitle", daoExceptions);
            throw new ServiceException("findMoviesByTitle", daoExceptions);
        } catch (ServiceException e) {
            logger.error("findMoviesByTitle", e);
            throw new ServiceException("findMoviesByTitle", e);
        }
        return mdto;
    }


    public boolean deleteMoviesByStrListIds(String[] strMovieIds) {
        boolean result = false;
        try {
            Integer[] movieIds = Arrays.asList(strMovieIds).stream().map(item -> Integer.parseInt(item)).toArray(Integer[]::new);
            for (Integer movieFormId : movieIds) {
                deleteMovieById(movieFormId);
            }
            result = true;
        } catch (ServiceException e) {
            logger.error("can not delete movie : ", e);
        }
        return result;
    }

}
