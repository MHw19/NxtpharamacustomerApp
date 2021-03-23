package com.lk.nxt_pharma.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lk.nxt_pharma.Home_page;
import com.lk.nxt_pharma.Login;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Patient;
import com.lk.nxt_pharma.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final int TIMEOUT_MS = 10000;
    Button signup,date,login;
    EditText date_field,fullname,mobile,email,password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Patient patient=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup=findViewById(R.id.signup);

        date=findViewById(R.id.date_select);
        date_field=findViewById(R.id.sdob);

        fullname=findViewById(R.id.sfullname);
        mobile=findViewById(R.id.smobile);
        email=findViewById(R.id.semail);
        password=findViewById(R.id.spassword);


        Bundle bundle = getIntent().getExtras();


       // String semail = bundle.getString("email");
      //  String sname = bundle.getString("name");
      //  String authid = bundle.getString("authid");


       // fullname.setText(semail);
      //  email.setText(sname);





//        patient.setFullName(fullname.getText().toString());
//        patient.setMobile(mobile.getText().toString());
//        patient.setEmail(email.getText().toString());






        date=findViewById(R.id.date_select);



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate();


            }
        });

       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if(!validatefullname() | !validatemobile() | !validatedob() | !validateEmail() | !validatepw()  ){






              }else{
                   String rname = fullname.getText().toString();
                   String remail = email.getText().toString();
                   String rmobile = mobile.getText().toString();
                   String pw = password.getText().toString();
                   String dob = date_field.getText().toString();
                   Toast.makeText(SignUp.this, "This "+ remail + "1calling.....", Toast.LENGTH_SHORT).show();



                   db.collection("patients").whereEqualTo("email",remail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {

                           if(task.isSuccessful()){

                               if(task.getResult().size()>0){


                                   Toast.makeText(SignUp.this, "This "+ remail + " already exists.", Toast.LENGTH_SHORT).show();

                               }else{

                                   Toast.makeText(SignUp.this, "This "+ remail + "2calling.....", Toast.LENGTH_SHORT).show();

                                   new AsyncTaskLogin(v).execute(rname,remail,rmobile,pw,dob);




                               }

                           }

                       }
                   });




               }




             //  Date rdob = date.getText().toString();
             //  String rname = fullname.getText().toString();



//
//
//               Patient patient=new Patient(rname,remail,rmobile,new Date(),1,"active",authid,null);
//
//
//                db.collection("patients").add(patient).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                        Toast.makeText(SignUp.this, "Registration Sucessfully !", Toast.LENGTH_SHORT).show();
//
//                        Intent intent=new Intent(SignUp.this, Home_page.class);
//
//                        intent.putExtra("email",remail);
//                        intent.putExtra("name",rname);
//
//                        startActivity(intent);
//
//
//
//                    }
//                });






           }
       });



    }

    private void handleDate() {

        Calendar calendar=Calendar.getInstance();

        int YEAR=calendar.get(Calendar.YEAR);
        int Month=calendar.get(Calendar.MONTH);
        int Date=calendar.get(Calendar.DATE);


        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String date=year+"/"+month+"/"+dayOfMonth;


                Calendar calendar1=Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DATE,dayOfMonth);

                CharSequence datCharSequence= DateFormat.format("EEEE, dd MMM yyyy",calendar1);
                date_field.setText(datCharSequence);




            }
        },YEAR,Month,Date);

        datePickerDialog.show();

    }

    private  boolean validatefullname(){

        String val = fullname.getText().toString();

        if(val.isEmpty()){
            fullname.setError("Filed cannot be empty");
            return false;

        }else{
            fullname.setError(null);
            return  true;

        }

    }

    private  boolean validatemobile(){

        String val = mobile.getText().toString();

        if(val.isEmpty()){
            mobile.setError("Filed cannot be empty");
            return false;

        }else{
            mobile.setError(null);
            return  true;

        }



    }

    private  boolean validatedob(){

        String val = date_field.getText().toString();

        if(val.isEmpty()){
            date_field.setError("Please select Date");
            return false;

        }else{
            date_field.setError(null);
            return  true;

        }



    }

    private  boolean validatepw(){

        String val = password.getText().toString();

        if(val.isEmpty()){
            password.setError("Filed cannot be empty");
            return false;

        }else{
            password.setError(null);
            return  true;

        }



    }
    private  boolean validateEmail(){

        String val = email.getText().toString();

        String pattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        if(val.isEmpty()){
            email.setError("Email cannot be empty");
            return false;

          }else if(!val.matches(pattern)){

            email.setError(" Invalid Email Address! ");
            return  false;

        } else{
            email.setError(null);
            return  true;

        }



    }









//    public  void registeruser(){
//
//        if(!validatefullname() | !validatemobile() | !validatedob() | !validateEmail() | !validatepw()  ){
//
//
//
//
//
//
//
//        }else{
//
//                 // JsonObject object=new JsonObject();
//
//                  //object.addProperty("fullName",fullname.getText().toString());
//                 // object.addProperty("email", email.getText().toString());
//                 // object.addProperty("mobile",mobile.getText().toString());
//
//
//
//
//
//
//
//
//
//            RequestQueue queue= Volley.newRequestQueue(SignUp.this);
//
//
//            String url="http://192.168.43.23:8080/Pharmacy/Userregister";
//            //192.168.43.23
//
//            StringRequest jsonObjectRequest= (StringRequest) new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    System.out.println(response);
//
//                    Gson g=new Gson();
//
//
//                    //List l=new ArrayList();
//
//                   List<String> s = g.fromJson(response.toString(), List.class);
//
//                    //System.out.println(s.get(0));
//
//                    String emailtag = s.get(0);
//
//
//
//                    if(emailtag.equals("Email Not Registerd")){
//
//                        int vcode = Integer.parseInt(s.get(1));
//                      patient=new Patient(fullname.getText().toString(),email.getText().toString(),mobile.getText().toString());
//                        User user=new User();
//
//                        user.setPassword(password.getText().toString());
//                        user.setStatus("active");
//                        user.setLogStatus("in");
//
//                      Intent intent=new Intent(SignUp.this,Verifyemail.class);
//
//                      intent.putExtra("extradata",patient);
//                      intent.putExtra("userdata",user);
//                      intent.putExtra("vcode",vcode);
//
//                      startActivity(intent);
//
//                     s.clear();
//
//                  }else{
//                      Toast.makeText(SignUp.this,response,Toast.LENGTH_LONG).show();
//                        s.clear();
//
//                  }
//
//
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    Toast.makeText(SignUp.this,error+"",Toast.LENGTH_LONG).show();
//
//                }
//            }){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//
//                    HashMap<String,String>m=new HashMap<>();
//
////                    m.put("fullName",fullname.getText().toString());
////                    m.put("email",email.getText().toString());
////                    m.put("mobile",mobile.getText().toString());toString
//
//                    m.put("parameter",email.getText().toString());
//
//                    return m;
//                }
//            }.setRetryPolicy(new DefaultRetryPolicy(
//                    TIMEOUT_MS,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//
//
//
//            queue.add(jsonObjectRequest);
//
//
//                  //  Toast.makeText(this, patient.getEmail(), Toast.LENGTH_LONG).show();
//                  //   Log.d("Error signup",patient.getEmail().toString());
//
//        }
//
//    }




}