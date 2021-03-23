package com.lk.nxt_pharma.appointments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lk.nxt_pharma.Adapter.Adapterfordoctorlist;
import com.lk.nxt_pharma.Adapter.Adapterfortimeslot;
import com.lk.nxt_pharma.ProductCart.Product_cart;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Appoinment;
import com.lk.nxt_pharma.entity.Doctor;
import com.lk.nxt_pharma.entity.FClinic;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Select_time_slot extends AppCompatActivity {


    TextView docname,docqualify,timeslottxt,selecteddatetxt;


    public FirestoreRecyclerAdapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String DocID;

    public Button timebtn1,timebtn2,timebtn3;

    Button docbookbtn;
    private String doctorname;
    private SharedPreferences sharedPreferences;
    private String cusID;
    private String cusName;

    //active cancle complete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time_slot);


        docname = findViewById(R.id.timedocname);
        docqualify = findViewById(R.id.timedocqualfy);

        recyclerView = findViewById(R.id.time_slotlist);
        timeslottxt = findViewById(R.id.timeslottxt);
        selecteddatetxt= findViewById(R.id.selecteddatetxt);

        timebtn1= findViewById(R.id.timebtn1);
        timebtn2= findViewById(R.id.timebtn2);
        timebtn3= findViewById(R.id.timebtn3);
        docbookbtn= findViewById(R.id.docbookbtn);

        sharedPreferences= Mysharedpreference.getInstance(Select_time_slot.this);

        cusID = sharedPreferences.getString("cusID","1");
        cusName = sharedPreferences.getString("name","1");

        timebtn1.setVisibility(View.INVISIBLE);
        timebtn2.setVisibility(View.INVISIBLE);
        timebtn3.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();

       doctorname = bundle.getString("docname");
        String qualification = bundle.getString("docqualify");
      DocID = bundle.getString("DocID");


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");

        String currentdate = s.format(new Date());


        String calculatedDate = getCalculatedDate("MM/dd/yyyy", 7);

        List<String> dates = getDates(currentdate, calculatedDate);

        for (String date : dates) {
            //Toast.makeText(this, ""+s.format(date), Toast.LENGTH_SHORT).show();
        }




        docname.setText(doctorname);
        docqualify.setText(qualification);



          recyclerView.setLayoutManager(new LinearLayoutManager(Select_time_slot.this,LinearLayoutManager.HORIZONTAL,false));




        Adapterfortimeslot adapter=new Adapterfortimeslot(dates,Select_time_slot.this,DocID);



        recyclerView.setAdapter(adapter);



//        RequestQueue queue= Volley.newRequestQueue(Select_time_slot.this);
//
//
//        String url="http://192.168.43.23:8080/Pharmacy/Addtime_slot";
//        //192.168.43.23
//
//        StringRequest jsonObjectRequest= (StringRequest) new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//                System.out.println(response);
//
//                Gson gson=new Gson();
//
//
//                Type Clinictype=new TypeToken<List<Clinic>>(){}.getType();
//
//                List<Clinic> clinicList = gson.fromJson(response, Clinictype);
//
//                 for (Clinic c:clinicList){
//
//                     System.out.println(c.getDate());
//
//
//
//
//
//
//                 }
//
//
//                // JSONArray jArray = (JSONArray) response;
//
//
//                adapter = new Adapterfortimeslot(Select_time_slot.this,clinicList);
//                recyclerView.setLayoutManager(new LinearLayoutManager(Select_time_slot.this,LinearLayoutManager.HORIZONTAL,false));
////
//               recyclerView.setAdapter(adapter);
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                HashMap<String,String> m=new HashMap<>();
//
//                m.put("parameter",name);
//                //   m.put("password",enterpw);
////                    m.put("mobile",mobile.getText().toString());toString
//
//
//
//                return m;
//            }
//        };
//
//
//
//
//
//        queue.add(jsonObjectRequest);




        timebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    timeslottxt.setText("");
                    timebtn2.setEnabled(true);
                    timebtn3.setEnabled(true);
                    timebtn1.setEnabled(false);

                    timeslottxt.setText(timebtn1.getText().toString());





            }
        });



        timebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeslottxt.setText("");
                timebtn2.setEnabled(false);
                timebtn3.setEnabled(true);
                timebtn1.setEnabled(true);
                timeslottxt.setText(timebtn2.getText().toString());

            }
        });



        timebtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeslottxt.setText("");
                timebtn2.setEnabled(true);
                timebtn3.setEnabled(false);
                timebtn1.setEnabled(true);

                timeslottxt.setText(timebtn3.getText().toString());
            }
        });



    docbookbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String times = timeslottxt.getText().toString();


            if(!times.isEmpty()){


                Appoinment appoinment=new Appoinment();

                appoinment.setDocID(DocID);
                appoinment.setDocname(doctorname);

                appoinment.setDate(selecteddatetxt.getText().toString());
                appoinment.setStatus("active");
                appoinment.setTimeslot(timeslottxt.getText().toString());
               appoinment.setCusID(cusID);
               appoinment.setCusname(cusName);


                db.collection("Appoinment").add(appoinment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(Select_time_slot.this, "Booking Sucessfully", Toast.LENGTH_SHORT).show();

                        }

                    }
                });



            }else{

                Toast.makeText(Select_time_slot.this, "Please select Date and timeslot", Toast.LENGTH_SHORT).show();

            }




        }
    });









    }



    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }


    private static List<String> getDates(String dateString1, String dateString2)
    {
        ArrayList<String> dates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(df1.format(cal1.getTime()));
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }




}