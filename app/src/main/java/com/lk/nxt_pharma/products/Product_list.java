package com.lk.nxt_pharma.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.nxt_pharma.Adapter.ProductlistHolder;
import com.lk.nxt_pharma.Home_page;
import com.lk.nxt_pharma.R;
import com.lk.nxt_pharma.entity.Product;
import com.lk.nxt_pharma.sharedpreference.Mysharedpreference;
import com.squareup.picasso.Picasso;

public class Product_list extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirestoreRecyclerAdapter adapter;

    String category;
    private SharedPreferences sharedPreferences;
    private String cusID;
    Spinner filteroption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

         category = bundle.getString("category");

        setTitle(category);

        setContentView(R.layout.activity_product_list);


        sharedPreferences= Mysharedpreference.getInstance(Product_list.this);

        cusID = sharedPreferences.getString("cusID","1");



        recyclerView=findViewById(R.id.cproduct_list);
        filteroption=findViewById(R.id.filterspinner);

        fillfilter();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StorageReference reference = FirebaseStorage.getInstance().getReference();

        Query loadquery = db.collection("Products").whereEqualTo("category", category);



        FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Product>().setQuery(loadquery,Product.class).build();



     adapter=new FirestoreRecyclerAdapter<Product, ProductlistHolder>(recyclerOptions){


         @NonNull
         @Override
         public ProductlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_oneitem, parent, false);

             return new ProductlistHolder(view);
         }

         @Override
         protected void onBindViewHolder(@NonNull ProductlistHolder holder, int position, @NonNull Product model) {
             reference.child("productimg/").child(model.getImgurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                 @Override
                 public void onSuccess(Uri uri) {

                     Picasso.with(Product_list.this).load(uri).into(holder.pimage);
                     holder.prdname.setText(model.getProductname());
                     holder.pqty.setText("Available qty:  "+model.getStock());
                     holder.disprice.setText(model.getPriceafterdiscount()+"");
                     holder.price.setText(model.getDiscount()+" % "+model.getPrice());


                     holder.productID= getSnapshots().getSnapshot(position).getId();

                     holder.cusID=cusID;


                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                 }
             });




         }
     };
//


        recyclerView.setAdapter(adapter);



        filteroption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedplace = parent.getSelectedItem().toString();

                if(selectedplace.equals("Price Low-High")){

                    StorageReference reference = FirebaseStorage.getInstance().getReference();

                    Query loadquery = db.collection("Products").orderBy("priceafterdiscount", Query.Direction.ASCENDING);

//.whereEqualTo("category",category)

                    FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Product>().setQuery(loadquery,Product.class).build();



                    adapter=new FirestoreRecyclerAdapter<Product,ProductlistHolder>(recyclerOptions){


                        @NonNull
                        @Override
                        public ProductlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_oneitem, parent, false);

                            return new ProductlistHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull ProductlistHolder holder, int position, @NonNull Product model) {
                            reference.child("productimg/").child(model.getImgurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Picasso.with(Product_list.this).load(uri).into(holder.pimage);
                                    holder.prdname.setText(model.getProductname());
                                    holder.pqty.setText("Available qty:  "+model.getStock());
                                    holder.disprice.setText(model.getPriceafterdiscount()+"");
                                    holder.price.setText(model.getDiscount()+" % "+model.getPrice());

                                    holder.productID= getSnapshots().getSnapshot(position).getId();

                                    holder.cusID=cusID;


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });




                        }
                    };
//
                    adapter.startListening();

                    recyclerView.setAdapter(adapter);


                }else if(selectedplace.equals("Price High-Low")){


                    StorageReference reference = FirebaseStorage.getInstance().getReference();

                    Query loadquery = db.collection("Products").orderBy("priceafterdiscount", Query.Direction.DESCENDING);

//.whereEqualTo("category",category)

                    FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Product>().setQuery(loadquery,Product.class).build();



                    adapter=new FirestoreRecyclerAdapter<Product,ProductlistHolder>(recyclerOptions){


                        @NonNull
                        @Override
                        public ProductlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_oneitem, parent, false);

                            return new ProductlistHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull ProductlistHolder holder, int position, @NonNull Product model) {
                            reference.child("productimg/").child(model.getImgurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Picasso.with(Product_list.this).load(uri).into(holder.pimage);
                                    holder.prdname.setText(model.getProductname());
                                    holder.pqty.setText("Available qty:  "+model.getStock());
                                    holder.disprice.setText(model.getPriceafterdiscount()+"");
                                    holder.price.setText(model.getDiscount()+" % "+model.getPrice());
                                    holder.productID= getSnapshots().getSnapshot(position).getId();

                                    holder.cusID=cusID;


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });




                        }
                    };
//
                    adapter.startListening();

                    recyclerView.setAdapter(adapter);





                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    private void fillfilter() {

        ArrayAdapter<String> optionadapter=new ArrayAdapter<>(Product_list.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.filteroptions));
        //this, android.R.layout.simple_spinner_item
        optionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        filteroption.setAdapter(optionadapter);


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu,menu);


        MenuItem item=menu.findItem(R.id.seach);


        SearchView searchView=(SearchView) item.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                processSearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);





    }

    private void processSearch(String s) {


        StorageReference reference = FirebaseStorage.getInstance().getReference();

        Query loadquery = db.collection("Products").orderBy("productname").startAt(s).endAt(s+"\uf8ff");

//.whereEqualTo("category",category)

        FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Product>().setQuery(loadquery,Product.class).build();



        adapter=new FirestoreRecyclerAdapter<Product,ProductlistHolder>(recyclerOptions){


            @NonNull
            @Override
            public ProductlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_oneitem, parent, false);

                return new ProductlistHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductlistHolder holder, int position, @NonNull Product model) {
                reference.child("productimg/").child(model.getImgurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.with(Product_list.this).load(uri).into(holder.pimage);
                        holder.prdname.setText(model.getProductname());
                        holder.pqty.setText("Available qty:  "+model.getStock());
                        holder.disprice.setText(model.getPriceafterdiscount()+"");
                        holder.price.setText(model.getDiscount()+" % "+model.getPrice());
                        holder.productID= getSnapshots().getSnapshot(position).getId();

                        holder.cusID=cusID;


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




            }
        };
//
         adapter.startListening();

         recyclerView.setAdapter(adapter);






    }
}