package com.example.charity_booking;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AdminMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Charity App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Button charitybookingstbtn = (Button)findViewById(R.id.charitybookingstbtn);
        Button charityadtbtn = (Button)findViewById(R.id.charityadbtn);
        Button charityprofiletbtn = (Button)findViewById(R.id.charityprofilebtn);
        Button charitylogouttbtn = (Button)findViewById(R.id.charitylogoutbtn);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        charitybookingstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), CharityBookings.class));
            }
        });

        charityadtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), Advertisement.class));
            }
        });

        charityprofiletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), AdminProfile.class));
            }
        });

        charitylogouttbtn.setOnClickListener(new View.OnClickListener() {
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