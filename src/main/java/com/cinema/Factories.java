package com.cinema;

import com.cinema.config.AppConfig;
import com.cinema.dao.api.*;
import com.cinema.dao.impl.*;
import com.cinema.service.api.*;
import com.cinema.service.impl.*;


/**
 * Created by dshvedchenko on 17.03.16.
 */
public class Factories {
    private static volatile Factories instance;
    ;
    private AppConfig config = AppConfig.getInstance();

    private TicketDAO ticketDao;
    private SessionDAO sessionDAO;

    private Factories() {
    }

    public static Factories getInstance() {
        if (instance == null) {
            synchronized (Factories.class) {
                if (instance == null) {
                    instance = new Factories();
                }
            }
        }
        return instance;
    }

    public static AppConfig getAppConfig() {
        return getInstance().config;
    }


    public UserDAO getUserDao() {
        UserDAO result = null;
        if (getInstance().config.isInMemoryDB()) {
            result = UserInMemoryDAO.getInstance();
        } else {
            result = UserDBDAO.getInstance();
        }
        return result;
    }

    public TicketDAO getTicketDoa() {
        TicketDAO result = null;
        if (getInstance().config.isInMemoryDB()) {
            result = TicketInMemoryDAO.getInstance();
        } else {
            result = TicketDBDAO.getInstance();
        }
        return result;
    }

    public SessionDAO getSessionDao() {
        SessionDAO result = null;
        if (getInstance().config.isInMemoryDB()) {
            result = SessionInMemoryDAO.getInstance();
        } else {
            result = SessionDBDAO.getInstance();
        }
        return result;
    }

    public MovieDAO getMovieDao() {
        MovieDAO result = null;
        if (getInstance().config.isInMemoryDB()) {
            result = MovieInMemoryDAO.getInstance();
        } else {
            result = MovieDBDAO.getInstance();
        }
        return result;
    }


    public HallDAO getHallDao() {
        HallDAO result = null;
        if (getInstance().config.isInMemoryDB()) {
            result = HallInMemoryDAO.getInstance();
        } else result = HallDBDAO.getInstance();
        return result;
    }

    public HallService getHallService() {
        return HallServiceImpl.getInstance();
    }

    public MovieService getMovieService() {
        return MovieServiceImpl.getInstance();
    }

    public SessionService getSessionService() {
        return SessionServiceImpl.getInstance();
    }

    public TicketService getTicketService() {
        return TicketServiceImpl.getInstance();
    }

    public UserService getUserService() {
        return UserServiceImpl.getInstance();
    }
}
