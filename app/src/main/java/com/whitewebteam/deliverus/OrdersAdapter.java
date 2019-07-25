package com.whitewebteam.deliverus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private Context context;
    private List<Order> orders;

    OrdersAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orders.get(position);
        String orderId = "#" + order.getId();
        holder.orderId.setText(orderId);
        holder.orderTime.setText(order.getTime());
        int grandTotal = order.getTotal() + order.getDeliveryFee();
        String sGrandTotal = "Ksh. " + grandTotal;
        holder.orderTotal.setText(sGrandTotal);
    }

    @Override
    public int getItemCount()
    {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderId, orderTime, orderTotal;

        ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            orderId = (TextView) view.findViewById(R.id.order_id);
            orderTime = (TextView) view.findViewById(R.id.order_time);
            orderTotal = (TextView) view.findViewById(R.id.order_total);
        }

        @Override
        public void onClick(View view) {
            HistoryActivity.orderId = orderId.getText().toString().substring(1);
            context.startActivity(new Intent(context, OrderDetailsActivity.class));
        }
    }
}
