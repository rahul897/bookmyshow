package com.bms.bo;

import com.bms.model.Booking;
import com.bms.model.Movie;
import com.bms.service.BookingService;
import com.bms.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingBO {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private MovieService movieService;

    public Booking add(Booking booking) {
        Booking savedBooking = new Booking();
        Movie movie = this.movieService.findById(booking.getMovieId());
        if(!movie.findOverlap(booking.getSelectedSeats())) {
            savedBooking = this.bookingService.add(booking);
            if (savedBooking.getStatus().equals("DONE")) {
                movie.fillSeats(booking.getSelectedSeats());
                this.movieService.save(movie);
            }
        }else{
            savedBooking.setMessage("selected seats are already booked");
        }
        //status if returned in the response object
        return savedBooking;
    }

    public Booking findById(Long bookingId) {
        return this.bookingService.findById(bookingId);
    }
}
