package com.example.jonib.forcweather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonib.forcweather.Helper_package.Helper_class;
import com.example.jonib.forcweather.Model.Main;
import com.example.jonib.forcweather.Model.OpenWeatherMap;
import com.example.jonib.forcweather.common_package.Common_class;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.net.Proxy;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    ImageView imageView;

    LocationManager locationManager;
    String provider;
    static double lat, lon;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    int MY_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewControls
        txtCity = findViewById(R.id.txtCity);
        txtDescription = findViewById(R.id.txtDescription);
        txtLastUpdate = findViewById(R.id.txtLastUpdate);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtTime = findViewById(R.id.txtTime);
        txtCelsius = findViewById(R.id.txtCelcius);
        imageView = findViewById(R.id.imageView);

        //Getting Coordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);

        Location location = locationManager.getLastKnownLocation(provider);
        if(location == null)
            Toast.makeText(this, "No Location, Please Anable your location", Toast.LENGTH_LONG);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();

        new GetWeather().execute(Common_class.apiRequest(String.valueOf(lat), String.valueOf(lon)));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class GetWeather extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            Helper_class http = new Helper_class();
            stream = http.getHTTPDate(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.contains("Erros: Not fount city")){
                progressDialog.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type type = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s, type);
            progressDialog.dismiss();

            txtCity.setText(String.format("%s, %s", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
            txtLastUpdate.setText(String.format("Last Updated: %s", Common_class.getDateNow()));
            txtDescription.setText(String.format("%s", openWeatherMap.getWeatherList().get(0).getDescription()));
            txtHumidity.setText(String.format("%s/%s", Common_class.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()), Common_class.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            txtCelsius.setText(String.format("%.2f °C.", openWeatherMap.getMain().getTemp()));
            Picasso.with(MainActivity.this)
                    .load(Common_class.getImage(openWeatherMap.getWeatherList().get(0).getIcon()))
                    .into(imageView);

        }

    }

}
