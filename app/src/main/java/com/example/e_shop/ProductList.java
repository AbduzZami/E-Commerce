package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    List<Product> mProduct ;
    ProductAdapter productAdapter ;
    String category_name ;
    DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        textView = findViewById(R.id.catname);

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("Catname")!=null)
        {
            category_name = bundle.getString("Catname");
            textView.setText(category_name);
        }

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mProduct = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Category_items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mProduct.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product.getItem_cat().equals(category_name))
                    mProduct.add(product);
                }
                productAdapter = new ProductAdapter(ProductList.this,mProduct);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}