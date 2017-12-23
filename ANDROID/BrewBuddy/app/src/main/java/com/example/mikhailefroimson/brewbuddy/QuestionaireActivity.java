package com.example.mikhailefroimson.brewbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class QuestionaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);
    }

    public void nextButtonClickFunction(View v) {
        setContentView(R.layout.activity_questionaire2);
    }

    public void nextButtonClickFunction2(View v) {
        setContentView(R.layout.activity_questionaire3);
    }


}
