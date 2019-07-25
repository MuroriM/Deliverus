package com.whitewebteam.deliverus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private ContentValues contentValues;

    DatabaseHelper(Context context) {
        super(context, "deliverus", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE sub_categories(name VARCHAR(50) PRIMARY KEY, category TEXT)");
        database.execSQL("CREATE TABLE order_details(order_id TEXT, item_name TEXT, " +
                "item_price INT, pcs INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS sub_categories");
        database.execSQL("DROP TABLE IF EXISTS order_details");
        onCreate(database);
    }

    void addSubCategory(String name, String category) {
        database = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("category", category);
        database.insertWithOnConflict("sub_categories", null, contentValues,
                SQLiteDatabase.CONFLICT_REPLACE);
        database.close();
    }

    void addOrderDetail(String orderId, String itemName, int itemPrice, int pcs) {
        database = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("order_id", orderId);
        contentValues.put("item_name", itemName);
        contentValues.put("item_price", itemPrice);
        contentValues.put("pcs", pcs);
        database.insert("order_details", null, contentValues);
        database.close();
    }

    Cursor getSubCategories(String category) {
        return this.getReadableDatabase().rawQuery("SELECT name FROM sub_categories WHERE category = '"
                + category + "'", null);
    }

    Cursor getOrderDetails(String orderId) {
        return this.getReadableDatabase().rawQuery("SELECT * FROM order_details WHERE order_id = '"
                + orderId + "'", null);
    }
}