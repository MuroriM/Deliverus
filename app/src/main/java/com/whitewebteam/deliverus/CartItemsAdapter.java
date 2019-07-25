package com.whitewebteam.deliverus;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class CartItemsAdapter extends BaseAdapter
{

    private Activity activity;
    private List<CartItem> cartItems;
    private CustomButtonListener customButtonListener;

    CartItemsAdapter(Activity activity, List<CartItem> cartItems)
    {
        this.activity = activity;
        this.cartItems = cartItems;
    }

    void setCustomButtonListener(CustomButtonListener customButtonListener)
    {
        this.customButtonListener = customButtonListener;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final CartItem cartItem = cartItems.get(position);
        View view;
        final ListViewHolder listViewHolder;
        final ViewGroup nullParent = null;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cart_item_layout, nullParent);
            listViewHolder = new ListViewHolder();
            listViewHolder.thumbnail = (ImageView) view.findViewById(R.id.cartItemThumbnail);
            listViewHolder.name = (TextView) view.findViewById(R.id.cartItemName);
            listViewHolder.price = (TextView) view.findViewById(R.id.cartItemPrice);
            listViewHolder.pcs = (TextView) view.findViewById(R.id.cartItemPcs);
            listViewHolder.subTotal = (TextView) view.findViewById(R.id.subTotal);
            listViewHolder.linearRemove = (LinearLayout) view.findViewById(R.id.linear_remove);
            listViewHolder.linearAdd = (LinearLayout) view.findViewById(R.id.linear_add);
            listViewHolder.remove = (ImageButton) view.findViewById(R.id.cartItemRemove);
            listViewHolder.add = (ImageView) view.findViewById(R.id.cartItemAdd);
            listViewHolder.delete = (ImageView) view.findViewById(R.id.cartItemDelete);
            view.setTag(listViewHolder);
        }
        else
        {
            view = convertView;
            listViewHolder = (ListViewHolder) view.getTag();
        }

        Glide
                .with(activity)
                .load(cartItem.getThumbnailURL())
                /*.placeholder(R.mipmap.logo_beta_2)
                .error(R.mipmap.logo_beta_2)*/
                .into(listViewHolder.thumbnail);
        listViewHolder.name.setText(cartItem.getName());
        String price = "Ksh. " + cartItem.getPrice();
        listViewHolder.price.setText(price);
        listViewHolder.pcs.setText(String.valueOf(cartItem.getPcs()));
        listViewHolder.subTotal.setText(getSubTotal(cartItem, listViewHolder.pcs));
        shopping_cart_activity.total.setText(activity.getString(R.string.cost, getTotal()));
        shopping_cart_activity.deliveryFee.setText(activity.getString(R.string.cost, getDeliveryFee
                (getTotal())));
        shopping_cart_activity.grandTotal.setText(activity.getString(R.string.cost, getGrandTotal()));

        listViewHolder.linearRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customButtonListener != null) {
                    customButtonListener.onButtonClickListener(position,
                            listViewHolder.pcs, -1, cartItem, listViewHolder.subTotal);
                    shopping_cart_activity.total.setText(activity.getString(R.string.cost,
                            getTotal()));
                    shopping_cart_activity.deliveryFee.setText(activity.getString(R.string.cost,
                            getDeliveryFee(getTotal())));
                    shopping_cart_activity.grandTotal.setText(activity.getString(R.string.cost,
                            getGrandTotal()));
                }
            }
        });

        listViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customButtonListener != null) {
                    customButtonListener.onButtonClickListener(position,
                            listViewHolder.pcs, -1, cartItem, listViewHolder.subTotal);
                    shopping_cart_activity.total.setText(activity.getString(R.string.cost,
                            getTotal()));
                    shopping_cart_activity.deliveryFee.setText(activity.getString(R.string.cost,
                            getDeliveryFee(getTotal())));
                    shopping_cart_activity.grandTotal.setText(activity.getString(R.string.cost,
                            getGrandTotal()));
                }
            }
        });

        listViewHolder.linearAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customButtonListener != null) {
                    customButtonListener.onButtonClickListener(position,
                            listViewHolder.pcs, 1, cartItem, listViewHolder.subTotal);
                    shopping_cart_activity.total.setText(activity.getString(R.string.cost,
                            getTotal()));
                    shopping_cart_activity.deliveryFee.setText(activity.getString(R.string.cost,
                            getDeliveryFee(getTotal())));
                    shopping_cart_activity.grandTotal.setText(activity.getString(R.string.cost,
                            getGrandTotal()));
                }
            }
        });

        listViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customButtonListener != null) {
                    customButtonListener.onButtonClickListener(position,
                            listViewHolder.pcs, 1, cartItem, listViewHolder.subTotal);
                    shopping_cart_activity.total.setText(activity.getString(R.string.cost,
                            getTotal()));
                    shopping_cart_activity.deliveryFee.setText(activity.getString(R.string.cost,
                            getDeliveryFee(getTotal())));
                    shopping_cart_activity.grandTotal.setText(activity.getString(R.string.cost,
                            getGrandTotal()));
                }

            }
        });

        listViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupermarketHomeActivity.cartItemsNumber = SupermarketHomeActivity.cartItemsNumber
                        - cartItem.getPcs();
                SupermarketHomeActivity.updateCartNumber();
                SubCategoriesActivity.updateCartNumber();
                cartItems.remove(position);
                shopping_cart_activity.cartItemAdapter.notifyDataSetChanged();
                shopping_cart_activity.total.setText(activity.getString(R.string.cost,
                        getTotal()));
                shopping_cart_activity.deliveryFee.setText(activity.getString(R.string.cost,
                        getDeliveryFee(getTotal())));
                shopping_cart_activity.grandTotal.setText(activity.getString(R.string.cost,
                        getGrandTotal()));
            }
        });

        return view;
    }

    private class ListViewHolder {
        private LinearLayout linearRemove, linearAdd;
        private ImageView thumbnail, remove, add, delete;
        private TextView name, price, subTotal, pcs;
    }

    private String getSubTotal(CartItem cartItem, TextView pcs)
    {
        int subTotal = cartItem.getPrice() * Integer.parseInt(pcs.getText().toString());
        cartItem.setSubTotal(subTotal);
        return "Ksh. " + subTotal;
    }

    private int getTotal()
    {
        int total = 0;
        for (int i = 0; i < cartItems.size(); i++)
        {
            total = total + cartItems.get(i).getSubTotal();
        }
        return total;
    }

    private int getDeliveryFee(int total) {
        double deliveryFee;
        if (total != 0) {
            if (total <= 750) {
                deliveryFee = 200;
            } else {
                deliveryFee = total / 500 * 50 + 200;
            }
        } else {
            deliveryFee = 0;
        }
        return (int) Math.ceil(deliveryFee);
    }

    private int getGrandTotal() {
        return getTotal() + getDeliveryFee(getTotal());
    }
}
