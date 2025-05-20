package com.taxi.controllers;

import com.taxi.models.Vehicle;
import com.taxi.services.RentalService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "EditRental", value = "/edit-rental")
public class EditRental extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RentalService.GenerateFile(request.getServletContext().getRealPath("/"));
        RentalService.loadRentals();

        int rentalID = Integer.parseInt(request.getParameter("rental-id")); // By days/ By Hours
        int duration = Integer.parseInt(request.getParameter("duration"));
        String date = request.getParameter("rental-date");
        int vehicleID =  Integer.parseInt(request.getParameter("vehicle-id"));

        Vehicle vehicle = Vehicle.getVehicleById(vehicleID);
        double price;
        if (RentalService.getRentalById(rentalID).getType().equalsIgnoreCase("by-days")){
            price = vehicle.getPriceUsingDay(duration);
        }
        else{
            price = vehicle.getPriceUsingHour(duration);
        }

        RentalService.updateRental(rentalID, date, duration, price);

        response.sendRedirect("bookingConfirmation.jsp");
    }

}