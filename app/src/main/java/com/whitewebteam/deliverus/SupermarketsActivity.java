package com.whitewebteam.deliverus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class SupermarketsActivity extends AppCompatActivity
{

    static String supermarketPos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkets);

        Toolbar toolbar = (Toolbar) findViewById(R.id.supermarkets_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_back);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        List<Integer> supermarketLogos = new ArrayList<>();
        supermarketLogos.add(R.drawable.supermarket_logo_tuskys);
        supermarketLogos.add(R.drawable.supermarket_logo_nakumatt);
        supermarketLogos.add(R.drawable.supermarket_logo_naivas);
        supermarketLogos.add(R.drawable.supermarket_logo_uchumi);

        SharedPreferences.Editor editor = getSharedPreferences("supermarketsPref",
                Context.MODE_PRIVATE).edit();
        editor.putString("0", "tuskys");
        editor.putString("1", "nakumatt");
        editor.putString("2", "naivas");
        editor.putString("3", "uchumi");
        editor.apply();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.supermarkets_recycler);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SupermarketsAdapter(this, supermarketLogos));

        //new BackgroundWorker(this).execute("getSupermarkets");
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right_back, R.anim.right_to_left_back);
    }
}
