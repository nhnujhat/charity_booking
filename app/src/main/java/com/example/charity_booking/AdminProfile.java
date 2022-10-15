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

public class AdminProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText charityname, charityemail, charityoldpass, charitynewpass, charityconpass, charitydescription, charityphone, charityaddress;
    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button charityupdatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Charity App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        charityname = findViewById(R.id.charitynameupdate);
        charityemail = findViewById(R.id.charitymailupdate);
        charityoldpass = findViewById(R.id.charityoldpasswordupdate);
        charitynewpass = findViewById(R.id.charitypasswordupdate);
        charityconpass= findViewById(R.id.charityconfirmpasswordupdate);
        charitydescription = findViewById(R.id.charitydescriptionupdate);
        charityphone = findViewById(R.id.charityphoneupdate);
        charityaddress = findViewById(R.id.charityaddressupdate);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fStore.collection("Charity").document(fAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null)
                {
                    charityname.setText(value.getString("Name"));
                    charitydescription.setText(value.getString("Description"));
                    charityphone.setText(value.getString("Phone"));
                    charityaddress.setText(value.getString("Address"));
                }}
        });

        charityupdatebtn = findViewById(R.id.charityupdatebtn);

        charityupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = charityname.getText().toString();
                final String email = charityemail.getText().toString();
                String password = charityoldpass.getText().toString();
                String newpassword = charitynewpass.getText().toString();
                String conpassword = charityconpass.getText().toString();
                final String description = charitydescription.getText().toString();
                final String phone = charityphone.getText().toString();
                final String address = charityaddress.getText().toString();

                if (name.length() < 7) {
                    showError(charityname, "Your Name is not valid");
                    return;
                }
                if((email.isEmpty() || password.isEmpty()) && !newpassword.isEmpty())
                {  showError(charityemail, "Please input mail and old password");
                    return;
                }
                if (!newpassword.isEmpty() && newpassword.length() < 7) {
                    showError(charitynewpass, "Password must be at least 7 characters");
                    return;
                }
                if ((conpassword.isEmpty() || !conpassword.equals(newpassword))&& !newpassword.isEmpty()) {
                    showError(charityconpass, "Password does not match");
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
                                            charityoldpass.setText("");
                                            charitynewpass.setText("");
                                            charityconpass.setText("");
                                            charityemail.setText("");

                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(AdminProfile.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                if(!name.isEmpty())
                {
                    fStore.collection("Charity").document(fAuth.getCurrentUser().getUid())
                            .update("Name", name)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    charityname.setText(name);
                                }
                            });

                }

                if(!email.isEmpty())
                {
                    fStore.collection("Charity").document(fAuth.getCurrentUser().getUid())
                            .update("Email", email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    charityemail.setText(email);
                                }
                            });

                }

                if(!description.isEmpty())
                {
                    fStore.collection("Charity").document(fAuth.getCurrentUser().getUid())
                            .update("Description", description)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    charitydescription.setText(description);
                                }
                            });

                }

                if(!phone.isEmpty())
                {
                    fStore.collection("Charity").document(fAuth.getCurrentUser().getUid())
                            .update("Phone", phone)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    charityphone.setText(phone);
                                }
                            });

                }

                if(!address.isEmpty())
                {
                    fStore.collection("Charity").document(fAuth.getCurrentUser().getUid())
                            .update("Address", address)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    charityaddress.setText(address);
                                }
                            });

                }
                Toast.makeText(AdminProfile.this, "Update Successful", Toast.LENGTH_SHORT).show();
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