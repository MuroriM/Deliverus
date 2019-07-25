package com.whitewebteam.deliverus;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    static List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_order_details);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_order_details_title);
        title.setText("Order Details");

        /*FadingImageView fadingImageView = (FadingImageView) findViewById(R.id.fade_image_view);
        fadingImageView.setEdgeLength(28);
        fadingImageView.setFadeDirection(FadingImageView.FadeSide.TOP_SIDE);*/

        recyclerView = (RecyclerView) findViewById(R.id.order_details_recycler);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrderDetails(this);
    }

    static void loadOrderDetails(Context context) {
        orders = new ArrayList<>();
        for (Order order : HistoryActivity.orders) {
            if (order.getId().equals(HistoryActivity.orderId)) {
                orders.add(new Order(Order.HEAD, order.getId(), order.getTime(), order.getUser(),
                        order.getSupermarket(), 0, 0, null, null, 0, 0));
                orders.add(new Order(Order.TAIL, null, null, null, null, order.getTotal(),
                        order.getDeliveryFee(), order.getDeliverer(), null, 0, 0));
                break;
            }
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Cursor cursor = databaseHelper.getOrderDetails(HistoryActivity.orderId);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                orders.add(new Order(Order.BODY, null, null, null, null, 0, 0, null,
                        cursor.getString(1), cursor.getInt(2),cursor.getInt(3)));
            }
            recyclerView.setAdapter(new OrderDetailsAdapter(orders));
        } else {
            new ProcessHandler(context, "Loading details...", "getOrderDetails",
                    HistoryActivity.orderId);
        }
        cursor.close();
    }
}
