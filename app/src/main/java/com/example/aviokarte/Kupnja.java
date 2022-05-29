package com.example.aviokarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Kupnja extends AppCompatActivity {

    TextView tv1s, tv1e, tv1ts, tv1te, tv1pr, tv1d,
    tv2s, tv2e, tv2ts, tv2te, tv2pr, tv2d, tvFP;
    Button btnCancel, btnBuy, button2;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference mbase;
    String korisnik;
    Letovi let1, let2;
    public int aj = 0;
    public int maxid = 0;
    AlertDialog alert;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kupnja);

        int fst = 0;
        int snd = 0;
        aj = 0;

        let1 = new Letovi();
        let2 = new Letovi();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        korisnik = user.getEmail();
        mbase = FirebaseDatabase.getInstance().getReference("karte");

        tv1s = findViewById(R.id.tv1Start);
        tv1e = findViewById(R.id.tv1End);
        tv1ts = findViewById(R.id.tv1tsStart);
        tv1te = findViewById(R.id.tv1tsEnd);
        tv1pr = findViewById(R.id.tv1Price);
        tv1d = findViewById(R.id.tv1Date);

        tv2s = findViewById(R.id.tv2Start);
        tv2e = findViewById(R.id.tv2End);
        tv2ts = findViewById(R.id.tv2tsStart);
        tv2te = findViewById(R.id.tv2tsEnd);
        tv2pr = findViewById(R.id.tv2Price);
        tv2d = findViewById(R.id.tv2Date);

        tvFP = findViewById(R.id.tvFinalPrice);

        btnCancel = findViewById(R.id.btnCancel);
        btnBuy = findViewById(R.id.btnBuy);

        fst = getIntent().getIntExtra("1Price",0);
        snd = getIntent().getIntExtra("2Price",0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Da li ste sigurni da želite kupiti ovu kartu?");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Karte karte = new Karte();
                        karte.cijena = String.valueOf(aj);
                        karte.vlasnik = korisnik;
                        karte.letovi = new ArrayList<Letovi>();
                        karte.letovi.add(let1);
                        karte.letovi.add(let2);
                        mbase.child(String.valueOf(maxid)).setValue(karte);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Kupnja karte uspješna", Toast.LENGTH_LONG).show();
                            }
                        });
                        startActivity(new Intent(Kupnja.this, MainActivity.class));
                    }
                });
            }
        });
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert = builder.create();


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid =(int)(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        if(snd == 0){
            let1.start = getIntent().getStringExtra("1Start");
            let1.end = getIntent().getStringExtra("1End");

            tv1s.setText(getIntent().getStringExtra("1Start"));
            tv1e.setText(getIntent().getStringExtra("1End"));
            button2 = findViewById(R.id.button2);
            button2.setVisibility(View.INVISIBLE);
            tv2pr.setVisibility(View.INVISIBLE);

            let2.start = "n/a";
            let2.end = "n/a";
            let2.tstart = "n/a";
            let2.tend = "n/a";
            let2.price = 0;
            let2.date = "n/a";;

        }else{
            let1.start = getIntent().getStringExtra("2End");
            let1.end = getIntent().getStringExtra("2Start");

            let2.start = getIntent().getStringExtra("2Start");
            let2.end = getIntent().getStringExtra("2End");
            let2.tstart = getIntent().getStringExtra("2tStart");
            let2.tend = getIntent().getStringExtra("2tEnd");
            let2.price = snd;
            let2.date = getIntent().getStringExtra("2date");

            tv1s.setText(getIntent().getStringExtra("2End"));
            tv1e.setText(getIntent().getStringExtra("2Start"));
        }

        let1.tstart = getIntent().getStringExtra("1tStart");
        let1.tend = getIntent().getStringExtra("1tEnd");
        let1.price = fst;
        let1.date = getIntent().getStringExtra("1date");

        tv1ts.setText(getIntent().getStringExtra("1tStart"));
        tv1te.setText(getIntent().getStringExtra("1tEnd"));
        tv1pr.setText(fst+"$");
        tv1d.setText(getIntent().getStringExtra("1date"));

        tv2s.setText(getIntent().getStringExtra("2Start"));
        tv2e.setText(getIntent().getStringExtra("2End"));
        tv2ts.setText(getIntent().getStringExtra("2tStart"));
        tv2te.setText(getIntent().getStringExtra("2tEnd"));
        tv2pr.setText(snd+"$");
        tv2d.setText(getIntent().getStringExtra("2date"));

        aj = fst+snd;
        tvFP.setText("Ukupna Cijena: "+(aj)+"$");


    }
}