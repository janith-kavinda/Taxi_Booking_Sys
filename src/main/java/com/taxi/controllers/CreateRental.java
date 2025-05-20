package com.taxi.controllers;

import java.io.*;

import com.taxi.models.Vehicle;
import com.taxi.services.RentalService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "createRental", value = "/create-rent")
public class CreateRental extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RentalService.GenerateFile(request.getServletContext().getRealPath("/"));
        RentalService.loadRentals();

        String rentalType = request.getParameter("rental-type"); // By days/ By Hours
        int duration = Integer.parseInt(request.getParameter("rental-duration"));
        String rentalDate = request.getParameter("rental-date");
        int vehicleID = Integer.parseInt(request.getParameter("vehicle-id"));
        int userID = Integer.parseInt(request.getParameter("user-id"));

        Vehicle vehicle = Vehicle.getVehicleById(vehicleID);
        double price;
        if (rentalType.equalsIgnoreCase("by-days")) {
            price = vehicle.getPriceUsingDay(duration);
        }
        else {
            price = vehicle.getPriceUsingHour(duration);
        }

        RentalService.createRental(vehicleID, userID, rentalDate, rentalType, duration, price);

        response.sendRedirect("bookingConfirmation.jsp");
    }

}