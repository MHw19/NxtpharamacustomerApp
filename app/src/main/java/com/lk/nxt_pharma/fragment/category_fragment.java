package com.lk.nxt_pharma.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.ads.mediation.AbstractAdViewAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class category_fragment  extends Fragment {

    private AdView mAdView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView eventtitle,eventcategory,eventduration,eventdiscount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        eventtitle  = view.findViewById(R.id.eventtitle);
        eventcategory  = view.findViewById(R.id.eventcategory);
        eventduration  = view.findViewById(R.id.eventduration);
        eventdiscount  = view.findViewById(R.id.eventdiscount);


        MobileAds.initialize(view.getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        mAdView =  view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        db.collection("Events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.getResult().size()>0){

                    Event event = task.getResult().getDocuments().get(0).toObject(Event.class);

                    String title = event.getTitle();
                    String category = event.getCategory();
                    int duration = event.getDuration();
                    double discount = event.getDiscount();

                    eventtitle.setText(title);
                    eventcategory.setText(category);
                    eventdiscount.setText(discount+" %");
                    eventduration.setText(" Duration:  Week  "+duration);



                }

            }
        });



        return  view;



    }
}
