package com.example.airlinereservation.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airlinereservation.R;
import com.example.airlinereservation.adapters.TicketAdapter;
import com.example.airlinereservation.database.DatabaseHelper;
import com.example.airlinereservation.models.Booking;

import java.util.List;

public class MyTicketsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTickets;
    private TextView tvNoTickets;
    private DatabaseHelper dbHelper;
    private TicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        dbHelper = new DatabaseHelper(this);
        recyclerViewTickets = findViewById(R.id.recyclerViewTickets);
        tvNoTickets = findViewById(R.id.tvNoTickets);

        recyclerViewTickets.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("USER_ID", -1);

        List<Booking> ticketList = dbHelper.getUserBookings(userId);

        if (ticketList.isEmpty()) {
            tvNoTickets.setVisibility(View.VISIBLE);
            recyclerViewTickets.setVisibility(View.GONE);
        } else {
            tvNoTickets.setVisibility(View.GONE);
            recyclerViewTickets.setVisibility(View.VISIBLE);
            adapter = new TicketAdapter(this, ticketList);
            recyclerViewTickets.setAdapter(adapter);
        }
    }
}
