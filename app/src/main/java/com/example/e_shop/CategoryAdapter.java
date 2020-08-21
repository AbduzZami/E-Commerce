package com.example.e_shop;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ImageViewHolder> {
    Context context;
    List<Category> mCategory ;

    public CategoryAdapter(Context context, List<Category> mCategory) {
        this.context = context;
        this.mCategory = mCategory;
    }


    @NonNull
    @Override
    public CategoryAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new CategoryAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ImageViewHolder holder, int position) {
        Category category = mCategory.get(position);
        holder.cat_name.setText(category.getCat_name());
        //holder.cat_image.setBackgroundColor(Color.parseColor(category.getCat_bg()));
        Picasso.get().load(category.getCat_icon_url()).placeholder(R.drawable.ic_baseline_image_24).fit().centerCrop().into(holder.cat_image);
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private TextView cat_name;
        private ImageView cat_image ;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_image = itemView.findViewById(R.id.catimage);
            cat_name = itemView.findViewById(R.id.catname);
        }
    }
}
