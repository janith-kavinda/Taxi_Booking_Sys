package com.example.demo.service;

import com.example.demo.model.Booking;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private static final String FILE_PATH = "C:/Temp/bookings.txt";

    public Booking createBooking(String userId, String name, String pickupLocation, String dropLocation, String vehicleType) {
        String bookingId = "BKG" + (getAllBookings().size() + 1); // Generate a simple booking ID
        Booking booking = new Booking(bookingId, userId, name, pickupLocation, dropLocation, vehicleType);
        saveBookingToFile(booking);
        return booking;
    }

    public Booking getBookingById(String bookingId) {
        List<Booking> bookings = getAllBookings();
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                return booking;
            }
        }
        return null; // Return null if booking not found
    }

    public Booking updateBooking(String bookingId, Booking updatedBooking) {
        List<Booking> bookings = getAllBookings();
        boolean updated = false;

        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().equals(bookingId)) {
                bookings.set(i, updatedBooking);
                updated = true;
                break;
            }
        }

        if (updated) {
            saveAllBookingsToFile(bookings);
            return updatedBooking;
        }
        return null; // Return null if booking not found
    }

    public boolean deleteBooking(String bookingId) {
        List<Booking> bookings = getAllBookings();
        boolean removed = bookings.removeIf(booking -> booking.getBookingId().equals(bookingId));

        if (removed) {
            saveAllBookingsToFile(bookings);
        }
        return removed;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    bookings.add(new Booking(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    private void saveBookingToFile(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(booking.getBookingId() + "," + booking.getUserId() + "," + booking.getName() + ","
                    + booking.getPickupLocation() + "," + booking.getDropLocation() + "," + booking.getVehicleType());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAllBookingsToFile(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Booking booking : bookings) {
                writer.write(booking.getBookingId() + "," + booking.getUserId() + "," + booking.getName() + ","
                        + booking.getPickupLocation() + "," + booking.getDropLocation() + "," + booking.getVehicleType());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
