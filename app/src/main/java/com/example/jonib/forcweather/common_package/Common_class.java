package com.example.jonib.forcweather.common_package;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jonib on 2/24/2018.
 */

public class Common_class {
    public static String API_KEY = "b3fde4c7acb6209d245a5d4741b065fa";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";

    @NonNull
    public static String apiRequest(String lat, String lon){
        StringBuilder stringBuilder = new StringBuilder(API_LINK);
        stringBuilder.append("?lat=%s&lon=%s&APPID=%s&units=metric").append(lat).append(lon).append(API_KEY);
        return stringBuilder.toString();

    }

    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("http://openweathermap.org/img/w/%s.ping", icon);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
