package com.example.airlinereservation.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.airlinereservation.R;
import com.example.airlinereservation.database.DatabaseHelper;
import com.example.airlinereservation.models.Flight;

public class AddEditFlightActivity extends AppCompatActivity {

    private EditText etFlightName, etSource, etDestination, etTime, etPrice;
    private Button btnSaveFlight;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_flight);

        dbHelper = new DatabaseHelper(this);
        etFlightName = findViewById(R.id.etFlightName);
        etSource = findViewById(R.id.etSource);
        etDestination = findViewById(R.id.etDestination);
        etTime = findViewById(R.id.etTime);
        etPrice = findViewById(R.id.etPrice);
        btnSaveFlight = findViewById(R.id.btnSaveFlight);

        btnSaveFlight.setOnClickListener(v -> saveFlight());
    }

    private void saveFlight() {
        String name = etFlightName.getText().toString().trim();
        String source = etSource.getText().toString().trim();
        String dest = etDestination.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();

        if (name.isEmpty() || source.isEmpty() || dest.isEmpty() || time.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        Flight flight = new Flight(0, name, source, dest, time, price);
        boolean isAdded = dbHelper.addFlight(flight);

        if (isAdded) {
            Toast.makeText(this, "Flight Added Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to Add Flight", Toast.LENGTH_SHORT).show();
        }
    }
}
