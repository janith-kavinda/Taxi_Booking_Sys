package com.taxi.controllers;

import com.taxi.services.RentalService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CancelRental", value = "/cancel-rental")
public class CancelRental extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RentalService.GenerateFile(request.getServletContext().getRealPath("/"));
        RentalService.loadRentals();

        int rentalID = Integer.parseInt(request.getParameter("rental-id")); // By days/ By Hours
        RentalService.deleteRental(rentalID);

        response.sendRedirect("bookingConfirmation.jsp");
    }

}