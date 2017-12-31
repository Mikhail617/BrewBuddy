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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BrewListActivity extends AppCompatActivity {
    private Context context;
    private static TableRow current_row;
    private static View view;
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
        String[] headerText = {"ID", "NAME", "TYPE", "ABV", "DESC", "BREWERY", "PRICE", "AVAILABILITY"};
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
                    int brew_id = cursor.getInt(0);
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
                    String[] colText = {""+brew_id, brew_name, brew_type, brew_abv, brew_description, brew_brewery, brew_price, brew_availability};
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
                            current_row = (TableRow) v;

                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            LayoutInflater inflater = getLayoutInflater();
                            //inflate view for alertdialog since we are using multiple views inside a viewgroup (root = Layout top-level) (linear, relative, framelayout etc..)
                            view = inflater.inflate(R.layout.brew_list_alert_dialog, (ViewGroup) findViewById(R.id.brewTableLayout), false);

                            Button save_button = (Button) view.findViewById(R.id.Save);
                            Button delete_button = (Button) view.findViewById(R.id.Delete);

                            TextView tv_id = (TextView) current_row.getChildAt(0);
                            final String id = tv_id.getText().toString();

                            TextView tv_name = (TextView) current_row.getChildAt(1);
                            String text_name = tv_name.getText().toString();
                            TextView tv_type = (TextView) current_row.getChildAt(2);
                            String text_type = tv_type.getText().toString();
                            TextView tv_abv = (TextView) current_row.getChildAt(3);
                            String text_abv = tv_abv.getText().toString();
                            TextView tv_desc = (TextView) current_row.getChildAt(4);
                            String text_desc = tv_desc.getText().toString();
                            TextView tv_brewery = (TextView) current_row.getChildAt(5);
                            String text_brewery = tv_brewery.getText().toString();
                            TextView tv_price = (TextView) current_row.getChildAt(6);
                            String text_price = tv_price.getText().toString();
                            TextView tv_avail = (TextView) current_row.getChildAt(7);
                            String text_availability = tv_avail.getText().toString();

                            alert.setTitle(text_name);

                            TextView name_tv = (TextView) view.findViewById(R.id.EditTextName);
                            name_tv.setText(text_name);
                            TextView type_tv = (TextView) view.findViewById(R.id.EditTextType);
                            type_tv.setText(text_type);
                            TextView abv_tv = (TextView) view.findViewById(R.id.EditTextABV);
                            abv_tv.setText(text_abv);
                            TextView desc_tv = (TextView) view.findViewById(R.id.EditTextDescription);
                            desc_tv.setText(text_desc);
                            TextView brewery_tv = (TextView) view.findViewById(R.id.EditTextBrewery);
                            brewery_tv.setText(text_brewery);
                            TextView price_tv = (TextView) view.findViewById(R.id.EditTextPrice);
                            price_tv.setText(text_price);
                            TextView avail_tv = (TextView) view.findViewById(R.id.EditTextAvailability);
                            avail_tv.setText(text_availability);

                            delete_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TextView tv_id = (TextView) current_row.getChildAt(0);
                                    String text_id = tv_id.getText().toString();
                                    dataHelper.deleteBrewById(text_id);
                                    Toast.makeText(context,"Deleting...", Toast.LENGTH_LONG).show();
                                }
                            });

                            save_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TextView name_tv = (TextView) view.findViewById(R.id.EditTextName);
                                    String text_name = name_tv.getText().toString();
                                    TextView type_tv = (TextView) view.findViewById(R.id.EditTextType);
                                    String text_type = type_tv.getText().toString();
                                    TextView abv_tv = (TextView) view.findViewById(R.id.EditTextABV);
                                    String text_abv = abv_tv.getText().toString();
                                    TextView desc_tv = (TextView) view.findViewById(R.id.EditTextDescription);
                                    String text_desc = desc_tv.getText().toString();
                                    TextView brewery_tv = (TextView) view.findViewById(R.id.EditTextBrewery);
                                    String text_brewery = brewery_tv.getText().toString();
                                    TextView price_tv = (TextView) view.findViewById(R.id.EditTextPrice);
                                    String text_price = price_tv.getText().toString();
                                    TextView avail_tv = (TextView) view.findViewById(R.id.EditTextAvailability);
                                    String text_avail = avail_tv.getText().toString();
                                    dataHelper.updateBrewById(id,
                                            text_name,
                                            text_type,
                                            text_abv,
                                            text_desc,
                                            text_brewery,
                                            text_price,
                                            text_avail);
                                    Toast.makeText(context,"Updating...", Toast.LENGTH_LONG).show();
                                }
                            });

                            alert.setView(view);
                            alert.show();
                            return true;
                        }
                    });
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog alertDialog = new AlertDialog.Builder(BrewListActivity.this).create(); //Read Update
                            alertDialog.setTitle("Brew Info:");
                            TableRow tr = (TableRow) v;
                            TextView tv_name = (TextView) tr.getChildAt(1);
                            String text_name = tv_name.getText().toString();
                            TextView tv_type = (TextView) tr.getChildAt(2);
                            String text_type = tv_type.getText().toString();
                            TextView tv_abv = (TextView) tr.getChildAt(3);
                            String text_abv = tv_abv.getText().toString();
                            TextView tv_desc = (TextView) tr.getChildAt(4);
                            String text_desc = tv_desc.getText().toString();
                            TextView tv_brewery = (TextView) tr.getChildAt(5);
                            String text_brewery = tv_brewery.getText().toString();
                            String text = "Name: " + text_name + "\n" +
                                            "Brewery: " + text_brewery + "\n" +
                                            "Type: " + text_type + "\n" +
                                            "Description: " + text_desc + "\n" +
                                            "ABV: " + text_abv +"%";
                            alertDialog.setMessage(text);
                            alertDialog.setButton("Continue", new DialogInterface.OnClickListener() {
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
            tableLayout.setColumnCollapsed(0, true);
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
