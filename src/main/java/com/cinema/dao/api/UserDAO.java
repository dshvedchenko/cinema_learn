package com.cinema.dao.api;

import com.cinema.exception.DAOExceptions;
import com.cinema.model.User;

import java.util.List;

/**
 * Created by dshvedchenko on 21.03.16.
 */
public interface UserDAO extends GenericDao<User, Integer> {
    List<User> findAllUsers() throws DAOExceptions;

    Integer saveUser(User entity) throws DAOExceptions;

    User findByLoginPassword(String login, String password) throws DAOExceptions;

    void deleteById(Integer id) throws DAOExceptions;

}
