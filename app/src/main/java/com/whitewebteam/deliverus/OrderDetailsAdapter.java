package com.whitewebteam.deliverus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> orders;

    OrderDetailsAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case Order.HEAD:
                View viewHead = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_detail_head, parent, false);
                viewHolder = new ViewHolderHead(viewHead);
                break;
            case Order.BODY:
                View viewBody = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_detail_body, parent, false);
                viewHolder = new ViewHolderBody(viewBody);
                break;
            case Order.TAIL:
                View viewTail = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_detail_tail, parent, false);
                viewHolder = new ViewHolderTail(viewTail);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Order order = orders.get(position);
        switch (holder.getItemViewType()) {
            case Order.HEAD:
                ViewHolderHead viewHolderHead = (ViewHolderHead) holder;
                viewHolderHead.id.setText("#" + order.getId());
                viewHolderHead.time.setText(order.getTime());
                viewHolderHead.user.setText(order.getUser());
                viewHolderHead.supermarket.setText(order.getSupermarket().toUpperCase());
                break;
            case Order.BODY:
                ViewHolderBody viewHolderBody = (ViewHolderBody) holder;
                viewHolderBody.name.setText(order.getItemName());
                String price = "Ksh. " + order.getItemPrice();
                viewHolderBody.price.setText(price);
                String pcs = order.getItemPcs() + " pcs";
                viewHolderBody.pcs.setText(pcs);
                String subtotal = "Ksh. " + (order.getItemPrice() * order.getItemPcs());
                viewHolderBody.subtotal.setText(subtotal);
                break;
            case Order.TAIL:
                ViewHolderTail viewHolderTail = (ViewHolderTail) holder;
                String total = "Ksh. " + order.getTotal();
                viewHolderTail.total.setText(total);
                String deliveryFee = "Ksh. " + order.getDeliveryFee();
                viewHolderTail.deliveryFee.setText(deliveryFee);
                viewHolderTail.deliverer.setText(order.getDeliverer());
                break;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        Order order = orders.get(position);
        return order.getType();
    }

    @Override
    public int getItemCount()
    {
        return orders.size();
    }

    private class ViewHolderHead extends RecyclerView.ViewHolder
    {

        TextView id, time, user, supermarket;

        ViewHolderHead(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.order_detail_id);
            time = (TextView) view.findViewById(R.id.order_detail_time);
            user = (TextView) view.findViewById(R.id.order_detail_user);
            supermarket = (TextView) view.findViewById(R.id.order_detail_supermarket);

        }
    }

    private class ViewHolderBody extends RecyclerView.ViewHolder
    {

        private TextView name, price, pcs, subtotal;

        ViewHolderBody(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.order_detail_name);
            price = (TextView) view.findViewById(R.id.order_detail_price);
            pcs = (TextView) view.findViewById(R.id.order_detail_pcs);
            subtotal = (TextView) view.findViewById(R.id.order_detail_subtotal);
        }
    }

    private class ViewHolderTail extends RecyclerView.ViewHolder
    {

        TextView total, deliveryFee, deliverer;

        ViewHolderTail(View view)
        {
            super(view);
            total = (TextView) view.findViewById(R.id.order_detail_total);
            deliveryFee = (TextView) view.findViewById(R.id.order_detail_delivery_fee);
            deliverer = (TextView) view.findViewById(R.id.order_detail_deliverer);
        }
    }
}

