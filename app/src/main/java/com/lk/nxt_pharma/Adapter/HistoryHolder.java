package com.lk.nxt_pharma.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Appoinment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class  HistoryHolder extends  RecyclerView.Adapter<HistoryHolder.ViewHolder> {


    List<Appoinment> historylist;

      public  HistoryHolder(List<Appoinment> historylist){


        this.historylist=historylist;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.itemofhistory, parent, false);

        HistoryHolder.ViewHolder viewHolder=new HistoryHolder.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Appoinment appoinment = historylist.get(position);

        holder.docname.setText(appoinment.getDocname());
        holder.date.setText(appoinment.getDate());

        holder.time.setText(appoinment.getTimeslot());

        holder.status.setText(appoinment.getStatus());



    }

    @Override
    public int getItemCount() {
        return historylist.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView docname,date,time,status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            docname=itemView.findViewById(R.id.hisdocname);
            date=itemView.findViewById(R.id.hisdate);
            time=itemView.findViewById(R.id.histime);
            status=itemView.findViewById(R.id.hisstatus);
        }
    }



}
