package com.example.mikhailefroimson.brewbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionaireActivity extends AppCompatActivity {

    Context context;

    private RadioGroup radioGroupQ1;
    private RadioGroup radioGroupQ2;
    private RadioGroup radioGroupQ3;
    private RadioGroup radioGroupQ4;
    private RadioGroup radioGroupQ5;
    private RadioGroup radioGroupQ6;

    int selectedQ1Id;
    int selectedQ2Id;
    int selectedQ3Id;
    int selectedQ4Id;
    int selectedQ5Id;
    int selectedQ6Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_questionaire1);
    }

    // Are you looking to get drunk?
    public void nextButtonClickFunction(View v) {
        radioGroupQ1 = (RadioGroup) findViewById(R.id.radioAnswers1);
        selectedQ1Id = radioGroupQ1.getCheckedRadioButtonId();
        setContentView(R.layout.activity_questionaire2);
    }

    // Lighter or darker beers?
    public void nextButtonClickFunction2(View v) {
        radioGroupQ2 = (RadioGroup) findViewById(R.id.radioAnswers2);
        selectedQ2Id = radioGroupQ2.getCheckedRadioButtonId();
        setContentView(R.layout.activity_questionaire3);
    }

    // Are you looking to pair your beer with food?
    public void nextButtonClickFunction3(View v) {
        radioGroupQ3 = (RadioGroup) findViewById(R.id.radioAnswers3);
        selectedQ3Id = radioGroupQ3.getCheckedRadioButtonId();
        if(selectedQ3Id == R.id.radioButtonYes)
            setContentView(R.layout.activity_questionaire4);
        else if (selectedQ2Id == R.id.radioButtonLighter)
            setContentView(R.layout.activity_questionaire5);
        else
            setContentView(R.layout.activity_questionaire6);
    }

    public void resultButtonClickFunction(View v) {
        if(selectedQ3Id == R.id.radioButtonYes) {
            radioGroupQ4 = (RadioGroup) findViewById(R.id.radioAnswers4);
            selectedQ4Id = radioGroupQ4.getCheckedRadioButtonId();
        } else {
            if (selectedQ2Id == R.id.radioButtonLighter) {
                radioGroupQ5 = (RadioGroup) findViewById(R.id.radioAnswers5);
                selectedQ5Id = radioGroupQ5.getCheckedRadioButtonId();
            } else {
                radioGroupQ6 = (RadioGroup) findViewById(R.id.radioAnswers6);
                selectedQ6Id = radioGroupQ6.getCheckedRadioButtonId();
            }
        }
        QuestionnaierResult qr = QuestionnaierResult.getInstance();
        if(selectedQ1Id == R.id.radioButtonYes)
            qr.setLookingToGetDrunk(true);
        else
            qr.setLookingToGetDrunk(false);
        if(selectedQ2Id == R.id.radioButtonLighter)
            qr.setLightOrDark(0);
        else
            qr.setLightOrDark(1);
        if(selectedQ3Id == R.id.radioButtonYes)
            qr.setLookingToPairWithFood(true);
        else
            qr.setLookingToPairWithFood(false);

        // set correct food options
        if(selectedQ4Id == R.id.radioButtonFoodOption1)
            qr.setFoodOption(0);
        if(selectedQ4Id == R.id.radioButtonFoodOption2)
            qr.setFoodOption(1);
        if(selectedQ4Id == R.id.radioButtonFoodOption3)
            qr.setFoodOption(2);
        if(selectedQ4Id == R.id.radioButtonFoodOption4)
            qr.setFoodOption(3);
        if(selectedQ4Id == R.id.radioButtonFoodOption5)
            qr.setFoodOption(4);
        if(selectedQ4Id == R.id.radioButtonFoodOption6)
            qr.setFoodOption(5);
        if(selectedQ4Id == R.id.radioButtonFoodOption7)
            qr.setFoodOption(6);
        if(selectedQ4Id == R.id.radioButtonFoodOption8)
            qr.setFoodOption(7);
        if(selectedQ4Id == R.id.radioButtonFoodOption9)
            qr.setFoodOption(8);

        if(selectedQ2Id == R.id.radioButtonLighter) {
            if(selectedQ5Id == R.id.radioButtonLighterBrewOption1)
                qr.setFlavorOption(1);
            if(selectedQ5Id == R.id.radioButtonLighterBrewOption2)
                qr.setFlavorOption(2);
            if(selectedQ5Id == R.id.radioButtonLighterBrewOption3)
                qr.setFlavorOption(3);
            if(selectedQ5Id == R.id.radioButtonLighterBrewOption4)
                qr.setFlavorOption(4);
            if(selectedQ5Id == R.id.radioButtonLighterBrewOption5)
                qr.setFlavorOption(5);
        }
        else {
            if(selectedQ6Id == R.id.radioButtonDarkerBrewOption1)
                qr.setFlavorOption(1);
            if(selectedQ6Id == R.id.radioButtonDarkerBrewOption2)
                qr.setFlavorOption(2);
            if(selectedQ6Id == R.id.radioButtonDarkerBrewOption3)
                qr.setFlavorOption(3);
            if(selectedQ6Id == R.id.radioButtonDarkerBrewOption4)
                qr.setFlavorOption(4);
        }

        setContentView(R.layout.activity_result);
        TextView tv = (TextView) findViewById(R.id.txtResult);
        String result = qr.findBrewType();
        tv.setText(result);
        RelativeLayout layout =(RelativeLayout)findViewById(R.id.resultView);

        // Set the right picture based on result
        if(result.equals("Pale Lager"))
            layout.setBackgroundResource(R.drawable.pilsner);
        else if(result.equals("Blonde Ale"))
            layout.setBackgroundResource(R.drawable.blonde_ale);
        else if(result.equals("Hefeweizen"))
            layout.setBackgroundResource(R.drawable.hefeweizen);
        else if(result.equals("Pale Ale"))
            layout.setBackgroundResource(R.drawable.pale_ale);
        else if(result.equals("Amber Ale"))
            layout.setBackgroundResource(R.drawable.amber_ale);
        else if(result.equals("Irish Red Ale"))
            layout.setBackgroundResource(R.drawable.irish_red_ale);
        else if(result.equals("Brown Ale"))
            layout.setBackgroundResource(R.drawable.brown_ale);
        else if(result.equals("Porter"))
            layout.setBackgroundResource(R.drawable.porter);
        else if(result.equals("Stout"))
            layout.setBackgroundResource(R.drawable.stout);
        else
            layout.setBackgroundResource(R.drawable.default_background);

        Button findLocalOptions = (Button) findViewById(R.id.btnFindLocalResults);

        findLocalOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(this, SuggestedResultsActivity.class);
                //startActivity(intent);
                AlertDialog alertDialog = new AlertDialog.Builder(QuestionaireActivity.this).create();
                alertDialog.setTitle("Local options found...");
                QuestionnaierResult qr = QuestionnaierResult.getInstance();
                BrewBuddyDatabaseHelper bbdb = new BrewBuddyDatabaseHelper(context);
                ArrayList<String> results = bbdb.getBrewsByType(qr.findBrewType());
                String message = "";
                for(String brew:results) {
                    message += brew + "\n";
                }
                alertDialog.setMessage(message);
                alertDialog.show();
            }
        });
    }
}