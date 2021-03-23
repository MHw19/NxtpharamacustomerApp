package com.lk.nxt_pharma.ProductCart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.StatusResponse;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.nxt_pharma.Adapter.ProductcartHolder;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Patient;
import com.lk.nxt_pharma.entity.Product;
import com.lk.nxt_pharma.entity.Shoppingcart;
import com.lk.nxt_pharma.products.Product_list;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_cart extends AppCompatActivity {


    private static final int PAYHERE_REQUEST =11010 ;
    private static final String TAG = "Product_cart";
    RecyclerView recyclerView;
    
    Button checkout;
    
    TextView total,shippingcost,grosstotal;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences sharedPreferences;
    private String cusID;

    public FirestoreRecyclerAdapter adapter;

    String shoppingcartID;

    double totalamount;

    Patient patient;

    Query cartTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);
        
        recyclerView=findViewById(R.id.cart_Itemlist);
        
        checkout=findViewById(R.id.checkoutbtn);


        total=findViewById(R.id.total_amount);
        grosstotal=findViewById(R.id.gross_amount);

        sharedPreferences= Mysharedpreference.getInstance(Product_cart.this);

        cusID = sharedPreferences.getString("cusID","1");
        
        recyclerView.setLayoutManager(new LinearLayoutManager(Product_cart.this));

        StorageReference reference = FirebaseStorage.getInstance().getReference();

        Query loadquery = db.collection("Shoppingcart").whereEqualTo("cusID", cusID);

        db.collection("patients").document(cusID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                patient = task.getResult().toObject(Patient.class);
            }
        });




      //  CollectionReference    =


        cartTotal = db.collection("Shoppingcart").whereEqualTo("cusID",cusID);


        CollectionReference productref = db.collection("Products");


        FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Shoppingcart>().setQuery(loadquery,Shoppingcart.class).build();


        adapter=new FirestoreRecyclerAdapter<Shoppingcart,ProductcartHolder>(recyclerOptions) {
            @NonNull
            @Override
            public ProductcartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(Product_cart.this).inflate(R.layout.cart_item, parent, false);

                return new ProductcartHolder(view,Product_cart.this);
            }


            @Override
            public long getItemId(int position) {
                return super.getItemId(position);



            }

            @Override
            protected void onBindViewHolder(@NonNull ProductcartHolder holder, int position, @NonNull Shoppingcart model) {


              //  shoppingcartID= getSnapshots().getSnapshot(position).getId();


                System.out.println(shoppingcartID);

                productref.document(model.getProductID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                        Product product = task.getResult().toObject(Product.class);

                        double dprice  = product.getPriceafterdiscount();


                        reference.child("productimg/").child(product.getImgurl()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                Uri uri = task.getResult();

                                Picasso.with(Product_cart.this).load(uri).into(holder.imageView);
                                holder.prdname.setText(model.getProductname());
                                holder.itemcost.setText("RS : "+model.getItemCost());

                                holder.ProductID=model.getProductID();
                                holder.itemPrice=dprice;
                                holder.shoppingcartID=getSnapshots().getSnapshot(position).getId();
                                holder.cusID=cusID;


                                holder.prdqty.setText(""+model.getQty());

                                updateTotal(cusID);


                            }
                        });




                    }
                });







            }
        };


        recyclerView.setAdapter(adapter);



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                InitRequest req = new InitRequest();
//                req.setMerchantId("1214250");       // Your Merchant PayHere ID
//                req.setMerchantSecret("4TxkxJfUL3c4koBI4Q3ahs4Tq08e8LbBW49aLFEDwhc2"); // Your Merchant secret (Add your app at Settings > Domains & Credentials, to get this))
//                req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
//                req.setAmount(totalamount);             // Final Amount to be charged
//                req.setOrderId("230000123");        // Unique Reference ID
//                req.setItemsDescription("Nxtpharma customer payment");  // Item description title
//                req.setCustom1("This is the custom message 1");
//                req.setCustom2("This is the custom message 2");
//                req.getCustomer().setFirstName(patient.getFullName());
//                req.getCustomer().setLastName("Perera");
//                req.getCustomer().setEmail(patient.getEmail());
//                req.getCustomer().setPhone(patient.getMobile());
//                req.getCustomer().getAddress().setAddress("No.1, Galle Road");
//                req.getCustomer().getAddress().setCity("Colombo");
//                req.getCustomer().getAddress().setCountry("Sri Lanka");
//
////Optional Params
//                req.getCustomer().getDeliveryAddress().setAddress("No.2, Kandy Road");
//                req.getCustomer().getDeliveryAddress().setCity("Kadawatha");
//                req.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");
//               // req.getItems().add(new Item(null, "Door bell wireless", 1, 1000.0));
//
//                Intent intent = new Intent(Product_cart.this,PHMainActivity.class);
//                intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
//                PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
//                startActivityForResult(intent, PAYHERE_REQUEST);


                Intent intent=new Intent(Product_cart.this,checkOut.class);

                intent.putExtra("total",totalamount);
                intent.putExtra("grosstotal",grosstotal.getText());
                intent.putExtra("patientname",patient.getFullName());
                intent.putExtra("patientmobi",patient.getMobile());
                intent.putExtra("patientemail",patient.getEmail());
                intent.putExtra("cusID",cusID);
                intent.putExtra("buttonID","cart");

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);



            }
        });















    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null)
                    if (response.isSuccess())
                        msg = "Activity result:" + response.getData().toString();
                    else
                        msg = "Result:" + response.toString();
                else
                    msg = "Result: no response";
                Log.d(TAG, msg);
                //  textView.setText(msg);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response != null) {
                    //  textView.setText(response.toString());
                } else {
                    // textView.setText("User canceled the request");
                }
            }
        }

    }







    public void updateTotal(String cusID) {
        db.collection("Shoppingcart").whereEqualTo("cusID", cusID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<Shoppingcart> shopcarts = queryDocumentSnapshots.toObjects(Shoppingcart.class);


                if(shopcarts.size()>0){

                    totalamount=0;

                    total=findViewById(R.id.total_amount);

                    for (Shoppingcart shoppingcart:shopcarts){

                        totalamount += shoppingcart.getItemCost();

                        total.setText("Rs : "+totalamount);
                        grosstotal.setText("Rs : "+totalamount);


                    }
                }else{
                    total.setText("Rs : "+0.00);
                    grosstotal.setText("Rs : "+0.00);


                }





            }
        });




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