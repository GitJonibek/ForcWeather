package com.example.jonib.forcweather.Model;

/**
 * Created by jonib on 2/24/2018.
 */

public class Coord {

    private double lat;
    private double lon;

    public Coord(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

}
