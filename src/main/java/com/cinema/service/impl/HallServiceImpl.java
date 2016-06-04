package com.cinema.service.impl;

import com.cinema.Factories;
import com.cinema.Transformer;
import com.cinema.dao.api.HallDAO;
import com.cinema.dto.HallDTO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.ServiceException;
import com.cinema.service.api.HallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by dshvedchenko on 28.03.16.
 */
public class HallServiceImpl implements HallService {

    Logger logger = LoggerFactory.getLogger(HallServiceImpl.class);

    private HallDAO dao = Factories.getInstance().getHallDao();

    private static volatile HallService instance;

    private HallServiceImpl() {
        logger = getLogger(this.getClass().getName());
    }

    public static HallService getInstance() {
        if (instance == null) {
            synchronized (HallServiceImpl.class) {
                if (instance == null) {
                    instance = new HallServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<HallDTO> findAllHalls() throws ServiceException {
        //return null;
        try {
            return Transformer.listHall_TO_HallDTO(dao.listAll());
        } catch (DAOExceptions daoExceptions) {
            logger.error("findAllHalls", daoExceptions);
            throw new ServiceException("findAllHalls", daoExceptions);
        }
    }

    @Override
    public HallDTO findHallById(Integer id) throws ServiceException {
        HallDTO result = null;

        try {
            result = Transformer.hall_TO_HallDTO(dao.findById(id));
        } catch (DAOExceptions daoExceptions) {
            logger.error("findHall By Id ", daoExceptions);
            throw new ServiceException("findHall By id", daoExceptions);
        }

        return result;
    }
}
