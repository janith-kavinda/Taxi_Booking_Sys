<%@ page import="java.util.ArrayList" %>
<%@ page import="com.taxi.models.Vehicle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Taxi Booking System - Rent a Car</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        * { box-sizing: border-box; }
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
        label {
            display: block;
            margin-bottom: 8px;
            color: #ffd700;
            font-weight: bold;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
        }
        .form-group { margin-bottom: 25px; }
        input[type="number"],
        input[type="date"],
        select {
            width: 100%;
            padding: 14px;
            font-size: 16px;
            border-radius: 10px;
            border: none;
            background-color: #ffffffdd;
            color: #000;
            transition: box-shadow 0.3s ease;
        }
        input:focus,
        select:focus {
            outline: none;
            box-shadow: 0 0 5px #ffcc00;
        }
        .radio-group {
            display: flex;
            gap: 20px;
            margin-top: 8px;
        }
        .radio-option {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 10px 16px;
            border-radius: 12px;
            background: rgba(255,255,255,0.12);
            color: #fff;
            cursor: pointer;
            transition: background 0.3s, transform 0.2s;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
        }
        .radio-option.active {
            background: #ffcc00;
            color: #000;
            transform: scale(1.05);
        }
        .car-options {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        .car-option {
            padding: 20px;
            background: rgba(255,255,255,0.1);
            border-radius: 15px;
            color: #fff;
            cursor: pointer;
            transition: transform 0.3s, background 0.3s, border 0.3s;
            border: 2px solid transparent;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
        }
        .car-option:hover {
            transform: scale(1.03);
            background: rgba(255,204,0,0.25);
            border-color: #ffcc00;
        }
        .car-option.selected {
            background: rgba(255,204,0,0.35);
            border-color: #ff9900;
            transform: scale(1.03);
        }
        .car-model {
            font-size: 20px;
            font-weight: 600;
            color: #fff;
        }
        .car-type,
        .car-price,
        .car-seats {
            font-size: 15px;
            margin-top: 6px;
            color: #eee;
        }
        .car-price { color: #ffdd57; font-weight: 500; }
        input[type="submit"] {
            background: linear-gradient(90deg, #ffcc00, #ffa500);
            color: #000;
            padding: 16px;
            font-size: 18px;
            font-weight: bold;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            width: 100%;
            margin-top: 30px;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        input[type="submit"]:hover {
            transform: scale(1.02);
            box-shadow: 0 6px 15px rgba(255,204,0,0.5);
        }
        #rental-type {
            margin-top: 8px;
            font-size: 16px;
            font-weight: bold;
            color: #ffdd57;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
        }
        #rental-summary {
            margin-top: 20px;
            padding: 15px;
            border-radius: 10px;
            background-color: rgba(0,0,0,0.75);
            color: #f1f1f1;
            font-weight: 500;
            font-size: 15px;
            display: none;
            box-shadow: 0 0 10px rgba(255,255,255,0.1);
        }
        ::placeholder { color: #aaa; }
        select option { color: #000; }
    </style>
</head>
<body>
<div class="container">
    <h1>Rent a Vehicle</h1>
    <form method="post" action="create-rent">
        <input type="hidden" name="user-id" value="1">

        <!-- Rental Type -->
        <div class="form-group">
            <label>Rental Type:</label>
            <div class="radio-group">
                <div class="radio-option" data-value="by-hours">
                    <input type="radio" name="rental-type" value="by-hours" id="radio-btn-hours" checked hidden>
                    Hours
                </div>
                <div class="radio-option" data-value="by-days">
                    <input type="radio" name="rental-type" value="by-days" id="radio-btn-days" hidden>
                    Days
                </div>
            </div>
            <p id="rental-type">Hours</p>
        </div>

        <!-- Duration -->
        <div class="form-group">
            <label for="rental-duration">Duration:</label>
            <input type="number" name="rental-duration" id="rental-duration" value="1" min="1" max="12">
        </div>

        <!-- Pickup Date -->
        <div class="form-group">
            <label for="rental-date">Pickup Date:</label>
            <input type="date" name="rental-date" id="rental-date">
        </div>

        <!-- Vehicle Type -->
        <div class="form-group">
            <label for="vehicle-type">Vehicle Type:</label>
            <select name="vehicle-type" id="vehicle-type">
                <option value="">-- Select --</option>
                <option value="Car" selected>Car</option>
                <option value="Van">Van</option>
            </select>
        </div>

        <!-- Available Vehicles -->
        <div class="form-group">
            <label>Available Vehicles:</label>
            <div class="car-options">
                <%
                    ArrayList<Vehicle> vehicles = Vehicle.getVehicles();
                    for (Vehicle vehicle : vehicles) {
                        String type = vehicle.getType();
                        int id = vehicle.getId();
                %>
                <div class="car-option"
                     data-type="<%=type.toLowerCase()%>"
                     data-id="<%=id%>">
                    <div class="car-model"><%=vehicle.getModel()%></div>
                    <div class="car-type"><%=vehicle.getType()%></div>
                    <div class="car-seats">Passengers: <%=vehicle.getSeats()%></div>
                    <div class="car-price">
                        LKR <%=vehicle.getCostPerDay()%> per day |
                        LKR <%=vehicle.getCostPerHour()%> per hour
                    </div>
                </div>
                <% } %>
            </div>
        </div>

        <input type="hidden" name="vehicle-id" id="selected-vehicle-id">
        <input type="submit" value="Rent">
        <div id="rental-summary"></div>
    </form>
</div>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const radios = document.querySelectorAll('.radio-option');
        const rentalTypeDisplay = document.getElementById('rental-type');
        const carOptions = document.querySelectorAll('.car-option');
        const vehicleTypeSelect = document.getElementById('vehicle-type');
        const selectedVehicleInput = document.getElementById('selected-vehicle-id');
        const durationInput = document.getElementById('rental-duration');
        const rentalDate = document.getElementById('rental-date');
        const summaryBox = document.getElementById('rental-summary');

        // Setup pickup date min
        rentalDate.min = new Date().toISOString().split('T')[0];

        // Rental type click
        radios.forEach(r => {
            r.addEventListener('click', () => {
                radios.forEach(xx => xx.classList.remove('active'));
                r.classList.add('active');
                const val = r.getAttribute('data-value');
                document.getElementById(val === 'by-hours' ? 'radio-btn-hours' : 'radio-btn-days').checked = true;
                rentalTypeDisplay.textContent = val === 'by-hours' ? 'Hours' : 'Days';
                updateSummary();
            });
        });

        // Filter cars by type
        vehicleTypeSelect.addEventListener('change', () => {
            const sel = vehicleTypeSelect.value.toLowerCase();
            carOptions.forEach(c => {
                const show = !sel || c.dataset.type === sel;
                c.style.display = show ? 'block' : 'none';
                c.classList.remove('selected');
            });
            selectedVehicleInput.value = '';
            summaryBox.style.display = 'none';
        });

        // Car selection hover effects
        carOptions.forEach(opt => {
            opt.addEventListener('click', () => {
                carOptions.forEach(o => o.classList.remove('selected'));
                opt.classList.add('selected');
                selectedVehicleInput.value = opt.dataset.id;
                updateSummary();
            });
            opt.addEventListener('mouseover', () => {
                opt.style.transform = 'scale(1.05)';
            });
            opt.addEventListener('mouseout', () => {
                if (!opt.classList.contains('selected')) {
                    opt.style.transform = '';
                }
            });
        });

        durationInput.addEventListener('input', updateSummary);

        function updateSummary() {
            const selected = document.querySelector('.car-option.selected');
            if (!selected) { summaryBox.style.display = 'none'; return; }

            const model = selected.querySelector('.car-model').textContent;
            const type = document.querySelector('input[name="rental-type"]:checked').value;
            const days = durationInput.value;
            const priceTxt = selected.querySelector('.car-price').textContent;
            const [dayPrice, hourPrice] = priceTxt.split('|').map(s => parseFloat(s));
            const cost = (type === 'by-hours' ? hourPrice : dayPrice) * days;

            summaryBox.innerHTML = `
                <p><strong>Vehicle:</strong> ${model}</p>
                <p><strong>Rental Type:</strong> ${type.replace('by-', '').toUpperCase()}</p>
                <p><strong>Duration:</strong> ${days} ${type == 'by-hours' ? 'hour(s)' : 'day(s)'}</p>
                <p><strong>Total Cost:</strong> $${cost.toFixed(2)}</p>
            `;
        }

        // Final form validation
        document.querySelector('form').addEventListener('submit', e => {
            if (!selectedVehicleInput.value) {
                e.preventDefault();
                alert('❗ Please select a vehicle before proceeding.');
            }
        });
    });
</script>
</body>
</html>
