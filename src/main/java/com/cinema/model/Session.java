package com.cinema.model;

import com.cinema.Factories;
import com.cinema.dao.annotations.Column;
import com.cinema.dao.annotations.Entity;
import com.cinema.dao.annotations.Id;
import com.cinema.dao.api.HallDAO;
import com.cinema.dao.api.MovieDAO;
import com.cinema.dao.api.TicketDAO;
import com.cinema.exception.DAOExceptions;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author by dshvedchenko on 14.03.16.
 */

@Entity(tableName = "session")
public class Session extends GenericModel<Integer> {

    @Id
    @Column
    @Getter
    @Setter
    private Integer id;

    @Column(name = "sessiondatetime")
    @Getter
    @Setter
    private LocalDateTime sessionDateTime;

    @Column
    @Getter
    @Setter
    private Integer hall_id;

    /**
     * Resolved Hall from reference @hall_id
     */
    private Hall hall;

    @Column
    @Getter
    @Setter
    private Integer movie_id;

    /**
     * Resolved Movie from reference movie_id
     */
    private Movie movie;

    @Setter
    private List<Ticket> tickets;


    public Session() {
    }

    /**
     * @param id              session id key
     * @param sessionDateTime session start time at date
     * @param hall_id         session hall id
     * @param movie_id        session movie id
     */
    public Session(Integer id, LocalDateTime sessionDateTime, Integer hall_id, Integer movie_id, List<Ticket> tickets) {
        this.id = id;
        this.sessionDateTime = sessionDateTime;
        this.hall_id = hall_id;
        this.movie_id = movie_id;
        this.tickets = tickets;
    }

    public Hall getHall() throws DAOExceptions {
        if (hall == null) {
            synchronized (this) {
                HallDAO hallDAO = Factories.getInstance().getHallDao();
                hall = hallDAO.findById(this.getHall_id());
            }
        }
        return hall;
    }

    public List<Ticket> getTickets() throws DAOExceptions {
        if (this.tickets == null) {
            synchronized (this) {
                TicketDAO ticketDAO = Factories.getInstance().getTicketDoa();
                List<Ticket> tickets = ticketDAO.findAllTicketsBySessionId(this.getId());
                this.setTickets(tickets);
            }
        }

        return this.tickets;
    }

    public Movie getMovie() throws DAOExceptions {
        if (this.movie == null) {
            synchronized (this) {
                MovieDAO movieDAO = Factories.getInstance().getMovieDao();
                setMovie(movieDAO.findById(getMovie_id()));
            }

        }

        return movie;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
        this.hall_id = hall.getId();
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        this.movie_id = movie.getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((movie_id == null) ? 0 : movie_id.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((hall_id == null) ? 0 : hall.hashCode());
        result = prime * result + ((sessionDateTime == null) ? 0 : sessionDateTime.hashCode());
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
        if (!(obj instanceof Session)) {
            return false;
        }
        Session other = (Session) obj;
        if (movie_id == null) {
            if (other.movie_id != null) {
                return false;
            }
        } else if (!movie_id.equals(other.movie_id)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (hall_id == null) {
            if (other.hall_id != null) {
                return false;
            }
        } else if (!hall_id.equals(other.hall_id)) {
            return false;
        }
        if (sessionDateTime == null) {
            if (other.sessionDateTime != null) {
                return false;
            }
        } else if (!sessionDateTime.equals(other.sessionDateTime)) {
            return false;
        }

        return true;
    }

}
