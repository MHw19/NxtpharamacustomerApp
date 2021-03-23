package com.lk.nxt_pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.nxt_pharma.entity.Patient;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;
import com.lk.nxt_pharma.signup.Verifyemail;

import java.util.HashMap;
import java.util.Map;

public class Coverpage extends AppCompatActivity {

    ImageView img;
    TextView txt;
   Animation imganim,txtanim;

    private static  int Splash=5000;


    SharedPreferences sharedPreferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide();
        setContentView(R.layout.activity_coverpage);


        img=findViewById(R.id.imageView);
        txt=findViewById(R.id.textView);

        imganim= AnimationUtils.loadAnimation(this,R.anim.animation1);
        txtanim= AnimationUtils.loadAnimation(this,R.anim.animation2);

        img.startAnimation(imganim);
        txt.startAnimation(txtanim);

      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {


              sharedPreferences= Mysharedpreference.getInstance(Coverpage.this);

              String email = sharedPreferences.getString("email","1");



              db.collection("patients").whereEqualTo("email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {

                      if(task.getResult().size()>0){
                          Patient patient = task.getResult().getDocuments().get(0).toObject(Patient.class);

                          if(patient.getLogStatus() ==1){
                              Intent registerintent=new Intent(Coverpage.this,Home_page.class);

                              registerintent.putExtra("email",patient.getEmail());
                              registerintent.putExtra("name",patient.getFullName());

                              startActivity(registerintent);

                          }else{

                              Intent intent=new Intent(Coverpage.this,Login.class);

                              startActivity(intent);



                          }



                      }else{

                          Intent intent=new Intent(Coverpage.this,Login.class);

                          startActivity(intent);



                      }


                  }
              });















//              sharedPreferences= Mysharedpreference.getInstance(Coverpage.this);
//
//              String email = sharedPreferences.getString("email","1");
//
//
//
//              RequestQueue queue= Volley.newRequestQueue(Coverpage.this);
//
//
//              String url="http://192.168.43.23:8080/Pharmacy/Checklogstatus";
//
//
//
//              //192.168.43.23
//
//              StringRequest jsonObjectRequest= (StringRequest) new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                  @Override
//                  public void onResponse(String response) {
//
//                      //Toast.makeText(Verifyemail.this,response,Toast.LENGTH_LONG).show();
//
//                      if(response.equals("true")){
//
//
////
////                          sharedPreferences= Mysharedpreference.getInstance(Verifyemail.this);
////
////                          SharedPreferences.Editor editor = sharedPreferences.edit();
////
////                          editor.putString("email",patient.getEmail());
//
////                          editor.commit();
//
//                          Intent intent=new Intent(Coverpage.this,Home_page.class);
//
//                          startActivity(intent);
//
//
//                          finish();
//
//
//                      }else{
//
//                          Intent intent=new Intent(Coverpage.this,Login.class);
//
//                          startActivity(intent);
//
//                          finish();
//
//                      }
//
//
//
//
//                  }
//              }, new Response.ErrorListener() {
//                  @Override
//                  public void onErrorResponse(VolleyError error) {
//
//
//                  }
//              }){
//                  @Override
//                  protected Map<String, String> getParams() throws AuthFailureError {
//
//                      HashMap<String,String> m=new HashMap<>();
//
////                    m.put("fullName",fullname.getText().toString());
////                    m.put("email",email.getText().toString());
////                    m.put("mobile",mobile.getText().toString());toString
//
//                      m.put("email",email);
//
//
//                      return m;
//                  }
//              };
//
//
//
//
//
//              queue.add(jsonObjectRequest);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//






          }
      },Splash);




    }
}