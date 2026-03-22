package com.example.airlinereservation.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airlinereservation.R;
import com.example.airlinereservation.activities.BookingActivity;
import com.example.airlinereservation.models.Flight;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private Context context;
    private List<Flight> flightList;

    public FlightAdapter(Context context, List<Flight> flightList) {
        this.context = context;
        this.flightList = flightList;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flight, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = flightList.get(position);
        holder.tvFlightName.setText(flight.getAirlineName());
        holder.tvSource.setText(flight.getSource());
        holder.tvDestination.setText(flight.getDestination());
        holder.tvTime.setText("Time: " + flight.getTime());
        holder.tvPrice.setText("$" + flight.getPrice());

        holder.btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingActivity.class);
            intent.putExtra("FLIGHT_ID", flight.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView tvFlightName, tvSource, tvDestination, tvTime, tvPrice;
        Button btnBook;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightName = itemView.findViewById(R.id.tvFlightName);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnBook = itemView.findViewById(R.id.btnAction); // Using btnAction defined in layout
            if(btnBook != null) {
                btnBook.setText("Book Now");
            }
        }
    }
}
