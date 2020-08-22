package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class ProductView extends AppCompatActivity {

    DatabaseReference databaseReference ;
    TextView prod_name , prod_price , toolbartitle;
    Button cartbutton ;
    ImageView prod_image ;
    String cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prod_name = findViewById(R.id.prod_name);
        prod_price = findViewById(R.id.prod_price);
        cartbutton = findViewById(R.id.cartho);
        prod_image = findViewById(R.id.prod_img);
        toolbartitle = findViewById(R.id.prod_name_toolbar);


        final Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        if (bundle.getString("ProductImage")!=null)
        {
            Picasso.get().load(bundle.getString("ProductImage")).fit().placeholder(R.drawable.ic_baseline_image_24).centerCrop().into(prod_image);
        }
        if (bundle.getString("ProductName")!=null)
        {
            prod_name.setText(bundle.getString("ProductName"));
            toolbartitle.setText(bundle.getString("ProductName"));
        }
        if (bundle.getString("ProductPrice")!=null)
        {
            prod_price.setText(bundle.getString("ProductPrice"));
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category_items");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product.getItem_id().equals(bundle.getString("ProductId"))) {
                        cart = product.getItem_cart();
                        if (cart.equals("yes")) {
                            cartbutton.setText(R.string.removefromcart);
                        } else {
                            cartbutton.setText(R.string.addtocart);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.equals("no"))
                {
                    //product.setItem_cart("yes");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category_items").child(bundle.getString("ProductId"));
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("item_cart","yes");
                    databaseReference.updateChildren(hashMap);
                    Toast.makeText(ProductView.this,"Added to cart",Toast.LENGTH_SHORT).show();
                    //cartbutton.setText(R.string.removefromcart);
                }
                else {
                    //product.setItem_cart("no");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category_items").child(bundle.getString("ProductId"));
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("item_cart","no");
                    databaseReference.updateChildren(hashMap);
                    //cartbutton.setText(R.string.addtocart);
                    Toast.makeText(ProductView.this,"Removed from cart",Toast.LENGTH_SHORT).show();
                }
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