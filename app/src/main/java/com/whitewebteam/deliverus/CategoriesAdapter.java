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

class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;

    CategoriesAdapter(Context context, List<Category> categories)
    {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());
        Glide.with(context).load(category.getImage()).into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView categoryName;
        private ImageView categoryImage;

        ViewHolder(View view)
        {
            super(view);
            view.setOnClickListener(this);
            categoryName = (TextView) view.findViewById(R.id.category_name);
            categoryImage = (ImageView) view.findViewById(R.id.category_image);
        }

        @Override
        public void onClick(View v) {
            SupermarketHomeActivity.category = categoryName.getText().toString().toLowerCase();
            context.startActivity(new Intent(context, SubCategoriesActivity.class));
            ((Activity)context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    }
}
