package com.whitewebteam.deliverus;

import android.content.Context;

class SubCategories {

    static final String DAIRIES = "dairies";
    static final String BAKERY = "bakery";
    static final String COOKING_INGREDIENTS = "cooking ingredients";
    static final String DEODORANTS = "deodorants";
    static final String SOAPS = "soaps";
    static final String BEVERAGES = "beverages";
    static final String DENTAL = "dental";
    static final String CONDIMENTS = "condiments";
    static final String JUICES = "juices";
    static final String CANNED_FOODS = "canned foods";
    static final String DIAPERS = "diapers";
    static final String PERFUMES= "perfumes";
    static final String CUTLERY = "cutlery";
    static final String CROCKERY = "crockery";
    static final String BEDDINGS = "beddings";
    static final String STATIONERY = "stationery";
    static final String SOCKS = "socks";
    static final String CANDY = "candy";
    static final String SOFT_DRINKS = "soft drinks";
    static final String SHOWER_GELS = "shower gels";
    static final String DETERGENTS = "detergents";
    static final String FARM_IMPLEMENT = "farm implement";
    static final String SHAVING = "shaving";
    static final String TISSUES_ROLLS_NAPKINS = "tissue rolls/napkins";
    static final String AEROSOLS = "aerosols";
    static final String TOWELS = "towels";
    static final String CLEANING_SUPPLIES = "cleaning supplies";
    static final String WATER = "water";
    static final String CEREALS = "cereals";
    static final String PASTA = "pasta";
    static final String SNACKS = "snacks";
    static final String LOTIONS = "lotions";
    static final String BATH_BODY = "bath/body";
    static final String ANTISEPTICS = "antiseptics";
    static final String SANITARY_TOWELS = "sanitary towels";

    void addSubCategories(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.addSubCategory(DAIRIES, CategoriesFragment.DRINKS);
        databaseHelper.addSubCategory(BAKERY, CategoriesFragment.FOOD);
        databaseHelper.addSubCategory(COOKING_INGREDIENTS, CategoriesFragment.FOOD);
        databaseHelper.addSubCategory(DEODORANTS, CategoriesFragment.BATH_BODY);
        databaseHelper.addSubCategory(SOAPS, CategoriesFragment.BATH_BODY);
        databaseHelper.addSubCategory(BEVERAGES, CategoriesFragment.DRINKS);
        databaseHelper.addSubCategory(DENTAL, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(CONDIMENTS, CategoriesFragment.FOOD);
        databaseHelper.addSubCategory(JUICES, CategoriesFragment.DRINKS);
        databaseHelper.addSubCategory(CANNED_FOODS, CategoriesFragment.FOOD);
        databaseHelper.addSubCategory(DIAPERS, CategoriesFragment.BABY_KIDS);
        databaseHelper.addSubCategory(PERFUMES, CategoriesFragment.BATH_BODY);
        databaseHelper.addSubCategory(CUTLERY, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(CROCKERY, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(BEDDINGS, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(STATIONERY, CategoriesFragment.SCHOOL_OFFICE);
        databaseHelper.addSubCategory(SOCKS, CategoriesFragment.CLOTHING);
        databaseHelper.addSubCategory(CANDY, CategoriesFragment.FOOD);
        databaseHelper.addSubCategory(SOFT_DRINKS, CategoriesFragment.DRINKS);
        databaseHelper.addSubCategory(SHOWER_GELS, CategoriesFragment.BATH_BODY);
        databaseHelper.addSubCategory(DETERGENTS, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(FARM_IMPLEMENT, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(SHAVING, CategoriesFragment.BATH_BODY);
        databaseHelper.addSubCategory(TISSUES_ROLLS_NAPKINS, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(CLEANING_SUPPLIES, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(AEROSOLS, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(TOWELS, CategoriesFragment.CLOTHING);
        databaseHelper.addSubCategory(WATER, CategoriesFragment.DRINKS);
        databaseHelper.addSubCategory(CEREALS, CategoriesFragment.FOOD);
        databaseHelper.addSubCategory(PASTA, CategoriesFragment.FOOD);
        databaseHelper.addSubCategory(ANTISEPTICS, CategoriesFragment.HOME);
        databaseHelper.addSubCategory(LOTIONS, CategoriesFragment.BATH_BODY);
        databaseHelper.addSubCategory(BATH_BODY, CategoriesFragment.BABY_KIDS);
        databaseHelper.addSubCategory(SANITARY_TOWELS, CategoriesFragment.BATH_BODY);
        databaseHelper.addSubCategory(SNACKS, CategoriesFragment.FOOD);
    }
}
