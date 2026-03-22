package com.example.airlinereservation.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.airlinereservation.R;

public class HomeActivity extends AppCompatActivity {

    private CardView cardSearchFlights, cardMyTickets;
    private Button btnLogout;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardSearchFlights = findViewById(R.id.cardSearchFlights);
        cardMyTickets = findViewById(R.id.cardMyTickets);
        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("USER_ID", -1);
        if(userId != -1) {
            tvWelcome.setText("Welcome to Airline Booking!");
        }

        cardSearchFlights.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, FlightSearchActivity.class));
        });

        cardMyTickets.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MyTicketsActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }
}
