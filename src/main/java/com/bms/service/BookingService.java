package com.bms.service;

import com.bms.dao.BookingRepository;
import com.bms.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class BookingService {

    private static final Logger LOGGER = Logger.getLogger(BookingService.class.getName());
    @Autowired
    private BookingRepository bookingRepository;

    public Booking add(Booking booking) {
        Booking errorBooking = new Booking();
        try {
            booking.preSave();
            return this.bookingRepository.save(booking);
        }catch (DataIntegrityViolationException e){
            LOGGER.info(e.getMessage());
            errorBooking.setMessage("userId/movieId doesn't exist");
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            errorBooking.setMessage(e.getMessage());
        }
        return errorBooking;
    }

    public Booking findById(Long bookingId) {
        return this.bookingRepository.findById(bookingId).orElse(null);
    }
}
