package com.lk.nxt_pharma.ProductCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.StatusResponse;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.nxt_pharma.Coverpage;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Orders;
import com.lk.nxt_pharma.entity.Shoppingcart;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class checkOut extends AppCompatActivity {


    private static final int PAYHERE_REQUEST =11010;
    TextView total,shipcost,grosstotal;
    Button proceed;
    double stotal;
    String patientname;
    String patientmobi;
    String patientemail;
    double shipcosts;
    double dtotal;
    double dgrosstotal;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RadioGroup radioGroup;
    private String cusID;
    Task<QuerySnapshot> querySnapshotTask;
    private SharedPreferences sharedPreferences;
    private String buttonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        total=findViewById(R.id.checktotal);
        shipcost=findViewById(R.id.checkshipcost);
        grosstotal=findViewById(R.id.checkgrosstotal);
        radioGroup=findViewById(R.id.radioGroup);



        Bundle bundle = getIntent().getExtras();


        dtotal= bundle.getDouble("total");
        String sgrosstotal = bundle.getString("grosstotal");
         patientname = bundle.getString("patientname");
         patientmobi = bundle.getString("patientmobi");
         patientemail = bundle.getString("patientemail");
         cusID = bundle.getString("cusID");
        buttonID = bundle.getString("buttonID");


       querySnapshotTask = db.collection("Shoppingcart").whereEqualTo("cusID",cusID).get();



        proceed=findViewById(R.id.proceedbtn);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if(radioGroup.getCheckedRadioButtonId()==-1){

                    Toast.makeText(checkOut.this, "Please select Payment...", Toast.LENGTH_SHORT).show();

                }else{

                    int selectedId = radioGroup.getCheckedRadioButtonId();

                    RadioButton radioButton = (RadioButton) findViewById(selectedId);


                    if(radioButton.isChecked()){

                        if(radioButton.getText().equals("card payment")){






                            proceed.setText("Processing...");
                            proceed.setEnabled(false);


                            InitRequest req = new InitRequest();
                            req.setMerchantId("1214250");       // Your Merchant PayHere ID
                            req.setMerchantSecret("4TxkxJfUL3c4koBI4Q3ahs4Tq08e8LbBW49aLFEDwhc2"); // Your Merchant secret (Add your app at Settings > Domains & Credentials, to get this))
                            req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
                            req.setAmount(dgrosstotal);             // Final Amount to be charged
                            req.setOrderId("230000123");        // Unique Reference ID
                            req.setItemsDescription("Nxtpharma customer payment");  // Item description title
                            req.setCustom1("This is the custom message 1");
                            req.setCustom2("This is the custom message 2");
                            req.getCustomer().setFirstName(patientname);
                            req.getCustomer().setLastName("Perera");
                            req.getCustomer().setEmail(patientemail);
                            req.getCustomer().setPhone(patientmobi);
                            req.getCustomer().getAddress().setAddress("No.1, Galle Road");
                            req.getCustomer().getAddress().setCity("Colombo");
                            req.getCustomer().getAddress().setCountry("Sri Lanka");

//Optional Params
                            req.getCustomer().getDeliveryAddress().setAddress("No.2, Kandy Road");
                            req.getCustomer().getDeliveryAddress().setCity("Kadawatha");
                            req.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");
                            // req.getItems().add(new Item(null, "Door bell wireless", 1, 1000.0));

                            Intent intent = new Intent(checkOut.this, PHMainActivity.class);
                            intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
                            PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
                            startActivityForResult(intent, PAYHERE_REQUEST);

                        }else if(radioButton.getText().equals("Cash On Delivery")){
                            //   Toast.makeText(checkOut.this, "cash payment", Toast.LENGTH_SHORT).show();


                            proceed.setText("Processing...");
                            proceed.setEnabled(false);

                            if(buttonID.equals("cart")){

                                querySnapshotTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if(task.getResult().size()>0){


                                            List<Shoppingcart> shoppingcarts = task.getResult().toObjects(Shoppingcart.class);


                                            Toast.makeText(checkOut.this, ""+shoppingcarts.size(), Toast.LENGTH_SHORT).show();

                                            for(int i=0;i<shoppingcarts.size();i++){

                                                Shoppingcart shoppingcart = shoppingcarts.get(i);


                                                Toast.makeText(checkOut.this, ""+shoppingcart.getProductname(), Toast.LENGTH_SHORT).show();
                                                Orders order=new Orders();

                                                order.setCusID(shoppingcart.getCusID());
                                                order.setProductname(shoppingcart.getProductname());
                                                order.setProductID(shoppingcart.getProductID());
                                                order.setItemcost(shoppingcart.getItemCost());
                                                order.setQty(shoppingcart.getQty());
                                                order.setStatus("placed");
                                                order.setPayment("cash");

                                                SimpleDateFormat df  = new SimpleDateFormat("E, dd MMM yyyy");
                                                String format = df.format(new Date());
                                                try {
                                                    Date currentdate = df.parse(format);

                                                    order.setDate(currentdate);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }



                                                db.collection("Orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                                        if(task.isSuccessful()){

                                                            db.collection("Shoppingcart").whereEqualTo("productID",shoppingcart.getProductID()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    String shopcarID = task.getResult().getDocuments().get(0).getId();

                                                                    db.collection("Shoppingcart").document(shopcarID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            proceed.setText("Proceed order");
                                                                            proceed.setEnabled(true);

                                                                            Toast.makeText(checkOut.this, "order palced", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });



                                                                }
                                                            });












                                                        }

                                                    }
                                                });






                                            }





                                        }else{








                                        }








                                    }
                                });

                            }else  if(buttonID.equals("buynow")){


                                sharedPreferences= Mysharedpreference.getInstance(checkOut.this);

                                String productname = sharedPreferences.getString("productname","1");
                                String productID = sharedPreferences.getString("productID","1");
                                String qty = sharedPreferences.getString("qty","0");


                                if(!productname.equals("1") | !productID.equals("1")| !qty.equals("0")){


                                    Orders order=new Orders();

                                    order.setCusID(cusID);
                                    order.setProductname(productname);
                                    order.setProductID(productID);
                                    order.setItemcost(dtotal);
                                    order.setQty(Double.parseDouble(qty));
                                    order.setStatus("placed");
                                    order.setPayment("cash");

                                    SimpleDateFormat df  = new SimpleDateFormat("E, dd MMM yyyy");
                                    String format = df.format(new Date());
                                    try {
                                        Date currentdate = df.parse(format);

                                        order.setDate(currentdate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    db.collection("Orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {

                                            if(task.isSuccessful()){




                                                sharedPreferences.edit().remove(productname).commit();
                                                sharedPreferences.edit().remove(productID).commit();
                                                sharedPreferences.edit().remove(qty).commit();

                                                proceed.setText("Proceed order");
                                                proceed.setEnabled(true);

                                                Toast.makeText(checkOut.this, "order palced", Toast.LENGTH_SHORT).show();






                                            }

                                        }
                                    });






                                }

                            }








                        }
                    }else{





                    }






                }

                // find the radio button by returned id







            }
        });




    }

    public void setEstimatedvalue(String distancetext) {
    }

    public void setEstimatedcost(double distancevalue) {

        Toast.makeText(this, ""+distancevalue, Toast.LENGTH_SHORT).show();
        shipcosts =(distancevalue/1000)*35;



        total.setText("RS : " +dtotal+"");


        shipcost.setText(shipcosts+"");
        dgrosstotal=dtotal+shipcosts;

        grosstotal.setText("RS : " +dgrosstotal+"");



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null)
                    if (response.isSuccess()) {
                        msg = "Activity result:" + response.getData().toString();

                        if(buttonID.equals("cart")){
                            querySnapshotTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if(task.getResult().size()>0){
                                        List<Shoppingcart> shoppingcarts = task.getResult().toObjects(Shoppingcart.class);


                                        Toast.makeText(checkOut.this, ""+shoppingcarts.size(), Toast.LENGTH_SHORT).show();

                                        for(int i=0;i<shoppingcarts.size();i++){

                                            Shoppingcart shoppingcart = shoppingcarts.get(i);


                                            Toast.makeText(checkOut.this, ""+shoppingcart.getProductname(), Toast.LENGTH_SHORT).show();
                                            Orders order=new Orders();

                                            order.setCusID(shoppingcart.getCusID());
                                            order.setProductname(shoppingcart.getProductname());
                                            order.setProductID(shoppingcart.getProductID());
                                            order.setItemcost(shoppingcart.getItemCost());
                                            order.setQty(shoppingcart.getQty());
                                            order.setStatus("placed");
                                            order.setPayment("card payment");

                                            SimpleDateFormat df  = new SimpleDateFormat("E, dd MMM yyyy");
                                            String format = df.format(new Date());
                                            try {
                                                Date currentdate = df.parse(format);

                                                order.setDate(currentdate);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }





                                            db.collection("Orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                                    if(task.isSuccessful()){

                                                        db.collection("Shoppingcart").whereEqualTo("productID",shoppingcart.getProductID()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                String shopcarID = task.getResult().getDocuments().get(0).getId();

                                                                db.collection("Shoppingcart").document(shopcarID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        proceed.setText("Proceed order");
                                                                        proceed.setEnabled(true);

                                                                        Toast.makeText(checkOut.this, "order palced", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });



                                                            }
                                                        });












                                                    }

                                                }
                                            });






                                        }

                                    }






                                }
                            });

                        }else  if(buttonID.equals("buynow")){


                            sharedPreferences= Mysharedpreference.getInstance(checkOut.this);

                            String productname = sharedPreferences.getString("productname","1");
                            String productID = sharedPreferences.getString("productID","1");
                            String qty = sharedPreferences.getString("qty","0");


                            if(!productname.equals("1") | !productID.equals("1")| !qty.equals("0")){


                                Orders order=new Orders();

                                order.setCusID(cusID);
                                order.setProductname(productname);
                                order.setProductID(productID);
                                order.setItemcost(dtotal);
                                order.setQty(Double.parseDouble(qty));
                                order.setStatus("placed");
                                order.setPayment("card payment");

                                SimpleDateFormat df  = new SimpleDateFormat("E, dd MMM yyyy");
                                String format = df.format(new Date());
                                try {
                                    Date currentdate = df.parse(format);

                                    order.setDate(currentdate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                db.collection("Orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                        if(task.isSuccessful()){




                                            sharedPreferences.edit().remove(productname).commit();
                                            sharedPreferences.edit().remove(productID).commit();
                                            sharedPreferences.edit().remove(qty).commit();
                                            proceed.setText("Proceed order");
                                            proceed.setEnabled(true);


                                            Toast.makeText(checkOut.this, "order palced", Toast.LENGTH_SHORT).show();






                                        }

                                    }
                                });






                            }


                        }





                    }else {
                        msg = "Result:" + response.toString();
                    }else
                    msg = "Result: no response";
              //  Log.d(TAG, msg);
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














}