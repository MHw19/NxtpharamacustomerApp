package com.lk.nxt_pharma.Adapter;

import android.view.View;
import android.widget.TextView;

import com.lk.nxt_pharma.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyorderHolder extends RecyclerView.ViewHolder {

    public  TextView myorderdate,myorderpname,myorderqty,myordercost,myorderpay,myorderstatus;


    public MyorderHolder(@NonNull View itemView) {
        super(itemView);


        myorderdate=itemView.findViewById(R.id.myorderdate);
        myorderpname=itemView.findViewById(R.id.myorderpname);
        myorderqty=itemView.findViewById(R.id.myorderqty);
        myordercost=itemView.findViewById(R.id.myordercost);
        myorderpay=itemView.findViewById(R.id.myorderpay);
        myorderstatus=itemView.findViewById(R.id.myorderstatus);




    }
}
