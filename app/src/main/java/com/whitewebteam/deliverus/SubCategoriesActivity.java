package com.whitewebteam.deliverus;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesActivity extends AppCompatActivity
{
    private String supermarket, category;
    private SimpleCursorAdapter cursorAdapter;
    private String[] suggestions;
    static RecyclerView recyclerView;
    private List<Category> subCategories;
    static TextView cartItemsView;
    static String subCategory;

    private final String SUPERMARKET = "supermarket";
    private final String CATEGORY = "category";
    private final String SUGGESTIONS = "suggestions";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);

        supermarket = SupermarketHomeActivity.supermarket;
        category = SupermarketHomeActivity.category;
        suggestions = SupermarketHomeActivity.suggestions;

        if (savedInstanceState != null) {
            supermarket = (String) savedInstanceState.getSerializable(SUPERMARKET);
            category = (String) savedInstanceState.getSerializable(CATEGORY);
            suggestions = (String[]) savedInstanceState.getSerializable(SUGGESTIONS);
        }

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (supermarket)
            {
                case "tuskys":
                    window.setStatusBarColor(getResources().getColor(R.color.tuskys_background));
                    break;
                case "nakumatt":
                    window.setStatusBarColor(getResources().getColor(R.color.nakumatt_background));
                    break;
                case "naivas":
                    window.setStatusBarColor(getResources().getColor(R.color.naivas_background));
                    break;
                case "uchumi":
                    window.setStatusBarColor(getResources().getColor(R.color.uchumi_background));
                    break;
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sub_categories);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.sub_categories_recycler);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(SubCategoriesActivity.this, 3));

        switch (supermarket)
        {
            case "tuskys":
                toolbar.setBackgroundColor(getResources().getColor(R.color.tuskys_background));
                recyclerView.setBackgroundColor(getResources().getColor(R.color.tuskys_background_tint));
                break;
            case "nakumatt":
                toolbar.setBackgroundColor(getResources().getColor(R.color.nakumatt_background));
                recyclerView.setBackgroundColor(getResources().getColor(R.color.nakumatt_background_tint));
                break;
            case "naivas":
                toolbar.setBackgroundColor(getResources().getColor(R.color.naivas_background));
                recyclerView.setBackgroundColor(getResources().getColor(R.color.naivas_background_tint));
                break;
            case "uchumi":
                toolbar.setBackgroundColor(getResources().getColor(R.color.uchumi_background));
                recyclerView.setBackgroundColor(getResources().getColor(R.color.uchumi_background_tint));
                break;
        }

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_back);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        TextView title = (TextView) findViewById(R.id.sub_toolbar_title);
        title.setText("Shop away...");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.getSubCategories(SupermarketHomeActivity.category).getCount() == 0)
        {
            new SubCategories().addSubCategories(this);
        }
        Cursor cursor = databaseHelper.getSubCategories(category);
        subCategories = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Category subCategory = new Category();
                subCategory.setName(cursor.getString(0));
                subCategory.setImage(getImage(cursor.getString(0)));
                subCategories.add(subCategory);
            }
        }
        cursor.close();

        recyclerView.setAdapter(new SubCategoriesAdapter(this, subCategories));

        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(SUPERMARKET, supermarket);
        savedInstanceState.putSerializable(CATEGORY, category);
        savedInstanceState.putSerializable(SUGGESTIONS, suggestions);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        supermarket = (String) savedInstanceState.getSerializable(SUPERMARKET);
        category = (String) savedInstanceState.getSerializable(CATEGORY);
        suggestions = (String[]) savedInstanceState.getSerializable(SUGGESTIONS);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right_back, R.anim.right_to_left_back);
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    private int getImage(String name)
    {
        int image = 0;
        switch(name)
        {
            case SubCategories.DAIRIES:
                image = R.drawable.subcategories_dairies;
                break;
            case SubCategories.BAKERY:
                image = R.drawable.subcategories_bakery;
                break;
            case SubCategories.COOKING_INGREDIENTS:
                image = R.drawable.subcategories_cooking_ingredients;
                break;
            case SubCategories.DEODORANTS:
                image = R.drawable.subcategories_deodorant;
                break;
            case SubCategories.SOAPS:
                image = R.drawable.subcategories_soaps;
                break;
            case SubCategories.BEVERAGES:
                image = R.drawable.subcategories_beverages;
                break;
            case SubCategories.DENTAL:
                image =R.drawable.subcategory_toothpastes;
                break;
            case SubCategories.CONDIMENTS:
                image = R.drawable.subcategories_condiments;
                break;
            case SubCategories.JUICES:
                image = R.drawable.subcategories_juices;
                break;
            case SubCategories.CANNED_FOODS:
                image = R.drawable.subcategories_canned_foods;
                break;
            case SubCategories.DIAPERS:
                image = R.drawable.subcategories_diapers;
                break;
            case SubCategories.PERFUMES:
                image = R.drawable.subcategories_perfumes;
                break;
            case SubCategories.CUTLERY:
                image = R.drawable.subcategories_cutlery;
                break;
            case SubCategories.CROCKERY:
                image = R.drawable.subcategories_crockery;
                break;
            case SubCategories.BEDDINGS:
                image = R.drawable.subcategories_beddings;
                break;
            case SubCategories.STATIONERY:
                image = R.drawable.subcategories_stationery;
                break;
            case SubCategories.SOCKS:
                image = R.drawable.subcategories_socks;
                break;
            case SubCategories.CANDY:
                image = R.drawable.subcategories_candy;
                break;
            case SubCategories.SOFT_DRINKS:
                image = R.drawable.subcategories_soft_drinks;
                break;
            case SubCategories.SHOWER_GELS:
                image = R.drawable.subcategories_shower_gels;
                break;
            case SubCategories.DETERGENTS:
                image = R.drawable.subcategories_detergents;
                break;
            case SubCategories.FARM_IMPLEMENT:
                image = R.drawable.subcategories_farm_implements;
                break;
            case SubCategories.SHAVING:
                image = R.drawable.subcategories_shaving;
                break;
            case SubCategories.TISSUES_ROLLS_NAPKINS:
                image = R.drawable.subcategories_tissue_rolls_napkins;
                break;
            case SubCategories.AEROSOLS:
                image = R.drawable.subcategories_aerosol_cans;
                break;
            case SubCategories.TOWELS:
                image = R.drawable.subcategories_towels;
                break;
            case SubCategories.CLEANING_SUPPLIES:
                image = R.drawable.subcategories_cleaning_supplies;
                break;
            case SubCategories.WATER:
                image = R.drawable.subcategories_water;
                break;
            case SubCategories.CEREALS:
                image = R.drawable.subcategories_cereals;
                break;
            case SubCategories.PASTA:
                image = R.drawable.subcategories_pasta;
                break;
            case SubCategories.SANITARY_TOWELS:
                image = R.drawable.subcategories_sanitary_towels;
                break;
            case SubCategories.BATH_BODY:
                image = R.drawable.subcategories_bath_body_kids;
                break;
            case SubCategories.LOTIONS:
                image = R.drawable.subcategories_lotions;
                break;
            case SubCategories.SNACKS:
                image = R.drawable.subcategories_snacks;
                break;
            case SubCategories.ANTISEPTICS:
                image = R.drawable.subcategories_antiseptics;
                break;
        }
        return image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        final SearchView searchView = (SearchView)menu.findItem(R.id.item_search).getActionView();
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
                new ProcessHandler(SubCategoriesActivity.this, "Searching...",
                        "getCategoryItems", searchView.getQuery().toString().trim(), supermarket);
                return true;
            }

            @Override public boolean onQueryTextChange(String text)
            {
                if (!text.equals(""))
                {
                    populateCursorAdapter(text);
                } else
                {
                    recyclerView.setAdapter(new SubCategoriesAdapter(SubCategoriesActivity.this,
                            subCategories));
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
                startActivity(new Intent(SubCategoriesActivity.this, shopping_cart_activity.class));
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });

        updateCartNumber();

        return super.onPrepareOptionsMenu(menu);
    }

    static void updateCartNumber()
    {
        if (SupermarketHomeActivity.cartItemsNumber == 0)
        {
            cartItemsView.setVisibility(View.INVISIBLE);
        }
        else
        {
            cartItemsView.setVisibility(View.VISIBLE);
            cartItemsView.setText(String.valueOf(SupermarketHomeActivity.cartItemsNumber));
            // supportInvalidateOptionsMenu();
        }
    }
}


