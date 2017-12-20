package com.example.mikhailefroimson.brewbuddy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Insert brewery data, parsed from csv file, into SQLite database
        // This will probably be done during install

        context = this;
        // Create DatabaseHelper instance
        BrewBuddyDatabaseHelper dataHelper = new BrewBuddyDatabaseHelper(context);

        InputStream inputStream = getResources().openRawResource(R.raw.breweries);
        Utils.CSVFile csvFile = new Utils.CSVFile(inputStream);
        List breweryList = csvFile.read();

        dataHelper.resetDatabase();

        for(Object breweryData:breweryList ) {
            String[] brewery_data_for_db = {"", "", "", "", "", "", "", ""};
            for(int i = 0; i < (Array.getLength(((String []) breweryData))-1); i++) {
                brewery_data_for_db[i] =  ((String []) breweryData)[i];
            }
            /*String name = ((String []) breweryData)[0];
            String address = ((String []) breweryData)[1] + " " + ((String []) breweryData)[2];
            String phone = ((String []) breweryData)[4];
            String type = ((String []) breweryData)[5];
            String website = ((String []) breweryData)[6];
            Log.d("DEBUG", "" + ((String[]) breweryData)[0] + ", " + ((String[]) breweryData)[1] +
                              "," + ((String[]) breweryData)[2] + "," + ((String[]) breweryData)[3] +
                                "," + ((String[]) breweryData)[4] + "," + ((String[]) breweryData)[5] +
                                "," + ((String[]) breweryData)[6]);
            //dataHelper.addBrewery(name, address, type, phone, website);*/
            String name = brewery_data_for_db[0];
            String address = brewery_data_for_db[1] + " " + brewery_data_for_db[2];
            String phone = brewery_data_for_db[4];
            String type = brewery_data_for_db[5];
            String website = brewery_data_for_db[6];
            Log.d("DEBUG", "name = " + name);
            Log.d("DEBUG", "type = " + type);
            Log.d("DEBUG", "address = " + address);
            Log.d("DEBUG", "phone = " + phone);
            Log.d("DEBUG", "website = " + website);
            if(address.contains("NJ"))
                dataHelper.addBrewery(name, address, type, phone, website);
        }

        // For Testing
        //dataHelper.resetDatabase();
    }

    public void mapButtonClickFunction(View v)
    {
        Intent intent = new Intent(this, BrewBuddyMapActivity.class);
        startActivity(intent);
    }

    public void breweryButtonClickFunction(View v)
    {
        Intent intent = new Intent(this, BreweryListAcitvity.class);
        startActivity(intent);
    }

    public void brewButtonClickFunction(View v) {
        Intent intent = new Intent(this, BrewListActivity.class);
        startActivity(intent);
    }

    public void addABrewButtonClickFunction(View v) {
        Intent intent = new Intent(this, AddABrewActivity.class);
        startActivity(intent);
    }
}
