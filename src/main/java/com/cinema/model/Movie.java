package com.cinema.model;

import com.cinema.Factories;
import com.cinema.dao.annotations.Column;
import com.cinema.dao.annotations.Entity;
import com.cinema.dao.annotations.Id;
import com.cinema.dao.api.SessionDAO;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Holds Movie Entity for DAO operations.
 *
 * @author dshvedchenko
 */
@Entity(tableName = "movie")
public class Movie extends GenericModel<Integer> {

    @Id
    @Column
    @Getter
    @Setter
    public Integer id;

    @Column
    @Getter
    @Setter
    public String title;

    @Column
    @Getter
    @Setter
    public String description;

    @Column
    @Getter
    @Setter
    public Integer duration;

    @Setter
    private List<Session> sessions;


    public Movie() {
    }

    public Movie(Integer id, String title, String description, Integer duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * (title != null ? title.hashCode() : -1) + (description != null ? description.hashCode() : -1);
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
        if (!(obj instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }

    public List<Session> getSessions() throws DAOExceptions, IllegalOperationException {
        if (sessions == null) {
            SessionDAO sessionDAO = Factories.getInstance().getSessionDao();
            sessions = sessionDAO.findByMovieId(getId());
        }
        return sessions;
    }

}
