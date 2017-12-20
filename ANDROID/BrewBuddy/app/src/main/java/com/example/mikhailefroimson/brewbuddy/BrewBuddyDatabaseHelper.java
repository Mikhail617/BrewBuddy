package com.example.mikhailefroimson.brewbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.File;

/**
 * Created by mikhail.efroimson on 12/18/2017.
 */

public class BrewBuddyDatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = "BrewBuddyDatabaseHelper";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "BrewBuddy.db";

    public BrewBuddyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        boolean doesExist = doesDatabaseExist(context, DATABASE_NAME);
        Log.d(TAG, "BrewBuddy.db exists = " + doesExist);
    }

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
        db.execSQL(SQL_CREATE_BREWERIES);
        db.execSQL(SQL_CREATE_BREWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CREATE_BREWERIES);
        db.execSQL(SQL_CREATE_BREWS);
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
        values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_NAME_TYPE, brewery_type);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(BrewBuddyDatabaseContract.Breweries.TABLE_NAME_BREWERIES, null, values);
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

}

