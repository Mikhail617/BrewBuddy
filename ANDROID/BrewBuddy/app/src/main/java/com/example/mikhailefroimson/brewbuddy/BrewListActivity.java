package com.example.mikhailefroimson.brewbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BrewListActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_brews);
        context = this;
        // Create DatabaseHelper instance
        BrewBuddyDatabaseHelper dataHelper = new BrewBuddyDatabaseHelper(context);
        // Reference to TableLayout
        TableLayout tableLayout = (TableLayout) findViewById(R.id.brewTableLayout);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"NAME", "TYPE", "ABV", "DESC", "BREWERY", "PRICE", "AVAILABILITY"};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        try {
            String selectQuery = "SELECT * FROM " + BrewBuddyDatabaseContract.Brews.TABLE_BREWS;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    // Read columns data
                    //int outlet_id = cursor.getInt(cursor.getColumnIndex("_ID"));
                    String brew_name = cursor.getString(cursor.getColumnIndex("Name"));
                    String brew_type = cursor.getString(cursor.getColumnIndex("Type"));
                    String brew_abv = cursor.getString(cursor.getColumnIndex("ABV"));
                    String brew_description = cursor.getString(cursor.getColumnIndex("Description"));
                    String brew_brewery = cursor.getString(cursor.getColumnIndex("Brewery"));
                    String brew_price = cursor.getString(cursor.getColumnIndex("Price"));
                    String brew_availability = cursor.getString(cursor.getColumnIndex("Availability"));

                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {brew_name, brew_type, brew_abv, brew_description, brew_brewery, brew_price, brew_availability};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);
                }
            }
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
