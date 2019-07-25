package com.whitewebteam.deliverus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // toolbar = (Toolbar) findViewById(R.id.toolbar_main);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
       // TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_main);
       // mTitle.setText(R.string.app_name);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        initNavigationDrawer();
        //anim();

//        FallingItemsView fallingItemsView = (FallingItemsView) findViewById(R.id.vusik);
//        int[] myImageList = new int[] {R.drawable.falling_bread,R.drawable.falling_can,
//                R.drawable.falling_coke, R.drawable.falling_lotion,R.drawable.falling_lotion_b,
//                R.drawable.falling_salt,R.drawable.falling_can, R.drawable.falling_flour,
//                R.drawable.falling_vitamins, R.drawable.falling_doughnut,R.drawable.falling_flour,
//                R.drawable.falling_yogurt, R.drawable.falling_wheat, R.drawable.falling_cream};
//        fallingItemsView.setImages(myImageList).start();
//        fallingItemsView.startNotesFall();
    }

    public void launchSupermarkets(View view)
    {
        startActivity(new Intent(this, SupermarketsActivity.class));
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public void initNavigationDrawer()
    {
        final SharedPreferences userDetailsPref = getSharedPreferences("userDetailsPref",
                Context.MODE_PRIVATE);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                int id = menuItem.getItemId();
                switch (id)
                {
                    case R.id.edit_profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.history:
                        startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.help:
                        //code to be added
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        LayoutInflater inflater = (LayoutInflater) getSystemService
                                (Context.LAYOUT_INFLATER_SERVICE);
                        final ViewGroup nullRoot = null;
                        View view = inflater.inflate(R.layout.prompt_layout, nullRoot);

                        TextView prompt = (TextView) view.findViewById(R.id.prompt);
                        prompt.setText("Are you sure you want to log out?");

                        builder.setView(view);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userDetailsPref.edit().clear().apply();
                                getSharedPreferences("initPref", Context.MODE_PRIVATE).edit()
                                        .putBoolean("signed", false).apply();
                                startActivity(new Intent(MainActivity.this,
                                        AnimationSignInSignUpActivity.class));
                                finish();
                                Toast.makeText(MainActivity.this, "You have been logged out successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView personName = (TextView) header.findViewById(R.id.person_name);
        user = userDetailsPref.getString("fname", null) + " " + userDetailsPref.getString
                ("lname", null);
        personName.setText(user);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

//    public void anim()
//    {
//        ImageButton button = (ImageButton) findViewById(R.id.button);
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spin);
//        animation.setRepeatCount(-1);
//        button.startAnimation(animation);
//    }
}
