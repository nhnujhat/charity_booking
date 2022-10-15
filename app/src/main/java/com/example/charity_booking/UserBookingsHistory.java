package com.example.charity_booking;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.charity_booking.UserProfile.TAG;

public class UserBookingsHistory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView mRecyclerView;
    FirebaseAuth fAuth;
    FirebaseFirestore dbBooking;
    ArrayList<Booking> bookinghistoryArrayList;
    BookingHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Charity App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bookings_history);

        bookinghistoryArrayList = new ArrayList<>();

        fAuth = FirebaseAuth.getInstance();
        mRecyclerView = findViewById(R.id.bookinghistoryRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbBooking = FirebaseFirestore.getInstance();

        dbBooking.collection("Appointments")
                .whereEqualTo("Uid", fAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Booking booking = new Booking(querySnapshot.getString("Charityname"),
                                    querySnapshot.getString("Date"),
                                    querySnapshot.getString("Time"),
                                    querySnapshot.getString("Donationtype"));
                            String strdate = querySnapshot.getString("Date");
                            char div = '-';
                            Log.d(TAG, " firebase boooking date" + strdate.charAt(1) + strdate.charAt(3));

                            if(strdate.charAt(2)==div && strdate.charAt(4)==div) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy", Locale.ENGLISH);
                                LocalDate date = LocalDate.parse(strdate, formatter);
                                String timeStamp = new SimpleDateFormat("dd-M-yyyy").format(Calendar.getInstance().getTime());
                                LocalDate date2 = LocalDate.parse(timeStamp, formatter);
                                Log.d(TAG, "boooking date" + date.toString() + "this date" + date2.toString());
                                if(date.isBefore(date2)) bookinghistoryArrayList.add(booking);
                            }
                            else if(strdate.charAt(2)==div && strdate.charAt(5)==div) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
                                LocalDate date = LocalDate.parse(strdate, formatter);
                                String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                                LocalDate date2 = LocalDate.parse(timeStamp, formatter);
                                Log.d(TAG, "boooking date" + date.toString() + "this date" + date2.toString());
                                if(date.isBefore(date2)) bookinghistoryArrayList.add(booking);
                            }
                            else if(strdate.charAt(1)==div && strdate.charAt(4)==div) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy", Locale.ENGLISH);
                                LocalDate date = LocalDate.parse(strdate, formatter);
                                String timeStamp = new SimpleDateFormat("d-MM-yyyy").format(Calendar.getInstance().getTime());
                                LocalDate date2 = LocalDate.parse(timeStamp, formatter);
                                Log.d(TAG, "boooking date" + date.toString() + "this date" + date2.toString());
                                if(date.isBefore(date2)) bookinghistoryArrayList.add(booking);
                            }
                            else if(strdate.charAt(1)==div && strdate.charAt(3)==div) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy", Locale.ENGLISH);
                                LocalDate date = LocalDate.parse(strdate, formatter);
                                String timeStamp = new SimpleDateFormat("d-M-yyyy").format(Calendar.getInstance().getTime());
                                LocalDate date2 = LocalDate.parse(timeStamp, formatter);
                                Log.d(TAG, "boooking date" + date.toString() + "this date" + date2.toString());
                                if(date.isBefore(date2)) bookinghistoryArrayList.add(booking);
                            }
                        }
                        adapter = new BookingHistoryAdapter(UserBookingsHistory.this, bookinghistoryArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserBookingsHistory.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}