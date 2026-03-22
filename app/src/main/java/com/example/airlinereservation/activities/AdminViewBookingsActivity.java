package com.example.airlinereservation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airlinereservation.R;
import com.example.airlinereservation.adapters.AdminBookingAdapter;
import com.example.airlinereservation.database.DatabaseHelper;
import com.example.airlinereservation.models.Booking;

import java.util.List;

public class AdminViewBookingsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAdminBookings;
    private TextView tvNoBookings;
    private DatabaseHelper dbHelper;
    private AdminBookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_bookings);

        dbHelper = new DatabaseHelper(this);
        recyclerViewAdminBookings = findViewById(R.id.recyclerViewAdminBookings);
        tvNoBookings = findViewById(R.id.tvNoBookings);

        recyclerViewAdminBookings.setLayoutManager(new LinearLayoutManager(this));

        List<Booking> bookingList = dbHelper.getAllBookingsAdmin();

        if (bookingList.isEmpty()) {
            tvNoBookings.setVisibility(View.VISIBLE);
            recyclerViewAdminBookings.setVisibility(View.GONE);
        } else {
            tvNoBookings.setVisibility(View.GONE);
            recyclerViewAdminBookings.setVisibility(View.VISIBLE);
            adapter = new AdminBookingAdapter(this, bookingList);
            recyclerViewAdminBookings.setAdapter(adapter);
        }
    }
}
