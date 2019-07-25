package com.whitewebteam.deliverus;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.provider.BaseColumns;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.widget.CursorAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    static String supermarket, subCategory;
    static List<Item> items;
    static String[] suggestions;
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        supermarket = SupermarketHomeActivity.supermarket;
        subCategory = SubCategoriesActivity.subCategory;

        if (savedInstanceState != null) {
            supermarket = (String) savedInstanceState.getSerializable("supermarket");
            subCategory = (String) savedInstanceState.getSerializable("subCategory");
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_items);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        switch (supermarket)
        {
            case "tuskys":
                toolbar.setBackgroundColor(getResources().getColor(R.color.tuskys_background));
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor
                        (R.color.tuskys_background)));
                break;
            case "nakumatt":
                toolbar.setBackgroundColor(getResources().getColor(R.color.nakumatt_background));
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor
                        (R.color.nakumatt_background)));
                break;
            case "naivas":
                toolbar.setBackgroundColor(getResources().getColor(R.color.naivas_background));
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor
                        (R.color.naivas_background)));
                break;
            case "uchumi":
                toolbar.setBackgroundColor(getResources().getColor(R.color.uchumi_background));
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor
                        (R.color.uchumi_background)));
                break;
        }

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_back);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        recyclerView = (RecyclerView) findViewById(R.id.items_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        suggestions = new String[0];
        items = new ArrayList<>();
        new ProcessHandler(this, "Loading items...", "getSubCategoryItems", supermarket, subCategory);

        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("supermarket", supermarket);
        savedInstanceState.putSerializable("subCategory", subCategory);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        supermarket = (String) savedInstanceState.getSerializable("supermarket");
        subCategory = (String) savedInstanceState.getSerializable("subCategory");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setQueryHint("Search Subcategory...");
        searchView.setSuggestionsAdapter(cursorAdapter);
        searchView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                searchView.setIconified(false);
            }
        });
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
                new ProcessHandler(ItemsActivity.this, "Searching...",
                        "getSearchedItems", searchView.getQuery().toString().trim(), supermarket,
                        SubCategoriesActivity.subCategory);
                return true;
            }

            @Override public boolean onQueryTextChange(String text)
            {
                if (!text.equals(""))
                {
                    populateCursorAdapter(text);
                }
                else
                {
                    recyclerView.setAdapter(new ItemsAdapter(ItemsActivity.this, items));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void launchCart(View view)
    {
        startActivity(new Intent(this, shopping_cart_activity.class));
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right_back, R.anim.right_to_left_back);
    }
}
