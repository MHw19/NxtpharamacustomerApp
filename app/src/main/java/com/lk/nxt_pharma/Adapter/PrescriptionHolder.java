package com.lk.nxt_pharma.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lk.nxt_pharma.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrescriptionHolder  extends RecyclerView.ViewHolder {


     public  TextView udate,ustatus;
      public ImageView imageView;

    public PrescriptionHolder(@NonNull View itemView) {
        super(itemView);

        udate=itemView.findViewById(R.id.upload_date);
        ustatus=itemView.findViewById(R.id.upload_status);
        imageView=itemView.findViewById(R.id.upload_img);






    }
}
