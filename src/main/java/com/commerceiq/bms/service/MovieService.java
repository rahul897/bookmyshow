package com.commerceiq.bms.service;

import com.commerceiq.bms.dao.MovieRepository;
import com.commerceiq.bms.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie findById(Long tid){
        return this.movieRepository.findById(tid).orElse(null);
    }

    public Movie save(Movie movie) {
        movie.preSave();
        return this.movieRepository.save(movie);
    }
}
