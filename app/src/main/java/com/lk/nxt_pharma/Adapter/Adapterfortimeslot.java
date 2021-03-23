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
import com.lk.nxt_pharma.appointments.Select_time_slot;
import com.lk.nxt_pharma.entity.FClinic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapterfortimeslot  extends  RecyclerView.Adapter<Adapterfortimeslot.ViewHolder> {


    private final List<String> Datelist;
    private final String DocID;
    public Select_time_slot context;

    public Adapterfortimeslot(List<String> itemlist,Select_time_slot context,String DocID){


        this.DocID=DocID;
        this.Datelist=itemlist;

        this.context=context;

       //

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.time_slot_item, parent, false);

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String date = Datelist.get(position);

        holder.timeslotbtn.setText(""+date);

    }

    @Override
    public int getItemCount() {
        return Datelist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public  Button timeslotbtn;

        public Button timebtn1,timebtn2,timebtn3;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            timeslotbtn= itemView.findViewById(R.id.timeslotbtn);


            timeslotbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   TextView textView= context.findViewById(R.id.selecteddatetxt);
                   TextView timeslottxt= context.findViewById(R.id.timeslottxt);


                    timebtn1= context.findViewById(R.id.timebtn1);
                    timebtn2= context.findViewById(R.id.timebtn2);
                    timebtn3= context.findViewById(R.id.timebtn3);




                   textView.setText(""+timeslotbtn.getText());

                    timeslottxt.setText("");
                    timebtn1.setVisibility(View.INVISIBLE);
                    timebtn2.setVisibility(View.INVISIBLE);
                    timebtn3.setVisibility(View.INVISIBLE);


                   db.collection("Clinic").whereEqualTo("docID",DocID).whereEqualTo("clinicDate",textView.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {

                           List<FClinic> fClinicList = task.getResult().toObjects(FClinic.class);

                           ArrayList<String> timeslots=new ArrayList<>();
                           timeslots.clear();

                           for(FClinic clinic:fClinicList){


                               timeslots.add(clinic.getTimeslot());




                           }

                           Toast.makeText(context, ""+timeslots.size(), Toast.LENGTH_SHORT).show();



                         for(int i=0;i<timeslots.size();i++){

                             Toast.makeText(context, ""+timeslots.get(i), Toast.LENGTH_SHORT).show();

                             switch (timeslots.get(i).toString()){

                                 case "Morning":
                                     timebtn1.setVisibility(View.VISIBLE);
                                     timebtn1.setEnabled(true);
                                   break;
                                 case "Afternoon":
                                     timebtn2.setVisibility(View.VISIBLE);
                                     timebtn2.setEnabled(true);
                                      break;
                                 case "Evening":

                                     timebtn3.setVisibility(View.VISIBLE);
                                     timebtn3.setEnabled(true);
                                    break;

                             }



                         }




                       }
                   });





                }
            });



        }
    }




}
