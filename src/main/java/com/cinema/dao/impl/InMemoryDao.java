package com.cinema.dao.impl;

/**
 * Created by dshvedchenko on 25.03.16.
 */

import com.cinema.dao.api.GenericDao;
import com.cinema.exception.IllegalOperationException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class InMemoryDao<E, K> implements GenericDao<E, K> {

    private List<E> entities = Collections.synchronizedList(new LinkedList<E>());

    volatile K keySequence;


    public InMemoryDao() {
        initKeySequence();
    }


    abstract void initKeySequence();

    protected abstract K injectKey(E entity);

    @Override
    public K add(E entity) {
        synchronized (entities) {
            //check that we already have such entity in DB
            if (entities.contains(entity)) try {
                return getIdByEntity(entity);
            } catch (IllegalOperationException e) {
                e.printStackTrace();
            }

            //if not, produce new key and inject it into entity
            K localKey = injectKey(entity);

            //store it in DB
            entities.add(entity);

            //return new Key
            return localKey;
        }
    }

    @Override
    public void update(E entity) {
        synchronized (entities) {
            int i = entities.indexOf(entity);
            entities.set(i, entity);
        }
    }

    @Override
    public void removeById(K id) {
        remove(findById(id));
    }

    @Override
    public void remove(E entity) {
        synchronized (entities) {
            entities.remove(entity);
        }
    }

    @Override
    public void removeAll() {
        synchronized (entities) {
            entities.clear();
        }
    }


    @Override
    public List<E> listAll() {
        return entities;
    }

    @Override
    public boolean exists(E entity) {
        return entities.indexOf(entity) >= 0;
    }

    @Override
    public E findById(K id) {
        for (E s : listAll()) {
            K curretnKey = null;
            try {
                curretnKey = getIdByEntity(s);
            } catch (IllegalOperationException e) {
                e.printStackTrace();
            }
            if (curretnKey.equals(id)) return s;
        }
        return null;
    }

}
