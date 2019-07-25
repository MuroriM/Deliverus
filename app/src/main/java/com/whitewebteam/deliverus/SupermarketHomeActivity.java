package com.whitewebteam.deliverus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.BaseColumns;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SupermarketHomeActivity extends AppCompatActivity
{

    static String supermarket;
    int color;
    private SimpleCursorAdapter cursorAdapter;
    static String[] suggestions;
    private static TextView cartItemsView;
    static List<CartItem> cartItems;
    static int cartItemsNumber;
    static String category;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_back);
        ab.setDisplayHomeAsUpEnabled(true);

        supermarket = getSharedPreferences("supermarketsPref", Context.MODE_PRIVATE)
                .getString(SupermarketsActivity.supermarketPos, null);

        if (savedInstanceState != null) {
            supermarket = (String) savedInstanceState.getSerializable("supermarket");
        }

        assert supermarket != null;
        ab.setTitle(supermarket.toUpperCase());

        final Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.grocery_dark));

        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/quicksand_bold.ttf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(font);
        collapsingToolbarLayout.setExpandedTitleTypeface(font);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ImageView banner = (ImageView) findViewById(R.id.supermarket_banner);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        CoordinatorLayout mCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.main_content);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        switch (supermarket)
        {
            case "tuskys":
                color = R.color.tuskys_background;
                banner.setImageResource(R.drawable.supermarket_logo_tuskys);
                tabLayout.setBackgroundColor(getResources().getColor(color));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(color));
                collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.tuskys_text));
                mCoordinatorLayout.setBackgroundColor(getResources().getColor(color));
                viewPager.setBackgroundColor(getResources().getColor(R.color.tuskys_background_tint));
                break;
            case "nakumatt":
                color = R.color.nakumatt_background;
                banner.setImageResource(R.drawable.supermarket_logo_nakumatt);
                tabLayout.setBackgroundColor(getResources().getColor(color));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(color));
                mCoordinatorLayout.setBackgroundColor(getResources().getColor(color));
                viewPager.setBackgroundColor(getResources().getColor(R.color.nakumatt_background_tint));
                break;
            case "naivas":
                color = R.color.naivas_background;
                banner.setImageResource(R.drawable.supermarket_logo_naivas);
                tabLayout.setBackgroundColor(getResources().getColor(color));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(color));
                collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.naivas_text));
                mCoordinatorLayout.setBackgroundColor(getResources().getColor(color));
                viewPager.setBackgroundColor(getResources().getColor(R.color.naivas_background_tint));
                break;
            case "uchumi":
                color = R.color.uchumi_background;
                banner.setImageResource(R.drawable.supermarket_logo_uchumi);
                tabLayout.setBackgroundColor(getResources().getColor(color));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(color));
                mCoordinatorLayout.setBackgroundColor(getResources().getColor(color));
                viewPager.setBackgroundColor(getResources().getColor(R.color.uchumi_background_tint));
                break;
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    //Collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        switch (supermarket)
                        {
                            case "tuskys":
                                window.setStatusBarColor(getResources().getColor(color));
                                break;
                            case "nakumatt":
                                window.setStatusBarColor(getResources().getColor(color));
                                break;
                            case "naivas":
                                window.setStatusBarColor(getResources().getColor(color));
                                break;
                            case "uchumi":
                                window.setStatusBarColor(getResources().getColor(color));
                                break;
                        }
                    }
                }
                else
                {
                    //Expanded
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        window.setStatusBarColor(getResources().getColor(R.color.transparent));
                    }
                }
            }
        });

        suggestions = new String[0];
        new BackgroundWorker(this).execute("getItemNames", supermarket);

        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        cartItems = new ArrayList<>();
        cartItemsNumber = 0;

        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("supermarket", supermarket);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        supermarket = (String) savedInstanceState.getSerializable("supermarket");
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setQueryHint("Search Supermarket...");
        searchView.setSuggestionsAdapter(cursorAdapter);
        searchView.setIconified(true);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position)
            {
                Cursor cursor = searchView.getSuggestionsAdapter().getCursor();
                cursor.moveToPosition(position);
                int index = Integer.parseInt(cursor.getString(0));
                String suggestion = suggestions[index];
                searchView.setQuery(suggestion, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position)
            {
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchView.clearFocus();
                new ProcessHandler(SupermarketHomeActivity.this, "Searching...",
                        "getSupermarketItems", searchView.getQuery().toString().trim(), supermarket);
                return true;
            }

            @Override public boolean onQueryTextChange(String text)
            {
                if (!text.equals(""))
                {
                    populateCursorAdapter(text);
                } else
                {
                    CategoriesFragment.recyclerView.setAdapter(new CategoriesAdapter
                            (SupermarketHomeActivity.this, CategoriesFragment.categories));
                }
                return false;
            }
        });
        return true;
    }

    private void populateCursorAdapter(String term) {
        MatrixCursor cursor = new MatrixCursor(new String[]{ BaseColumns._ID, "name"});
        for (int i = 0; i < suggestions.length; i++) {
            if (suggestions[i].toLowerCase().contains(term.toLowerCase()))
                cursor.addRow(new Object[] {i, suggestions[i]});
        }
        cursorAdapter.changeCursor(cursor);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        final MenuItem toolbarCart = menu.findItem(R.id.item_cart_number);
        RelativeLayout toolbarCartLayout = (RelativeLayout) toolbarCart.getActionView();

        cartItemsView = (TextView) toolbarCartLayout.findViewById(R.id.badge_textView);
        Button toolbarCartButton =(Button) toolbarCartLayout.findViewById(R.id.badge_icon_button);

        toolbarCartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SupermarketHomeActivity.this, shopping_cart_activity.class));
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });

        updateCartNumber();

        return super.onPrepareOptionsMenu(menu);
    }

    static void updateCartNumber()
    {
        if (cartItemsNumber == 0)
        {
            cartItemsView.setVisibility(View.INVISIBLE );
        }
        else
        {
            cartItemsView.setVisibility(View.VISIBLE);
            cartItemsView.setText(String.valueOf(cartItemsNumber));
            // supportInvalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_cart_number:
                //invalidateOptionsMenu();
                startActivity(new Intent(SupermarketHomeActivity.this, shopping_cart_activity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed()
    {
        if (cartItemsNumber != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ViewGroup nullRoot = null;
            View view = inflater.inflate(R.layout.prompt_layout, nullRoot);

            TextView prompt = (TextView) view.findViewById(R.id.prompt);
            prompt.setText("Are you sure you want to leave the supermarket? " +
                    "Leaving will clear your cart.");

            builder.setView(view);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    SupermarketHomeActivity.super.onBackPressed();
                    overridePendingTransition(R.anim.left_to_right_back, R.anim.right_to_left_back);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            SupermarketHomeActivity.super.onBackPressed();
            overridePendingTransition(R.anim.left_to_right_back, R.anim.right_to_left_back);
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new CategoriesFragment(), "CATEGORIES");
        fragmentAdapter.addFragment(new OffersFragment(), "OFFERS");
        viewPager.setAdapter(fragmentAdapter);
    }
}




