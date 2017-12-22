package com.example.mikhailefroimson.brewbuddy;

import android.provider.BaseColumns;

public final class BrewBuddyDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private BrewBuddyDatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class Breweries implements BaseColumns {
        public static final String TABLE_BREWERIES = "Breweries";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_PHONE = "Phone";
        public static final String COLUMN_WEBSITE = "Website";
    }

    /* Inner class that defines the table contents */
    public static class Brews implements BaseColumns {
        public static final String TABLE_BREWS = "Brews";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_ABV = "ABV";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_BREWERY = "Brewery";
        public static final String COLUMN_PRICE = "Price";
        public static final String COLUMN_AVAILABILITY = "Availability";
    }
}
