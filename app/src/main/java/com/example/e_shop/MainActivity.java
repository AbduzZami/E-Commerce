package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ViewFlipper viewFlipper ;
    RecyclerView recyclerView , recyclerView2;
    PopularAdapter popularAdapter ;
    List<Popular> mpopular ;
    CategoryAdapter categoryAdapter;
    DatabaseReference databaseReference ;
    List<BannerPics> bannerPics ;
    List<Category> mCategory ;
    ImageButton button1,button2;
    int collapspop = 1;
    int collapscat = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        viewFlipper = findViewById(R.id.viewflipper);
        button1 = findViewById(R.id.collapspopular);
        button2 = findViewById(R.id.collapscategory);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collapspop == 1) {
                    recyclerView.setVisibility(View.GONE);
                    button1.setImageResource(R.drawable.visible);
                    collapspop = 0 ;
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    button1.setImageResource(R.drawable.invisible);
                    collapspop = 1 ;
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collapscat == 1) {
                    recyclerView2.setVisibility(View.GONE);
                    button2.setImageResource(R.drawable.visible);
                    collapscat = 0 ;
                }
                else
                {
                    recyclerView2.setVisibility(View.VISIBLE);
                    button2.setImageResource(R.drawable.invisible);
                    collapscat = 1 ;
                }
            }
        });

        readPopularItems();
        readCategories();

        bannerPics = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Banner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bannerPics.clear();

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren())
                {
                    BannerPics bannerPics1 = dataSnapshot1.getValue(BannerPics.class);
                    bannerPics.add(bannerPics1);
                    //BannerFlipper(dataSnapshot.getValue().toString());
                    
                }
                usingFirebaseImages(bannerPics);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readCategories() {
        recyclerView2 = findViewById(R.id.recyclerview2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new GridLayoutManager(this,2));
        mCategory = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mCategory.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Category category = dataSnapshot.getValue(Category.class);
                    mCategory.add(category);
                }
                categoryAdapter = new CategoryAdapter(getApplicationContext(),mCategory);
                recyclerView2.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readPopularItems() {
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        mpopular = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Popular");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mpopular.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Popular popular = dataSnapshot.getValue(Popular.class);
                    mpopular.add(popular);
                }
                popularAdapter = new PopularAdapter(getApplicationContext(),mpopular);
                recyclerView.setAdapter(popularAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void usingFirebaseImages(List<BannerPics> slideLists) {
        for (int i = 0; i < slideLists.size(); i++) {
            String downloadImageUrl = slideLists.get(i).getImage_url();
            BannerFlipper(downloadImageUrl);
        }
    }

    public void BannerFlipper(String image)
    {

        ImageView imageView = new ImageView(this);
        Picasso.get().load(image).placeholder(R.drawable.az_market).into(imageView);
        viewFlipper.addView(imageView);
        //viewFlipper.setInAnimation(this,R.anim.fade_in);
        //viewFlipper.setOutAnimation(this,R.anim.fade_out);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cart)
        {
            Toast.makeText(getApplicationContext(),"Cart clicked",Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.account)
        {
            Intent intent = new Intent(MainActivity.this,UserProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}