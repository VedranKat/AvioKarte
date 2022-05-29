package com.example.aviokarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MojeKarte extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    List<Karte> list = new ArrayList<>();
    private static final String TAG = "MojeKarte";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moje_karte);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        RecyclerView recyclerView = findViewById(R.id.rvIspisKarte);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("karte");

        KarteAdapter karteAdapter = new KarteAdapter(this, list);
        recyclerView.setAdapter(karteAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Karte karte = new Karte();


                    karte.cijena = data.child("cijena").getValue().toString();
                    karte.vlasnik = data.child("vlasnik").getValue().toString();
                    karte.letovi = new ArrayList<Letovi>();


                    for(DataSnapshot adSnapshot: data.child("letovi").getChildren()){

                        Letovi letovi = new Letovi();

                        letovi.start = adSnapshot.child("start").getValue().toString();
                        letovi.end = adSnapshot.child("end").getValue().toString();
                        letovi.tstart = adSnapshot.child("tstart").getValue().toString();
                        letovi.tend = adSnapshot.child("tend").getValue().toString();
                        letovi.price = Integer.parseInt(adSnapshot.child("price").getValue().toString());
                        letovi.date = adSnapshot.child("date").getValue().toString();

                        karte.letovi.add(letovi);


                    }

                    if(karte.vlasnik.equals(currentUser.getEmail())){
                        list.add(karte);
                    }
                }

                recyclerView.setAdapter(karteAdapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}