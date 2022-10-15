package com.example.charity_booking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class BookingFormAdvertise extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Button btnDatePicker, btnTimePicker, confirmbtn;
    EditText bookingusername, bookinguserphone, bookinguserdonationtype;
    TextView txtDate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int mYear2, mMonth2, mDay2, mHour2, mMinute2;
    String charityname = "";
    private Spinner btntime;
    ArrayList<String> slots;
    ArrayList<String> timeArrayList;
    ArrayList<String> bookedtimeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Charity App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        slots = new ArrayList<>();
        slots.add("8:00");
        slots.add("9:00");
        slots.add("10:00");
        slots.add("11:00");
        slots.add("12:00");
        slots.add("13:00");
        slots.add("14:00");
        slots.add("15:00");
        slots.add("16:00");
        slots.add("17:00");
        timeArrayList = new ArrayList<>();
        bookedtimeArrayList = new ArrayList<>();

        Intent intent = getIntent();
        final String cid = intent.getStringExtra(UserMainActivity.EXTRA_TEXT3);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        bookingusername = (EditText) findViewById(R.id.bookingusername);
        bookinguserphone = (EditText) findViewById(R.id.bookinguserphone);
        bookinguserdonationtype = (EditText) findViewById(R.id.bookinguserdonationtype);
        confirmbtn = (Button) findViewById(R.id.confirmbtn);

        btnDatePicker = findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(this);
        btntime = findViewById(R.id.btn_time);
        btntime.setOnItemSelectedListener(this);

        txtDate = (TextView) findViewById(R.id.in_date);

        btntime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String slot = parent.getItemAtPosition(position).toString();
                //txtTime.setText(slot);
                Toast.makeText(parent.getContext(), "Selected: " + slot, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        DocumentReference docRef = fstore.collection("Charity").document(cid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        charityname = charityname + document.getString("Name");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "got failed with ", task.getException());
                }
            }
        });

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = bookingusername.getText().toString().toUpperCase();
                final String phone = bookinguserphone.getText().toString();
                final String donationtype = bookinguserdonationtype.getText().toString();
                final String date = txtDate.getText().toString().toUpperCase();
                //final String time = txtTime.getText().toString().toUpperCase();

                if (name.isEmpty()) {
                    showError(bookingusername, "You have to insert a Name");
                    return;
                }
                if (phone.isEmpty()) {
                    showError(bookinguserphone, "You have to insert a Phone Number");
                    return;
                }
                if (donationtype.isEmpty()) {
                    showError(bookinguserdonationtype, "You have to insert the Type of Donation");
                    return;
                }
                if (date.isEmpty()||date.equals("Selected Date")) {
                    showError(txtDate, "You have to select a Date");
                    return;
                }
                /*if (time.isEmpty()||time.equals("Selected Time")) {
                    showError(txtTime, "You have to select a Time");
                    return;
                }*/

                String timeslot = btntime.getSelectedItem().toString();
                //txtTime.setText(timeslot);

                DocumentReference documentReference = fstore.collection("Appointments").document();
                Map<String, Object> appointment = new HashMap<>();
                appointment.put("Name", name);
                appointment.put("Phone", phone);
                appointment.put("Date", mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                appointment.put("Time", timeslot);
                appointment.put("Donationtype", donationtype);
                appointment.put("Uid", fAuth.getCurrentUser().getUid());
                appointment.put("Charityname", charityname);
                appointment.put("Charityid", cid);
                documentReference.set(appointment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: appointment is created");
                    }
                });

                DocumentReference documentReference2 = fstore.collection(timeslot).document(txtDate.getText().toString());
                Map<String, Object> bookedslot = new HashMap<>();
                bookedslot.put("Booked", "Yes");
                bookedslot.put("Timeslot", timeslot);
                documentReference2.set(bookedslot).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: slot is booked");
                    }
                });

                startActivity(new Intent(getApplicationContext(), UserMainActivity.class));
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
    private void showError(TextView input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BookingFormAdvertise.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                mYear2 = year;
                mMonth2 = monthOfYear;
                mDay2 = dayOfMonth;

                FirebaseFirestore fstore = FirebaseFirestore.getInstance();

                slots = new ArrayList<>();
                slots.add("8:00");
                slots.add("9:00");
                slots.add("10:00");
                slots.add("11:00");
                slots.add("12:00");
                slots.add("13:00");
                slots.add("14:00");
                slots.add("15:00");
                slots.add("16:00");
                slots.add("17:00");


                Log.d(TAG, "booker");
                DocumentReference docRef1 = fstore.collection("8:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("8:00");
                            } else {
                                timeArrayList.add("8:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef2 = fstore.collection("9:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("9:00");
                            } else {
                                timeArrayList.add("9:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef3 = fstore.collection("10:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("10:00");
                            } else {
                                timeArrayList.add("10:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef4 = fstore.collection("11:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef4.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("11:00");
                            } else {
                                timeArrayList.add("11:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef5 = fstore.collection("12:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef5.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("12:00");
                            } else {
                                timeArrayList.add("12:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef6 = fstore.collection("13:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef6.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("13:00");
                            } else {
                                timeArrayList.add("13:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef7 = fstore.collection("14:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef7.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("14:00");
                            } else {
                                timeArrayList.add("14:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef8 = fstore.collection("15:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef8.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("15:00");
                                Log.d(TAG, "booker" + mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                            } else {
                                timeArrayList.add("15:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef9 = fstore.collection("16:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef9.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("16:00");
                            } else {
                                timeArrayList.add("16:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                DocumentReference docRef10 = fstore.collection("17:00").document(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);
                docRef10.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                slots.remove("17:00");
                            } else {
                                timeArrayList.add("17:00");
                            }
                        } else {
                            Log.d(TAG, "got failed with ", task.getException());
                        }
                    }
                });
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(BookingFormAdvertise.this, android.R.layout.simple_spinner_item, slots);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                btntime.setAdapter(spinnerArrayAdapter);

            }
        }, mYear, mMonth, mDay);
        long now = System.currentTimeMillis() - 1000;
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.getDatePicker().setMaxDate(now + (1000*60*60*24*7));

        datePickerDialog.show();
    }
}