package com.example.charity_booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CharityAdapter extends RecyclerView.Adapter<ViewHolderCharity> implements AdapterView.OnItemSelectedListener {

    CharityList charityList;
    ArrayList<Charity> charityArrayList;

    public CharityAdapter(CharityList charityList, ArrayList<Charity> charityArrayList) {
        this.charityList = charityList;
        this.charityArrayList = charityArrayList;
    }

    @NonNull
    @Override
    public ViewHolderCharity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(charityList.getBaseContext());
        View view = layoutInflater.inflate(R.layout.charity_list, parent, false);
        return new ViewHolderCharity(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCharity holder, final int position) {
        holder.name.setText(charityArrayList.get(position).getName());
        holder.description.setText(charityArrayList.get(position).getDescription());
        holder.email.setText(charityArrayList.get(position).getEmail());
        holder.phone.setText(charityArrayList.get(position).getPhone());
        holder.address.setText(charityArrayList.get(position).getAddress());

        holder.bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String an = charityArrayList.get(position).getCharityid();
                charityList.appointment(an);
            }
        });
    }

    @Override
    public int getItemCount() {
        return charityArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}