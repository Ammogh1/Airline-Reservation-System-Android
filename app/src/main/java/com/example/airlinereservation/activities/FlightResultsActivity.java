package com.example.airlinereservation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airlinereservation.R;
import com.example.airlinereservation.adapters.FlightAdapter;
import com.example.airlinereservation.database.DatabaseHelper;
import com.example.airlinereservation.models.Flight;

import java.util.List;

public class FlightResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFlights;
    private TextView tvNoFlights;
    private DatabaseHelper dbHelper;
    private FlightAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_results);

        dbHelper = new DatabaseHelper(this);
        recyclerViewFlights = findViewById(R.id.recyclerViewFlights);
        tvNoFlights = findViewById(R.id.tvNoFlights);

        recyclerViewFlights.setLayoutManager(new LinearLayoutManager(this));

        String source = getIntent().getStringExtra("SOURCE");
        String dest = getIntent().getStringExtra("DEST");
        String date = getIntent().getStringExtra("DATE");

        List<Flight> flightList = dbHelper.searchFlights(source, dest, date);

        if (flightList.isEmpty()) {
            tvNoFlights.setVisibility(View.VISIBLE);
            recyclerViewFlights.setVisibility(View.GONE);
        } else {
            tvNoFlights.setVisibility(View.GONE);
            recyclerViewFlights.setVisibility(View.VISIBLE);
            adapter = new FlightAdapter(this, flightList);
            recyclerViewFlights.setAdapter(adapter);
        }
    }
}
