package com.example.charity_booking;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderBooking extends RecyclerView.ViewHolder {

    public TextView name, date, time, donationtype, phone;

    public ViewHolderBooking(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.bookingname);
        date = itemView.findViewById(R.id.bookingdate);
        time = itemView.findViewById(R.id.bookingtime);
        donationtype = itemView.findViewById(R.id.bookingdonationtype);
        phone = itemView.findViewById(R.id.bookingphone);
    }
}