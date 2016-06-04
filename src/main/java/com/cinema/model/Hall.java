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
 * Created by dshvedchenko on 14.03.16.
 */

@Entity(tableName = "hall")
public class Hall extends GenericModel<Integer> {

    @Id
    @Column
    @Getter
    @Setter
    private Integer id;

    @Column
    @Getter
    @Setter
    private String name;

    @Column(name = "seatrows")
    @Getter
    @Setter
    private Integer seatRows;

    @Column(name = "seatcols")
    @Getter
    @Setter
    private Integer seatCols;

    @Setter
    private List<Session> sessions;

    public Hall() {
    }

    /**
     * @param seatRows number of rows in cinema
     * @param seatCols number of seats in row
     */
    public Hall(String name, Integer seatRows, Integer seatCols) {
        this.name = name;
        this.seatRows = seatRows;
        this.seatCols = seatCols;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (name != null ? name.hashCode() : -1) * prime;

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
        if (!(obj instanceof Hall)) {
            return false;
        }
        Hall other = (Hall) obj;
        if (id != other.getId()) return false;

        if (name == null) {
            if (other.name != null) return false;
        } else return name.equals(other.name);

        return true;
    }

    public List<Session> getSessions() throws DAOExceptions, IllegalOperationException {
        if (this.sessions == null) {
            SessionDAO sessionDAO = Factories.getInstance().getSessionDao();
            setSessions(sessionDAO.findByHallId(getId()));
        }

        return this.sessions;
    }

}
