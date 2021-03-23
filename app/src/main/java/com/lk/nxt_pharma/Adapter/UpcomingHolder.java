package com.lk.nxt_pharma.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Appoinment;
import com.lk.nxt_pharma.entity.FClinic;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingHolder extends RecyclerView.Adapter<UpcomingHolder.ViewHolder> {

    private final String cusID;
    List<Appoinment> upcominglist;

    public  UpcomingHolder(List<Appoinment> upcominglist, String cusID){

        this.upcominglist=upcominglist;
        this.cusID=cusID;


    }

    public void update(List<Appoinment> upcominglist ){
        upcominglist.clear();
        upcominglist.addAll(upcominglist);

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.itemofupcominglist, parent, false);

        UpcomingHolder.ViewHolder viewHolder=new UpcomingHolder.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Appoinment appoinment = upcominglist.get(position);

        holder.docname.setText(appoinment.getDocname());
        holder.date.setText(appoinment.getDate());

        holder.time.setText(appoinment.getTimeslot());

        holder.status.setText(appoinment.getStatus());

        if(appoinment.getStatus().equals("active")){

            holder.cancle.setText("cancle");
        }else if(appoinment.getStatus().equals("cancle")){
            holder.cancle.setText("activate");


        }



        holder.cusID=cusID;

//      notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return upcominglist.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView docname,date,time,status;
        Button cancle;

        public String cusID;

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            docname=itemView.findViewById(R.id.upsdocname);
            date=itemView.findViewById(R.id.upsdate);
            time=itemView.findViewById(R.id.upstime);
            status=itemView.findViewById(R.id.upsstatus);
            cancle=itemView.findViewById(R.id.upcanclebtn);






            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(cancle.getText().equals("cancle")){

                        cancle.setText("activate");

                        db.collection("Appoinment").whereEqualTo("cusID",cusID).whereEqualTo("docname",docname.getText()).whereEqualTo("date",date.getText()).whereEqualTo("timeslot",time.getText()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                String docID = task.getResult().getDocuments().get(0).getId();


                                db.collection("Appoinment").document(docID).update("status","cancle").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        Toast.makeText(itemView.getContext(), "Udated....", Toast.LENGTH_SHORT).show();

                                    }
                                });




                            }
                        });




                    }else if(cancle.getText().equals("activate")){

                        cancle.setText("cancle");

                        db.collection("Appoinment").whereEqualTo("cusID",cusID).whereEqualTo("docname",docname.getText()).whereEqualTo("date",date.getText()).whereEqualTo("timeslot",time.getText()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                String docID = task.getResult().getDocuments().get(0).getId();


                                db.collection("Appoinment").document(docID).update("status","active").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        Toast.makeText(itemView.getContext(), "Udated....", Toast.LENGTH_SHORT).show();

                                    }
                                });




                            }
                        });




                    }

                 notifyDataSetChanged();
                }
            });





        }
    }



}
