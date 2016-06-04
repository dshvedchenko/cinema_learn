package com.cinema.service.impl;

import com.cinema.Factories;
import com.cinema.dto.MovieDTO;
import com.cinema.exception.ServiceException;
import com.cinema.service.api.MovieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author dshvedchenko on 26.03.16.
 */
public class MovieServiceImplTest {

    private MovieService ms = Factories.getInstance().getMovieService();

    @Before
    public void setup() {
        try {
            ms.clearMovies();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertOneMovie() {

        MovieDTO mdto = new MovieDTO();

        mdto.setDescription("Rebel Again");
        mdto.setTitle("Matrix");
        mdto.setDuration(1000);


        ms.createMovie(mdto);

        Assert.assertTrue(mdto.getId() != null);

    }

    @Test
    public void testFindAllMovie() {


        MovieDTO mdto = new MovieDTO();

        mdto.setDescription("Rebel Again");
        mdto.setTitle("Matrix");
        mdto.setDuration(1000);

        ms.createMovie(mdto);

        mdto = new MovieDTO();

        mdto.setDescription("Star Wars: New Hope");
        mdto.setTitle("Star Wars Episode IV");
        mdto.setDuration(123000);

        ms.createMovie(mdto);

        List<MovieDTO> tecka = null;
        try {
            tecka = ms.findAllMovies();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(tecka);
        Assert.assertTrue(tecka.size() == 2);
    }

    @Test
    public void testUpdateOneMovie() {
        MovieDTO mdto = new MovieDTO();

        mdto.setDescription("RayB");
        mdto.setTitle("DdVine");
        mdto.setDuration(1800);

        ms.createMovie(mdto);

        mdto.setDuration(2000);

        ms.updateMovie(mdto);
        List<MovieDTO> tecka = null;
        try {
            tecka = ms.findMoviesByTitle("DdVine");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(mdto.getDescription().equals("RayB"));
        Assert.assertNotNull(tecka);

    }
}
