<%@ page import="com.taxi.models.Rental" %>
<%@ page import="com.taxi.services.RentalService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.taxi.models.Vehicle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RentalService.GenerateFile(request.getServletContext().getRealPath("/"));
    RentalService.loadRentals();
    ArrayList<Rental> rentals = RentalService.getRentals();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Rentals</title>
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
            max-width: 1000px;
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

        .rental-card {
            background: rgba(255,255,255,0.1);
            border-radius: 15px;
            padding: 25px;
            margin-bottom: 30px;
            transition: transform 0.3s ease, background 0.3s ease;
            border: 2px solid transparent;
        }

        .rental-card:hover {
            transform: translateY(-5px);
            background: rgba(255,204,0,0.25);
            border-color: #ffcc00;
        }

        .info-item {
            margin-bottom: 12px;
        }

        .info-label {
            font-weight: bold;
            color: #ffd700;
            width: 160px;
            display: inline-block;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
        }

        .info-value {
            color: #ffffff;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
        }

        .action-buttons {
            display: flex;
            gap: 15px;
            margin-top: 20px;
            flex-wrap: wrap;
            align-items: center;
        }

        .action-btn, form button.action-btn {
            padding: 12px 0; /* Changed to equal vertical padding */
            border-radius: 8px;
            font-weight: 600;
            border: none;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            color: #000;
            display: inline-block;
            text-align: center;
            width: 120px; /* Fixed equal width */
            box-sizing: border-box;
            font-size: 14px; /* Ensures text fits */
            font-family: 'Poppins', sans-serif; /* Ensures same font */
        }

        .action-btn:hover, form button.action-btn:hover {
            transform: scale(1.05);
        }

        .btn-edit, a.action-btn.btn-edit {
            background: linear-gradient(90deg, #ffcc00, #ffa500);
        }

        .btn-edit:hover, a.action-btn.btn-edit:hover {
            background: linear-gradient(90deg, #ffd633, #ffb733);
        }

        .btn-cancel, form button.action-btn.btn-cancel {
            background: linear-gradient(90deg, #ff3333, #cc0000);
        }

        .btn-cancel:hover, form button.action-btn.btn-cancel:hover {
            background: linear-gradient(90deg, #ff4d4d, #cc1a1a);
        }

        form {
            margin: 0;
            display: inline; /* Ensures form doesn't add extra space */
        }

        p.no-rentals {
            text-align: center;
            font-size: 1.2em;
            color: #ffcc00;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
        }

        @media (max-width: 600px) {
            .info-label {
                display: block;
                width: auto;
                margin-bottom: 4px;
            }

            .action-buttons {
                flex-direction: column;
                gap: 10px;
            }

            .action-btn, form button.action-btn {
                width: 100%; /* Full width on mobile */
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>All Rental Bookings</h1>

    <%
        if (rentals != null && !rentals.isEmpty()) {
            for (Rental rental : rentals) {
    %>
    <div class="rental-card">
        <div class="info-item">
            <span class="info-label">Rental ID:</span>
            <span class="info-value"><%= rental.getId() %></span>
        </div>

        <%
            Vehicle vehicle = Vehicle.getVehicleById(rental.getVehicleID());
        %>

        <div class="info-item">
            <span class="info-label">Vehicle Name:</span>
            <span class="info-value"><%= vehicle.getModel() %></span>
        </div>

        <div class="info-item">
            <span class="info-label">Vehicle Type:</span>
            <span class="info-value"><%= vehicle.getType() %></span>
        </div>

        <div class="info-item">
            <span class="info-label">Duration:</span>
            <span class="info-value"><%= rental.getDuration() %> <%= rental.getType().equalsIgnoreCase("by-hours") ? "hours" : "days" %></span>
        </div>
        <div class="info-item">
            <span class="info-label">Date:</span>
            <span class="info-value"><%= rental.getDate() %></span>
        </div>

        <div class="info-item">
            <span class="info-label">Price:</span>
            <span class="info-value">Rs. <%= rental.getPrice() %></span>
        </div>

        <div class="action-buttons">
            <a href="editRental.jsp?rental-id=<%= rental.getId() %>" class="action-btn btn-edit">Edit</a>

            <form action="cancel-rental" method="post" onSubmit="return confirm('Are you sure you want to cancel this rental?')">
                <input type="hidden" name="rental-id" value="<%= rental.getId() %>">
                <button type="submit" class="action-btn btn-cancel">Cancel</button>
            </form>
        </div>
    </div>
    <%
        }
    } else {
    %>
    <p class="no-rentals">No rentals available.</p>
    <%
        }
    %>
</div>

<script>
    // Add animation to rental cards when page loads
    document.addEventListener('DOMContentLoaded', function() {
        const cards = document.querySelectorAll('.rental-card');
        cards.forEach((card, index) => {
            setTimeout(() => {
                card.style.opacity = '1';
                card.style.transform = 'translateY(0)';
            }, index * 100);
        });

        // Add click effect to buttons
        const buttons = document.querySelectorAll('.action-btn');
        buttons.forEach(button => {
            button.addEventListener('click', function(e) {
                this.style.transform = 'scale(0.95)';
                setTimeout(() => {
                    this.style.transform = 'scale(1.05)';
                }, 100);
            });
        });
    });
</script>

</body>
</html>