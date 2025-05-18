package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(
            booking.getUserId(),
            booking.getName(),
            booking.getPickupLocation(),
            booking.getDropLocation(),
            booking.getVehicleType()
        );
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(@PathVariable String bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PutMapping("/{bookingId}")
    public Booking updateBooking(@PathVariable String bookingId, @RequestBody Booking updatedBooking) {
        return bookingService.updateBooking(bookingId, updatedBooking);
    }

    @DeleteMapping("/{bookingId}")
    public boolean deleteBooking(@PathVariable String bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/count")
    public int getTotalBookingsCount() {
        return bookingService.getAllBookings().size();
    }
}
