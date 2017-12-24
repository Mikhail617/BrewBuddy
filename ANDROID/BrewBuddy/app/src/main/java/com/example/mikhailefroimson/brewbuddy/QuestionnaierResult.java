package com.example.mikhailefroimson.brewbuddy;

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
        switch(switch_option+1) {
            case 1:
                return "Pale Larger/Pilsner";
            case 2:
                return "Blonde Ale";
            case 3:
                return "Hefeweizen";
            case 4:
                return "Pale Ale/IPA";
            case 5:
                return "Amber Ale";
            case 6:
                return "Irish Red Ale";
            case 7:
                return "Brown Ale";
            case 8:
                return "Porter";
            case 9:
                return "Stout";
        }
        return result;
    }
}
