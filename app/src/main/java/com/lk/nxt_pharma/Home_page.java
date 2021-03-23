package com.lk.nxt_pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lk.nxt_pharma.aboutus.Aboutus;
import com.lk.nxt_pharma.aboutus.userprofile;
import com.lk.nxt_pharma.fragment.Home_fragment;
import com.lk.nxt_pharma.fragment.Myappoinment_fragment;
import com.lk.nxt_pharma.fragment.Myorder_fragment;
import com.lk.nxt_pharma.fragment.category_fragment;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;

import java.util.HashMap;
import java.util.Map;

public class Home_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbars;
    Button remind;

    SharedPreferences sharedPreferences;

   BottomNavigationView bottomNavigationView;
    private String FCMToken;
    private String cusID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

       drawerLayout=findViewById(R.id.drawer_layout);
       navigationView=findViewById(R.id.nav_view);
       toolbars=(Toolbar)findViewById(R.id.toolbars);

     //  remind= findViewById(R.id.reminder);

       bottomNavigationView=findViewById(R.id.bottom_navigation);

      bottomNavigationView.setOnNavigationItemSelectedListener(navlistner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_fragment()).commit();

       setSupportActionBar(toolbars);


        Menu menu=navigationView.getMenu();

        menu.findItem(R.id.nav_appointment).setVisible(false);


       navigationView.bringToFront();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbars,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);




         sharedPreferences= Mysharedpreference.getInstance(Home_page.this);

        String email = sharedPreferences.getString("email","1");
         cusID = sharedPreferences.getString("cusID","1");

        Toast.makeText(Home_page.this,email,Toast.LENGTH_LONG).show();



    }



    private BottomNavigationView.OnNavigationItemSelectedListener navlistner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;

            switch (item.getItemId()){

                case R.id.home_nav :

                    selectedFragment = new Home_fragment();

                    break;
                case R.id.category_nav :

                    selectedFragment = new category_fragment();

                    break;
                case R.id.Myorder_nav :

                    selectedFragment = new Myorder_fragment();

                    break;
                case R.id.Myappointment_nav :

                    selectedFragment = new Myappoinment_fragment();

                    break;




            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


            return true;
        }
    };




    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);

        }else{

           // super.onBackPressed();

            finish();
        }


    }






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_logout:



                signOut();



//                sharedPreferences= Mysharedpreference.getInstance(Home_page.this);
//
//                String email = sharedPreferences.getString("email","1");
//
//
//                RequestQueue queue= Volley.newRequestQueue(Home_page.this);
//
//
//                String url="http://192.168.43.23:8080/Pharmacy/checklogout";
//                //192.168.43.23
//
//                StringRequest jsonObjectRequest= (StringRequest) new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println(response);
//
//                        if(response.equals("Logout Sucessfully")){
//
//
//                            sharedPreferences= Mysharedpreference.getInstance(Home_page.this);
//
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            editor.putString("email",email);
//
//                            editor.commit();
//
//
//                            Intent intent=new Intent(Home_page.this,Login.class);
//                            startActivity(intent);
//
//                        }else{
//
//                            //Toast.makeText(Login.this,response.toString(),Toast.LENGTH_LONG).show();
//
//                        }
//
//
//
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//
//                        HashMap<String,String> m=new HashMap<>();
//
//                        m.put("email",email);
//                    //    m.put("password",spw.getText().toString());
////                    m.put("mobile",mobile.getText().toString());toString
//
//
//
//                        return m;
//                    }
//                };
//
//
//
//
//
//                queue.add(jsonObjectRequest);






                break;

            case R.id.nav_upload:
                Intent intent2=new Intent(Home_page.this,Upload_prescription.class);
                startActivity(intent2);
                break;

            case R.id.aboutus :

                Intent intent=new Intent(Home_page.this, Aboutus.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);



                break;



            case R.id.nav_user:
                Intent intent3=new Intent(Home_page.this,userprofile.class);
                startActivity(intent3);
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        db.collection("patients").document(cusID).update("logStatus",0).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent=new Intent(Home_page.this,Login.class);
                                startActivity(intent);
                            }
                        });





                        // ...
                    }
                });
        // [END auth_fui_signout]
    }




}