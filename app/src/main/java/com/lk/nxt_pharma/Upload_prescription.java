package com.lk.nxt_pharma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lk.nxt_pharma.entity.Prescription;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class Upload_prescription extends AppCompatActivity {


    Button cammera,gallery,exis,deleteimg,request;
    public static  final  int GET_IMG_REQUEST=11;
    public static  final  int GET_Gallery_REQUEST=12;
    Dialog mdialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageReference storageRef;
    private Uri prescriptionURI;
    byte[] datas;
    private SharedPreferences sharedPreferences;

    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_prescription);

        cammera=findViewById(R.id.cammera);
        gallery=findViewById(R.id.gallery);
        exis=findViewById(R.id.exist);
        deleteimg=findViewById(R.id.deleteimg);
        request=findViewById(R.id.request_quotation);

        description=findViewById(R.id.des_prescription);

        storageRef= FirebaseStorage.getInstance().getReference();


        mdialog=new Dialog(Upload_prescription.this);
        mdialog.setContentView(R.layout.popup_uploadprescription);
       // Toast.makeText(Upload_prescription.this,"click",Toast.LENGTH_LONG).show();
        mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mdialog.show();

        exis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });




        cammera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(Upload_prescription.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(Upload_prescription.this, new String[] {Manifest.permission.CAMERA}, GET_IMG_REQUEST);

                }else{

                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent,GET_IMG_REQUEST);

                }





            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/*");

                startActivityForResult(intent,GET_Gallery_REQUEST);



            }
        });


        deleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView=findViewById(R.id.imageView3);
                imageView.setImageDrawable(null);
                deleteimg.setBackgroundColor(Color.TRANSPARENT);
                deleteimg.setText("");


            }
        });


     request.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


             ImageView imageView=findViewById(R.id.imageView3);




           //  imageView.VISIBLE

             if(imageView.getDrawable() != null){

                 request.setEnabled(false);
                 request.setText("Processing...");


                 Prescription prescription=new Prescription();
                 sharedPreferences= Mysharedpreference.getInstance(Upload_prescription.this);

                String uemail = sharedPreferences.getString("email","1");
                String uname = sharedPreferences.getString("name","1");

                 prescription.setUploadstatus("submit");
                // prescription.setImgurl("");
                 prescription.setPatientname(uname);
                 prescription.setPatientemail(uemail);
                 prescription.setDescription(description.getText()+"");
                 prescription.setSubmitdate(new Date());


              String imgpath="Uploadprescription"+System.currentTimeMillis()+".png";


              if(datas !=null){
                  storageRef.child("prescriptionimg/"+imgpath).putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                      }
                  }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                          prescription.setImgurl(imgpath);
                           datas=null;
                          uploadprescription(prescription);


                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                      }
                  });


              }else{

                  storageRef.child("prescriptionimg/"+imgpath).putFile(prescriptionURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          Toast.makeText(Upload_prescription.this, "UPload Sucessfully", Toast.LENGTH_SHORT).show();


                      }
                  }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                          prescription.setImgurl(imgpath);
                          prescriptionURI=null;

                          uploadprescription(prescription);

                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                      }
                  });






              }













             }else {

                 Toast.makeText(Upload_prescription.this,"Please Upload prescription",Toast.LENGTH_LONG).show();

             }


         }
     });






    }


public  void uploadprescription(Prescription prescription){


    db.collection("prescription").add(prescription).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
        @Override
        public void onComplete(@NonNull Task<DocumentReference> task) {
            Toast.makeText(Upload_prescription.this, "UPload Sucessfully", Toast.LENGTH_SHORT).show();
            request.setEnabled(true);
            request.setText("Request For Quotation");
            ImageView imageView=findViewById(R.id.imageView3);
            imageView.setImageDrawable(null);
            deleteimg.setBackgroundColor(Color.TRANSPARENT);
            deleteimg.setText("");
            description.setText("");



        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            request.setEnabled(true);
            request.setText("Request For Quotation");
            ImageView imageView=findViewById(R.id.imageView3);

        }
    });

}





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_IMG_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,GET_IMG_REQUEST);
            } else {

            }
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_IMG_REQUEST & resultCode==RESULT_OK){

           // prescriptionURI= data.getData();

             Bitmap   bitmap = data.getParcelableExtra("data");


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
             datas = baos.toByteArray();

           ImageView imageView=findViewById(R.id.imageView3);
            imageView.setImageBitmap(bitmap);
            deleteimg.setBackgroundColor(Color.BLACK);
            deleteimg.setText(R.string.deletebtn);

            //imageView.setImageBitmap();

            Toast.makeText(this," taken",Toast.LENGTH_LONG).show();
        }else{

           // Toast.makeText(this,"Not taken",Toast.LENGTH_LONG).show();

        }


        if(requestCode==GET_Gallery_REQUEST & resultCode==RESULT_OK){


            prescriptionURI = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), prescriptionURI);
                ImageView imageView=findViewById(R.id.imageView3);
                imageView.setImageBitmap(bitmap);
                deleteimg.setBackgroundColor(Color.BLACK);
                deleteimg.setText(R.string.deletebtn);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }






    }







}