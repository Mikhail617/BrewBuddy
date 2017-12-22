package com.example.mikhailefroimson.brewbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mikhailefroimson.brewbuddy.BrewBuddyDatabaseHelper;

public class AddABrewActivity extends AppCompatActivity {

    EditText brewNameTextField;
    EditText brewTypeTextField;
    EditText brewABVTextField;
    EditText brewDescriptionTextField;
    EditText brewBreweryTextField;
    EditText brewPriceTextField;
    EditText brewAvailabilityTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_abrew);
        brewNameTextField = (EditText) findViewById(R.id.EditTextName);
        brewTypeTextField = (EditText) findViewById(R.id.EditTextType);
        brewABVTextField = (EditText) findViewById(R.id.EditTextABV);
        brewDescriptionTextField = (EditText) findViewById(R.id.EditTextDescription);
        brewBreweryTextField = (EditText) findViewById(R.id.EditTextBrewery);
        brewPriceTextField = (EditText) findViewById(R.id.EditTextPrice);
        brewAvailabilityTextField = (EditText) findViewById(R.id.EditTextAvailability);
    }

    public void addBrew(View button) {
        BrewBuddyDatabaseHelper bbdb = new BrewBuddyDatabaseHelper(button.getContext());
        String name = brewNameTextField.getText().toString();
        String type = brewTypeTextField.getText().toString();
        String abv = brewABVTextField.getText().toString();
        String desc = brewDescriptionTextField.getText().toString();
        String brewery = brewBreweryTextField.getText().toString();
        String price = brewPriceTextField.getText().toString();
        String availability = brewAvailabilityTextField.getText().toString();
        bbdb.addBrew(name, type, abv, desc, brewery, price, availability);
    }
}