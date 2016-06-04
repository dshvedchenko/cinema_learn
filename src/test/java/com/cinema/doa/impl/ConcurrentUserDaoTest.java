package com.cinema.doa.impl;

import com.cinema.Factories;
import com.cinema.dao.api.UserDAO;
import com.cinema.exception.DAOExceptions;
import com.cinema.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by dshvedchenko on 03.04.16.
 */
@Ignore
public class ConcurrentUserDaoTest {

    private UserDAO userDao = Factories.getInstance().getUserDao();

    @Before
    public void setup() {
        try {
            userDao.removeAll();
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
    }

    @Test
    public void TestAdmin2Threads() throws InterruptedException {

        List<Runnable> tasks = new ArrayList<>();
        tasks.add(new UserAdmin("left__"));
        tasks.add(new UserAdmin("right__"));
        tasks.add(new UserAdmin("africa__"));

        List<Thread> execs = new ArrayList<>();

        for (Runnable r : tasks) {
            Thread t = new Thread(r);
            execs.add(t);
            t.start();
        }

        for (Thread t : execs) {
            t.join();
        }

        try {
            assertTrue(userDao.listAll().size() == 3000);
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
    }

}


class UserAdmin implements Runnable {


    private UserDAO userDao = Factories.getInstance().getUserDao();
    private String prefix;


    public UserAdmin(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            User e = new User(prefix + i, "password1");
            try {
                userDao.add(e);
            } catch (DAOExceptions daoExceptions) {
                daoExceptions.printStackTrace();
            }
            feedback(i);
        }

    }

    private void feedback(int i) {
        if (i % 1000 == 0) System.out.println(" STATS : " + Thread.currentThread().getName() + " : " + i);
    }
}
