package com.mobileapp.wowapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity extends AppCompatActivity {

    View progressOverlayView;
    RelativeLayout screenRootView;
    Location userlocation;

    public void showToast(String msg)
    {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout,msg, Snackbar.LENGTH_INDEFINITE).setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                    }
        }).setActionTextColor(getResources().getColor(android.R.color.holo_green_dark)).show();
    }

    @Override
    public void setContentView(int layoutResID)
    {
        screenRootView = new RelativeLayout(this);
        screenRootView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View screenView=inflater.inflate(layoutResID,null);
        screenView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        progressOverlayView = inflater.inflate(R.layout.progress_bar_view, null);
        progressOverlayView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
        screenRootView.addView(screenView);
        super.setContentView(screenRootView);
    }

    public void showLoading()
    {
        if(progressOverlayView!=null)
        {
            screenRootView.addView(progressOverlayView);
            screenRootView.getChildAt(0).setAlpha(0.7f);
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            enableLocationSettings();
        }
    }

    public void askForPermission(String permission, Integer requestCode)
    {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
        else
        {
            enableLocationSettings();
        }
    }

    protected void enableLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(100)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())
                .addOnSuccessListener(this, (LocationSettingsResponse response) ->
                {
                    setupLocation();
                })
                .addOnFailureListener(this, ex -> {
                    if (ex instanceof ResolvableApiException) {
                        // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) ex;
                            resolvable.startResolutionForResult(BaseActivity.this, 100);
                        } catch (IntentSender.SendIntentException sendEx) {
                        }
                    }
                });
    }

    @SuppressLint("MissingPermission")
    public void setupLocation()
    {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,null).addOnSuccessListener(new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(Location location)
            {
                userlocation=location;
            }
        });
    }

    public Location getUserlocation() {
        return userlocation;
    }

    public void hideLoading()
    {
        if(progressOverlayView!=null)
        {
            screenRootView.removeView(progressOverlayView);
            screenRootView.getChildAt(0).setAlpha(1f);
        }
    }
}