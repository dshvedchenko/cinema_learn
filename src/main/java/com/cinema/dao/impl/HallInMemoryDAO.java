package com.cinema.dao.impl;

import com.cinema.dao.api.HallDAO;
import com.cinema.model.Hall;

/**
 * Created by dshvedchenko on 25.03.16.
 */
public class HallInMemoryDAO extends InMemoryDao<Hall, Integer> implements HallDAO {


    @Override
    public Integer getIdByEntity(Hall entity) {
        return entity.getId();
    }

    private volatile static HallDAO instance;


    public static HallDAO getInstance() {
        if (instance == null) {
            synchronized (HallInMemoryDAO.class) {
                if (instance == null) {
                    instance = new HallInMemoryDAO();
                }
            }
        }

        return instance;
    }


    /**
     * Setup reference list of Halls
     */
    private HallInMemoryDAO() {
        add(new Hall("Red", 2, 5));
        add(new Hall("Green", 2, 5));
    }

    @Override
    public Integer injectKey(Hall entity) {
        keySequence++;
        entity.setId(keySequence);
        return keySequence;
    }

    @Override
    void initKeySequence() {
        if (keySequence == null) {
            synchronized (HallInMemoryDAO.class) {
                keySequence = 0;
            }
        }
    }
}
