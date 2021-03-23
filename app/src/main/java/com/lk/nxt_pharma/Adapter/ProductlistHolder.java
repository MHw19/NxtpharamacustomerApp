package com.lk.nxt_pharma.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.nxt_pharma.Login;
import com.lk.nxt_pharma.ProductCart.Product_cart;
import com.lk.nxt_pharma.ProductCart.checkOut;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Patient;
import com.lk.nxt_pharma.entity.Shoppingcart;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ProductlistHolder extends RecyclerView.ViewHolder {

   public TextView prdname,pqty,disprice,price;
   public ImageView pimage;

   Button addbtn,buynowbtn;

    public String productID;
   public  String cusID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences sharedPreferences;

    public ProductlistHolder(@NonNull View itemView) {
        super(itemView);


        prdname=itemView.findViewById(R.id.prdname);
        pqty=itemView.findViewById(R.id.available_qty);
        disprice=itemView.findViewById(R.id.dprice);
        price=itemView.findViewById(R.id.bdprice);
        pimage=itemView.findViewById(R.id.prd_image);


        addbtn=itemView.findViewById(R.id.cartbtn);
        buynowbtn=itemView.findViewById(R.id.buynowbtn);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Shoppingcart cart=new Shoppingcart();


                cart.setCusID(cusID);
                cart.setProductID(productID);
                cart.setProductname(prdname.getText().toString());
                cart.setQty(1);
                cart.setItemCost(Double.parseDouble(disprice.getText().toString())*1);

                Toast.makeText(itemView.getContext(),""+productID, Toast.LENGTH_SHORT).show();

                db.collection("Shoppingcart").whereEqualTo("productID",productID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful() & !task.getResult().isEmpty()){

                            Shoppingcart shoppingcart = task.getResult().getDocuments().get(0).toObject(Shoppingcart.class);




                            Toast.makeText(itemView.getContext(), "update"+shoppingcart.getProductname(), Toast.LENGTH_SHORT).show();


                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                            String currentcardID = documentSnapshot.getId();

                           // Shoppingcart shoppingcart = documentSnapshot.toObject(Shoppingcart.class);


                            Toast.makeText(itemView.getContext(), ""+currentcardID, Toast.LENGTH_SHORT).show();


                            db.collection("Shoppingcart").document(currentcardID).update("qty",shoppingcart.getQty()+1,"itemCost",shoppingcart.getItemCost()+(Double.parseDouble(disprice.getText().toString())*1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(itemView.getContext(), "Update  Cart Sucessfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(itemView.getContext(), Product_cart.class);

                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    itemView.getContext().startActivity(intent);

                                }
                            });


                        }else{

                           Toast.makeText(itemView.getContext(), "Add", Toast.LENGTH_SHORT).show();

                            db.collection("Shoppingcart").add(cart).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {


                                    if(task.isSuccessful()){


                                        Toast.makeText(itemView.getContext(), "ADD To Cart Sucessfully", Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(itemView.getContext(), Product_cart.class);

                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                        itemView.getContext().startActivity(intent);









                                    }

                                }
                            });





                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(itemView.getContext(), "failed", Toast.LENGTH_SHORT).show();

                    }
                });






            }
        });


        buynowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(itemView.getContext(), " buy now calling...", Toast.LENGTH_SHORT).show();
                String fullName,mobile,email;

                db.collection("patients").document(cusID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                       if(task.isSuccessful()){


                           Patient patient = task.getResult().toObject(Patient.class);

                           String fullName = patient.getFullName();
                           String mobile = patient.getEmail();
                           String email = patient.getMobile();



                           double discountprice = Double.parseDouble((String) disprice.getText());

                           double total = discountprice * 1;

                           Intent intent=new Intent(itemView.getContext(), checkOut.class);

                           intent.putExtra("cusID",cusID);
                           intent.putExtra("total",total);
                           intent.putExtra("grosstotal",String.valueOf(total));
                           intent.putExtra("patientname",fullName);
                           intent.putExtra("patientmobi",mobile);
                           intent.putExtra("patientemail",email);
                           intent.putExtra("buttonID","buynow");


                           sharedPreferences= Mysharedpreference.getInstance(itemView.getContext());

                           SharedPreferences.Editor editor = sharedPreferences.edit();

                           editor.putString("productname",prdname.getText().toString());
                           editor.putString("productID",productID);

                           editor.putString("qty","1");

                           editor.commit();

                           itemView.getContext().startActivity(intent);



                       }


                    }
                });














                //db.collection("").



            }
        });









    }
}
