package com.example.aviokarte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Flights extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Button btnSearchFlights, btnA;
    TextView tvDate;
    String start, end, date;
    private static final String TAG = "Destinacije";
    List<String> list = new ArrayList<>();
    Spinner startSpinner, endSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        btnSearchFlights = findViewById(R.id.btnSearchFlights);
        btnA = findViewById(R.id.btnAllFlights);
        tvDate = findViewById(R.id.tvDate);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        start = "";
        end = "";

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("destinacije");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Destinacije destinacije = new Destinacije();
                    destinacije.naziv = data.child("naziv").getValue().toString();
                    list.add(destinacije.naziv);
                }

                addItemsOnSpinner1(list);
                addItemsOnSpinner2(list);
                addListenerOnSpinnerItemSelection();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Flights.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        date = year+"-"+month+"-"+dayOfMonth;
                        tvDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnSearchFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Flights.this, IspisLetovi.class);
                if (!start.isEmpty() && !end.isEmpty() && date != null) {
                    intent.putExtra("istart", start);
                    intent.putExtra("iend", end);
                    intent.putExtra("idate", date);
                    intent.putExtra("return", "");
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Unesite sve podatke", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Flights.this, IspisLetovi.class);
                if(!start.isEmpty()){
                    intent.putExtra("istart", start);
                    intent.putExtra("iend", end);
                    intent.putExtra("idate", "ne");
                    intent.putExtra("return", "");
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Unesite polazište", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void addItemsOnSpinner1(List<String> data) {

        startSpinner = (Spinner) findViewById(R.id.start_spinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner2(List<String> data) {

        endSpinner = (Spinner) findViewById(R.id.end_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        startSpinner = (Spinner) findViewById(R.id.start_spinner);
        startSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               start = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        endSpinner = (Spinner) findViewById(R.id.end_spinner);
        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



}