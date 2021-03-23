package com.lk.nxt_pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lk.nxt_pharma.entity.Patient;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;
import com.lk.nxt_pharma.signup.SignUp;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN =132;
    SignInButton Loginbtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    SharedPreferences sharedPreferences;
    private String FCMToken;

    Button signupbtn,normalloginbtn;

    EditText username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Loginbtn = findViewById(R.id.signinbtn);
        signupbtn = findViewById(R.id.signupbtn);
        normalloginbtn = findViewById(R.id.normalloginbtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        intFCM();
     Loginbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             createSignInIntent();

         }
     });

     signupbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent registerintent=new Intent(Login.this,SignUp.class);




             startActivity(registerintent);
         }
     });



     normalloginbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {





             if(!validpw() | !validateEmail()){


             }else{
                 String usern = username.getText().toString();
                 String pw = password.getText().toString();

                 db.collection("patients").whereEqualTo("email",usern).whereEqualTo("password",pw).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {

                         if(task.getResult().size()>0){

                             String cusID= task.getResult().getDocuments().get(0).getId();

                             Patient patient = task.getResult().getDocuments().get(0).toObject(Patient.class);
                             Intent registerintent=new Intent(Login.this,Home_page.class);

                             registerintent.putExtra("email",patient.getEmail());
                             registerintent.putExtra("name",patient.getFullName());

                             updateFCMToken(cusID);

                             sharedPreferences= Mysharedpreference.getInstance(Login.this);

                             SharedPreferences.Editor editor = sharedPreferences.edit();

                             editor.putString("email",patient.getEmail());
                             editor.putString("name",patient.getFullName());

                             editor.putString("cusID",cusID);

                             editor.commit();


                             startActivity(registerintent);


                         }else{

                             Toast.makeText(Login.this, "Invalid Login...", Toast.LENGTH_SHORT).show();

                         }


                     }
                 });





             }



         }
     });




    }

    private void intFCM() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {



                if(task.isSuccessful()){

                    FCMToken=task.getResult();

                    Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();

                }





            }
        });







    }





    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(

                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "sucess sign in", Toast.LENGTH_SHORT).show();
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                String email = user.getEmail();
                String name = user.getDisplayName();
                String googleauthid = user.getUid();


               db.collection("patients").whereEqualTo("email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {

                       if(task.getResult().size()>0){


                           String cusID= task.getResult().getDocuments().get(0).getId();
                           Intent registerintent=new Intent(Login.this,Home_page.class);

                           registerintent.putExtra("email",email);
                           registerintent.putExtra("name",name);

                           updateFCMToken(cusID);

                           sharedPreferences= Mysharedpreference.getInstance(Login.this);

                           SharedPreferences.Editor editor = sharedPreferences.edit();

                           editor.putString("email",email);
                           editor.putString("name",name);

                           editor.putString("cusID",cusID);

                           editor.commit();


                           startActivity(registerintent);

                       }else{


                           Intent registerintent=new Intent(Login.this,SignUp.class);

                           registerintent.putExtra("email",email);
                           registerintent.putExtra("name",name);
                           registerintent.putExtra("authid",googleauthid);





                           startActivity(registerintent);

                       }


                   }
               });


















                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...

                Toast.makeText(this, "unsucessfully  sign in", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateFCMToken(String cusID) {


        db.collection("patients").document(cusID).update("fcmId",FCMToken,"logStatus",1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

               // Log.d(TAG,FCMToken);

            }
        });



    }
    // [END auth_fui_result]

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }

    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_patient_details)      // Set logo drawable
                        // .setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }

    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_pp_tos]
    }





    private  boolean validpw(){

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

        String val = username.getText().toString();

        String pattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        if(val.isEmpty()){
            username.setError("Email cannot be empty");
            return false;

        }else if(!val.matches(pattern)){

            username.setError(" Invalid Email Address! ");
            return  false;

        } else{
            username.setError(null);
            return  true;

        }



    }





















//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String enteremail = semail.getText().toString();
//                String enterpw = spw.getText().toString();
//
//
//                if(!validateemail() | !validatepw()  ){
//
//
//
//
//                }else{
//
//                    RequestQueue queue= Volley.newRequestQueue(Login.this);
//
//
//                    String url="http://192.168.43.23:8080/Pharmacy/Checklogin";
//                    //192.168.43.23
//
//                    StringRequest jsonObjectRequest= (StringRequest) new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            System.out.println(response);
//
//                            if(response.equals("Login Sucessfully")){
//
//
//                                sharedPreferences= Mysharedpreference.getInstance(com.lk.nxt_pharma.Login.this);
//
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                                editor.putString("email",semail.getText().toString());
//
//                                editor.commit();
//
//
//                                Intent intent=new Intent(Login.this,Home_page.class);
//
//                                startActivity(intent);
//
//                            }else{
//
//                                Toast.makeText(Login.this,response.toString(),Toast.LENGTH_LONG).show();
//
//                            }
//
//
//
//
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//
//                        }
//                    }){
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//
//                            HashMap<String,String> m=new HashMap<>();
//
//                            m.put("email",enteremail);
//                            m.put("password",enterpw);
////                    m.put("mobile",mobile.getText().toString());toString
//
//
//
//                            return m;
//                        }
//                    };
//
//
//
//
//
//                    queue.add(jsonObjectRequest);
//
//
//                }


//
//
//
//            }
//        });
//
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent=new Intent(Login.this, SignUp.class);
//
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                startActivity(intent);
//
//
//            }
//        });
//
//
//
//
//    }
//
//    private  boolean validateemail(){
//
//        String val = semail.getText().toString();
//
//        String pattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//
//
//        if(val.isEmpty()){
//            semail.setError("Email cannot be empty");
//            return false;
//
//        }else if(!val.matches(pattern)){
//
//            semail.setError(" Invalid Email Address! ");
//            return  false;
//
//        } else{
//            semail.setError(null);
//            return  true;
//
//        }
//
//
//    }
//
//    private  boolean validatepw(){
//
//        String val = spw.getText().toString();
//
//        if(val.isEmpty()){
//           spw.setError("Filed cannot be empty");
//            return false;
//
//        }else{
//            spw.setError(null);
//            return  true;
//
//        }
//
//    }




}