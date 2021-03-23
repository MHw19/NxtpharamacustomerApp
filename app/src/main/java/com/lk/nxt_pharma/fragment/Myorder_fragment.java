package com.lk.nxt_pharma.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lk.nxt_pharma.Order_prescription;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.Shoppingorders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Myorder_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorder, container, false);


        Button  upload_order = view.findViewById(R.id.upload_prescripbtn);
        Button  my_order = view.findViewById(R.id.myorderbtn);


        upload_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(view.getContext(), Order_prescription.class);

                startActivity(intent);


            }
        });


        my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(view.getContext(), Shoppingorders.class);

                startActivity(intent);


            }
        });




        return view  ;
    }
}
