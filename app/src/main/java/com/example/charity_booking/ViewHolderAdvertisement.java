package com.example.charity_booking;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderAdvertisement extends RecyclerView.ViewHolder {

    public TextView name, description;
    public Button bookappointment;
    public ImageView imageView;

    public ViewHolderAdvertisement(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.adcharityNameTextView);
        description = itemView.findViewById(R.id.adDescriptionTextView);
        imageView = itemView.findViewById(R.id.adshowimage);
        bookappointment = itemView.findViewById(R.id.adappointment_btn);
    }
}