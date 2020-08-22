package com.example.e_shop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ImageViewHolder> {
    private Context context;
    private List<Product> mpopular ;

    public PopularAdapter(Context context, List<Product> mpopular) {
        this.context = context;
        this.mpopular = mpopular;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_item,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final Product product = mpopular.get(position);
        holder.product_name.setText(product.getItem_name());
        holder.priduct_price.setText(product.getItem_price());
        Picasso.get().load(product.getItem_image()).fit().placeholder(R.drawable.ic_baseline_image_24).centerCrop().into(holder.product_image);


        if (product.getItem_cart().equals("no"))
        {
            holder.product_cart.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        else
            holder.product_cart.setImageResource(R.drawable.ic_baseline_favorite_24);

        holder.product_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getItem_cart().equals("no"))
                {
                    //product.setItem_cart("yes");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category_items").child(product.getItem_id());
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("item_cart","yes");
                    databaseReference.updateChildren(hashMap);
                }
                else {
                    //product.setItem_cart("no");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category_items").child(product.getItem_id());
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("item_cart","no");
                    databaseReference.updateChildren(hashMap);
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProductView.class);
                intent.putExtra("ProductName",product.getItem_name());
                intent.putExtra("ProductPrice",product.getItem_price());
                intent.putExtra("ProductId",product.getItem_id());
                intent.putExtra("ProductImage",product.getItem_image());
                intent.putExtra("ProductCart",product.getItem_cart());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mpopular.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private TextView product_name,priduct_price;
        private ImageView product_image , product_cart ;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            priduct_price = itemView.findViewById(R.id.product_price);
            product_cart = itemView.findViewById(R.id.cartimg);
        }
    }
}
