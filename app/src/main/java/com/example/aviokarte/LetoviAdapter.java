package com.example.aviokarte;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class LetoviAdapter extends RecyclerView.Adapter<LetoviViewHolder> {

    private List<Letovi> data;
    Context context;
    String returnDate;
    String povratak;
    PrviLet prvi;
    public LetoviAdapter(Context ct, List<Letovi> firebaseData, String po, PrviLet pr){
        data = firebaseData;
        context = ct;
        povratak = po;
        prvi = pr;
    }

    @NonNull
    @Override
    public LetoviViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vh_letovi_layout, parent, false);
        return new LetoviViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LetoviViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.setData(data.get(position));
        if(povratak.equals("da")){
            holder.cbox.setVisibility(View.INVISIBLE);
        }

        /*holder.cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbox.isChecked() && povratak.isEmpty()){
                    Calendar calendar = Calendar.getInstance();
                    final int year = calendar.get(Calendar.YEAR);
                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month+1;
                            returnDate = year+"-"+month+"-"+dayOfMonth;
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });*/

        holder.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(holder.cbox.isChecked() && povratak.isEmpty()){

                    Intent intent = new Intent(context, IspisLetovi.class);
                    intent.putExtra("istart", data.get(position).end);
                    intent.putExtra("iend", data.get(position).start);
                    intent.putExtra("idate", "ne");

                    intent.putExtra("oldStartTime", data.get(position).tstart);
                    intent.putExtra("oldEndTime", data.get(position).tend);
                    intent.putExtra("oldDate", data.get(position).date);
                    intent.putExtra("oldPrice", data.get(position).price);
                    intent.putExtra("return", "da");

                    context.startActivity(intent);
                }else{
                    if (povratak.isEmpty()){
                        Intent intent = new Intent(context, Kupnja.class);
                        intent.putExtra("1Start", data.get(position).start);
                        intent.putExtra("1End", data.get(position).end);
                        intent.putExtra("1tStart", data.get(position).tstart);
                        intent.putExtra("1tEnd", data.get(position).tend);
                        intent.putExtra("1Price", data.get(position).price);
                        intent.putExtra("1date", data.get(position).date);
                        context.startActivity(intent);
                    }else{
                        Intent intent = new Intent(context, Kupnja.class);
                        intent.putExtra("2Start", data.get(position).start);
                        intent.putExtra("2End", data.get(position).end);
                        intent.putExtra("2tStart", data.get(position).tstart);
                        intent.putExtra("2tEnd", data.get(position).tend);
                        intent.putExtra("2Price", data.get(position).price);
                        intent.putExtra("2date", data.get(position).date);

                        intent.putExtra("1tStart", prvi.prviTStart);
                        intent.putExtra("1tEnd", prvi.prviTEnd);
                        intent.putExtra("1Price", prvi.prviCijena);
                        intent.putExtra("1date", prvi.prviDatum);
                        context.startActivity(intent);

                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class LetoviViewHolder extends RecyclerView.ViewHolder {

    TextView start, end, tstart, tend, price, date;
    Button btnSelect;
    CheckBox cbox;

    public LetoviViewHolder(@NonNull View itemView) {
        super(itemView);
        start = itemView.findViewById(R.id.tvStart_vhLetovi);
        end = itemView.findViewById(R.id.tvEnd_vhLetovi);
        tstart = itemView.findViewById(R.id.tvTStart_vhLetovi);
        tend = itemView.findViewById(R.id.tvTEnd_vhLetovi);
        price = itemView.findViewById(R.id.tvPrice_vhLetovi);
        date = itemView.findViewById(R.id.tvDate_vhLetovi);
        btnSelect = itemView.findViewById(R.id.btnSelect);
        cbox = itemView.findViewById(R.id.cboxReturn);
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
            cbox.setVisibility(View.INVISIBLE);
            btnSelect.setVisibility(View.INVISIBLE);
        }
        date.setText(let.date);
    }


}
