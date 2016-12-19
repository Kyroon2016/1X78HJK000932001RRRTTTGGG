package com.kyroon.onestop.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {
    //nabel added the following line on 11/15/2016 to have a reference to the coordinatedlayout.
    private CoordinatorLayout coordinatorLayout;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        //  Intent intent = new Intent(this,RegisterActivity.class);
        //   startActivity(intent);
        switch (id) {
            case R.id.action_settings:
                Snackbar.make(coordinatorLayout,
                        "you have selected Settings", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;
            case R.id.action_register:
//                Intent intent = new Intent(this, RegisterActivity.class);
//                startActivity(intent);
//                return true;
                Snackbar.make(coordinatorLayout,
                        "you have selected Register", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;
            case R.id.action_map:
                Intent intentmap = new Intent(this, MapsActivity.class);
                startActivity(intentmap);
                Snackbar.make(coordinatorLayout,
                        "you have selected Map", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                return true;
            case R.id.action_locate:
                Intent intent = new Intent(this, LocateActivity.class);
               startActivity(intent);
                return  true;


            case R.id.action_categories:
                Intent intentCategories = new Intent(this, CategoryActivity.class);
                startActivity(intentCategories);
                return  true;

        }

        return super.onOptionsItemSelected(item);
    }
}
