package com.cinema.doa.impl;

import com.cinema.Factories;
import com.cinema.dao.api.*;
import com.cinema.dto.UserRole;
import com.cinema.exception.DAOExceptions;
import com.cinema.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dshvedchenko.
 *         Test Ticket can be created and validate its referecnes correctly read
 */

public class TicketDBDAO {

    private MovieDAO movieDAO = Factories.getInstance().getMovieDao();
    private HallDAO hallDAO = Factories.getInstance().getHallDao();
    private SessionDAO sessionDao = Factories.getInstance().getSessionDao();
    private TicketDAO ticketDAO = Factories.getInstance().getTicketDoa();
    private UserDAO userDAO = Factories.getInstance().getUserDao();
    private User sampleUser;
    private Session sampleSession;

    @Before
    public void cleanup() throws DAOExceptions {
        movieDAO.removeAll();
        hallDAO.removeAll();
        sessionDao.removeAll();
        userDAO.removeAll();
        ticketDAO.removeAll();

        Hall sampleHall = new Hall("Red", 4, 5);
        hallDAO.add(sampleHall);
        Assert.assertNotNull(sampleHall);
        Assert.assertNotNull(sampleHall.getId());

        sampleUser = new User("admin", "admin");
        sampleUser.setRole(UserRole.ADMIN.ordinal());
        userDAO.add(sampleUser);
        Assert.assertNotNull(sampleUser);
        Assert.assertNotNull(sampleUser.getId());

        Movie sampleMovie = new Movie();
        sampleMovie.setTitle("SAMPLE MOVIEW");
        sampleMovie.setDescription("SAMPLE MOVIE DESCRIPTION");
        sampleMovie.setDuration(1000);
        movieDAO.add(sampleMovie);
        Assert.assertNotNull(sampleMovie);
        Assert.assertNotNull(sampleMovie.getId());

        sampleSession = new Session();
        sampleSession.setHall(sampleHall);
        sampleSession.setMovie(sampleMovie);
        sampleSession.setSessionDateTime(LocalDateTime.of(2016, 4, 24, 10, 10));
        sessionDao.add(sampleSession);
        Assert.assertNotNull(sampleSession);
        Assert.assertNotNull(sampleSession.getId());
        Assert.assertNotNull(sampleSession.getHall_id());
        Assert.assertNotNull(sampleSession.getMovie_id());
    }

    @Test()
    public void OneTicketCreated() throws DAOExceptions {
        Ticket ticket = new Ticket();
        ticket.setBookedByUser(sampleUser);
        ticket.setSession(sampleSession);
        ticket.setRow(1);
        ticket.setSeat(1);
        ticketDAO.add(ticket);
        List<Ticket> tickets = ticketDAO.listAll();
        Assert.assertEquals(tickets.size(), 1);
        Assert.assertNotNull(tickets.get(0).getBookedByUser());
        Assert.assertNotNull(tickets.get(0).getSession());
    }


    @Test
    public void testRemoveAll() throws DAOExceptions {
        hallDAO.removeAll();
        List<Hall> halls;
        halls = hallDAO.listAll();
        Assert.assertEquals(halls.size(), 0);
    }

}
