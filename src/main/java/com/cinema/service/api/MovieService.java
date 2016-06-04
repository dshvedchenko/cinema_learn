package com.cinema.service.api;

import com.cinema.dto.MovieDTO;
import com.cinema.exception.ServiceException;
import com.cinema.model.Movie;

import java.util.List;

/**
 * @author dshvedchenko
 */
public interface MovieService {

    /**
     * Provide full list of movies
     *
     * @return
     */
    List<MovieDTO> findAllMovies() throws ServiceException;

    MovieDTO findMovieById(Integer id) throws ServiceException;

    void createMovie(MovieDTO movie);

    void deleteMovieById(Integer id) throws ServiceException;

    void deleteMovie(MovieDTO movie) throws ServiceException;

    void updateMovie(MovieDTO movie);

    List<MovieDTO> findMoviesByTitle(String title) throws ServiceException;

    void clearMovies() throws ServiceException;

    /**
     * Accept array of string ids representation and perform delete from DB
     *
     * @param strMovieIds
     * @return
     */
    boolean deleteMoviesByStrListIds(String[] strMovieIds);

}
