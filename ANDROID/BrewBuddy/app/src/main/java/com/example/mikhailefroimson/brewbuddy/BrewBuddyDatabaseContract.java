package com.example.mikhailefroimson.brewbuddy;

import android.provider.BaseColumns;

public final class BrewBuddyDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private BrewBuddyDatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class Breweries implements BaseColumns {
        public static final String TABLE_NAME_BREWERIES = "Breweries";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_ADDRESS = "Address";
        public static final String COLUMN_NAME_TYPE = "Type";
    }

    /* Inner class that defines the table contents */
    public static class Brews implements BaseColumns {
        public static final String TABLE_NAME_BREWS = "Brews";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_TYPE = "Type";
    }
}
