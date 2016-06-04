package com.cinema.dao.impl;

import com.cinema.dao.api.UserDAO;
import com.cinema.model.Movie;
import com.cinema.model.User;

import java.util.List;

/**
 * Created by dshvedchenko on 25.03.16.
 */
public class UserInMemoryDAO extends InMemoryDao<User, Integer> implements UserDAO {


    private volatile static UserDAO instance;


    public static UserDAO getInstance() {
        if (instance == null) {
            synchronized (UserInMemoryDAO.class) {
                if (instance == null) {
                    instance = new UserInMemoryDAO();
                }
            }
        }

        return instance;
    }

    @Override
    public List<User> findAllUsers() {
        return listAll();
    }

    @Override
    public Integer saveUser(User entity) {
        return add(entity);
    }

    @Override
    public User findByLoginPassword(String login, String password) {
        User result = null;
        for (User user : listAll()) {
            if (user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(password)) {
                result = user;
                break;
            }
        }
        return result;
    }

    @Override
    public void deleteById(Integer id) {

    }


    @Override
    void initKeySequence() {
        if (keySequence == null) {
            synchronized (UserInMemoryDAO.class) {
                keySequence = 0;
            }
        }
    }

    @Override
    public Integer injectKey(User entity) {
        Integer localKey = -1;
        synchronized (instance) {
            localKey = keySequence++;
            entity.setId(keySequence);
        }
        return localKey;
    }


    @Override
    public Integer getIdByEntity(User entity) {
        return entity.getId();
    }
}
