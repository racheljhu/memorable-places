package com.example.memplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.memplaces.databinding.ActivityMapsBinding;
import com.google.android.material.dialog.InsetDialogOnTouchListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    LocationManager locationManager;
    LocationListener locationListener;
    Intent intent;
    int value;
    //Geocoder geocoder;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NonNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { //checking if user granted permission
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        intent = getIntent();
        if(intent.hasExtra("savedLoc")) {
            int value = intent.getIntExtra("savedLoc", 0);
            Log.i("value", String.valueOf(value));
        }



    }

    public void updateList(String addy){
        //Intent intent = getIntent();
        //int value = intent.getIntExtra("savedLoc", 0);


            value = intent.getIntExtra("savedLoc", 0);
            //Log.i("value", String.valueOf(value));


        Log.i("value", String.valueOf(value));
        Log.i("latLoc", String.valueOf(MainActivity.latitudes.get(0)));

        //LatLng sydney = new LatLng(MainActivity.latitudes.get(value-2), MainActivity.longitudes.get(value-2));
        //LatLng sydney = new LatLng(MainActivity.latitudes.get(value-2), MainActivity.longitudes.get(value-2));
        //mMap.addMarker(new MarkerOptions().position(sydney).title(addy));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        /*Intent searchAddress = new  Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+addy));
        startActivity(searchAddress);
/*
        //Intent intent = getIntent();
        Log.i("intent works", addy);

        /*String setAddy = intent.getStringExtra("savedLoc");
        Log.i("addy saved", "yes");

        Log.i("add", setAddy);*/

        //Geocoder coder = new Geocoder(this);
        //List<Address> address;
        /*
        try {


            Geocoder geocoder1 = new Geocoder(getApplicationContext(), Locale.getDefault());

            if (addy != null && !addy.isEmpty()) {

                List<Address> addressList = geocoder1.getFromLocationName(addy, 1);
                if (addressList != null && addressList.size() > 0) {
                    double lat = addressList.get(0).getLatitude();
                    double lng = addressList.get(0).getLongitude();
                    Log.i("addy go through", "success");
                }
            }
        } catch(Exception e){
                e.printStackTrace();
                // end catch
            } // end if

        }*/
/*
        try {
            Log.i("addy", "running");
            address = geocoder.getFromLocationName(addy,1);

            if (address==null) {
                Log.i("address", null);
            }

            Address location=address.get(0);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Log.i("lat", String.valueOf(latitude));
            Log.i("long", String.valueOf(longitude));

            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title(addy));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



        } catch (Exception e) {
            e.printStackTrace();
        }*/
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

        mMap.setOnMapLongClickListener(this);

        Intent intent = getIntent();
        int value = intent.getIntExtra("savedLoc", -1);

        if (value == -1){
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
        } else {
            LatLng itemLoc = new LatLng(MainActivity.latitudes.get(value-2), MainActivity.longitudes.get(value-2));
            mMap.addMarker(new MarkerOptions().position(itemLoc).title("address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(itemLoc));
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                //mMap.clear();
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {

                    List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if (listAddresses != null && listAddresses.size() > 0){
                        String address = "";

                        if (listAddresses.get(0).getThoroughfare() != null){
                            address += listAddresses.get(0).getThoroughfare() + " ";
                        }
                        if (listAddresses.get(0).getLocality() != null){
                            address += listAddresses.get(0).getLocality() + " ";
                        }
                        if (listAddresses.get(0).getAdminArea() != null){
                            address += listAddresses.get(0).getAdminArea();
                        }
                        if (listAddresses.get(0).getPostalCode() != null){
                            address += listAddresses.get(0).getPostalCode() + " ";
                        }
                        //Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { //checking if user granted permission
                //asking user for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                //Log.i("lat", String.valueOf(lastKnownLocation.getLatitude()));
                //Log.i("long", String.valueOf(lastKnownLocation.getLongitude()));

                mMap.clear();
                LatLng sydney = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }
    }

    @Override
    public void onMapLongClick(@NonNull @org.jetbrains.annotations.NotNull LatLng latLng) {

        try {
            double locLatitude = latLng.latitude;
            double locLongitude = latLng.longitude;
            MainActivity.latitudes.add(locLatitude);
            MainActivity.longitudes.add((double) latLng.longitude);

            Geocoder coder = new Geocoder(this);
            List<Address> listAddresses = coder.getFromLocation(locLatitude, locLongitude, 1);

            if (listAddresses != null && listAddresses.size() > 0){
                String address = "";

                if (listAddresses.get(0).getThoroughfare() != null){
                    address += listAddresses.get(0).getThoroughfare() + " ";
                }
                if (listAddresses.get(0).getLocality() != null){
                    address += listAddresses.get(0).getLocality() + " ";
                }
                if (listAddresses.get(0).getAdminArea() != null){
                    address += listAddresses.get(0).getAdminArea() + " ";
                }
                if (listAddresses.get(0).getPostalCode() != null){
                    address += listAddresses.get(0).getPostalCode();
                }


                mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                Toast.makeText(MapsActivity.this, "Location saved!", Toast.LENGTH_SHORT).show();

                //ArrayList<String> locations = new ArrayList<String>();

                //locations.add(address);
                //Log.i("list", locations.get(0));

                MainActivity.locations.add(address);


                //Intent intent = getIntent();
                //intent.putExtra("address", address);

                Log.i("address", address);

                MainActivity.arrayAdapter.notifyDataSetChanged();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
