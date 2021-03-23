package com.lk.nxt_pharma.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.Reminder;
import com.lk.nxt_pharma.appointments.Find_doctors;
import com.lk.nxt_pharma.products.Product_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Home_fragment extends Fragment {


    Button personal_carebtn;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);

      Button remind= view.findViewById(R.id.reminder);
      Button appoinment=view.findViewById(R.id.appoint_btn);

      personal_carebtn= view.findViewById(R.id.btn_personal);

        MobileAds.initialize(view.getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        mAdView =  view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


      personal_carebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent intent=new Intent(view.getContext(), Product_list.class);

               String btntext = personal_carebtn.getText().toString();

               intent.putExtra("category",btntext);

               startActivity(intent);




          }
      });




      appoinment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent intent=new Intent(view.getContext(), Find_doctors.class);

              startActivity(intent);


          }
      });



                remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(view.getContext(), Reminder.class);

                startActivity(intent);

            }
        });




        return view;

    }
}
