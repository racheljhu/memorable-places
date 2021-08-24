package com.example.memplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> locations;
    public static ArrayList<Double> latitudes;
    public static ArrayList<Double> longitudes;
    public static ArrayAdapter arrayAdapter;

    public void goToMap(View view){
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

        startActivity(intent); //might need to change to finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

        ListView listView = findViewById(R.id.listView);

        locations = new ArrayList<String>();
        //locations.add("12825 SE 225th CT Kent WA");
        //locations.add("Sequoia Avenue Millbrae 94030 California");
        latitudes = new ArrayList<Double>();
        longitudes = new ArrayList<Double>();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, locations);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                intent.putExtra("savedLoc", i);

                Log.i("i value", String.valueOf(i));
                Log.i("lat", latitudes.get(i).toString());
                startActivity(intent);

                //String clickedAddress = locations.get(i);
                //MapsActivity.updateList(clickedAddress);

                //MapsActivity mapsActivity = new MapsActivity();
                //mapsActivity.goToLocationFromAddress(clickedAddress);
                //mapsActivity.updateList(clickedAddress);
                //intent.putExtra("arrayList", locations);   can prob delete

            }
        });

        Log.i("loc size", String.valueOf(locations.size()));
        if(locations.size() > 0) {
            Toast.makeText(this, locations.get(0), Toast.LENGTH_SHORT).show();
            Log.i("addresses", locations.get(0));
        }
        //Log.i("address", intent.getStringExtra("address"));
    }
}