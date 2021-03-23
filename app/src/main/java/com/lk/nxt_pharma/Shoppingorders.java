package com.lk.nxt_pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lk.nxt_pharma.Adapter.MyorderHolder;
import com.lk.nxt_pharma.ProductCart.Product_cart;
import com.lk.nxt_pharma.entity.Orders;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;

public class Shoppingorders extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter<Orders, MyorderHolder> recycleAdapter;
    private SharedPreferences sharedPreferences;
    private String cusID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingorders);

        recyclerView=findViewById(R.id.myorderlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(Shoppingorders.this));

        sharedPreferences= Mysharedpreference.getInstance(Shoppingorders.this);

        cusID = sharedPreferences.getString("cusID","1");


        Query loadquery = db.collection("Orders").whereEqualTo("cusID",cusID);
        FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Orders>().setQuery(loadquery,Orders.class).build();


        recycleAdapter= new FirestoreRecyclerAdapter<Orders,MyorderHolder>(recyclerOptions) {


            @NonNull
            @Override
            public MyorderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_singleitem, parent, false);

                return new MyorderHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyorderHolder holder, int position, @NonNull Orders model) {



               holder.myorderdate.setText(String.valueOf(model.getDate()));
               holder.myorderpname.setText(model.getProductname());
               holder.myorderqty.setText(model.getQty()+"");
               holder.myordercost.setText(model.getItemcost()+"");
               holder.myorderpay.setText(model.getPayment());

               holder.myorderstatus.setText(model.getStatus());


                //holder.prdname.setText(model.getProductname());


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