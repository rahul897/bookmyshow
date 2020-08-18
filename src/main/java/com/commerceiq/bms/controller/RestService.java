package com.commerceiq.bms.controller;

import com.commerceiq.bms.bo.BookingBO;
import com.commerceiq.bms.bo.MovieBO;
import com.commerceiq.bms.bo.TheatreBO;
import com.commerceiq.bms.bo.UserBO;
import com.commerceiq.bms.dao.Cache;
import com.commerceiq.bms.model.Booking;
import com.commerceiq.bms.model.Movie;
import com.commerceiq.bms.model.Theatre;
import com.commerceiq.bms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class RestService {

    @Autowired
    private TheatreBO theatreBO;
    @Autowired
    private MovieBO movieBO;
    @Autowired
    private UserBO userBO;
    @Autowired
    private BookingBO bookingBO;

    @GetMapping(path = "/",produces = "application/json")
    public String hello(){
        return "hello";
    }

    @PostMapping(path = "/user",produces = "application/json",consumes = "application/json")
    public User addUser(@Validated @RequestBody User user){
        return this.userBO.add(user);
    }

    @PostMapping(path = "/theatre",produces = "application/json",consumes = "application/json")
    public Theatre addTheatre(@Validated @RequestBody Theatre theatre){
        return this.theatreBO.add(theatre);
    }

    @PostMapping(path = "/theatre/{tid}/movie",produces = "application/json",consumes = "application/json")
    public Movie addMovie(@Validated @RequestBody Movie movie, @PathVariable Long tid ){
        return this.movieBO.addMovie(movie,tid);
    }

    @GetMapping(path = "/locations" ,produces = "application/json")
    public List<String> listLocation(){
        return this.theatreBO.findLocations();
    }

    @GetMapping(path = "/location/{location}", produces = "application/json")
    public List<Theatre> listTheatres(@PathVariable String location){
        return this.theatreBO.getTheatresOnLocation(location);
    }

    @GetMapping(path = "/movie/{mid}" ,produces = "application/json")
    public String getAvailableSeats(@PathVariable Long mid){
        return this.movieBO.getAvailableSeats(mid);
    }

    @PostMapping(path = "/book",produces = "application/json",consumes = "application/json")
    public Booking addBooking(@Validated @RequestBody Booking booking){
        //below both operations need to happen in single transaction if concurrency is needed
        //once request is running,any other request has to wait until this request is failed of done
        //if there is overlap between seats

        //that can be controlled by maintaining write through cache which can act as lock system
        //simlar to repeatable read isolation level in any db
        Long movieId = booking.getMovieId();
        Booking errorBooking = new Booking();
        Set<String> inProcessSeats = Cache.seatCache.getOrDefault(movieId,null);
        Set<String> finalInProcessSeats = inProcessSeats;
        if(inProcessSeats!=null && booking.getSelectedSeats().stream().anyMatch(finalInProcessSeats::contains)){
            errorBooking.setMessage("those are reserved,please try after some time");
            return errorBooking;
        }else{
            Cache.seatCache.put(movieId,new HashSet<>(booking.getSelectedSeats()));
            inProcessSeats = Cache.seatCache.get(movieId);
        }
        Booking savedBooking = this.bookingBO.add(booking);
        inProcessSeats.removeAll(booking.getSelectedSeats());
        return savedBooking;
    }


}
