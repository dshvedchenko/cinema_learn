package com.cinema;

import com.cinema.controller.admin.AdminSessionServlet;
import com.cinema.dto.*;
import com.cinema.exception.DAOExceptions;
import com.cinema.exception.IllegalOperationException;
import com.cinema.exception.ServiceException;
import com.cinema.model.*;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * utils to convert entity and dtos
 */
public class Transformer {

    private static Logger logger = getLogger(AdminSessionServlet.class);

    /**
     * Converts list of Movie to list of MovieDTO
     *
     * @param movies movies Entities
     * @return movies DTOs
     */
    public static List<MovieDTO> listMovie_To_ListMovieDTO(List<Movie> movies) throws DAOExceptions, ServiceException {
        List<MovieDTO> mdto = new LinkedList<>();
        for (Movie m : movies) {
            mdto.add(movie_To_MovieDTO(m, true));
        }

        return mdto;
    }

    /**
     * Convert one Movie to MovieDTO
     *
     * @param movie movie Entity
     * @return movie DTO
     */
    public static MovieDTO movie_To_MovieDTO(Movie movie, boolean resolveReferences) throws ServiceException, DAOExceptions {
        List<Session> sessions;
        try {
            sessions = movie.getSessions();
        } catch (IllegalOperationException e) {
            logger.error("Error in movie to Movie DTO on get Sessions");
            throw new ServiceException("Error in movie to Movie DTO on get Sessions", e);
        }

        return new MovieDTO(
                movie.getId(), movie.getTitle(),
                movie.getDescription(),
                movie.getDuration(),
                (sessions != null && resolveReferences ? listSession_TO_SessionDTO(sessions) : null)
        );
    }


    /**
     * Convert MovieDTO to Movie
     *
     * @param mdto movie DTO
     * @return movie Entity
     */
    public static Movie movieDTO_To_Movie(MovieDTO mdto) {
        return new Movie(mdto.getId(), mdto.getTitle(), mdto.getDescription(), mdto.getDuration());
    }


    /**
     * Convert List of @User to List of UserDTO
     *
     * @param users users Entities
     * @return udto user DTO
     */
    public static List<UserDTO> listUser_TO_ListUserDTO(List<User> users) throws DAOExceptions, ServiceException {
        List<UserDTO> udto = new LinkedList<>();
        for (User u : users) {
            udto.add(user_TO_UserDTO(u));
        }

        return udto;
    }

    /**
     * Converts User to UserDTO
     *
     * @param u user Entity
     * @return user DTO
     */
    public static UserDTO user_TO_UserDTO(User u) throws DAOExceptions, ServiceException {
        UserDTO udto = new UserDTO(u.getId(), u.getLogin(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getBirthDate(), UserRole.of(u.getRole()), u.getEmail());
        udto.setTickets(listTickets_TO_TicketDTO(u.getTickets()));
        return udto;
    }

    /**
     * @param user user DTO
     * @return user Entity
     */
    public static User userDTO_TO_User(UserDTO user) {
        User u = new User(user.getLogin(), user.getPassword());

        u.setId(user.getId());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());

        u.setBirthDate(user.getBirthDate());
        u.setRole(user.getRole().ordinal());
        u.setEmail(user.getEmail());

        return u;
    }

    /**
     * Converts list of Hall to List of HallDTO
     *
     * @param halls halls Entities
     * @return hall DTO
     */
    public static List<HallDTO> listHall_TO_HallDTO(List<Hall> halls) {
        List<HallDTO> hdto = new LinkedList<>();
        for (Hall h : halls) {
            hdto.add(hall_TO_HallDTO(h));
        }
        return hdto;
    }

    /**
     * Converts Hall to HallDTO
     *
     * @param hall hall entity
     * @return hall dto
     */
    public static HallDTO hall_TO_HallDTO(Hall hall) {
        HallDTO hdto = new HallDTO(hall.getName(), hall.getSeatRows(), hall.getSeatCols());
        hdto.setId(hall.getId());
        return hdto;
    }

    /**
     * Converts HallDTO to Hall
     *
     * @param hdto hall DTO
     * @return hall entity
     */
    public static Hall hallDTO_TO_Hall(HallDTO hdto) {
        Hall hall = new Hall(hdto.getName(), hdto.getSeatRows(), hdto.getSeatCols());
        hall.setId(hdto.getId());
        return hall;
    }

