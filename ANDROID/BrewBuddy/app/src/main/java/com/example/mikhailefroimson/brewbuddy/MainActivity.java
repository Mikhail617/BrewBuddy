package com.example.mikhailefroimson.brewbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void addABrewButtonClickFunction(View v) {
        Intent intent = new Intent(this, AddABrewActivity.class);
        startActivity(intent);
    }
}
