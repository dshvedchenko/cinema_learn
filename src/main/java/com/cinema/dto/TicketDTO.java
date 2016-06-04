package com.cinema.dto;

import com.cinema.dto.UserDTO;
import lombok.Data;

/**
 * @author dshvedchenko on 21.03.16.
 *         holds ticket object between client and server
 */
@Data
public class TicketDTO {

    private Integer id;
    private Integer row;
    private Integer seat;
    private UserDTO bookedByUser;
    private SessionDTO session;

    /**
     * Construct one TicketDTO object with set row from placeParts[0] and seat from placeParts[1]
     *
     * @param placeParts
     * @return
     */
    public static TicketDTO of(String[] placeParts) {
        TicketDTO ticket = new TicketDTO();
        ticket.setRow(Integer.valueOf(placeParts[0]));
        ticket.setSeat(Integer.valueOf(placeParts[1]));
        return ticket;
    }

    /**
     * Construct TicketDTO from string representation "p:s"
     * where p is number of row, s number of seat
     *
     * @param placeParts
     * @return
     */
    public static TicketDTO of(String placeParts) {
        return of(placeParts.split(":"));
    }

}
