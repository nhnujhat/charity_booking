package com.example.charity_booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdvertiseAdapter extends RecyclerView.Adapter<ViewHolderAdvertisement> implements AdapterView.OnItemSelectedListener {

    UserMainActivity userMainActivity;
    ArrayList<Advertise> advertiseArrayList;

    public AdvertiseAdapter(UserMainActivity userMainActivity, ArrayList<Advertise> advertiseArrayList) {
        this.userMainActivity = userMainActivity;
        this.advertiseArrayList = advertiseArrayList;
    }

    @NonNull
    @Override
    public ViewHolderAdvertisement onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(userMainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.advertisement_list, parent, false);
        return new ViewHolderAdvertisement(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderAdvertisement holder, final int position) {
        holder.name.setText(advertiseArrayList.get(position).getCharity());
        holder.description.setText(advertiseArrayList.get(position).getDescription());

        String imguri = null;
        imguri = advertiseArrayList.get(position).getDownloadlink();
        Picasso.get().load(imguri).into(holder.imageView);

        holder.bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String an = advertiseArrayList.get(position).getCharityid();
                userMainActivity.appointment(an);
            }
        });
    }

    @Override
    public int getItemCount() {
        return advertiseArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}