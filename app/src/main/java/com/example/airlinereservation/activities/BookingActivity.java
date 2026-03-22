package com.example.airlinereservation.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.airlinereservation.R;
import com.example.airlinereservation.database.DatabaseHelper;
import com.example.airlinereservation.models.Booking;

public class BookingActivity extends AppCompatActivity {

    private EditText etPassengerName, etPassengerAge;
    private Button btnConfirmBooking;
    private DatabaseHelper dbHelper;
    private int flightId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dbHelper = new DatabaseHelper(this);
        etPassengerName = findViewById(R.id.etPassengerName);
        etPassengerAge = findViewById(R.id.etPassengerAge);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);

        flightId = getIntent().getIntExtra("FLIGHT_ID", -1);

        btnConfirmBooking.setOnClickListener(v -> confirmBooking());
    }

    private void confirmBooking() {
        String name = etPassengerName.getText().toString().trim();
        String ageStr = etPassengerAge.getText().toString().trim();

        if (name.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("USER_ID", -1);

        if (userId == -1 || flightId == -1) {
            Toast.makeText(this, "Error in booking process.", Toast.LENGTH_SHORT).show();
            return;
        }

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setFlightId(flightId);
        booking.setPassengerName(name);
        booking.setPassengerAge(age);

        boolean isBooked = dbHelper.bookTicket(booking);
        if (isBooked) {
            Toast.makeText(this, "Ticket Booked Successfully!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Booking Failed.", Toast.LENGTH_SHORT).show();
        }
    }
}
