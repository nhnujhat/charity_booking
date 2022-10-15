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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class UserSignup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Charity App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        Button usersignupbtn = (Button) findViewById(R.id.usersignupbtn);
        Button usergotologinbtn = (Button) findViewById(R.id.usergotologinbtn);

        EditText username = findViewById(R.id.username);
        EditText useremail = findViewById(R.id.useremail);
        EditText userpassword = findViewById(R.id.userpassword);
        EditText userconfirmpassword = findViewById(R.id.userconfirmpassword);
        EditText userdescription = findViewById(R.id.userdescription);
        EditText userphone = findViewById(R.id.userphone);
        EditText useraddress = findViewById(R.id.useraddress);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        usersignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = username.getText().toString().toUpperCase();
                final String email = useremail.getText().toString();
                String password = userpassword.getText().toString();
                String confirmpassword = userconfirmpassword.getText().toString();
                final String description = userdescription.getText().toString();
                final String phone = userphone.getText().toString();
                final String address = useraddress.getText().toString();

                if (name.isEmpty() || name.length() < 7) {
                    showError(username, "Your Name is not valid");
                    return;
                }
                if (email.isEmpty() || !email.contains("@")) {
                    showError(useremail, "Email is not Valid");
                    return;
                }
                if (password.isEmpty() || password.length() < 7) {
                    showError(userpassword, "Password must be at least 7 characters");
                    return;
                }
                if (confirmpassword.isEmpty() || !confirmpassword.equals(password)) {
                    showError(userconfirmpassword, "Password does not match");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserSignup.this, "User Created", Toast.LENGTH_SHORT).show();
                            String userId = fAuth.getCurrentUser().getUid();
                            Log.d(TAG,"userid" + userId);
                            DocumentReference documentReference = fstore.collection("User").document(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Name", name);
                            user.put("Email", email);
                            user.put("Description", description);
                            user.put("Phone", phone);
                            user.put("Address", address);
                            user.put("Type", "User");
                            user.put("Userid", userId);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created");
                                }
                            });
                            DocumentReference documentReference3 = fstore.collection("Usertype").document(userId);
                            Map<String, Object> type = new HashMap<>();
                            type.put("Type", "User");

                            documentReference3.set(type).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user type is created");
                                }

                            });


                            startActivity(new Intent(getApplicationContext(), UserMainActivity.class));
                        } else {
                            Toast.makeText(UserSignup.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        usergotologinbtn.setOnClickListener(new View.OnClickListener() {
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