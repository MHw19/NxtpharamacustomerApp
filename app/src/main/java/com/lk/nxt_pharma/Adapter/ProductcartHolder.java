package com.lk.nxt_pharma.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.nxt_pharma.ProductCart.Product_cart;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Product;
import com.lk.nxt_pharma.entity.Shoppingcart;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductcartHolder extends RecyclerView.ViewHolder {

    private final Product_cart context;
    public ImageView imageView;
    public TextView prdname,prdqty,itemcost;

    ImageButton reducebtn,addbtn,deletebtn;

    public  String ProductID;
    public  String shoppingcartID;
    public  String cusID;

     double currentqty;
     public double itemPrice;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProductcartHolder(@NonNull View itemView, Product_cart context) {
        super(itemView);
        this.context=context;


       prdname= itemView.findViewById(R.id.cartprd_name);
       prdqty= itemView.findViewById(R.id.cart_qty);
       itemcost= itemView.findViewById(R.id.item_total);
       imageView=itemView.findViewById(R.id.cart_img);


       reducebtn=itemView.findViewById(R.id.reducebtn);
       addbtn=itemView.findViewById(R.id.addbtn);
       deletebtn=itemView.findViewById(R.id.deletebtn);



       reducebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               reduceQty();

           }
       });

       addbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               increaseQty();

           }
       });


       deletebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               deleteCartItem();
              // ((Product_cart)getActivity()).startChronometer();

           }
       });








    }

    private void deleteCartItem() {

        Toast.makeText(itemView.getContext(), "del calling1 "+shoppingcartID, Toast.LENGTH_SHORT).show();

        db.collection("Shoppingcart").document(shoppingcartID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(itemView.getContext(), "del calling1 "+shoppingcartID, Toast.LENGTH_SHORT).show();



                context.updateTotal(cusID);
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(itemView.getContext(), "dete calling2", Toast.LENGTH_SHORT).show();


            }
        });






    }







    private void reduceQty() {

         currentqty = Double.parseDouble(prdqty.getText().toString());

        if(currentqty >1){

            currentqty--;



        }else{

            currentqty=1;

        }



        prdqty.setText(""+currentqty);
        //qty,item price
        updateqtycost(currentqty,itemPrice);



    }

    private void updateqtycost(double currentqty, double itemPrice) {


        Toast.makeText(itemView.getContext(), ""+shoppingcartID, Toast.LENGTH_SHORT).show();

        db.collection("Shoppingcart").document(shoppingcartID).update("qty",currentqty,"itemCost",(currentqty*itemPrice)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });



    }

    private void increaseQty() {

         currentqty = Double.parseDouble(prdqty.getText().toString());


         db.collection("Products").document(ProductID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {




                 Product product = documentSnapshot.toObject(Product.class);

                 Integer stock = product.getStock();

                 if(stock>currentqty){

                    currentqty++;



                 }else{


                     currentqty=stock;
                 }


                 prdqty.setText(""+currentqty);

                 updateqtycost(currentqty,itemPrice);


             }
         });





    }



}
