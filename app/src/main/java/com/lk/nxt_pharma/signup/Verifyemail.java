package com.lk.nxt_pharma.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;
import com.lk.nxt_pharma.Home_page;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Patient;
import com.lk.nxt_pharma.entity.User;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Verifyemail extends AppCompatActivity {



  EditText vcodetxt;
  Button verifybtn;

  SharedPreferences sharedPreferences;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyemail);

        vcodetxt=findViewById(R.id.vcode);
        verifybtn=findViewById(R.id.verifybtn);


        Bundle bundle = getIntent().getExtras();

        String code = bundle.getString("code");
        String fullName = bundle.getString("fullName");
        String email = bundle.getString("email");
        String mobile = bundle.getString("mobile");
        String password = bundle.getString("password");
        String dob = bundle.getString("dob");

        vcodetxt.setText(code);



//        Patient patient = (Patient)getIntent().getSerializableExtra("extradata");
//        User user = (User)getIntent().getSerializableExtra("userdata");
//        int vcode = getIntent().getIntExtra("vcode",0);

      //  System.out.println("verify code"+vcode);



        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(code.equals(vcodetxt.getText().toString())){


                    Patient patient=new Patient();
                    patient.setFullName(fullName);
                    patient.setEmail(email);
                    patient.setMobile(mobile);
                    patient.setDob(new Date());
                    patient.setLogStatus(1);
                    patient.setUserStatus("active");
                    patient.setPassword(password);




                    //patient.


                    db.collection("patients").add(patient).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            Toast.makeText(Verifyemail.this, "User registerd...", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Verifyemail.this, Home_page.class);

                        intent.putExtra("email",email);
                        intent.putExtra("name",fullName);

                        startActivity(intent);



                        }
                    });


                }else{


                    Toast.makeText(Verifyemail.this, "Invalid code", Toast.LENGTH_SHORT).show();

                }




//                JsonObject object=new JsonObject();
//
//                object.addProperty("fullName",patient.getFullName().toUpperCase());
//                object.addProperty("email", patient.getEmail().toString());
//                object.addProperty("mobile",patient.getMobile().toString());
//
//
//                JsonObject object2=new JsonObject();
//
//                object2.addProperty("password",user.getPassword().toString());
//                object2.addProperty("logStatus", user.getLogStatus().toString());
//                object2.addProperty("status",user.getStatus().toString());
//
//
//                int verifycode =Integer.parseInt(vcodetxt.getText().toString());
//
//
//               if(verifycode == vcode){
//
//
//                   RequestQueue queue= Volley.newRequestQueue(Verifyemail.this);
//
//
//                   String url="http://192.168.43.23:8080/Pharmacy/PatientRegister";
//
//
//
//                   //192.168.43.23
//
//                   StringRequest jsonObjectRequest= (StringRequest) new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                       @Override
//                       public void onResponse(String response) {
//
//                           //Toast.makeText(Verifyemail.this,response,Toast.LENGTH_LONG).show();
//
//                           if(response.equals("Registration Sucesfully")){
//
//
//
//                              sharedPreferences= Mysharedpreference.getInstance(Verifyemail.this);
//
//                               SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                               editor.putString("email",patient.getEmail());
//
//                               editor.commit();
//
//
//
//                               Intent intent=new Intent(Verifyemail.this, Home_page.class);
//
//                               startActivity(intent);
//
//
//
//                           }
//
//
//
//
//                       }
//                   }, new Response.ErrorListener() {
//                       @Override
//                       public void onErrorResponse(VolleyError error) {
//
//
//                       }
//                   }){
//                       @Override
//                       protected Map<String, String> getParams() throws AuthFailureError {
//
//                           HashMap<String,String> m=new HashMap<>();
//
////                    m.put("fullName",fullname.getText().toString());
////                    m.put("email",email.getText().toString());
////                    m.put("mobile",mobile.getText().toString());toString
//
//                          m.put("parameter",object.toString());
//                          m.put("parameter2",object2.toString());
//
//                           return m;
//                       }
//                   };
//
//
//
//
//
//                   queue.add(jsonObjectRequest);


















//                   // Toast.makeText(Verifyemail.this,"correct",Toast.LENGTH_LONG).show();
//
//               }else{
//
//                  Toast.makeText(Verifyemail.this,"Incorrect",Toast.LENGTH_LONG).show();
//
//               }
//
//
//
//
//
//
//
        }
       });








    }
}

   