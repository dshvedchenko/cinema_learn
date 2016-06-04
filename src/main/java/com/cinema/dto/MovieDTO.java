package com.cinema.dto;

import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * Created by dshvedchenko on 14.03.16.
 */
@Data()
public class MovieDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer duration;
    private List<SessionDTO> sessionDTOList;

    /**
     * @param id          movie PK
     * @param title       movie title
     * @param description moview description
     * @param duration    movie duration
     * @param sessionDTOs list of sessions for this Movie
     */
    public MovieDTO(Integer id, String title, String description, Integer duration, List<SessionDTO> sessionDTOs) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.sessionDTOList = sessionDTOs;
    }

    public MovieDTO() {
    }
}
