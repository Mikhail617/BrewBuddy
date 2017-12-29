package com.example.mikhailefroimson.brewbuddy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.RunnableFuture;

public class BrewBuddyMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "BrewBuddyMapActivity";
    private GoogleMap mMap;
    private static Context context;
    Handler mHandler;
    //private BreweryPlacemarkManager sBreweryPlacemarkManager;
    protected static Queue breweryQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_buddy_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        context = this;

        if (ActivityCompat.checkSelfPermission(BrewBuddyMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BrewBuddyMapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BrewBuddyMapActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            if (!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
                lm.requestLocationUpdates(provider, 20000, 0, (LocationListener) this);
            }

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    get_breweries_from_db();
                }
            });

            mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable (){
                @Override
                public void run() {
                    if(!breweryQueue.isEmpty()) {
                        Brewery brewery = (Brewery) breweryQueue.remove();
                        LatLng brewery_location = getLocationFromAddress(context, brewery.getAddress());
                        if(brewery_location != null)
                            mMap.addMarker(new MarkerOptions().position(brewery_location).title(brewery.getName()));
                        mHandler.postDelayed(this, 10);
                    }
                }
            });
            //plot_breweries_from_file();
            //plot_breweries_from_db();

            if (myLocation != null) {
                LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10), 1500, null);
            }

            /*mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
                    mMap.moveCamera(center);
                    mMap.animateCamera(zoom);

                }
            });*/

            // set listener for brewery marker clicks
            googleMap.setOnMarkerClickListener(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String message = String.format(
                "New Location \n Longitude: %1$s \n Latitude: %2$s",
                location.getLongitude(), location.getLatitude()
        );
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10), 1500, null);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public static LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }

    public void plot_breweries_from_file() {
        InputStream inputStream = getResources().openRawResource(R.raw.breweries);
        Utils.CSVFile csvFile = new Utils.CSVFile(inputStream);
        List breweryList = csvFile.read();
        for(Object breweryData:breweryList ) {
            // place marker on the map
            String[] sbreweryData = (String[]) breweryData;
            try {
                //Log.d(TAG, "Brewery item: " + Arrays.toString(sbreweryData));
                if(sbreweryData[2].contains("NJ")) {
                    LatLng address = getLocationFromAddress(this, sbreweryData[1] + "," + sbreweryData[2]);
                    mMap.addMarker(new MarkerOptions().position(address).title(sbreweryData[0]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void get_breweries_from_db() {
        // Create DatabaseHelper instance
        BrewBuddyDatabaseHelper dataHelper = new BrewBuddyDatabaseHelper(this);
        // Open the database for reading
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        // Create a new brewery Queue
        breweryQueue = new LinkedList();
        try {
            String selectQuery = "SELECT * FROM " + BrewBuddyDatabaseContract.Breweries.TABLE_BREWERIES;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String brewery_name = cursor.getString(cursor.getColumnIndex("Name"));
                    String brewery_type = cursor.getString(cursor.getColumnIndex("Type"));
                    String brewery_address = cursor.getString(cursor.getColumnIndex("Address"));
                    String brewery_phone = cursor.getString(cursor.getColumnIndex("Phone"));
                    String brewery_website = cursor.getString(cursor.getColumnIndex("Website"));
                    Brewery brewery = new Brewery(brewery_name, brewery_type,  brewery_address, brewery_phone, brewery_website);
                    breweryQueue.add(brewery);
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final TextView message = new TextView(this);
        String breweryName = marker.getTitle();
        AlertDialog alertDialog = new AlertDialog.Builder(BrewBuddyMapActivity.this).create(); //Read Update
        alertDialog.setTitle(breweryName);
        HashMap brewery = getBreweryInfo(breweryName);
        String website = ((String) brewery.get("website")).trim();
        String msg = ((String) brewery.get("name")).trim() + " \n" +
                ((String) brewery.get("type")).trim() + " \n" +
                ((String) brewery.get("address")).trim() + " \n" +
                ((String) brewery.get("phone")).trim() + " \n" + website + "\n\nBrews:\n";

        // Get the menu items
        BrewBuddyDatabaseHelper bbdb = new BrewBuddyDatabaseHelper(context);
        ArrayList<String> results = bbdb.getBrewsByBrewery(((String) brewery.get("name")).trim());
        for(String brew:results) {
            msg += brew + "\n";
        }

        final SpannableString s = new SpannableString(msg);
        Linkify.addLinks(s, Linkify.WEB_URLS);
        message.setText(s);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        alertDialog.setView(message);
        alertDialog.setButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
        return true;
    }

    // This should be somewhere else...
    public HashMap getBreweryInfo(String breweryName) {
        HashMap breweryData = new HashMap();
        BrewBuddyDatabaseHelper dataHelper = new BrewBuddyDatabaseHelper(this);
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT Name, Type, Address, Phone, Website FROM " + BrewBuddyDatabaseContract.Breweries.TABLE_BREWERIES + " WHERE Name = '" + breweryName + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String brewery_name = cursor.getString(cursor.getColumnIndex("Name"));
                    String brewery_type = cursor.getString(cursor.getColumnIndex("Type"));
                    String brewery_address = cursor.getString(cursor.getColumnIndex("Address"));
                    String brewery_phone = cursor.getString(cursor.getColumnIndex("Phone"));
                    String brewery_website = cursor.getString(cursor.getColumnIndex("Website"));
                    breweryData.put("name", brewery_name);
                    breweryData.put("address", brewery_address);
                    breweryData.put("type", brewery_type);
                    breweryData.put("phone", brewery_phone);
                    breweryData.put("website", brewery_website);
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return breweryData;
    }
}