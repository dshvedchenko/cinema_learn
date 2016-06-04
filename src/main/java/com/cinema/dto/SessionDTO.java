package com.cinema.dto;

import com.cinema.model.Hall;
import com.cinema.model.Movie;
import com.cinema.model.Ticket;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dshvedchenko on 14.03.16.
 */
@Data
public class SessionDTO {
    private Integer id;
    private LocalDateTime sessionDateTime;
    private HallDTO hall;
    private MovieDTO movie;

    private List<TicketDTO> tickets;

    /**
     * @param id              session id
     * @param sessionDateTime - session start time at date
     * @param hall            session hall
     * @param movie           session movie
     */
    public SessionDTO(Integer id, LocalDateTime sessionDateTime, HallDTO hall, MovieDTO movie, List<TicketDTO> tickets) {
        this.id = id;
        this.sessionDateTime = sessionDateTime;
        this.hall = hall;
        this.movie = movie;
        this.tickets = tickets;
    }

    public SessionDTO() {
    }
}
