package com.whitewebteam.deliverus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {

    private Context context;
    private List<Category> subCategories;

    SubCategoriesAdapter(Context context, List<Category> subCategories)
    {
        this.context = context;
        this.subCategories = subCategories;
    }

    @Override
    public SubCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_card,
                parent, false);
        return new SubCategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubCategoriesAdapter.ViewHolder holder, int position)
    {
        Category subCategory = subCategories.get(position);
        holder.subCategoryName.setText(subCategory.getName());
        Glide.with(context).load(subCategory.getImage()).into(holder.subCategoryImage);
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView subCategoryName;
        private ImageView subCategoryImage;

        ViewHolder(View view)
        {
            super(view);
            view.setOnClickListener(this);
            subCategoryName = (TextView) view.findViewById(R.id.sub_category_name);
            subCategoryImage = (ImageView) view.findViewById(R.id.sub_category_image);
        }

        @Override
        public void onClick(View v) {
            SubCategoriesActivity.subCategory = subCategoryName.getText().toString().toLowerCase();
            context.startActivity(new Intent(context, ItemsActivity.class));
            ((Activity)context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    }
}

