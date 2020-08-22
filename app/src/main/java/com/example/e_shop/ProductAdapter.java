package com.example.e_shop;

import android.content.Context;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ImageViewHolder> {
    private Context context;
    private List<Product> mProduct ;

    public ProductAdapter(Context context, List<Product> mProduct) {
        this.context = context;
        this.mProduct = mProduct;
    }

    @NonNull
    @Override
    public ProductAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_item,parent,false);
        return new ProductAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ImageViewHolder holder, int position) {
        final Product product = mProduct.get(position);
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


    }



    @Override
    public int getItemCount() {
        return mProduct.size();
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
