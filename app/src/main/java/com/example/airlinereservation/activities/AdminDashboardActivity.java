package com.example.airlinereservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.airlinereservation.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnAddFlight, btnViewBookings, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnAddFlight = findViewById(R.id.btnAddFlight);
        btnViewBookings = findViewById(R.id.btnViewBookings);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddFlight.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, AddEditFlightActivity.class));
        });

        btnViewBookings.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, AdminViewBookingsActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}
