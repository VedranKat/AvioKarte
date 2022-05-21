package com.example.aviokarte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Flights extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Button btnSearchFlights;
    EditText etStart, etEnd;
    TextView tvDate;
    DatePickerDialog.OnDateSetListener setListener;
    String start, end, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        btnSearchFlights = findViewById(R.id.btnSearchFlights);
        etStart = findViewById(R.id.etStart);
        etEnd = findViewById(R.id.etEnd);
        tvDate = findViewById(R.id.tvDate);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                start = etStart.getText().toString();
                end = etEnd.getText().toString();
                Intent intent = new Intent(Flights.this, IspisLetovi.class);
                if (start.isEmpty() == false && end.isEmpty() == false && date.isEmpty() == false) {
                    intent.putExtra("istart", start);
                    intent.putExtra("iend", end);
                    intent.putExtra("idate", date);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Unesite sve podatke", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}