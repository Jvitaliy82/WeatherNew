package com.jdeveloperapps.weather.gpsData;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.jdeveloperapps.weather.Presenter;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class GpsCoordinator implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CODE = 10;
    private LocationManager locationManager;
    private List<String> providers;
    private String provider;
    private Context context;
    private Presenter presenter;

    public GpsCoordinator(Presenter presenter) {
        this.presenter = presenter;
    }

    public void getLatLot(Context context) {
        this.context = context;
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation(context);
        } else {
            requestLocationPermission(context);
        }
    }

    private void requestLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_CHECKIN_PROPERTIES) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            provider = locationManager.getBestProvider(criteria, true);
        }

        if (provider != null) {
            locationManager.requestSingleUpdate(provider, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    String latitude = Double.toString(location.getLatitude());
                    String lontitude = Double.toString(location.getLongitude());
                    presenter.requestWeatherByGPS(latitude, lontitude);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            }, Looper.myLooper());
        }
    }

    private void requestLocationPermission(Context context) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2 && (grantResults[0] == PackageManager.PERMISSION_GRANTED) ||
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                requestLocation(context);
            }
        }
    }

}
