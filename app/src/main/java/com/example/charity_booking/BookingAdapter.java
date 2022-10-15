package com.example.charity_booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<ViewHolderBooking> implements AdapterView.OnItemSelectedListener {

    UserBookings userBookings;
    ArrayList<Booking> bookingArrayList;

    public BookingAdapter(UserBookings userBookings, ArrayList<Booking> bookingArrayList) {
        this.userBookings = userBookings;
        this.bookingArrayList = bookingArrayList;
    }

    @NonNull
    @Override
    public ViewHolderBooking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(userBookings.getBaseContext());
        View view = layoutInflater.inflate(R.layout.booking_list, parent, false);
        return new ViewHolderBooking(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderBooking holder, final int position) {
        holder.name.setText(bookingArrayList.get(position).getName());
        holder.date.setText(bookingArrayList.get(position).getDate());
        holder.time.setText(bookingArrayList.get(position).getTime());
        holder.donationtype.setText(bookingArrayList.get(position).getDonationtype());
    }

    @Override
    public int getItemCount() {
        return bookingArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}