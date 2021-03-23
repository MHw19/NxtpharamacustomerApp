package com.lk.nxt_pharma.appointments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.nxt_pharma.Adapter.Adapterfordoctorlist;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Doctor;
import com.squareup.picasso.Picasso;

public class Book_doctor extends AppCompatActivity {


    TextView heading;
    RecyclerView recyclerView;

    public FirestoreRecyclerAdapter  adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String profileImgurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_doctor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        heading=findViewById(R.id.doctortype);
        recyclerView=findViewById(R.id.doctorlist);



        Intent intent = getIntent();

        String type = intent.getStringExtra("type");


        heading.setText(type);
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(Book_doctor.this));

        Query loadquery = db.collection("Doctors").whereEqualTo("specification", type);

//.whereEqualTo("name","Child specialist")
//document().collection("Doctor")

        FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Doctor>().setQuery(loadquery,Doctor.class).build();



        adapter=new FirestoreRecyclerAdapter<Doctor, Adapterfordoctorlist>(recyclerOptions) {
            @NonNull
            @Override
            public Adapterfordoctorlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(Book_doctor.this).inflate(R.layout.onedoctor_item, parent, false);

                return new Adapterfordoctorlist(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Adapterfordoctorlist holder, int position, @NonNull Doctor model) {

                //Doctor


                profileImgurl = model.getDocImgurl();


                reference.child("Doctorimg/").child(profileImgurl).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Uri uri = task.getResult();
                        Picasso.with(Book_doctor.this).load(uri).into(holder.imageView15);
                        String DocID = getSnapshots().getSnapshot(position).getId();

                        holder.docname.setText(model.getFullname());
                        holder.docqualification.setText(model.getQualification());
                        holder.docemail.setText(model.getEmail());
                        holder.docexperience.setText(model.getExperience());

                        holder.DocID=DocID;




                    }
                });












            }
        };


        recyclerView.setAdapter(adapter);






//        db.collection("Doctors").whereEqualTo("specification",type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                List<Doctor> doctorList= task.getResult().toObjects(Doctor.class);
//
//                if(doctorList.size()>0){
//                    Toast.makeText(Book_doctor.this, ""+doctorList.size(), Toast.LENGTH_SHORT).show();
//
//                   // adapter = new Adapterfordoctorlist(Book_doctor.this,doctorList);
//                   // recyclerView.setLayoutManager(new LinearLayoutManager(Book_doctor.this));
////
//                   // recyclerView.setAdapter(adapter);
//
//
//                }else{
//
//                    Toast.makeText(Book_doctor.this, "No found any value", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//


//
//        db.collection("spefecification").document("Jx2Uxp1oF9fYf88qH9Ge").collection("Doctor")
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                List<Doctor> doctorList = queryDocumentSnapshots.toObjects(Doctor.class);
//                if(doctorList.size()>0){
//                    Toast.makeText(Book_doctor.this, ""+doctorList.size(), Toast.LENGTH_SHORT).show();
//
//                    adapter = new Adapterfordoctorlist(Book_doctor.this,doctorList);
//                   recyclerView.setLayoutManager(new LinearLayoutManager(Book_doctor.this));
////
//                  recyclerView.setAdapter(adapter);
//
//
//                }else{
//
//                    Toast.makeText(Book_doctor.this, "No found any value", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Book_doctor.this, "failed to connect", Toast.LENGTH_SHORT).show();
//
//            }
//        });








//        RequestQueue queue= Volley.newRequestQueue(Book_doctor.this);
//
//
//        String url="http://192.168.43.23:8080/Pharmacy/Adddoctor_type";
//        //192.168.43.23
//
//        StringRequest jsonObjectRequest= (StringRequest) new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//                System.out.println(response);
//
//               Gson gson=new Gson();
//
//
//                Type Doctortype=new TypeToken<List<Doctor>>(){}.getType();
//
//                List<Doctor> doctorList = gson.fromJson(response, Doctortype);
//
//
//
//
//               // JSONArray jArray = (JSONArray) response;
//
//
//                adapter = new Adapterfordoctorlist(Book_doctor.this,doctorList);
//                recyclerView.setLayoutManager(new LinearLayoutManager(Book_doctor.this));
//
//                  recyclerView.setAdapter(adapter);
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
//                m.put("parameter",type);
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








    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();


    }
}