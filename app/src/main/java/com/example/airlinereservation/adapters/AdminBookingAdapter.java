package com.example.airlinereservation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airlinereservation.R;
import com.example.airlinereservation.models.Booking;

import java.util.List;

public class AdminBookingAdapter extends RecyclerView.Adapter<AdminBookingAdapter.AdminBookingViewHolder> {

    private Context context;
    private List<Booking> adminBookingList;

    public AdminBookingAdapter(Context context, List<Booking> adminBookingList) {
        this.context = context;
        this.adminBookingList = adminBookingList;
    }

    @NonNull
    @Override
    public AdminBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_booking, parent, false);
        return new AdminBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminBookingViewHolder holder, int position) {
        Booking booking = adminBookingList.get(position);
        holder.tvBookingIdUser.setText("Booking ID: " + booking.getId() + " - User: " + booking.getUserEmail());
        holder.tvFlightName.setText(booking.getFlightName() + " (" + booking.getTime() + ")");
        holder.tvPassengerInfo.setText("Passenger: " + booking.getPassengerName() + ", Age: " + booking.getPassengerAge());
        holder.tvRoute.setText(booking.getSource() + " \u2192 " + booking.getDestination());
    }

    @Override
    public int getItemCount() {
        return adminBookingList.size();
    }

    public static class AdminBookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookingIdUser, tvFlightName, tvPassengerInfo, tvRoute;

        public AdminBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookingIdUser = itemView.findViewById(R.id.tvBookingIdUser);
            tvFlightName = itemView.findViewById(R.id.tvFlightName);
            tvPassengerInfo = itemView.findViewById(R.id.tvPassengerInfo);
            tvRoute = itemView.findViewById(R.id.tvRoute);
        }
    }
}
