package com.commerceiq.bms.bo;

import com.commerceiq.bms.model.Movie;
import com.commerceiq.bms.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class MovieBO {
    @Autowired
    private MovieService movieService;

    public Movie add(Movie movie) {
        return this.movieService.save(movie);
    }

    public Movie findById(Long movieId) {
        return this.movieService.findById(movieId);
    }

    public String getAvailableSeats(Long mid) {
        Movie movie = this.movieService.findById(mid);
        return movie.getAvailableSeatsJson();
    }

    public Movie addMovie(Movie movie, Long tid) {
        movie.setTheatreId(tid);
        movie.setAvailableSeats(new HashSet<>(movie.getSeatingLayout()));
        return this.movieService.save(movie);
    }
}
