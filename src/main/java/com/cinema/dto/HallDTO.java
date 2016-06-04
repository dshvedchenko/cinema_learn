package com.cinema.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by dshvedchenko on 14.03.16.
 */
@Data
public class HallDTO {
    private Integer id;
    private String name;
    private Integer seatRows;
    private Integer seatCols;


    /**
     * @param name     HallName
     * @param seatRows hall rows
     * @param seatCols seats in a row
     */
    public HallDTO(String name, Integer seatRows, Integer seatCols) {
        this.name = name;
        this.seatRows = seatRows;
        this.seatCols = seatCols;
    }
}
