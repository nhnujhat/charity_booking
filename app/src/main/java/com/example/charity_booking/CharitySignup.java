package com.example.charity_booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class CharitySignup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Charity App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_signup);

        Button charitysignupbtn = (Button) findViewById(R.id.charitysignupbtn);
        Button charitygotologinbtn = (Button) findViewById(R.id.charitygotologinbtn);

        EditText charityname = findViewById(R.id.charityname);
        EditText charityemail = findViewById(R.id.charityemail);
        EditText charitypassword = findViewById(R.id.charitypassword);
        EditText charityconfirmpassword = findViewById(R.id.charityconfirmpassword);
        EditText charitydescription = findViewById(R.id.charitydescription);
        EditText charityphone = findViewById(R.id.charityphone);
        EditText charityaddress = findViewById(R.id.charityaddress);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        charitysignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = charityname.getText().toString().toUpperCase();
                final String email = charityemail.getText().toString();
                String password = charitypassword.getText().toString();
                String confirmpassword = charityconfirmpassword.getText().toString();
                final String description = charitydescription.getText().toString();
                final String phone = charityphone.getText().toString();
                final String address = charityaddress.getText().toString();

                if (name.isEmpty() || name.length() < 7) {
                    showError(charityname, "Your Name is not valid");
                    return;
                }
                if (email.isEmpty() || !email.contains("@")) {
                    showError(charityemail, "Email is not Valid");
                    return;
                }
                if (password.isEmpty() || password.length() < 7) {
                    showError(charitypassword, "Password must be at least 7 characters");
                    return;
                }
                if (confirmpassword.isEmpty() || !confirmpassword.equals(password)) {
                    showError(charityconfirmpassword, "Password does not match");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CharitySignup.this, "Charity Created", Toast.LENGTH_SHORT).show();
                            String userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("Charity").document(userId);
                            Map<String, Object> charity = new HashMap<>();
                            charity.put("Name", name);
                            charity.put("Email", email);
                            charity.put("Description", description);
                            charity.put("Phone", phone);
                            charity.put("Address", address);
                            charity.put("Type", "Charity");
                            charity.put("Charityid",userId);
                            documentReference.set(charity).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                            DocumentReference documentReference3 = fstore.collection("Usertype").document(userId);
                            Map<String, Object> type = new HashMap<>();
                            type.put("Type", "Charity");

                            documentReference3.set(type).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }

                            });


                            startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                        } else {
                            Toast.makeText(CharitySignup.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        charitygotologinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
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