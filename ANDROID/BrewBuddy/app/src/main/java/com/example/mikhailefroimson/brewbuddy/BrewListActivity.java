package com.example.mikhailefroimson.brewbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class BrewListActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_brews);
        context = this;
        // Create DatabaseHelper instance
        final BrewBuddyDatabaseHelper dataHelper = new BrewBuddyDatabaseHelper(context);
        // Reference to TableLayout
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.brewTableLayout);
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
                    final TableRow row = new TableRow(context);
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
                    row.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {
                            TableRow tr = (TableRow) v;
                            TextView tv_name = (TextView) tr.getChildAt(0);
                            String text_name = tv_name.getText().toString();
                            //dataHelper.deleteBrewByName(text_name);
                            return true;
                        }
                    });
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog alertDialog = new AlertDialog.Builder(BrewListActivity.this).create(); //Read Update
                            alertDialog.setTitle("Brew Info:");
                            TableRow tr = (TableRow) v;
                            TextView tv_name = (TextView) tr.getChildAt(0);
                            String text_name = tv_name.getText().toString();
                            TextView tv_type = (TextView) tr.getChildAt(1);
                            String text_type = tv_type.getText().toString();
                            TextView tv_abv = (TextView) tr.getChildAt(2);
                            String text_abv = tv_abv.getText().toString();
                            TextView tv_desc = (TextView) tr.getChildAt(3);
                            String text_desc = tv_desc.getText().toString();
                            TextView tv_brewery = (TextView) tr.getChildAt(4);
                            String text_brewery = tv_brewery.getText().toString();
                            String text = "Name: " + text_name + "\n" +
                                            "Brewery: " + text_brewery + "\n" +
                                            "Type: " + text_type + "\n" +
                                            "Description: " + text_desc + "\n" +
                                            "ABV: " + text_abv +"%";
                            alertDialog.setMessage(text);
                            alertDialog.setButton("Delete?", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();
                        }
                    });
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
