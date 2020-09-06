package com.ramadan.eva.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ramadan.eva.R;
import com.ramadan.eva.data.model.Place;
import com.ramadan.eva.ui.viewmodel.PlaceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.ramadan.eva.util.util.calculateDistance;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, AdapterView.OnItemSelectedListener {

    private GoogleMap map;
    PlaceViewModel placeViewModel;
    private Location location;
    LatLng myLocation;
    private LocationManager locationManager;
    boolean isGPSEnabled;
    private String[] placesArray = new String[8];
    Map<Integer, String> treeMap = new TreeMap<>();
    private List<Place> placeList = new ArrayList<>();
    Spinner spinner;
    LatLng latLng = new LatLng(26.8206, 30.8025);
    String distance;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        spinner = findViewById(R.id.spinner);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 99);
            return;
        }
        updateLocationWithLatKnown();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        dataObserver();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDropDownAdapter() {
        placesArray = new String[placeList.size()];
        treeMap.put(0, "Nearest City");
        for (int i = 0; i < placeList.size(); i++) {
            treeMap.put(Integer.valueOf(placeList.get(i).getDistance()), placeList.get(i).getName());
        }
        placesArray = treeMap.values().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, placesArray);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String targetPlace = String.valueOf(adapterView.getItemAtPosition(i));
        placeList.forEach(place -> {
            if (place.getName().equals(targetPlace)) {
                latLng = new LatLng(place.getLatitude(), place.getLongitude());
                distance = place.getDistance();
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                        latLng, 12);
                map.animateCamera(location);
                Toast.makeText(getApplicationContext(), distance + " Km away", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dataObserver() {
        placeViewModel.getPlacesLiveData().observe(this, places -> {
            placeList = places;
            places.forEach(place -> {
                place.setDistance(String.valueOf(
                        calculateDistance(myLocation.latitude, myLocation.longitude,
                                place.getLatitude(), place.getLongitude())));
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLatitude(), place.getLongitude()))
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(place.getName())
                        .snippet(place.getDistance() + "Km"));
            });
            setDropDownAdapter();
        });

    }

    private void updateLocationWithLatKnown() {
        if (isGPSEnabled) {
            if (location == null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]
                                    {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                            , 101);
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 99) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                updateLocationWithLatKnown();
            } else {
                Toast.makeText(this, "You have to approve permissions", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(this, "Provider Disabled", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        locationManager.removeUpdates(this);
//    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.light_map));
        myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        map = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(new MarkerOptions()
                .position(myLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .title("You're here!"));
        googleMap.addMarker(new MarkerOptions()
                .position(myLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .title("You're here!"));
        googleMap.setOnMarkerClickListener(marker -> {
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    marker.getPosition(), 15);
            googleMap.animateCamera(location);
            marker.showInfoWindow();
            return true;
        });
        googleMap.setMyLocationEnabled(true);

    }


}