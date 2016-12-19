package com.kyroon.onestop.delivery;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback
        ,GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener{ // allow this for map onready not to work with saved state
    private static final float DEFAULTZOOM =15 ;
    public GoogleMap mMap;
    // MapView mMapView;
    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1972;
    private static final double TorontoLAT = 43.7171556,
            TorontoLONG = -79.341469,
            DearbornLAT = 42.3053148,
            DearbornLONG = -83.2334044;
    private GoogleApiClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


   if (checkPlayServices()) {
            setContentView(R.layout.activity_maps);
            // mMapView = (MapView) findViewById(R.id.map);
            //  mMapView.onCreate(savedInstanceState);
            //nabel please check the following method does not execute properly check the onMapready method
          initMap();
        } else {
            setContentView(R.layout.activity_maps);

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manu_map, menu);
        return true;
    }

    /* use this with implement onMapReady */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
            mLocationClient = new GoogleApiClient.Builder(this)
                                .addApi(LocationServices.API)
                                .addConnectionCallbacks(this)
                                .addOnConnectionFailedListener(this)
                                .build();
            mLocationClient.connect();

        } else {
            Toast.makeText(this, "Map not available", Toast.LENGTH_SHORT).show();
            //  gotoLocation(DearbornLAT, DearbornLONG);
        }


        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng Toronto = new LatLng(TorontoLAT, TorontoLONG);
//        mMap.addMarker(new MarkerOptions().position(Toronto).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Toronto, 15));
       gotoLocation(DearbornLAT,DearbornLONG,DEFAULTZOOM);

    }

/* you only use the followingmethods if you are using view
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

*/

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, REQUEST_GOOGLE_PLAY_SERVICES)
                        .show();
            } else {
                Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }

    private void initMap() {
        if (mMap == null) {

            //MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            SupportMapFragment mapFrag = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);



        }

    }
    public void geoLocate(View v) throws IOException {
       hideSoftKeyboard(v);
        EditText txtPlace = (EditText) findViewById(R.id.txtMapLocationEdit);
        String location = txtPlace.getText().toString();
        Geocoder gc = new Geocoder(this); //this does a search and returns java list
        List<Address> list = gc.getFromLocationName(location, 1); //nabel revisit this this list type
        Address add =list.get(0);
        String locality = add.getLocality();

        Toast.makeText(this,locality, Toast.LENGTH_SHORT).show();
        double lat  = add.getLatitude();
        double lng = add.getLongitude();
        gotoLocation(lat,lng,DEFAULTZOOM);

    }


        private void hideSoftKeyboard(View v) {
            InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(v.getWindowToken(),0);


        }
        public  void gotoLocation(double lat, double lng,float zoom)
        {
            LatLng ll = new LatLng(lat,lng);

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
            mMap.moveCamera(update);


        }

    @Override
    protected void onStop() {
        super.onStop();
        MapStateManager mgr = new MapStateManager(this);
        mgr.saveMapState(mMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MapStateManager mgr = new MapStateManager(this);
        CameraPosition position = mgr.getSavedCameraPosition();
        if(position !=null)
        {
            if(mMap != null) {
                CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
                mMap.moveCamera(update);

            }
            else {
                SupportMapFragment mapFrag = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
                mapFrag.getMapAsync(this);


            }

        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Location Connection is ready", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showCurrentLocation(MenuItem item) {
        try {
            Location currentLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mLocationClient);
            if (currentLocation == null) {
            } else {
            }
        } catch (SecurityException e) {

        }
    }
}