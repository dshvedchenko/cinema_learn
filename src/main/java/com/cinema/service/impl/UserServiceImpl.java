package com.cinema.service.impl;

import com.cinema.Factories;
import com.cinema.Transformer;
import com.cinema.dao.api.TicketDAO;
import com.cinema.dao.api.UserDAO;
import com.cinema.dto.UserDTO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.ServiceException;
import com.cinema.model.User;
import com.cinema.service.api.UserService;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by dshvedchenko on 21.03.16.
 * //TODO
 */

public class UserServiceImpl implements UserService {

    UserDAO dao = Factories.getInstance().getUserDao();
    TicketDAO tDao = Factories.getInstance().getTicketDoa();

    private static UserService instance;
    Logger logger = null;

    private UserServiceImpl() {
        logger = getLogger(this.getClass().getName());
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (SessionServiceImpl.class) {
                if (instance == null) {
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public List<UserDTO> findAllUsers() throws ServiceException {
        List<User> users = null;
        try {
            users = dao.findAllUsers();
        } catch (DAOExceptions daoExceptions) {
            logger.error("findALlUsers", daoExceptions);
            throw new ServiceException("findAllUsers", daoExceptions);
        }
        try {
            return Transformer.listUser_TO_ListUserDTO(users);
        } catch (DAOExceptions daoExceptions) {
            logger.error("Find All Users error", daoExceptions);
            throw new ServiceException("Find All Users error", daoExceptions);
        }
    }

    /**
     * Allows store user by user only if login not changed and role not changed
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUser(UserDTO user) throws ServiceException {
        User userEntity = Transformer.userDTO_TO_User(user);
        boolean result = false;
        User stored = null;
        try {
            stored = dao.findById(user.getId());
        } catch (DAOExceptions daoExceptions) {
            logger.error("updateUser", daoExceptions);
            throw new ServiceException("udpateUser", daoExceptions);
        }
        if (userEntity.getRole() == stored.getRole()) {
            userEntity.setLogin(stored.getLogin());
            dao.update(userEntity);
            result = true;
        } else {
            throw new ServiceException("Illegal attemp to update or role");
        }
        return result;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public boolean updateUserByAdmin(UserDTO user) throws ServiceException {
        User userEntity = Transformer.userDTO_TO_User(user);
        boolean result = false;
        User stored = null;
        try {
            stored = dao.findById(user.getId());
        } catch (DAOExceptions daoExceptions) {
            logger.error("find user error", daoExceptions);
            throw new ServiceException("find user error", daoExceptions);
        }
        if (stored != null) {
            userEntity.setLogin(stored.getLogin());
            dao.update(userEntity);
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteUsersByStrListIds(String[] strUserIds) {
        boolean result = false;
        try {
            Integer[] movieIds = Arrays.asList(strUserIds).stream().map(item -> Integer.parseInt(item)).toArray(Integer[]::new);
            for (Integer movieFormId : movieIds) {
                deleteUserById(movieFormId);
            }
            result = true;
        } catch (ServiceException e) {
            logger.error("can not delete movie : ", e);
        }
        return result;
    }

    @Override
    public Integer createUser(UserDTO user) throws ServiceException {
        Integer userId = null;
        try {
            userId = dao.saveUser(Transformer.userDTO_TO_User(user));
        } catch (DAOExceptions daoExceptions) {
            daoExceptions.printStackTrace();
            throw new ServiceException("Error on creating user", daoExceptions);
        }
        user.setId(userId);
        return userId;
    }

    @Override
    public UserDTO findUserById(Integer id) throws ServiceException {
        User user = null;
        try {
            user = dao.findById(id);
        } catch (DAOExceptions daoExceptions) {
            logger.error("find user error", daoExceptions);
            throw new ServiceException("find user error", daoExceptions);
        }
        UserDTO udto = null;
        if (user != null) {
            try {
                udto = Transformer.user_TO_UserDTO(user);
            } catch (DAOExceptions daoExceptions) {
                logger.error("convert user error", daoExceptions);
                throw new ServiceException("find user error", daoExceptions);
            }
        }
        return udto;
    }

    @Override
    public UserDTO findUserByLoginPassword(String login, String password) throws ServiceException {
        User user = null;
        try {
            user = dao.findByLoginPassword(login, password);
        } catch (DAOExceptions daoExceptions) {
            logger.error("find user error", daoExceptions);
            throw new ServiceException("find user error", daoExceptions);
        }
        UserDTO udto = null;
        if (user != null) try {
            udto = Transformer.user_TO_UserDTO(user);
        } catch (DAOExceptions daoExceptions) {
            logger.error("convert user error", daoExceptions);
            throw new ServiceException("find user error", daoExceptions);
        }
        return udto;
    }

    @Override
    public void deleteUserById(Integer id) throws ServiceException {
        try {
            dao.deleteById(id);
        } catch (DAOExceptions daoExceptions) {
            logger.error("delete user error", daoExceptions);
            throw new ServiceException("delete user error", daoExceptions);
        }
    }

}
