package com.cinema.service.api;

import com.cinema.dto.UserDTO;
import com.cinema.exception.ServiceException;

import java.util.List;

/**
 * Created by dshvedchenko on 21.03.16.
 */
public interface UserService {
    Integer createUser(UserDTO user) throws ServiceException;

    UserDTO findUserById(Integer id) throws ServiceException;

    UserDTO findUserByLoginPassword(String login, String password) throws ServiceException;

    void deleteUserById(Integer id) throws ServiceException;

    List<UserDTO> findAllUsers() throws ServiceException;

    boolean updateUser(UserDTO user) throws ServiceException;

    boolean updateUserByAdmin(UserDTO user) throws ServiceException;

    boolean deleteUsersByStrListIds(String[] strUserIds);
}
