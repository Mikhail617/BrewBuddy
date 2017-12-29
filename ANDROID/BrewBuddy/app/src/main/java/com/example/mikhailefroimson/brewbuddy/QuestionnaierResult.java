package com.example.mikhailefroimson.brewbuddy;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mikhail.efroimson on 12/23/2017.
 */

public class QuestionnaierResult {

    private boolean isLookingToGetDrunk;
    private boolean isLookingToPairWithFood;
    private int lightOrDark;
    private int foodOption;
    private int flavorOption;

    private static QuestionnaierResult instance;

    public static QuestionnaierResult getInstance() {
        if(instance == null) {
            instance = new QuestionnaierResult();
        }
        return instance;
    }

    private QuestionnaierResult() {

    }

    public void setLookingToGetDrunk(boolean lookingToGetDrunk) {
        isLookingToGetDrunk = lookingToGetDrunk;
    }

    public void setLookingToPairWithFood(boolean lookingToPairWithFood) {
        isLookingToPairWithFood = lookingToPairWithFood;
    }

    public void setLightOrDark(int lightOrDark) {
        this.lightOrDark = lightOrDark;
    }

    public void setFoodOption(int foodOption) {
        this.foodOption = foodOption;
    }

    public void setFlavorOption(int flavorOption) {
        this.flavorOption = flavorOption;
    }

    public boolean isLookingToGetDrunk() {
        return isLookingToGetDrunk;
    }

    public boolean isLookingToPairWithFood() {
        return isLookingToPairWithFood;
    }

    public int getLightOrDark() {
        return lightOrDark;
    }

    public int getFoodOption() {
        return foodOption;
    }

    public int getFlavorOption() {
        return flavorOption;
    }

    public String findBrewType() {
        String result = "No suggestions.";
        int switch_option;
        if(isLookingToPairWithFood) {
            switch_option = getFoodOption();
        } else {
            switch_option = getFlavorOption();
        }
        switch(switch_option) {
            case 0:
                return "Pale Lager";
            case 1:
                return "Blonde Ale";
            case 2:
                return "Hefeweizen";
            case 3:
                return "Pale Ale";
            case 4:
                return "Amber Ale";
            case 5:
                return "Irish Red Ale";
            case 6:
                return "Brown Ale";
            case 7:
                return "Porter";
            case 8:
                return "Stout";
        }
        return result;
    }

    public ArrayList<String> findLocalBrewsByType(String type) {
        ArrayList<String> brews = new ArrayList<>();
        return brews;
    }
}
