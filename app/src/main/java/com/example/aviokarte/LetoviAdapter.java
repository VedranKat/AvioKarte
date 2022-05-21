package com.example.aviokarte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LetoviAdapter extends RecyclerView.Adapter<LetoviViewHolder> {

    private List<Letovi> data;
    Context context;
    public LetoviAdapter(Context ct, List<Letovi> firebaseData){
        data = firebaseData;
        context = ct;
    }

    @NonNull
    @Override
    public LetoviViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vh_letovi_layout, parent, false);
        return new LetoviViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LetoviViewHolder holder, int position) {

        holder.setData(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class LetoviViewHolder extends RecyclerView.ViewHolder {

    TextView start, end, tstart, tend, price, date;
    public LetoviViewHolder(@NonNull View itemView) {
        super(itemView);
        start = itemView.findViewById(R.id.tvStart_vhLetovi);
        end = itemView.findViewById(R.id.tvEnd_vhLetovi);
        tstart = itemView.findViewById(R.id.tvTStart_vhLetovi);
        tend = itemView.findViewById(R.id.tvTEnd_vhLetovi);
        price = itemView.findViewById(R.id.tvPrice_vhLetovi);
        date = itemView.findViewById(R.id.tvDate_vhLetovi);
    }

    public void setData(Letovi let){

        start.setText(let.start);
        end.setText(let.end);
        tstart.setText(let.tstart);
        tend.setText(let.tend);
        if(let.price != 0){
            price.setText(let.price +"$");
        }else{
            price.setVisibility(View.INVISIBLE);
        }
        date.setText(let.date);
    }


}
