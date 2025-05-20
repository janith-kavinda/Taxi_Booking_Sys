<%@ page import="com.taxi.services.RentalService, com.taxi.models.Rental" %>
<%@ page import="com.taxi.models.Vehicle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  int rentalID = Integer.parseInt(request.getParameter("rental-id"));
  Rental rental = RentalService.getRentalById(rentalID);
  Vehicle vehicle = Vehicle.getVehicleById(rental.getVehicleID());
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Taxi Booking System - Edit Rental</title>
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background-image: url('images/background.jpg');
      background-size: cover;
      background-position: center;
      background-attachment: fixed;
      margin: 0;
      padding: 0;
      color: #fff;
      text-shadow: 1px 1px 2px rgba(0,0,0,0.7);
    }

    .container {
      max-width: 850px;
      margin: 50px auto;
      padding: 40px;
      background: rgba(20,20,20,0.85);
      border-radius: 20px;
      box-shadow: 0 8px 32px rgba(0,0,0,0.6);
    }

    h1 {
      text-align: center;
      font-size: 2.8rem;
      color: #ffcc00;
      margin-bottom: 40px;
      text-shadow: 2px 2px 4px rgba(0,0,0,0.6);
    }

    label {
      font-weight: bold;
      display: block;
      margin-bottom: 8px;
      color: #ffd700;
      text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
    }

    .form-group {
      margin-bottom: 25px;
    }

    input[type="number"],
    input[type="date"],
    input[type="text"] {
      width: 100%;
      padding: 14px;
      font-size: 16px;
      border-radius: 10px;
      border: none;
      background-color: rgba(255,255,255,0.9);
      color: #000;
      transition: box-shadow 0.3s ease;
    }

    input:focus {
      outline: none;
      box-shadow: 0 0 5px #ffcc00;
    }

    input[readonly] {
      background-color: rgba(255,255,255,0.7);
    }

    .duration-label {
      color: #eee;
      margin-top: 8px;
      display: block;
      text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
    }

    input[type="submit"] {
      background: linear-gradient(90deg, #ffcc00, #ffa500);
      color: #000;
      border: none;
      padding: 16px;
      font-size: 18px;
      font-weight: bold;
      border-radius: 10px;
      cursor: pointer;
      margin-top: 30px;
      width: 100%;
      transition: transform 0.3s, box-shadow 0.3s;
      text-shadow: none;
    }

    input[type="submit"]:hover {
      transform: scale(1.02);
      box-shadow: 0 6px 15px rgba(255,204,0,0.5);
    }

    @media (max-width: 600px) {
      .container {
        padding: 25px;
        margin: 30px auto;
      }

      h1 {
        font-size: 2.2rem;
      }
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Edit Rental Booking</h1>

  <form action="edit-rental" method="POST" onSubmit="return confirm('Are you sure you want to save these changes?');">
    <input type="hidden" name="rental-id" value="<%=rentalID%>">
    <input type="hidden" name="vehicle-id" value="<%=vehicle.getId()%>">

    <div class="form-group">
      <label>Rental ID:</label>
      <input type="text" value="<%=rentalID%>" readonly>
    </div>

    <div class="form-group">
      <label>Vehicle Name:</label>
      <input type="text" name="vehicle-name" value="<%=vehicle.getModel()%>" readonly>
    </div>

    <div class="form-group">
      <label>Vehicle Type:</label>
      <input type="text" name="vehicle-type" value="<%=vehicle.getType()%>" readonly>
    </div>

    <div class="form-group">
      <label>Price:</label>
      <input type="text" name="price" value="<%=rental.getPrice()%>" readonly>
    </div>

    <div class="form-group">
      <label>Duration:</label>
      <input type="number" name="duration" min="1" max="12" value="<%=rental.getDuration()%>">
      <span class="duration-label"><%= rental.getType().equalsIgnoreCase("by-days") ? "Days" : "Hours" %></span>
    </div>

    <div class="form-group">
      <label>Pickup Date:</label>
      <input type="date" name="rental-date" value="<%=rental.getDate()%>">
    </div>

    <input type="submit" value="Save Changes">
  </form>
</div>

<script>
  // Add animation to form elements when page loads
  document.addEventListener('DOMContentLoaded', function() {
    const inputs = document.querySelectorAll('.form-group');
    inputs.forEach((input, index) => {
      setTimeout(() => {
        input.style.opacity = '1';
        input.style.transform = 'translateY(0)';
      }, index * 100);
    });
  });
</script>
</body>
</html>