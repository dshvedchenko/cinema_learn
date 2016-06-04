package com.cinema.dto;

import lombok.Data;

/**
 * @author by dshvedchenko on 25.04.16.
 */
@Data
public class SessionAction {
    private Integer id;
    private boolean removable = true;
    private boolean active = true;

}
