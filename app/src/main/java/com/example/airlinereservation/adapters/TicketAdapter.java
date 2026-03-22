package com.example.airlinereservation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airlinereservation.R;
import com.example.airlinereservation.database.DatabaseHelper;
import com.example.airlinereservation.models.Booking;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private Context context;
    private List<Booking> ticketList;
    private DatabaseHelper dbHelper;

    public TicketAdapter(Context context, List<Booking> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Booking ticket = ticketList.get(position);
        holder.tvFlightName.setText(ticket.getFlightName());
        holder.tvPassenger.setText("Passenger: " + ticket.getPassengerName());
        holder.tvSource.setText(ticket.getSource());
        holder.tvDestination.setText(ticket.getDestination());
        holder.tvTime.setText("Time: " + ticket.getTime());

        holder.btnCancel.setOnClickListener(v -> {
            boolean isCancelled = dbHelper.cancelTicket(ticket.getId());
            if (isCancelled) {
                ticketList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, ticketList.size());
                Toast.makeText(context, "Ticket Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvFlightName, tvPassenger, tvSource, tvDestination, tvTime;
        Button btnCancel;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightName = itemView.findViewById(R.id.tvFlightName);
            tvPassenger = itemView.findViewById(R.id.tvPassenger);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvTime = itemView.findViewById(R.id.tvTime);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
