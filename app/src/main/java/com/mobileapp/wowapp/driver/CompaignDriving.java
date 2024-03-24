package com.mobileapp.wowapp.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.utils.LatLngInterpolator;
import com.mobileapp.wowapp.utils.MyLocationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CompaignDriving extends AppCompatActivity
{
    GoogleMap mMap;
    LatLng currentLocation;
    int drivingId=0;
    int todayKms=0;
    int counter=0;
    Marker positonMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compaign_driving);
        ImageView imgBack=findViewById(R.id.img_back);
        MyLocationListener locationListener=new MyLocationListener(this);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        NetworkManager manager=NetworkManager.getInstance(this);
        MaterialButton startDriving=findViewById(R.id.button_driving);
        startDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(startDriving.getText().toString().equalsIgnoreCase("Start Driving"))
                {
                    locationListener.startListening();
                    startDriving.setText("Stop Driving");
                    startDriving.getBackground().setTint(getColor(R.color.stop_driving));
                }
                else
                {
                    locationListener.stopListening();
                    startDriving.setText("Start Driving");
                    startDriving.getBackground().setTint(getColor(R.color.start_driving));
                }
            }
        });
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap)
            {
                mMap=googleMap;
                mMap.getUiSettings().setAllGesturesEnabled(false);
                Compaign compaign=(Compaign) getIntent().getSerializableExtra("campaign");
                TextView tvCompaignName=findViewById(R.id.tv_title);
                tvCompaignName.setText(compaign.getName());
                HashMap<String,Object>map=new HashMap<>();
                map.put("campaignId",compaign.getId());
                locationListener.setLocationUpdate(new MyLocationListener.LocationUpdateListener() {
                    @Override
                    public void onLocationUpdate(Location location)
                    {
                        LatLng position=new LatLng(location.getLatitude(),location.getLongitude());

                        if(positonMarker==null)
                        {
                            currentLocation=position;
                            positonMarker=mMap.addMarker(new MarkerOptions().position(position));
                            positonMarker.setIcon(BitmapFromVector(getBaseContext(), R.drawable.ic_location_pin));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0f));
                            testDriving();
                        }
                    }
                });


                manager.postRequest(APIList.START_DRIVING, map, new IResultData() {
                    @Override
                    public void notifyResult(String result)
                    {
                        try
                        {
                            JSONObject object=new JSONObject(result).getJSONObject("data");
                            drivingId=object.getInt("drivingId");
                            todayKms=object.getInt("todayKms");
                            locationListener.startListening();
                            startDriving.setText("Stop Driving");
                            startDriving.getBackground().setTint(getColor(R.color.stop_driving));

                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }


    public void testDriving()
    {
        List<LatLng> latLngList=new ArrayList<>();
        latLngList.add(new LatLng(33.608842, 72.970218));
        latLngList.add(new LatLng(33.608887, 72.970797));
        latLngList.add(new LatLng(33.609656, 72.970701));
        latLngList.add(new LatLng(33.610853, 72.970465));
        latLngList.add(new LatLng(33.611440, 72.970365));
        latLngList.add(new LatLng(33.611147, 72.967565));
        latLngList.add(new LatLng(33.610762, 72.965087));
        latLngList.add(new LatLng(33.612443, 72.964137));
        latLngList.add(new LatLng(33.614256, 72.963148));
        latLngList.add(new LatLng(33.617685, 72.963568));
        final Handler handler = new Handler();
        final int delay = 3000;
        handler.postDelayed(new Runnable() {
            public void run()
            {
                animateMarker(latLngList.get(counter));
                counter=counter+1;
                if(counter<latLngList.size())
                {
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);

    }

    private void animateMarker(final LatLng destination) {
        final LatLng startPosition = positonMarker.getPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(destination));
        final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000); // Change duration as per your requirement
        valueAnimator.setInterpolator(input -> input); // Linear interpolation
        valueAnimator.addUpdateListener(animation -> {
            float v = animation.getAnimatedFraction();
            LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, destination);
            positonMarker.setPosition(newPosition);
        });
        valueAnimator.start();
    }
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId)
    {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}