package com.example.mikhailefroimson.brewbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
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

        // For Testing
        //dataHelper.resetDatabase();

        for(Object breweryData:breweryList ) {
            String[] brewery_data_for_db = {"", "", "", "", "", "", "", "", ""};
            for(int i = 0; i < (Array.getLength(((String []) breweryData))-1); i++) {
                brewery_data_for_db[i] =  ((String []) breweryData)[i];
            }
            //Log.d("DEBUG", "breweryData: " + Arrays.toString(((String []) breweryData)));
            if (Array.getLength((String []) breweryData) >= 7)
                //Log.d("DEBUG", ((String []) breweryData)[6]);
                brewery_data_for_db[6] = ((String []) breweryData)[6];
            String name = brewery_data_for_db[0].replace("\"", "");
            String address = brewery_data_for_db[1].replace("\"", "") + " " + brewery_data_for_db[2].replace("\"", "").replace("|", "");
            String phone = brewery_data_for_db[4].replace("Phone:", "");
            String type = brewery_data_for_db[5].replace("Type:", "");
            String website = brewery_data_for_db[6];
            //if(address.contains("NJ") || address.contains("NY") || address.contains("CT") || address.contains("MA"))
            //    dataHelper.addBrewery(name, address, type, phone, website);
            // big data test
            //dataHelper.addBrewery(name, address, type, phone, website);
        }
    }

    public void mapButtonClickFunction(View v) {
        Intent intent = new Intent(this, BrewBuddyMapActivity.class);
        startActivity(intent);
    }

    public void breweryButtonClickFunction(View v) {
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

    public void questionaireButtonClickFunction(View v) {
        Intent intent = new Intent(this, QuestionaireActivity.class);
        startActivity(intent);
    }

    public void favoritesButtonClickFunction(View v) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void sponsoredLinkssButtonClickFunction(View v) {
        Intent intent = new Intent(this, SponsoredLinksActivity.class);
        startActivity(intent);
    }

    public void aboutButtonClickFunction(View v) {
        Intent intent = new Intent(this, AboutScreenActivity.class);
        startActivity(intent);
    }

    public void exitButtonClickFunction(View v) {
        finish();
        System.exit(0);
    }
}
