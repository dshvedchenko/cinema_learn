package com.cinema.dao.impl;

import com.cinema.Factories;
import com.cinema.dao.api.MovieDAO;
import com.cinema.dao.api.SessionDAO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import com.cinema.model.Movie;
import com.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by dshvedchenko on 11.04.16.
 */
public class MovieDBDAO extends GenericDBDao<Movie, Integer> implements MovieDAO {


    private volatile static MovieDAO instance;
    private SessionDAO sessionDAO = null;

    private final String FIND_BY_TITLE = "SELECT * FROM %s where %s like ?";

    public static MovieDAO getInstance() {
        if (instance == null) {
            synchronized (UserDBDAO.class) {
                if (instance == null) {

                    instance = new MovieDBDAO(Movie.class);
                }
            }
        }

        return instance;
    }


    public MovieDBDAO(Class<Movie> classs) {
        super(classs);

    }

    @Override
    public List<Movie> getAllMovies() throws DAOExceptions {
        return listAll();
    }

    @Override
    public void createMovie(Movie entity) {
        try {
            add(entity);
        } catch (DAOExceptions daoExceptions) {
            logger.error("Gte All Movies Error", daoExceptions);
        }
    }

    @Override
    public void deleteMovieById(Integer id) throws DAOExceptions {
        remove(findById(id));
    }

    @Override
    public void deleteMovie(Movie entity) throws DAOExceptions {
        remove(entity);
    }

    @Override
    public List<Movie> findAllByTitle(String title) throws DAOExceptions {
        List<Movie> movies = null;


        String sql = String.format(FIND_BY_TITLE, getTableName(), "title");

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Create Movie Error", e);
        }
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                movies = readAll(resultSet);
            }

        } catch (SQLException e) {
            logger.error("FindAllByTitle Error", e);
        } finally {
            cleaunpConnectionsResources(preparedStatement, connection);
        }


        return movies;
    }

//    @Override
//    public boolean populateReferences(Movie entity) throws IllegalOperationException, DAOExceptions {
//        sessionDAO = Factories.getInstance().getSessionDao();
//        entity.setSessions(sessionDAO.findByMovieId(entity.getId()));
//        for (Session session: entity.getSessions()) {
//            sessionDAO.populateReferences(session);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean deleteReferences(Movie entity) throws IllegalOperationException, DAOExceptions {
//        return false;
//    }


}
