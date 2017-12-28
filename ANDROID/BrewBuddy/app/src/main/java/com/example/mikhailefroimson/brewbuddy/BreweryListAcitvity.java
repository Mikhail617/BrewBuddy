package com.example.mikhailefroimson.brewbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mikhail.efroimson on 10/24/2017.
 */

public class BreweryListAcitvity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_list);
        context = this;
        // Create DatabaseHelper instance
        BrewBuddyDatabaseHelper dataHelper = new BrewBuddyDatabaseHelper(context);
        // Reference to TableLayout
        TableLayout tableLayout = (TableLayout) findViewById(R.id.breweryTableLayout);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"NAME", "TYPE", "ADDRESS", "PHONE", "WEBSITE"};
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
            String selectQuery = "SELECT * FROM " + BrewBuddyDatabaseContract.Breweries.TABLE_BREWERIES;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                cursor.moveToNext(); // skip the headers
                while (cursor.moveToNext()) {
                    // Read columns data
                    //int outlet_id = cursor.getInt(cursor.getColumnIndex("_ID"));
                    String brewery_name = cursor.getString(cursor.getColumnIndex("Name"));
                    String brewery_address = cursor.getString(cursor.getColumnIndex("Address"));
                    String brewery_phone = cursor.getString(cursor.getColumnIndex("Phone"));
                    String brewery_website = cursor.getString(cursor.getColumnIndex("Website"));
                    String brewery_type = cursor.getString(cursor.getColumnIndex("Type"));

                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    //String[] colText = {outlet_id + "", outlet_name, outlet_type};
                    String[] colText = {brewery_name, brewery_type, brewery_address, brewery_phone, brewery_website};
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
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final TextView message = new TextView(context);
                            AlertDialog alertDialog = new AlertDialog.Builder(BreweryListAcitvity.this).create(); //Read Update
                            alertDialog.setTitle("Brewery Info:");
                            TableRow tr = (TableRow) v;
                            TextView tv_name = (TextView) tr.getChildAt(0);
                            String text_name = tv_name.getText().toString();
                            TextView tv_type = (TextView) tr.getChildAt(1);
                            String text_type = tv_type.getText().toString();
                            TextView tv_address = (TextView) tr.getChildAt(2);
                            String text_address = tv_address.getText().toString();
                            TextView tv_phone = (TextView) tr.getChildAt(3);
                            String text_phone = tv_phone.getText().toString();
                            TextView tv_website = (TextView) tr.getChildAt(4);
                            String text_website = tv_website.getText().toString();
                            String msg = text_name + "\n" +
                                            //"Type: " + text_type + "\n" +
                                            text_address + "\n" +
                                            text_phone + "\n" +
                                            text_website + "";
                            final SpannableString s = new SpannableString(msg);
                            Linkify.addLinks(s, Linkify.WEB_URLS);
                            message.setText(s);
                            message.setMovementMethod(LinkMovementMethod.getInstance());
                            alertDialog.setView(message);
                            alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // bring up the menu activity
                                }
                            });
                            alertDialog.show();  //<-- See This!
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
            // End the transaction.
            db.close();
            // Close database
        }
    }
}
