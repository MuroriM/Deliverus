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

public class HistoryActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    static List<Order> orders;
    static String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_order_history);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_order_history_title);
        title.setText("Orders History");

        recyclerView = (RecyclerView) findViewById(R.id.history_recycler);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new ProcessHandler(this, "Loading history...", "getOrders", String.valueOf
                (getSharedPreferences("userDetailsPref", Context.MODE_PRIVATE).getInt("id", 0)));
    }
}
