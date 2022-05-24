package com.example.aviokarte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Kupnja extends AppCompatActivity {

    TextView tv1s, tv1e, tv1ts, tv1te, tv1pr, tv1d,
    tv2s, tv2e, tv2ts, tv2te, tv2pr, tv2d, tvFP;
    Button btnCancel, btnBuy, button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kupnja);

        int fst = 0;
        int snd = 0;
        int aj = 0;

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

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        if(snd == 0){
            tv1s.setText(getIntent().getStringExtra("1Start"));
            tv1e.setText(getIntent().getStringExtra("1End"));
            button2 = findViewById(R.id.button2);
            button2.setVisibility(View.INVISIBLE);
            tv2pr.setVisibility(View.INVISIBLE);

        }else{
            tv1s.setText(getIntent().getStringExtra("2End"));
            tv1e.setText(getIntent().getStringExtra("2Start"));
        }

        tv1ts.setText(getIntent().getStringExtra("1tStart"));
        tv1te.setText(getIntent().getStringExtra("1tEnd"));
        tv1pr.setText(String.valueOf(fst));
        tv1d.setText(getIntent().getStringExtra("1Date"));

        tv2s.setText(getIntent().getStringExtra("2Start"));
        tv2e.setText(getIntent().getStringExtra("2End"));
        tv2ts.setText(getIntent().getStringExtra("2tStart"));
        tv2te.setText(getIntent().getStringExtra("2tEnd"));
        tv2pr.setText(String.valueOf(snd));
        tv2d.setText(getIntent().getStringExtra("2Date"));

        aj = fst+snd;
        tvFP.setText("Ukupna Cijena: "+(aj)+"$");


    }
}