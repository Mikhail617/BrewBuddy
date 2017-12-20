package com.example.mikhailefroimson.brewbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mikhailefroimson.brewbuddy.BrewBuddyDatabaseHelper;

public class AddABrewActivity extends AppCompatActivity {

    EditText brewNameTextField;
    EditText brewTypeTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_abrew);

        brewNameTextField = (EditText) findViewById(R.id.EditTextName);
        brewTypeTextField = (EditText) findViewById(R.id.EditTextType);
    }

    public void addBrew(View button) {
        BrewBuddyDatabaseHelper bbdb = new BrewBuddyDatabaseHelper(button.getContext());
        String name = brewNameTextField.getText().toString();
        String type = brewTypeTextField.getText().toString();
        bbdb.addBrew(name, type);
    }
}
