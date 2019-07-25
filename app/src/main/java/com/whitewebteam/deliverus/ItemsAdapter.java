package com.whitewebteam.deliverus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>
{

    private Context context;
    private List<Item> items;


    ItemsAdapter(Context context, List<Item> items)
    {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        Item item = items.get(position);
        holder.id.setText(String.valueOf(item.getId()));
        holder.thumbnailURL.setText(item.getThumbnailURL());
        Glide
                .with(context)
                .load(item.getThumbnailURL())
                /*.placeholder(R.mipmap.logo_beta_2)
                .error(R.mipmap.logo_beta_2)*/
                .into(holder.thumbnail);
        holder.name.setText(item.getName());
        String price = "Ksh. " + item.getPrice();
        holder.price.setText(price);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView id, thumbnailURL, name, price;
        private ImageView thumbnail;

        ViewHolder(View view)
        {
            super(view);
            view.setOnClickListener(this);
            id = (TextView) view.findViewById(R.id.itemId);
            thumbnailURL = (TextView) view.findViewById(R.id.itemThumbnailURL);
            thumbnail = (ImageView) view.findViewById(R.id.itemThumbnail);
            name = (TextView) view.findViewById(R.id.itemName);
            price = (TextView) view.findViewById(R.id.itemPrice);
        }

        private int dId;
        private String dThumbnailURL, dName, dPrice;
        private TextView dialogPrice, dialogPcs;

        @Override
        public void onClick(final View view)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            dId = Integer.parseInt(id.getText().toString());
            dThumbnailURL = thumbnailURL.getText().toString();
            dName = name.getText().toString();
            dPrice = price.getText().toString();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            final ViewGroup nullRoot = null;
            View dialogView = inflater.inflate(R.layout.item_dialog, nullRoot);

            ImageView dialogThumbnail = (ImageView) dialogView.findViewById(R.id.dialog_thumbnail);
            Glide
                    .with(context)
                    .load(dThumbnailURL)
                    /*.placeholder(R.mipmap.logo_beta_2)
                    .error(R.mipmap.logo_beta_2)*/
                    .into(dialogThumbnail);

            TextView dialogName = (TextView) dialogView.findViewById(R.id.dialog_name);
            dialogName.setText(dName);

            dialogPrice = (TextView) dialogView.findViewById(R.id.dialog_price);
            dialogPrice.setText(dPrice);

            dialogPcs = (TextView) dialogView.findViewById(R.id.dialog_pcs);
            dialogPcs.setText(String.valueOf(1));

            LinearLayout linearRemove = (LinearLayout) dialogView.findViewById(
                    R.id.linear_dialog_remove);
            linearRemove.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    removePcs();
                }
            });

            ImageButton dialogRemove = (ImageButton) dialogView.findViewById(R.id.dialog_remove);
            dialogRemove.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    removePcs();
                }
            });

            LinearLayout linearAdd = (LinearLayout) dialogView.findViewById
                    (R.id.linear_dialog_add);
            linearAdd.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    addPcs();
                }
            });

            ImageButton dialogAdd = (ImageButton) dialogView.findViewById(R.id.dialog_add);
            dialogAdd.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    addPcs();
                }
            });

            builder.setView(dialogView);

            builder.setNegativeButton("Buy", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {addCartItem(view);
                }
            });

            builder.show();
        }

        private void removePcs()
        {
            String pcs = dialogPcs.getText().toString();
            if (!pcs.equals("1"))
            {
                int newPcs = Integer.parseInt(pcs) - 1;
                dialogPcs.setText(String.valueOf(newPcs));
                updatePrice(newPcs);
            }
        }

        private void addPcs()
        {
            String pcs = dialogPcs.getText().toString();
            if (!pcs.equals("100"))
            {
                int newPcs = Integer.parseInt(pcs) + 1;
                dialogPcs.setText(String.valueOf(newPcs));
                updatePrice(newPcs);
            }
        }

        private void updatePrice(int newPcs) {
            String newPrice = "Ksh. " + (Integer.parseInt(dPrice.substring(5)) * newPcs);
            dialogPrice.setText(newPrice);
        }

        private void addCartItem(View view)
        {
            CartItem cartItem = new CartItem();
            cartItem.setId(dId);
            cartItem.setName(dName);
            cartItem.setPrice(Integer.parseInt(dPrice.substring(5)));
            cartItem.setThumbnailURL(dThumbnailURL);
            cartItem.setPcs(Integer.parseInt(dialogPcs.getText().toString()));

            SupermarketHomeActivity.cartItemsNumber = SupermarketHomeActivity.cartItemsNumber
                    + Integer.parseInt(dialogPcs.getText().toString());
            SupermarketHomeActivity.updateCartNumber();
            if (SubCategoriesActivity.cartItemsView != null)
                SubCategoriesActivity.updateCartNumber();

            for (int i = 0; i < SupermarketHomeActivity.cartItems.size(); i++)
            {
                if (SupermarketHomeActivity.cartItems.get(i).getId() == cartItem.getId())
                {
                    int newPcs = SupermarketHomeActivity.cartItems.get(i).getPcs() + cartItem.getPcs();
                    //pcs up to 100
                    cartItem.setPcs(newPcs);
                    SupermarketHomeActivity.cartItems.remove(i);
                    break;
                }
            }
            SupermarketHomeActivity.cartItems.add(cartItem);
            Snackbar.make(view, "Item added to cart", Snackbar.LENGTH_SHORT).show();
        }
    }
}
