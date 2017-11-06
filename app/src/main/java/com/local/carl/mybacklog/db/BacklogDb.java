package com.local.carl.mybacklog.db;

/**
 * Created by carlr on 10/22/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.local.carl.mybacklog.Category;
import com.local.carl.mybacklog.Item;

import java.util.ArrayList;
import java.util.List;

public class BacklogDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Backlog.db";
    public static final String ITEM_TABLE_NAME = "item";
    public static final String CATEGORY_NAME = "categoryname";
    public static final String ITEM_NAME = "itemname";
    public static final String ITEM_DESCRIPTION = "itemdescription";
    public static final String PRIORITY = "priority";
    public static final String DONE = "done";
    public static final String OWN = "own";
    public static final String RATING = "rating";
    //new column can be "y" or "n"


    public BacklogDb (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table item " +
                        "(itemname text primary key, categoryname text, itemdescription text, priority integer," +
                        " done integer, own integer, rating double)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // TODO Define onUpgrade
    }

    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.execSQL("DROP TABLE IF EXISTS item");
        db.execSQL(
                "create table item " +
                        "(itemname text primary key, categoryname text itemdescription text, priority integer," +
                        " done integer, own integer, rating double)"
        );
    }

    public List<Item> getAllFromCategory(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from item where categoryname = '"+name + "'", null);
        res.moveToFirst();
        List<Item> itemList = new ArrayList<Item>();
        if (res.getCount()==0) {
            return new ArrayList<>();
        }
        while(!res.isAfterLast()) {
            Item i = new Item();
            i.setName(res.getString(res.getColumnIndex(ITEM_NAME)));
            i.setDesc(res.getString(res.getColumnIndex(ITEM_DESCRIPTION)));
            i.setPriority(res.getInt(res.getColumnIndex(PRIORITY)));
            i.setOwn(intToBoolean(res.getInt(res.getColumnIndex(OWN))));
            i.setFinished(intToBoolean(res.getInt(res.getColumnIndex(DONE))));
            i.setRating(res.getDouble(res.getColumnIndex(RATING)));
            i.setCategoryName(res.getString(res.getColumnIndex(CATEGORY_NAME)));
            itemList.add(i);
            res.moveToNext();
        }
        res.close();
        return itemList;
    }

    public Category getAllAsCategory(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from item where categoryname = '"+name + "'", null);
        res.moveToFirst();
        List<Item> itemList = new ArrayList<Item>();
        if (res.getCount()==0) {
            res.close();
            return new Category(name);
        }
        while(!res.isAfterLast()) {
            Item i = new Item();
            i.setName(res.getString(res.getColumnIndex(ITEM_NAME)));
            i.setDesc(res.getString(res.getColumnIndex(ITEM_DESCRIPTION)));
            i.setPriority(res.getInt(res.getColumnIndex(PRIORITY)));
            i.setOwn(intToBoolean(res.getInt(res.getColumnIndex(OWN))));
            i.setFinished(intToBoolean(res.getInt(res.getColumnIndex(DONE))));
            i.setRating(res.getDouble(res.getColumnIndex(RATING)));
            i.setCategoryName(res.getString(res.getColumnIndex(CATEGORY_NAME)));
            itemList.add(i);
            res.moveToNext();
        }
        res.close();
        return new Category(name, itemList);
    }

    public List<String> getAllCategories(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select distinct categoryname from item", null);
        List <String> categoryList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()){
            String val = res.getString(res.getColumnIndex(CATEGORY_NAME));
            categoryList.add(val);
            res.moveToNext();
        }
        res.close();
        return categoryList;
    }

    public List<Item> getTopTenPriority(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from item order by priority desc", null);
        res.moveToFirst();
        List<Item> itemList = new ArrayList<Item>();
        if (res.getCount()==0) {
            res.close();
            return new ArrayList<>();
        }
        try {
            for (int i = 0; i < 10; i++) {
                Item it = new Item();
                it.setName(res.getString(res.getColumnIndex(ITEM_NAME)));
                it.setDesc(res.getString(res.getColumnIndex(ITEM_DESCRIPTION)));
                it.setPriority(res.getInt(res.getColumnIndex(PRIORITY)));
                it.setOwn(intToBoolean(res.getInt(res.getColumnIndex(OWN))));
                it.setFinished(intToBoolean(res.getInt(res.getColumnIndex(DONE))));
                it.setRating(res.getDouble(res.getColumnIndex(RATING)));
                it.setCategoryName(res.getString(res.getColumnIndex(CATEGORY_NAME)));
                itemList.add(it);
                res.moveToNext();
            }
        }catch (CursorIndexOutOfBoundsException oob){
            //Eat. Means we have less than 10 items total.
        }
        res.close();
        return itemList;
    }

    public int saveItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getName());
        values.put(CATEGORY_NAME, item.getCategoryName());
        values.put(ITEM_DESCRIPTION,item.getDesc());
        values.put(PRIORITY, item.getPriority());
        values.put(OWN, boolToInt(item.isOwn()));
        values.put(RATING, item.getRating());
        values.put(DONE, boolToInt(item.isFinished()));


        String selection = ITEM_NAME + "=?";
        String [] selectionArgs = {item.getName()};
        int updated = db.update(ITEM_TABLE_NAME, values, selection, selectionArgs);
        return updated;
    }

    public void insertItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getName());
        values.put(CATEGORY_NAME, item.getCategoryName());
        values.put(ITEM_DESCRIPTION,item.getDesc());
        values.put(PRIORITY, item.getPriority());
        values.put(OWN, boolToInt(item.isOwn()));
        values.put(RATING, item.getRating());
        values.put(DONE, boolToInt(item.isFinished()));
        db.insert(ITEM_TABLE_NAME, null, values);
    }


    private int boolToInt(boolean bool){
        return (bool)? 1: 0;
    }

    private boolean intToBoolean(int i){
        return (i==1);
    }
}
