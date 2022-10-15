package com.example.charity_booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class UserProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText username, useremail, useroldpass, usernewpass, userconpass, userdescription, userphone, useraddress;
    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button userupdatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Charity App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.usernameupdate);
        useremail = findViewById(R.id.usermailupdate);
        useroldpass = findViewById(R.id.useroldpasswordupdate);
        usernewpass = findViewById(R.id.userpasswordupdate);
        userconpass= findViewById(R.id.userconfirmpasswordupdate);
        userdescription = findViewById(R.id.userdescriptionupdate);
        userphone = findViewById(R.id.userphoneupdate);
        useraddress = findViewById(R.id.useraddressupdate);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fStore.collection("User").document(fAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null)
                {
                    username.setText(value.getString("Name"));
                    userdescription.setText(value.getString("Description"));
                    userphone.setText(value.getString("Phone"));
                    useraddress.setText(value.getString("Address"));
                }}
        });

        userupdatebtn = findViewById(R.id.userupdatebtn);

        userupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = username.getText().toString();
                final String email = useremail.getText().toString();
                String password = useroldpass.getText().toString();
                String newpassword = usernewpass.getText().toString();
                String conpassword = userconpass.getText().toString();
                final String description = userdescription.getText().toString();
                final String phone = userphone.getText().toString();
                final String address = useraddress.getText().toString();

                if (name.length() < 7) {
                    showError(username, "Your Name is not valid");
                    return;
                }
                if((email.isEmpty() || password.isEmpty()) && !newpassword.isEmpty())
                {  showError(useremail, "Please input mail and old password");
                    return;
                }
                if (!newpassword.isEmpty() && newpassword.length() < 7) {
                    showError(usernewpass, "Password must be at least 7 characters");
                    return;
                }
                if ((conpassword.isEmpty() || !conpassword.equals(newpassword))&& !newpassword.isEmpty()) {
                    showError(userconpass, "Password does not match");
                    return;
                }

                if(!email.isEmpty() && !password.isEmpty() && !conpassword.isEmpty())
                {

                    AuthCredential credential = EmailAuthProvider.getCredential(email, password);

                    FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                FirebaseAuth.getInstance().getCurrentUser().updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        View coordinatorLayout;
                                        if(!task.isSuccessful()){
                                            Log.d(TAG, "Error");
                                        }else {
                                            useroldpass.setText("");
                                            usernewpass.setText("");
                                            userconpass.setText("");
                                            useremail.setText("");

                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(UserProfile.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                if(!name.isEmpty())
                {
                    fStore.collection("User").document(fAuth.getCurrentUser().getUid())
                            .update("Name", name)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    username.setText(name);
                                }
                            });

                }

                if(!email.isEmpty())
                {
                    fStore.collection("User").document(fAuth.getCurrentUser().getUid())
                            .update("Email", email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    useremail.setText(email);
                                }
                            });

                }

                if(!description.isEmpty())
                {
                    fStore.collection("User").document(fAuth.getCurrentUser().getUid())
                            .update("Description", description)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    userdescription.setText(description);
                                }
                            });

                }

                if(!phone.isEmpty())
                {
                    fStore.collection("User").document(fAuth.getCurrentUser().getUid())
                            .update("Phone", phone)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    userphone.setText(phone);
                                }
                            });

                }

                if(!address.isEmpty())
                {
                    fStore.collection("User").document(fAuth.getCurrentUser().getUid())
                            .update("Address", address)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    useraddress.setText(address);
                                }
                            });

                }
                Toast.makeText(UserProfile.this, "Update Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}