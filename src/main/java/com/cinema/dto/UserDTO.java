package com.cinema.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dshvedchenko on 21.03.16.
 */
@Data
public class UserDTO {
    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private UserRole role;
    private String email;
    private List<TicketDTO> tickets;

    /**
     * @param id
     * @param login
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param role
     * @param email
     */
    public UserDTO(Integer id, String login, String password, String firstName, String lastName, LocalDate birthDate, UserRole role, String email) {
        setId(id);
        setLogin(login);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setRole(role);
        setEmail(email);
    }

    public UserDTO() {
    }

    ;
}
