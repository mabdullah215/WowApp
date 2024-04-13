package com.mobileapp.wowapp.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CompaignDriving extends BaseActivity
{
    GoogleMap mMap;
    int drivingId=0;
    float todayKms=0;
    float currentKms=0;
    Marker positonMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compaign_driving);
        ImageView imgBack=findViewById(R.id.img_back);
        MyLocationListener locationListener=new MyLocationListener(this);
        NetworkManager manager=NetworkManager.getInstance(this);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                locationListener.stopListening();
                Animatoo.INSTANCE.animateSlideRight(CompaignDriving.this);
            }
        });

        TextView tvDate=findViewById(R.id.tv_today_date);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        tvDate.setText(format.format(new Date()));
        TextView tvDistance=findViewById(R.id.tv_distance);
        MaterialButton startDriving=findViewById(R.id.button_driving);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap)
            {
                mMap=googleMap;
                mMap.getUiSettings().setAllGesturesEnabled(false);
                showLoading();
                TextView tvAmount=findViewById(R.id.tv_amount);
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
                            positonMarker=mMap.addMarker(new MarkerOptions().position(position));
                            positonMarker.setIcon(BitmapFromVector(getBaseContext(), R.drawable.ic_location_pin));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0f));
                        }
                        else
                        {
                            Location source=new Location("");
                            source.setLatitude(positonMarker.getPosition().latitude);
                            source.setLongitude(positonMarker.getPosition().longitude);
                            float distance=source.distanceTo(location)/1000;
                            currentKms=currentKms+distance;
                            float totalfortoday=currentKms+todayKms;

                            if(totalfortoday>compaign.getKms_per_day())
                            {
                                float difference=totalfortoday-compaign.getKms_per_day();
                                currentKms=currentKms-difference;
                                locationListener.stopListening();
                                startDriving.setText("Start Driving");
                                startDriving.setEnabled(false);
                                startDriving.getBackground().setTint(getColor(R.color.inactivecolor));
                                stopDriving();
                            }

                            double amount=currentKms*compaign.getCity().getMoney_constant();
                            DecimalFormat df2 = new DecimalFormat("#.#");
                            tvDistance.setText(df2.format(currentKms));
                            tvAmount.setText(df2.format(amount));
                            animateMarker(new LatLng(position.latitude,position.longitude));
                        }
                    }
                });
                manager.postRequest(APIList.START_DRIVING, map, new IResultData() {
                    @Override
                    public void notifyResult(String result)
                    {
                        try
                        {
                            hideLoading();
                            JSONObject object=new JSONObject(result).getJSONObject("data");
                            drivingId=object.getInt("drivingId");
                            todayKms=object.getInt("todayKms");
                            locationListener.startListening();
                            startDriving.setText("Stop Driving");
                            startDriving.getBackground().setTint(getColor(R.color.stop_driving));
                            startDriving.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    if(currentKms<compaign.getKms_per_day())
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
                                            stopDriving();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getBaseContext(), "You have already drove allowed Kms for today.Please come back tomorrow", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


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

    public void stopDriving()
    {
        NetworkManager manager=NetworkManager.getInstance(this);
        HashMap<String,Object>map=new HashMap<>();
        map.put("kms",currentKms);
        map.put("latitude",positonMarker.getPosition().latitude);
        map.put("longitude",positonMarker.getPosition().longitude);
        map.put("isDriving",0);
        map.put("drivingId",drivingId);
        manager.setAllowDashboardRefresh(true);
        //manager.postRequest(APIList.STOP_DRIVING,map,null);
    }

    /*public void dummyDriving()
    {
        List<LatLng> list=new ArrayList<>();
        list.add(new LatLng(33.608833, 72.970293));
        list.add(new LatLng(33.608894, 72.970798));
        list.add(new LatLng(33.607919, 72.971075));
        list.add(new LatLng(33.608155, 72.972093));
        list.add(new LatLng(33.608286, 72.973430));
        list.add(new LatLng(33.609075, 72.973311));
        list.add(new LatLng(33.609866, 72.973193));
        list.add(new LatLng(33.611699, 72.972876));
        list.add(new LatLng(33.611531, 72.971485));
        list.add(new LatLng(33.611440, 72.970228));
        list.add(new LatLng(33.611170, 72.967816));
        list.add(new LatLng(33.610862, 72.965404));
        list.add(new LatLng(33.610862, 72.965404));
        list.add(new LatLng(33.610862, 72.965404));
        list.add(new LatLng(33.610862, 72.965404));
        //list.add(new LatLng());

        final Handler handler = new Handler();
        final int delay = 5000; // 1000 milliseconds == 1 second
        final AtomicInteger index = new AtomicInteger(0);
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                if(index.get()<list.size())
                {

                    LatLng position=list.get(index.get());

                    if(positonMarker==null)
                    {
                        positonMarker=mMap.addMarker(new MarkerOptions().position(position));
                        positonMarker.setIcon(BitmapFromVector(getBaseContext(), R.drawable.ic_location_pin));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0f));
                    }
                    else
                    {
                        Location source=new Location("");
                        Location dest=new Location("");
                        dest.setLatitude(position.latitude);
                        dest.setLongitude(position.longitude);
                        source.setLatitude(positonMarker.getPosition().latitude);
                        source.setLongitude(positonMarker.getPosition().longitude);
                        float distance=source.distanceTo(dest)/1000;
                        currentKms=currentKms+distance;
                        float totalfortoday=currentKms+todayKms;

                        if(totalfortoday>20)
                        {
                            float difference=totalfortoday-20;
                            currentKms=currentKms-difference;
                        }

                    }


                    animateMarker(list.get(index.get()));
                    index.incrementAndGet();
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);

    }*/

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void animateMarker(final LatLng destination) {
        final LatLng startPosition = positonMarker.getPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(destination));
        final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(4500); // Change duration as per your requirement
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