package com.lk.nxt_pharma.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.nxt_pharma.Adapter.HistoryHolder;
import com.lk.nxt_pharma.Adapter.UpcomingHolder;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.appointments.Select_time_slot;
import com.lk.nxt_pharma.entity.Appoinment;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Myappoinment_fragment extends Fragment {


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    private SharedPreferences sharedPreferences;
    private String cusID;

    UpcomingHolder adapter1;
    List<Appoinment> upcominglist=new ArrayList<>();
    List<Appoinment> historylist=new ArrayList<>();
    List<Appoinment> bhistorylist=new ArrayList<>();
    List<Appoinment> bupcominglis=new ArrayList<>();
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_appoinment, container, false);


        recyclerView=view.findViewById(R.id.upcominglist);
        recyclerView2=view.findViewById(R.id.historylist);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));

        sharedPreferences= Mysharedpreference.getInstance(view.getContext());

        cusID = sharedPreferences.getString("cusID","1");



       db.collection("Appoinment").whereEqualTo("cusID",cusID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {

               List<Appoinment> appoinments = task.getResult().toObjects(Appoinment.class);


               // total date list
               List<String> datelist=new ArrayList<>();

               for(Appoinment appoinment:appoinments){

                   datelist.add(appoinment.getDate());

               }

               // get current Date object
               Date date2=new Date();
               String sdate=new SimpleDateFormat("MM/dd/yyyy").format(date2);

               Date date= null;
               try {
                   date = new SimpleDateFormat("MM/dd/yyyy").parse(sdate);
               } catch (ParseException e) {
                   e.printStackTrace();
               }


               //upcoming
               List<Date> tupdatedlist=new ArrayList<>();

               //history
               List<Date> hisdatelist=new ArrayList<>();

               for(int i=0;i<datelist.size();i++){

                   try {
                       Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(datelist.get(i).toString());
                       if(date.before(date1) || date.equals(date1)){
                           tupdatedlist.add(date1);

                       }else{

                           hisdatelist.add(date1);
                       }



                   } catch (ParseException e) {
                       e.printStackTrace();
                   }


               }

               upcominglist.clear();
               historylist.clear();

               for(Date dated:tupdatedlist){

                   String upsdate=new SimpleDateFormat("MM/dd/yyyy").format(dated);


                   //String sdates = dated.toString();

                   for(Appoinment appoinment:appoinments){
                     if(appoinment.getDate().equals(upsdate)){

                        upcominglist.add(appoinment);


                     }


                 }


               }


               for(Date hisdated:hisdatelist){


                   String hissdate=new SimpleDateFormat("MM/dd/yyyy").format(hisdated);

                   for(Appoinment appoinment:appoinments){

                       if(appoinment.getDate().equals(hissdate)){
                          // System.out.println("appoint"+appoinment.getDate());




                          bhistorylist.add(appoinment);









                       }


                   }


               }

               int x=1;

                   historylist.clear();

                  // upcominglist.clear();

                     //  System.out.println("Appointment"+appoinment3.getDocname()+appoinment3.getTimeslot());





               for(Appoinment appoinment3 :upcominglist){

                   if(bupcominglis.size()>=1){

                       for(int i=0;i<bupcominglis.size();i++) {


                           Appoinment appoinment4 = bupcominglis.get(i);

                           if (appoinment4.getDate().equals(appoinment3.getDate()) & appoinment4.getDocname().equals(appoinment3.getDocname()) & appoinment4.getTimeslot().equals(appoinment3.getTimeslot())) {
                               Toast.makeText(view.getContext(), appoinment3.getDate() + appoinment3.getDocname(), Toast.LENGTH_SHORT).show();

                               break;
                           } else {
                               Toast.makeText(view.getContext(), "No" + appoinment3.getDate() + appoinment3.getDocname(), Toast.LENGTH_SHORT).show();

                               bupcominglis.add(appoinment3);
                              // notifyAll();
                               break;
                           }




                       }

                   }else{

                       bupcominglis.add(appoinment3);

//                       notifyAll();

                   }



               }







                       for(Appoinment appoinment3 : bhistorylist){

                          if(historylist.size()>=1){

                              for(int i=0;i<historylist.size();i++) {


                                  Appoinment appoinment4 = historylist.get(i);

                                  if (appoinment4.getDate().equals(appoinment3.getDate()) & appoinment4.getDocname().equals(appoinment3.getDocname()) & appoinment4.getTimeslot().equals(appoinment3.getTimeslot())) {
                                      Toast.makeText(view.getContext(), appoinment3.getDate() + appoinment3.getDocname(), Toast.LENGTH_SHORT).show();

                                      break;
                                  } else {
                                      Toast.makeText(view.getContext(), "No" + appoinment3.getDate() + appoinment3.getDocname(), Toast.LENGTH_SHORT).show();

                                      historylist.add(appoinment3);


                                     break;
                                  }




                              }

                          }else{

                              historylist.add(appoinment3);

                          }



                       }














               for(Appoinment appoinment:bupcominglis){

                   System.out.println( "Book Date:  "+appoinment.getDate());

               }












                HistoryHolder adapter2=new HistoryHolder(historylist);

                       recyclerView2.setAdapter(adapter2);









            adapter1=new UpcomingHolder(bupcominglis,cusID);


               recyclerView.setAdapter(adapter1);
               adapter1.notifyDataSetChanged();
















           }
       });










        return  view;
    }



}