    /**
     * @param sessions sessions Entities
     * @return sessions DTOs
     */
    public static List<SessionDTO> listSession_TO_SessionDTO(List<Session> sessions) throws DAOExceptions, ServiceException {
        List<SessionDTO> sessionDTOs = new LinkedList<>();
        for (Session session : sessions) {
            sessionDTOs.add(session_TO_SessionDTO(session));
        }

        return sessionDTOs;
    }

    /**
     * converts session to sessionDTO
     *
     * @param session session DTO
     * @return session entity
     */
    public static SessionDTO session_TO_SessionDTO(Session session) throws DAOExceptions, ServiceException {
        HallDTO hallDTO = hall_TO_HallDTO(session.getHall());
        MovieDTO movieDTO = movie_To_MovieDTO(session.getMovie(), false);
        List<TicketDTO> ticketDTOs = listTickets_TO_TicketDTO(session.getTickets());
        SessionDTO sessionDTO = new SessionDTO(session.getId(), session.getSessionDateTime(), hallDTO, movieDTO, ticketDTOs);
        return sessionDTO;
    }

    /**
     * converts SessionDTO to Session
     *
     * @param sessionDTO session DTO
     * @return session entity
     */
    public static Session sessionDTO_TO_Session(SessionDTO sessionDTO) {
        List<Ticket> tickets = listTicketsDTO_TO_ListTickets(sessionDTO.getTickets());
        Session session = new Session(sessionDTO.getId(), sessionDTO.getSessionDateTime(), sessionDTO.getHall().getId(), sessionDTO.getMovie().getId(), tickets);
        session.setHall(hallDTO_TO_Hall(sessionDTO.getHall()));
        session.setMovie(movieDTO_To_Movie(sessionDTO.getMovie()));
        return session;
    }


    /**
     * converts ticket entity to ticket DTO
     *
     * @param ticket ticket entity
     * @return ticket DTO
     */
    public static TicketDTO ticket_TO_ticketDTO(Ticket ticket) throws DAOExceptions, ServiceException {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setSeat(ticket.getSeat());
        ticketDTO.setRow(ticket.getRow());
        return ticketDTO;

    }

    /**
     * converts ticket entity to ticket DTO with resolving Session and BookedByUser
     *
     * @param ticket ticket entity
     * @return ticket DTO
     */
    public static TicketDTO ticket_TO_ticketDTO_ResolvingReferences(Ticket ticket) throws DAOExceptions, ServiceException {
        TicketDTO ticketDTO = ticket_TO_ticketDTO(ticket);
        ticketDTO.setSession(session_TO_SessionDTO(ticket.getSession()));
        ticketDTO.setBookedByUser(user_TO_UserDTO(ticket.getBookedByUser()));
        return ticketDTO;
    }


    /**
     * Converts ticketDTO to ticket
     *
     * @param ticketDTO ticket dto
     * @return ticket entity
     */
    public static Ticket ticketDTO_TO_ticket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDTO.getId());
        ticket.setSeat(ticketDTO.getSeat());
        ticket.setRow(ticketDTO.getRow());
        ticket.setSessionId(ticketDTO.getSession().getId());
        ticket.setBookedByUserId(ticketDTO.getBookedByUser().getId());
        return ticket;
    }

    /**
     * Convert list of tickets DTO to List of Entitites
     *
     * @param ticketDTOList ticket DTO list
     * @return tickets entitites
     */
    public static List<Ticket> listTicketsDTO_TO_ListTickets(List<TicketDTO> ticketDTOList) {
        List<Ticket> tickets = new LinkedList<>();
        if (ticketDTOList != null) {
            for (TicketDTO ticketDTO : ticketDTOList) {
                tickets.add(Transformer.ticketDTO_TO_ticket(ticketDTO));
            }
        }
        return tickets;
    }

    public static List<TicketDTO> listTickets_TO_TicketDTO(List<Ticket> tickets) throws DAOExceptions, ServiceException {
        List<TicketDTO> ticketDTOList = new LinkedList<>();
        for (Ticket ticket : tickets) {
            ticketDTOList.add(Transformer.ticket_TO_ticketDTO(ticket));
        }

        return ticketDTOList;
    }

    public static List<TicketDTO> listTickets_TO_TicketDTO_ResolvingReferences(List<Ticket> tickets) throws DAOExceptions, ServiceException {
        List<TicketDTO> ticketDTOList = new LinkedList<>();
        for (Ticket ticket : tickets) {
            ticketDTOList.add(Transformer.ticket_TO_ticketDTO_ResolvingReferences(ticket));
        }

        return ticketDTOList;
    }

}
