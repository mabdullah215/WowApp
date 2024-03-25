package com.mobileapp.wowapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mobileapp.wowapp.BaseActivity;

public class MyLocationListener {

    private static final String TAG = "MyLocationListener";
    private Context mContext;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    LocationUpdateListener locationUpdateListener;
    public interface LocationUpdateListener
    {
       void onLocationUpdate(Location location);
    }

    public void setLocationUpdate(LocationUpdateListener locationUpdate) {
        this.locationUpdateListener = locationUpdate;
    }

    public MyLocationListener(Context context) {
        this.mContext = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        createLocationRequest();
        createLocationCallback();
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    private void createLocationCallback()
    {
        mLocationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult)
            {
                super.onLocationResult(locationResult);
                Location location=locationResult.getLocations().get(0);
                if(locationUpdateListener!=null)
                {
                    Log.i("locationUpdate",location.getLatitude()+","+location.getLongitude());
                    locationUpdateListener.onLocationUpdate(location);
                }

            }
        };
    }

    public void startListening() {
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper());
        } catch (SecurityException e) {
            Log.e(TAG, "Security Exception: " + e.getMessage());
        }
    }

    public void stopListening() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
