package com.cinema.dao.impl;

import com.cinema.Factories;
import com.cinema.exception.DAOExceptions;
import com.cinema.dao.api.TicketDAO;
import com.cinema.dao.api.UserDAO;
import com.cinema.exception.IllegalOperationException;
import com.cinema.model.Ticket;
import com.cinema.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dshvedchenko on 04.04.16.
 */
public class UserDBDAO extends GenericDBDao<User, Integer> implements UserDAO {

    private volatile static UserDAO instance;
    private TicketDAO ticketDAO = null;

    private UserDBDAO(Class<User> type) {
        super(type);
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            synchronized (UserDBDAO.class) {
                if (instance == null) {
                    instance = new UserDBDAO(User.class);
                }
            }
        }

        return instance;
    }


    @Override
    public List<User> findAllUsers() throws DAOExceptions {
        return listAll();
    }

    @Override
    public Integer saveUser(User entity) throws DAOExceptions {
        return add(entity);
    }

    @Override
    /**
     * Query Users with login and password and return first appropriate
     */
    public User findByLoginPassword(String login, String password) throws DAOExceptions {
        User user = null;
        List<User> users = null;
        try {
            users = findByFK("login", login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (users != null && users.get(0).getPassword().equals(password)) {
            user = users.get(0);
        }

        return user;
    }

    @Override
    public void deleteById(Integer id) throws DAOExceptions {
        removeById(id);
    }

}
