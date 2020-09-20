package com.eyris.desimurghi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eyris.desimurghi.Essentials.StringManipulator;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import okhttp3.internal.platform.Platform;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "LocationActivity";
    private static final int LOCATION_PERMISSION = 33;
    Button bt_next;

    private MapView mv;

    private GoogleMap mMap;
    LatLng location;
    int height = 100;
    int width = 100;
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;
    Marker[] newMarker;
    FusedLocationProviderClient fusedLocationClient;
    private ProgressBar pb_loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        findViews();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocationActivity.this);


        bitmapdraw = (BitmapDrawable) ContextCompat.getDrawable(LocationActivity.this, R.mipmap.pin);
        b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        mv.onCreate(savedInstanceState);
        mv.onResume();
        mv.getMapAsync(this);

        ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION && ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Load the location
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                Log.d(TAG, "onSuccess: location acquired");
                LatLng latLng = StringManipulator.getLatLng(location);
                newMarker[0].remove();
                newMarker[0] = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).title("You !"));
                LocationActivity.this.location = latLng;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));


            }).addOnFailureListener(e -> Log.d(TAG, "onFailure: location failure " + e.getMessage()));
        }
    }

    private void findViews() {
        bt_next = findViewById(R.id.bt_next);
        mv = findViewById(R.id.mapFragment);
        pb_loader = findViewById(R.id.pb_loader);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        bt_next.setOnClickListener(v -> startActivity(new Intent(LocationActivity.this, MainActivity.class).putExtra("lat", location.latitude).putExtra("lon", location.longitude)));

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_loader.setVisibility(View.VISIBLE);
                Geocoder geoCoder = new Geocoder(LocationActivity.this);
                (new Thread() {
                    public void run() {
                        // do stuff
                        List<Address> matches = null;
                        try {
                            matches = geoCoder.getFromLocation(location.latitude, location.longitude, 1);
                            Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                            if (bestMatch != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pb_loader.setVisibility(View.GONE);
                                        startActivity(new Intent(LocationActivity.this, MainActivity.class).putExtra("lat", location.latitude).putExtra("lon", location.longitude).putExtra("addr", bestMatch.getAddressLine(0)));
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LocationActivity.this, "Could not get locality", Toast.LENGTH_SHORT).show();
                                        pb_loader.setVisibility(View.GONE);
                                    }
                                });

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

//                    List<Address> matches = null;
//                    matches = geoCoder.getFromLocation(location.latitude, location.longitude, 1);
//                    Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
//                    if (bestMatch != null) {
//                        pb_loader.setVisibility(View.GONE);
//                        startActivity(new Intent(LocationActivity.this, MainActivity.class).putExtra("lat", location.latitude).putExtra("lon", location.longitude).putExtra("addr", bestMatch.getAddressLine(0)));
//                    } else {
//                        Toast.makeText(LocationActivity.this, "Could not get locality", Toast.LENGTH_SHORT).show();
//                        pb_loader.setVisibility(View.GONE);
//                    }

            }
        });

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap = googleMap;


        LatLng defaultLocation = new LatLng(25.391713576503374, 68.35760246962309);

        newMarker = new Marker[]{mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).position(defaultLocation).title("You !"))};
        location = defaultLocation;

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 14));
        mMap.setOnMapClickListener(latLng -> {
            newMarker[0].remove();
            newMarker[0] = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).title("You !"));
            location = latLng;
        });
    }

}