package com.cinema.model;

import com.cinema.Factories;
import com.cinema.dao.annotations.Column;
import com.cinema.dao.annotations.Entity;
import com.cinema.dao.annotations.Id;
import com.cinema.dao.api.SessionDAO;
import com.cinema.dao.api.UserDAO;
import com.cinema.exception.DAOExceptions;
import lombok.Getter;
import lombok.Setter;

/**
 * @author by dshvedchenko on 21.03.16.
 */

@Entity(tableName = "ticket")
public class Ticket extends GenericModel<Integer> {

    @Id
    @Column
    @Getter
    @Setter
    private Integer id;

    @Column
    @Getter
    @Setter
    private Integer row;

    @Column(name = "seat")
    @Getter
    @Setter
    private Integer seat;

    @Column(name = "session_id")
    @Getter
    private Integer sessionId;

    @Column(name = "booked_by_user_id")
    @Getter
    private Integer bookedByUserId;

    private User bookedByUser;

    private Session session;

    public Ticket() {
    }

    public void setBookedByUser(User user) {
        this.bookedByUser = user;
        this.bookedByUserId = bookedByUser.getId();
    }

    public User getBookedByUser() throws DAOExceptions {
        if (this.bookedByUser == null) {
            UserDAO userDAO = Factories.getInstance().getUserDao();
            setBookedByUser(userDAO.findById(getBookedByUserId()));

        }
        return this.bookedByUser;
    }

    public void setSession(Session session) {
        this.session = session;
        this.sessionId = session.getId();
    }

    public Session getSession() throws DAOExceptions {
        if (this.session == null) {
            SessionDAO sessionDAO = Factories.getInstance().getSessionDao();
            setSession(sessionDAO.findById(getSessionId()));
        }
        return this.session;
    }

    public void setBookedByUserId(Integer bookedByUserId) {
        this.bookedByUserId = bookedByUserId;
        this.bookedByUser = null;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
        this.session = null;
    }
}
