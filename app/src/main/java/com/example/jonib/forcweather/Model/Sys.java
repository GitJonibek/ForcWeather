package com.example.jonib.forcweather.Model;

/**
 * Created by jonib on 2/24/2018.
 */

public class Sys {

    private int type;
    private int id;
    private double message;
    private double sunset;
    private double sunrise;
    private String country;

    public Sys(int type, int id, double message, double sunset, double sunrise, String country) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.country = country;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }

    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
