package com.example.aviokarte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KarteAdapter extends RecyclerView.Adapter<KarteViewHolder> {

    private List<Karte> data;
    Context context;

    public KarteAdapter(Context ct, List<Karte> firebaseData){
        context = ct;
        data = firebaseData;
    }


    @NonNull
    @Override
    public KarteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vh_karte_layout, parent, false);
        return new KarteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KarteViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class KarteViewHolder extends RecyclerView.ViewHolder {

    TextView tv1s, tv1e, tv1ts, tv1te, tv1pr, tv1d,
            tv2s, tv2e, tv2ts, tv2te, tv2pr, tv2d, tvFP;

    public KarteViewHolder(@NonNull View itemView){
        super(itemView);

        tv1s = itemView.findViewById(R.id.tv1StartKarte);
        tv1e = itemView.findViewById(R.id.tv1EndKarte);
        tv1ts = itemView.findViewById(R.id.tv1tsStartKarte);
        tv1te = itemView.findViewById(R.id.tv1tsEndKarte);
        tv1pr = itemView.findViewById(R.id.tv1PriceKarte);
        tv1d = itemView.findViewById(R.id.tv1DateKarte);

        tv2s = itemView.findViewById(R.id.tv2StartKarte);
        tv2e = itemView.findViewById(R.id.tv2EndKarte);
        tv2ts = itemView.findViewById(R.id.tv2tsStartKarte);
        tv2te = itemView.findViewById(R.id.tv2tsEndKarte);
        tv2pr = itemView.findViewById(R.id.tv2PriceKarte);
        tv2d = itemView.findViewById(R.id.tv2DateKarte);

        tvFP = itemView.findViewById(R.id.tvFinalPriceKarte);
    }

    public void setData(Karte karte){
        try{
            tv1s.setText(karte.letovi.get(0).start);
            tv1e.setText(karte.letovi.get(0).end);
            tv1ts.setText(karte.letovi.get(0).tstart);
            tv1te.setText(karte.letovi.get(0).tend);
            tv1pr.setText(String.valueOf(karte.letovi.get(0).price));
            tv1d.setText(karte.letovi.get(0).date);

            tv2s.setText(karte.letovi.get(1).start);
            tv2e.setText(karte.letovi.get(1).end);
            tv2ts.setText(karte.letovi.get(1).tstart);
            tv2te.setText(karte.letovi.get(1).tend);
            tv2pr.setText(String.valueOf(karte.letovi.get(1).price));
            tv2d.setText(karte.letovi.get(1).date);

            tvFP.setText(karte.cijena+"$");
        }catch(Exception e){

        }

    }

}