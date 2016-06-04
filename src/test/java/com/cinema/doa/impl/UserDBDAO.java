package com.cinema.doa.impl;

import com.cinema.Factories;
import com.cinema.dao.api.UserDAO;
import com.cinema.exception.DAOExceptions;
import com.cinema.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dshvedchenko on 04.04.16.
 */

public class UserDBDAO {

    private UserDAO userDao = Factories.getInstance().getUserDao();


    @Before
    public void setupUsers() {
        try {
            userDao.removeAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        User user = new User("userX", "passX");
        try {
            userDao.add(user);
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }

    }

    //@After
    public void cleanUp() {
        try {
            userDao.removeAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
    }


    @Test
    public void GetTWOUsers() {

        try {
            userDao.removeAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }

        User user = new User("user1", "pass1");
        try {
            userDao.add(user);
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }

        user = new User("user2", "pass2");
        try {
            userDao.add(user);
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        List<User> users = null;
        try {
            users = userDao.findAllUsers();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        Assert.assertEquals(users.size(), 2);
    }


    @Test
    public void testRemoveAll() {
        try {
            userDao.removeAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        List<User> users = null;
        try {
            users = userDao.findAllUsers();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        Assert.assertEquals(users.size(), 0);
    }

    @Test
    public void testRemove_1() {
        List<User> users = null;
        try {
            users = userDao.findAllUsers();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }

        Assert.assertTrue(users.size() > 0);

        User user = null;
        try {
            user = userDao.findById(users.get(0).getId());
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        Assert.assertTrue(user != null);

        try {
            userDao.remove(user);
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        try {
            user = userDao.findById(users.get(0).getId());
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        Assert.assertEquals(user, null);
    }

    @Test
    public void testUpdateFirst() {
        List<User> users = null;
        try {
            users = userDao.listAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        Assert.assertTrue(users.size() != 0);

        User user = users.get(0);

        user.setBirthDate(LocalDate.of(1977, 11, 26));
        user.setEmail("kuku@example.com");
        user.setFirstName("Denis");
        user.setLastName("Shvedchenko");
        user.setRole(1);

        userDao.update(user);

    }


}
