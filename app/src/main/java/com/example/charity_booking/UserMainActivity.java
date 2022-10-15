package com.example.charity_booking;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView mRecyclerView3;
    ArrayList<Advertise> advertiseArrayList;
    AdvertiseAdapter adapter;

    public static final String EXTRA_TEXT3 = "com.example.application.example.EXTRA_TEXT3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Charity App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        advertiseArrayList = new ArrayList<>();

        mRecyclerView3 = findViewById(R.id.advertisementRV);
        mRecyclerView3.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView3.setLayoutManager(layoutManager);

        Button userbookingstbtn = (Button)findViewById(R.id.userbookingstbtn);
        Button usercharitylisttbtn = (Button)findViewById(R.id.usercharitylisttbtn);
        Button userprofiletbtn = (Button)findViewById(R.id.userprofilebtn);
        Button userlogouttbtn = (Button)findViewById(R.id.userlogouttbtn);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        userbookingstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), UserBookings.class));
            }
        });

        usercharitylisttbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), CharityList.class));
            }
        });

        userprofiletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });

        userlogouttbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if( fAuth.getInstance()!=null)
                {
                    fAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        });

        fstore.collection("Advertisements")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Advertise advertise = new Advertise(querySnapshot.getString("Charity"),
                                    querySnapshot.getString("Charityid"),
                                    querySnapshot.getString("Description"),
                                    querySnapshot.getString("Downloadlink"));
                            advertiseArrayList.add(advertise);
                        }
                        adapter = new AdvertiseAdapter(UserMainActivity.this, advertiseArrayList);
                        mRecyclerView3.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserMainActivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    public void appointment(String s) {
        Intent intent = new Intent(UserMainActivity.this, BookingFormAdvertise.class);
        intent.putExtra(EXTRA_TEXT3, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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