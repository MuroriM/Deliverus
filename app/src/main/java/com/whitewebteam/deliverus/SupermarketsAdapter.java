package com.whitewebteam.deliverus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

class SupermarketsAdapter extends RecyclerView.Adapter<SupermarketsAdapter.ViewHolder>
{
    private Context context;
    private List<Integer> supermarketLogos;

    SupermarketsAdapter(Context context, List<Integer> supermarketLogos)
    {
        this.context = context;
        this.supermarketLogos = supermarketLogos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supermarket_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Glide.with(context).load(supermarketLogos.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount()
    {
        return supermarketLogos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private ImageView imageView;

        ViewHolder(View view)
        {
            super(view);
            view.setOnClickListener(this);
            imageView = (ImageView) view.findViewById(R.id.supermarketLogo);
        }

        @Override
        public void onClick(View view)
        {
            SupermarketsActivity.supermarketPos = String.valueOf(getAdapterPosition());
            context.startActivity(new Intent(context, SupermarketHomeActivity.class));
            ((Activity)context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    }
}
