package com.example.charity_booking;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderCharity extends RecyclerView.ViewHolder {

    public TextView name, description, email, phone, address;
    public Button bookappointment;

    public ViewHolderCharity(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.charityNameTextView);
        description = itemView.findViewById(R.id.charityDescriptionTextView);
        email = itemView.findViewById(R.id.charityEmailTextView);
        phone = itemView.findViewById(R.id.charityPhoneTextView);
        address = itemView.findViewById(R.id.charityAddressTextView);
        bookappointment = itemView.findViewById(R.id.appointment_btn);
    }
}