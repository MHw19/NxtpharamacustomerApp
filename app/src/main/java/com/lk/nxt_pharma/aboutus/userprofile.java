package com.lk.nxt_pharma.aboutus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lk.nxt_pharma.Home_page;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Patient;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class userprofile extends AppCompatActivity {


    ImageButton upimgbtn;
    private int GET_Gallery_REQUESTS=113;

    ImageView upimgview;
    private Uri imgURI;
    private SharedPreferences sharedPreferences;
    private String cusID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText upname,upemail,upmobile,updob;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        upimgbtn=findViewById(R.id.upimgbtn);
        upimgview=findViewById(R.id.upimgview);
        upname=findViewById(R.id.upname);
        upemail=findViewById(R.id.upemail);
        upmobile=findViewById(R.id.upmobile);
        updob=findViewById(R.id.updob);



        sharedPreferences= Mysharedpreference.getInstance(userprofile.this);

       // String email = sharedPreferences.getString("email","1");
        cusID = sharedPreferences.getString("cusID","1");

        db.collection("patients").document(cusID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Patient patient = task.getResult().toObject(Patient.class);
                
                upname.setText(patient.getFullName());
                upemail.setText(patient.getEmail());
                upmobile.setText(patient.getMobile());
                updob.setText(patient.getDob()+"");
                
                
                
                
                

            }
        });





        upimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/*");

                startActivityForResult(intent,GET_Gallery_REQUESTS);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_Gallery_REQUESTS & resultCode==RESULT_OK){


            imgURI = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgURI);

                upimgview.setImageBitmap(bitmap);



            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}