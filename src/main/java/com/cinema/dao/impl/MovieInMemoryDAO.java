package com.cinema.dao.impl;

import com.cinema.dao.api.MovieDAO;
import com.cinema.model.Movie;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dshvedchenko on 25.03.16.
 */
public class MovieInMemoryDAO extends InMemoryDao<Movie, Integer> implements MovieDAO {


    private volatile static MovieDAO instance;


    public static MovieDAO getInstance() {
        if (instance == null) {
            synchronized (HallInMemoryDAO.class) {
                if (instance == null) {
                    instance = new MovieInMemoryDAO();
                }
            }
        }

        return instance;
    }

    @Override
    public List<Movie> getAllMovies() {
        return listAll();
    }

    @Override
    public void createMovie(Movie entity) {
        add(entity);
    }

    @Override
    public void deleteMovieById(Integer id) {
        remove(findById(id));
    }

    @Override
    public void deleteMovie(Movie entity) {
        remove(entity);
    }

    @Override
    public List<Movie> findAllByTitle(String title) {
        return listAll().stream().filter(movie -> movie.getTitle().contains(title)).collect(Collectors.toList());
    }


    @Override
    void initKeySequence() {
        if (keySequence == null) {
            synchronized (MovieInMemoryDAO.class) {
                keySequence = 0;
            }
        }
    }


    @Override
    public Integer getIdByEntity(Movie entity) {
        return entity.getId();
    }

    @Override
    public Integer injectKey(Movie entity) {
        Integer localKey = -1;
        synchronized (instance) {
            localKey = keySequence++;
            entity.setId(keySequence);
        }
        return localKey;
    }
}
