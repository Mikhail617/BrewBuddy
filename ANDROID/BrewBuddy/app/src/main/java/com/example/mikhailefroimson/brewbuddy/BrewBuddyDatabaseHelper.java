package com.example.mikhailefroimson.brewbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by mikhail.efroimson on 12/18/2017.
 */

public class BrewBuddyDatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BrewBuddy.db";

    private static final String SQL_CREATE_BREWERIES =
            "CREATE TABLE " + BrewBuddyDatabaseContract.Breweries.TABLE_NAME_BREWERIES + " (" +
                    BrewBuddyDatabaseContract.Breweries._ID + " INTEGER PRIMARY KEY," +
                    BrewBuddyDatabaseContract.Breweries.COLUMN_NAME_NAME + " TEXT," +
                    BrewBuddyDatabaseContract.Breweries.COLUMN_NAME_TYPE + " TEXT)";

    private static final String SQL_CREATE_BREWS =
            "CREATE TABLE " + BrewBuddyDatabaseContract.Brews.TABLE_NAME_BREWS + " (" +
                    BrewBuddyDatabaseContract.Brews._ID + " INTEGER PRIMARY KEY," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_NAME_NAME + " TEXT," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_NAME_TYPE + " TEXT)";

    private static final String SQL_DELETE_BREWERIES =
            "DROP TABLE IF EXISTS " + BrewBuddyDatabaseContract.Breweries.TABLE_NAME_BREWERIES;

    private static final String SQL_DELETE_BREWS =
            "DROP TABLE IF EXISTS " + BrewBuddyDatabaseContract.Brews.TABLE_NAME_BREWS;

    public BrewBuddyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addBrew(String brew_name, String brew_type) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_NAME_NAME, brew_name);
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_NAME_TYPE, brew_type);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(BrewBuddyDatabaseContract.Brews.TABLE_NAME_BREWS, null, values);
    }

    public void addBrewery(String brewery_name,
                           String brewery_address,
                           String brewery_type) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_NAME_NAME, brewery_name);
        values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_NAME_ADDRESS, brewery_address);
        Values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_NAME_TYPE, brewery_type);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(BrewBuddyDatabaseContract.Breweries.TABLE_NAME_BREWERIES, null, values);
    }
}

