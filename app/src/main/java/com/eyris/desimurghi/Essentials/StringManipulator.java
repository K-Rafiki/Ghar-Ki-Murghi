package com.eyris.desimurghi.Essentials;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class StringManipulator {

    public static LatLng getLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }


}
