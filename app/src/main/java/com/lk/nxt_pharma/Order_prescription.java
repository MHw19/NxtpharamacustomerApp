package com.lk.nxt_pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.nxt_pharma.Adapter.PrescriptionHolder;
import com.lk.nxt_pharma.entity.Prescription;
import com.squareup.picasso.Picasso;

public class Order_prescription extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
     FirestoreRecyclerAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_prescription);


        recyclerView=findViewById(R.id.prescription_list);
        StorageReference reference = FirebaseStorage.getInstance().getReference();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

      Query loadquery = db.collection("prescription");
      FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Prescription>().setQuery(loadquery,Prescription.class).build();

      //  Toast.makeText(Order_prescription.this, "After onBind Call", Toast.LENGTH_SHORT).show();

     recycleAdapter= new FirestoreRecyclerAdapter<Prescription,PrescriptionHolder>(recyclerOptions){



         @Override
         protected void onBindViewHolder(@NonNull PrescriptionHolder holder, int position, @NonNull Prescription model) {




             reference.child("prescriptionimg/").child(model.getImgurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                 @Override
                 public void onSuccess(Uri uri) {

                     Picasso.with(Order_prescription.this).load(uri).into(holder.imageView);
                     holder.udate.setText(model.getSubmitdate()+"");
                     holder.ustatus.setText(model.getUploadstatus());

                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                     Log.d("Order_prescription","failed to"+e.getMessage());

                     Toast.makeText(Order_prescription.this, "failed to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                 }
             });



         }

         @NonNull
          @Override
          public PrescriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            // Toast.makeText(Order_prescription.this, " Top onBind Call", Toast.LENGTH_SHORT).show();
             View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_item,parent,false);

             return new PrescriptionHolder(view);
          }


      };


          recyclerView.setAdapter(recycleAdapter);




    }


    @Override
    protected void onStart() {
        super.onStart();


        recycleAdapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        recycleAdapter.stopListening();



    }



}