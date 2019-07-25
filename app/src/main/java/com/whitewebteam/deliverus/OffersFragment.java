package com.whitewebteam.deliverus;

import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class OffersFragment extends Fragment
{

    static RecyclerView recyclerView;
    static String supermarket;
    static List<Integer> itemIds;
    static List<Item> items;
    static String[] suggestions;
    private SimpleCursorAdapter cursorAdapter;
    static List<CartItem> cartItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.offers_fragment, container, false);

        /*recyclerView = (RecyclerView) view.findViewById(R.id.all_items_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        supermarket = getActivity().getSharedPreferences("supermarketsPref", Context.MODE_PRIVATE)
                .getString(SupermarketsActivity.supermarketPos, null);

        Cursor cursor = new DatabaseHelper(getActivity()).getSubCategoryItems(supermarket);
        if (cursor.getCount() > 0) {
            itemIds = new ArrayList<>();
            while (cursor.moveToNext()) {
                itemIds.add(cursor.getInt(0));
            }
            new ProcessHandler(getActivity(), "Loading items...", "getItemPrices");
        } else {
            new ProcessHandler(getActivity(), "Loading items...", "getSubCategoryItems", supermarket);
        }

        final String[] from = new String[] {"name"};
        final int[] to = new int[] {android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1,
                null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        cartItems = new ArrayList<>();

        final SearchView searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setQueryHint("Search Item");
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
                Cursor cursor = new DatabaseHelper(getActivity()).searchItem
                        (searchView.getQuery().toString().toLowerCase().trim(), supermarket);
                if (cursor.getCount() > 0)
                {
                    List<Integer> ids = new ArrayList<>();
                    while (cursor.moveToNext())
                    {
                        ids.add(cursor.getInt(0));
                    }
                    List<Item> newItems = new ArrayList<>();
                    for (int id : ids)
                    {
                        for (Item item : items)
                        {
                            if (id == item.getId())
                            {
                                newItems.add(item);
                            }
                        }
                    }
                    recyclerView.setAdapter(new ItemsAdapter(getActivity(), newItems));
                }
                else
                {
                    Toast.makeText(getActivity(), "No results found for '"
                                    + searchView.getQuery().toString().trim() + "'",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override public boolean onQueryTextChange(String newText)
            {
                if (!newText.equals(""))
                {
                    populateCursorAdapter(newText);
                }
                else
                {
                    recyclerView.setAdapter(new ItemsAdapter(getActivity(), items));
                }
                return false;
            }
        });*/

        return view;
    }

    private void populateCursorAdapter(String term)
    {
        final MatrixCursor cursor = new MatrixCursor(new String[]{ BaseColumns._ID, "name"});
        for (int i = 0; i < suggestions.length; i++)
        {
            if (suggestions[i].toLowerCase().contains(term.toLowerCase()))
                cursor.addRow(new Object[] {i, suggestions[i]});
        }
        cursorAdapter.changeCursor(cursor);
    }
}