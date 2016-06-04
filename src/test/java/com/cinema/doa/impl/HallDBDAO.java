package com.cinema.doa.impl;

import com.cinema.Factories;
import com.cinema.dao.api.HallDAO;
import com.cinema.dao.api.UserDAO;
import com.cinema.exception.DAOExceptions;
import com.cinema.model.Hall;
import com.cinema.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dshvedchenko on 04.04.16.
 */

public class HallDBDAO {

    private HallDAO hallDao = Factories.getInstance().getHallDao();


    @Test
    public void CreateOneHall() {

        try {
            hallDao.removeAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }

        Hall hall = new Hall("Red", 4, 5);
        try {
            hallDao.add(hall);
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }

        List<Hall> halls = null;
        try {
            halls = hallDao.listAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        Assert.assertEquals(halls.size(), 1);
    }


    @Test
    public void testRemoveAll() {
        try {
            hallDao.removeAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        List<Hall> halls = null;
        try {
            halls = hallDao.listAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
        Assert.assertEquals(halls.size(), 0);
    }

//    @Test
//    public void testUpdateFirst() {
//        List<User> users = userDao.listAll();
//        Assert.assertTrue(users.size() != 0);
//
//        User user = users.get(0);
//
//        user.setBirthDate(LocalDate.of(1977,11,26));
//        user.setEmail("kuku@example.com");
//        user.setFirstName("Denis");
//        user.setLastName("Shvedchenko");
//        user.setRole(1);
//
//        userDao.update(user);
//
//    }


}
