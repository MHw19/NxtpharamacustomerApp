package com.lk.nxt_pharma.appointments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lk.nxt_pharma.Home_page;
import com.lk.nxt_pharma.Login;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;

import java.util.HashMap;
import java.util.Map;

public class Find_doctors extends AppCompatActivity {


    Button opdbtn,childbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctors);

        opdbtn=findViewById(R.id.womenhealthbtn);
        childbtn=findViewById(R.id.childbtn);

        opdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String option = opdbtn.getTag().toString();
                Intent intent=new Intent(Find_doctors.this,Book_doctor.class);
                intent.putExtra("type",option);
                startActivity(intent);




            }
        });

        childbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String option = childbtn.getTag().toString();
                Intent intent=new Intent(Find_doctors.this,Book_doctor.class);
                intent.putExtra("type",option);
                startActivity(intent);


            }
        });















    }
}