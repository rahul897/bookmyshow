package com.commerceiq.bms.service;

import com.commerceiq.bms.dao.BookingRepository;
import com.commerceiq.bms.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking add(Booking booking) {
        booking.preSave();
        return this.bookingRepository.save(booking);
    }

    public Booking findById(Long bookingId) {
        return this.bookingRepository.findById(bookingId).orElse(null);
    }
}
