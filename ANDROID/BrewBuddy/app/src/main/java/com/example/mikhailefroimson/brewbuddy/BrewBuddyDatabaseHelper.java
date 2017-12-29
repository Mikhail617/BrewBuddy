package com.example.mikhailefroimson.brewbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
            "CREATE TABLE " + BrewBuddyDatabaseContract.Breweries.TABLE_BREWERIES + " (" +
                    BrewBuddyDatabaseContract.Breweries._ID + " INTEGER PRIMARY KEY," +
                    BrewBuddyDatabaseContract.Breweries.COLUMN_NAME + " TEXT," +
                    BrewBuddyDatabaseContract.Breweries.COLUMN_TYPE + " TEXT," +
                    BrewBuddyDatabaseContract.Breweries.COLUMN_ADDRESS + " TEXT," +
                    BrewBuddyDatabaseContract.Breweries.COLUMN_PHONE + " TEXT," +
                    BrewBuddyDatabaseContract.Breweries.COLUMN_WEBSITE + " TEXT)";

    private static final String SQL_CREATE_BREWS =
            "CREATE TABLE " + BrewBuddyDatabaseContract.Brews.TABLE_BREWS + " (" +
                    BrewBuddyDatabaseContract.Brews._ID + " INTEGER PRIMARY KEY," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_NAME + " TEXT," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_TYPE + " TEXT," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_ABV + " TEXT," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_DESCRIPTION + " TEXT," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_BREWERY + " TEXT," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_PRICE + " TEXT," +
                    BrewBuddyDatabaseContract.Brews.COLUMN_AVAILABILITY + " TEXT)";

    private static final String SQL_DELETE_BREWERIES =
            "DROP TABLE IF EXISTS " + BrewBuddyDatabaseContract.Breweries.TABLE_BREWERIES;

    private static final String SQL_DELETE_BREWS =
            "DROP TABLE IF EXISTS " + BrewBuddyDatabaseContract.Brews.TABLE_BREWS;

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

    public void addBrew(String brew_name,
                        String brew_type,
                        String brew_abv,
                        String brew_description,
                        String brew_brewery,
                        String brew_price,
                        String brew_availability) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_NAME, brew_name);
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_TYPE, brew_type);
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_ABV, brew_abv);
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_DESCRIPTION, brew_description);
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_BREWERY, brew_brewery);
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_PRICE, brew_price);
        values.put(BrewBuddyDatabaseContract.Brews.COLUMN_AVAILABILITY, brew_availability);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(BrewBuddyDatabaseContract.Brews.TABLE_BREWS, null, values);
    }

    public void deleteBrewByName(String name) {
        // Gets the data repository in write mode
        String[] names = {name};
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + BrewBuddyDatabaseContract.Brews.TABLE_BREWS + " WHERE Name = '" + name +"'");
        //db.delete(BrewBuddyDatabaseContract.Brews.TABLE_BREWS, "WHERE Name = ", names);
    }

    public void addBrewery(String brewery_name,
                           String brewery_address,
                           String brewery_type,
                           String brewery_phone,
                           String brewery_website) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_NAME, brewery_name);
        values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_ADDRESS, brewery_address);
        values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_TYPE, brewery_type);
        values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_PHONE, brewery_phone);
        values.put(BrewBuddyDatabaseContract.Breweries.COLUMN_WEBSITE, brewery_website);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(BrewBuddyDatabaseContract.Breweries.TABLE_BREWERIES, null, values);
    }

    public ArrayList<String> getBrewsByType(String type) {
        ArrayList<String> results = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + BrewBuddyDatabaseContract.Brews.TABLE_BREWS +
                                " WHERE Type LIKE '%" + type + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String brew_name = cursor.getString(cursor.getColumnIndex("Name"));
                String brew_type = cursor.getString(cursor.getColumnIndex("Type"));
                if(brew_type.contains(type)) {
                    results.add(brew_name);
                }
            }
        }
        return results;
    }

    public ArrayList<String> getBrewsByBrewery(String brewery_name) {
        ArrayList<String> results = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + BrewBuddyDatabaseContract.Brews.TABLE_BREWS +
                " WHERE Brewery LIKE '%" + brewery_name + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // TODO: Should only return results within the geo-fence eventually
                String brew_name = cursor.getString(cursor.getColumnIndex("Name"));
                results.add(brew_name);
            }
        }
        return results;
    }

    public void resetDatabase() {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + BrewBuddyDatabaseContract.Brews.TABLE_BREWS);
        db.execSQL("DROP TABLE IF EXISTS " + BrewBuddyDatabaseContract.Breweries.TABLE_BREWERIES);
        this.onCreate(db);
    }

    // Database debug methods

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private void getColumnNames(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.query(table, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        Log.d("DATABSE DEBUG", Arrays.toString(columnNames));
    }

    private void getTableNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                arrTblNames.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }
        Log.d("DATABSE DEBUG", Arrays.toString(arrTblNames.toArray()));

    }

}

