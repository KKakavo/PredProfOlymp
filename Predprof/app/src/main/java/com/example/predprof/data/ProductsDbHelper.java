package com.example.predprof.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ProductsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    public ProductsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String REQUEST = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT NOT NULL);";
        db.execSQL(REQUEST);
    }

    public void addProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, productName);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateProduct(String originalProductName, String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, productName);
        db.update(TABLE_NAME, values, "name=?", new String[]{originalProductName});
        db.close();
    }

    public ArrayList<String> readProducts() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<String> productsArrayList = new ArrayList<>();
        if (cursorProducts.moveToFirst()) {
            do {

                productsArrayList.add(cursorProducts.getString(1));
            } while (cursorProducts.moveToNext());
        }

        cursorProducts.close();
        return productsArrayList;
    }
    public void deleteProduct(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "name=?", new String[]{name});
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
