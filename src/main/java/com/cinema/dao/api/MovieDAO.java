package com.cinema.dao.api;

import com.cinema.exception.DAOExceptions;
import com.cinema.model.Movie;

import java.util.List;


public interface MovieDAO extends GenericDao<Movie, Integer> {

    List<Movie> getAllMovies() throws DAOExceptions;

    void createMovie(Movie entity);

    void deleteMovieById(Integer id) throws DAOExceptions;

    void deleteMovie(Movie entity) throws DAOExceptions;

    List<Movie> findAllByTitle(String title) throws DAOExceptions;
}
