package com.ramadan.eva.util;

import android.location.Location;

public class util {

    public static int calculateDistance(double lat1, double long1, double lat2, double long2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(long1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(long2);
        return Math.round(loc1.distanceTo(loc2) / 1000);
    }

}
