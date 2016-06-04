package com.cinema.service.api;

import com.cinema.dto.HallDTO;
import com.cinema.exception.ServiceException;

import java.util.List;

/**
 * Created by dshvedchenko on 26.03.16.
 */
public interface HallService {

    /**
     * Returns list of all Halls accessible in system
     *
     * @return List of HallDTO
     */
    List<HallDTO> findAllHalls() throws ServiceException;

    HallDTO findHallById(Integer id) throws ServiceException;

}
