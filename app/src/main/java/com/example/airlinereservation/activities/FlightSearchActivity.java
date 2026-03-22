package com.example.airlinereservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.airlinereservation.R;

public class FlightSearchActivity extends AppCompatActivity {

    private EditText etSource, etDestination, etDate;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);

        etSource = findViewById(R.id.etSource);
        etDestination = findViewById(R.id.etDestination);
        etDate = findViewById(R.id.etDate);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            String source = etSource.getText().toString().trim();
            String dest = etDestination.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            Intent intent = new Intent(FlightSearchActivity.this, FlightResultsActivity.class);
            intent.putExtra("SOURCE", source);
            intent.putExtra("DEST", dest);
            intent.putExtra("DATE", date);
            startActivity(intent);
        });
    }
}
