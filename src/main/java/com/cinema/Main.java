package com.cinema;

import com.cinema.config.AppConfig;
import com.cinema.dto.MovieDTO;
import com.cinema.exception.ServiceException;
import com.cinema.service.api.MovieService;
import com.cinema.service.impl.MovieServiceImpl;

/**
 * Created by dshvedchenko on 14.03.16.
 */
public class Main {

    private static AppConfig config = AppConfig.getInstance();

    public static void main(String[] args) {
        MovieDTO mdto = new MovieDTO();
        mdto.setDescription("Rebel Again");
        mdto.setTitle("Matrix");
        mdto.setDuration(1000);
        MovieService ms = MovieServiceImpl.getInstance();
        ms.createMovie(mdto);

        try {
            System.out.println(ms.findAllMovies());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        System.out.println("args = [" + config.isInMemoryDB() + "]");

    }
}
