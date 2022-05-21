package com.example.aviokarte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IspisLetovi extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    List<Letovi> list = new ArrayList<>();
    private static final String TAG = "ListActivity";
    SimpleDateFormat format;
    Date iDatum;
    Date vhDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ispis_letovi);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("letovi");

        RecyclerView recyclerView = findViewById(R.id.rvIspisLetovi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LetoviAdapter letoviAdapter = new LetoviAdapter(this, list);
        recyclerView.setAdapter(letoviAdapter);

        format = new SimpleDateFormat("yyyy-MM-dd");
        String idt = getIntent().getStringExtra("idate");
        try {
            iDatum = format.parse(idt);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Letovi letovi = new Letovi();

                    letovi.start = data.child("start").getValue().toString();
                    letovi.end = data.child("end").getValue().toString();
                    letovi.tstart = data.child("tstart").getValue().toString();
                    letovi.tend = data.child("tend").getValue().toString();
                    letovi.price = Integer.parseInt(data.child("price").getValue().toString());
                    letovi.date = data.child("date").getValue().toString();

                    try {
                        vhDatum = format.parse(letovi.date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if(letovi.start.equalsIgnoreCase(getIntent().getStringExtra("istart"))
                            && letovi.end.equalsIgnoreCase(getIntent().getStringExtra("iend"))
                            && vhDatum.equals(iDatum)){
                        list.add(letovi);
                    }

                }

                if(list.size() == 0) {
                    Letovi prazno = new Letovi();
                    prazno.tstart = "NEMA REZULTATA";
                    list.add(prazno);
                }

                recyclerView.setAdapter(letoviAdapter);


                Log.d(TAG, "Value is: " + list.get(0).start);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}