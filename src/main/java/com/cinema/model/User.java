package com.cinema.model;

import com.cinema.Factories;
import com.cinema.dao.annotations.Column;
import com.cinema.dao.annotations.Entity;
import com.cinema.dao.annotations.Id;
import com.cinema.dao.api.TicketDAO;
import com.cinema.exception.DAOExceptions;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by dshvedchenko on 21.03.16.
 */

@Entity(tableName = "user")
public class User extends GenericModel<Integer> {

    @Id
    @Column
    @Getter
    @Setter
    private Integer id;

    @Column
    @Getter
    @Setter
    private String login;

    @Column
    @Getter
    @Setter
    private String password;

    @Column
    @Getter
    @Setter
    private String firstName;

    @Column
    @Getter
    @Setter
    private String lastName;

    @Column
    @Getter
    @Setter
    private LocalDate birthDate;

    @Column
    @Getter
    @Setter
    private Integer role = 0;

    @Column
    @Getter
    @Setter
    private String email;

    @Setter
    private List<Ticket> tickets;

    public User() {
    }

    ;

    public User(String login, String password) {
        setLogin(login);
        setPassword(password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public List<Ticket> getTickets() throws DAOExceptions {
        if (this.tickets == null) {
            TicketDAO ticketDAO = Factories.getInstance().getTicketDoa();
            List<Ticket> tickets = ticketDAO.findAllTicketsByUserId(getId());
            setTickets(tickets);
        }
        return this.tickets;
    }

}
