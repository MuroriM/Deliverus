package com.whitewebteam.deliverus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends android.support.v4.app.Fragment
{

    static final String FOOD = "food";
    static final String DRINKS = "drinks";
    static final String HOME = "home";
    static final String SCHOOL_OFFICE = "school/office";
    static final String BATH_BODY = "bath/body";
    static final String BABY_KIDS = "baby/kids";
    static final String CLOTHING = "clothing";

    static List<Category> categories;
    static RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int[] categoryImages = {R.drawable.category_foods, R.drawable.category_drinks,
                R.drawable.category_home, R.drawable.category_stationery, R.drawable.category_bath_body,
                R.drawable.category_baby_kids, R.drawable.category_clothing};

        String[] categoryTitles = {FOOD, DRINKS, HOME, SCHOOL_OFFICE, BATH_BODY, BABY_KIDS, CLOTHING};

        categories = new ArrayList<>();
        for (int i = 0; i < categoryImages.length; i++)
        {
            Category category = new Category();
            category.setImage(categoryImages[i]);
            category.setName(categoryTitles[i]);
            categories.add(category);
        }
        recyclerView = (RecyclerView) inflater.inflate(R.layout.categories_fragment, container, false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new CategoriesAdapter(getActivity(), categories));
        return recyclerView;
    }
}
