package com.whitewebteam.deliverus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class shopping_cart_activity extends AppCompatActivity implements CustomButtonListener
{
    static TextView total, deliveryFee, grandTotal;
    private List<CartItem> cartItems;
    static CartItemsAdapter cartItemAdapter;
    static int userId;
    static String orderId, time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        String supermarket = getSharedPreferences("supermarketsPref", Context.MODE_PRIVATE)
                .getString(SupermarketsActivity.supermarketPos, null);
        //seek better way to handle null supermarket after resuming
        if (supermarket == null) {
            supermarket = "";
        }

        Button payButton = (Button) findViewById(R.id.checkout);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (supermarket)
            {
                case "tuskys":
                    window.setStatusBarColor(getResources().getColor
                            (R.color.tuskys_background));
                    break;
                case "nakumatt":
                    window.setStatusBarColor(getResources().getColor
                            (R.color.nakumatt_background));
                    break;
                case "naivas":
                    window.setStatusBarColor(getResources().getColor
                            (R.color.naivas_background));
                    break;
                case "uchumi":
                    window.setStatusBarColor(getResources().getColor
                            (R.color.uchumi_background));
                    break;
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_to_right_back, R.anim.right_to_left_back);
            }
        });

        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_cart_title);
        title.setText("Cart");

        setSupportActionBar(toolbar);

        switch (supermarket)
        {
            case "tuskys":
                toolbar.setBackgroundColor(getResources().getColor(R.color.tuskys_background));
                payButton.setBackgroundColor(getResources().getColor(R.color.tuskys_background));
                break;
            case "nakumatt":
                toolbar.setBackgroundColor(getResources().getColor(R.color.nakumatt_background));
                payButton.setBackgroundColor(getResources().getColor(R.color.nakumatt_background));
                break;
            case "naivas":
                toolbar.setBackgroundColor(getResources().getColor(R.color.naivas_background));
                payButton.setBackgroundColor(getResources().getColor(R.color.naivas_background));
                break;
            case "uchumi":
                toolbar.setBackgroundColor(getResources().getColor(R.color.uchumi_background));
                payButton.setBackgroundColor(getResources().getColor(R.color.uchumi_background));
                break;
        }

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_back);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        cartItems = SupermarketHomeActivity.cartItems;

        total = (TextView) findViewById(R.id.total);
        deliveryFee = (TextView) findViewById(R.id.delivery_fee);
        grandTotal = (TextView) findViewById(R.id.grand_total);

        ListView cartItemsListView = (ListView) findViewById(R.id.cart_items);
        cartItemAdapter = new CartItemsAdapter(this, cartItems);
        cartItemsListView.setAdapter(cartItemAdapter);
        cartItemAdapter.setCustomButtonListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right_back, R.anim.right_to_left_back);
    }

    public void processOrder(View view)
    {
        if (cartItems.size() > 0)
        {
            SharedPreferences userDetailsPref = getSharedPreferences("userDetailsPref",
                    Context.MODE_PRIVATE);
            userId = userDetailsPref.getInt("id", 0);
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.ENGLISH);
            dateFormat.setLenient(false);
            Date date = new Date();
            String orderIdTime = dateFormat.format(date);

            orderId = userId + orderIdTime;
            time = DateFormat.getDateTimeInstance().format(date);

            startActivity(new Intent(shopping_cart_activity.this, MapsActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(this, "No items in cart", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onButtonClickListener(int position, TextView pcs, int value, CartItem cartItem,
                                      TextView subTotal)
    {
        String pcsValue = pcs.getText().toString();
        int newPcs = Integer.parseInt(pcsValue) + value;
        SupermarketHomeActivity.cartItemsNumber = SupermarketHomeActivity.cartItemsNumber + value;
        SupermarketHomeActivity.updateCartNumber();
        SubCategoriesActivity.updateCartNumber();
        if (newPcs < 1)
            newPcs = 1;
        pcs.setText(String.valueOf(newPcs));
        cartItem.setPcs(newPcs);

        int subTotalInt = cartItem.getPrice() * newPcs;
        String subTotalValue = "Ksh. " + subTotalInt;
        subTotal.setText(subTotalValue);
        cartItem.setSubTotal(subTotalInt);
    }

    static void refreshMap(Context context) {
        context.startActivity(new Intent(context, MapsActivity.class));
        ((Activity) context).finish();
    }
}