package com.commerceiq.bms.service;

import com.commerceiq.bms.dao.MovieRepository;
import com.commerceiq.bms.model.Movie;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MovieService {


    private static final Logger LOGGER = Logger.getLogger(MovieService.class.getName());
    @Autowired
    private MovieRepository movieRepository;

    public Movie findById(Long tid){
        return this.movieRepository.findById(tid).orElse(null);
    }

    public Movie save(Movie movie) {
        Movie errorMovie = new Movie();
        try {
            movie.preSave();
            return this.movieRepository.save(movie);
        }catch (DataIntegrityViolationException e){
            LOGGER.info(e.getMessage());
            errorMovie.setMessage("theatre id is not present");
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            errorMovie.setMessage(e.getMessage());
        }

        return errorMovie;
    }
}
