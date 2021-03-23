package com.lk.nxt_pharma.Adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.appointments.Select_time_slot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapterfordoctorlist  extends RecyclerView.ViewHolder {


    public  TextView docname,docqualification,docemail,docexperience;
    public  ImageView imageView15;

     public Button bookappoint;

    public  String DocID;


    public Adapterfordoctorlist(@NonNull View itemView) {
        super(itemView);

        docname=itemView.findViewById(R.id.docname);
        docqualification=itemView.findViewById(R.id.docqualification);
        docemail=itemView.findViewById(R.id.docemail);
        docexperience=itemView.findViewById(R.id.docexperience);

        imageView15=itemView.findViewById(R.id.imageView15);


        bookappoint=itemView.findViewById(R.id.bookappoint);


        bookappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(itemView.getContext(),Select_time_slot.class);

                intent.putExtra("DocID",DocID);
                intent.putExtra("docname",docname.getText().toString());
                intent.putExtra("docqualify",docqualification.getText().toString());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                itemView.getContext().startActivity(intent);


            }
        });





    }
}
