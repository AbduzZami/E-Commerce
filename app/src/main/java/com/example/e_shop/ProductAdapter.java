package com.example.e_shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ImageViewHolder> {
    private Context context;
    private List<Popular> mpopular ;

    public ProductAdapter(Context context, List<Popular> mpopular) {
        this.context = context;
        this.mpopular = mpopular;
    }

    @NonNull
    @Override
    public ProductAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_item,parent,false);
        return new ProductAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ImageViewHolder holder, int position) {
        Popular popular = mpopular.get(position);
        holder.product_name.setText(popular.getItem_name());
        holder.priduct_price.setText(popular.getItem_price());
        Picasso.get().load(popular.getItem_image()).fit().placeholder(R.drawable.ic_baseline_image_24).centerCrop().into(holder.product_image);

    }



    @Override
    public int getItemCount() {
        return mpopular.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private TextView product_name,priduct_price;
        private ImageView product_image ;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            priduct_price = itemView.findViewById(R.id.product_price);
        }
    }
}
