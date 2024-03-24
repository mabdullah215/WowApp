package com.mobileapp.wowapp.driver.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.OnboardActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.customer.utils.HelpSheet;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.CompaignDriving;
import com.mobileapp.wowapp.driver.DriverPersonalInformation;
import com.mobileapp.wowapp.driver.ServiceProvidrList;
import com.mobileapp.wowapp.driver.UpcomingCompaigns;
import com.mobileapp.wowapp.driver.model.Driver;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DriverHomeFragment extends Fragment
{
    ConstraintLayout progressLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_driver_home, container, false);
        ImageView imgsupport=view.findViewById(R.id.img_support);
        progressLayout=view.findViewById(R.id.progress_layout);
        HelpSheet helpSheet=new HelpSheet(getContext(),R.style.AppBottomSheetDialogTheme);
        LinearLayout compaignView=view.findViewById(R.id.active_compaign_layout);
        compaignView.setVisibility(View.GONE);
        imgsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                helpSheet.show();
            }
        });

        TextView tvDate=view.findViewById(R.id.tv_today_date);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        tvDate.setText(format.format(new Date()));
        TextView tvName=view.findViewById(R.id.tv_name);

        progressLayout.setVisibility(View.VISIBLE);
        Gson gson=new Gson();
        NetworkManager manager=NetworkManager.getInstance(getContext());
        if(manager.getCityList().isEmpty())
        {
            manager.setCityList();
            manager.setBankList();
        }
        DataSource source=DataSource.getInstance(getContext());
        Log.i("userTOken",source.getUserToken());

        /*FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.i("vlaue", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.i("fcmToken",token);
                    }
                });*/



        manager.getRequest(APIList.GET_PROFILE, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                APIResultSingle apiResult=gson.fromJson(result,APIResultSingle.class);
                if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                {
                    String data=gson.toJson(apiResult.getData());
                    Driver driver=gson.fromJson(data,Driver.class);
                    manager.setDriver(driver);
                    tvName.setText(driver.getName());
                    manager.setDriver(driver);
                    getAssignedCompaigns(view,compaignView,driver);

                    if(!driver.isVerified())
                    {
                        showUpdateProfileDialog(driver);
                    }
                }
                else
                {
                    progressLayout.setVisibility(View.GONE);
                    DataSource source=DataSource.getInstance(getActivity());
                    source.setUsertype("");
                    getActivity().finish();
                    Intent i = new Intent(getContext(), OnboardActivity.class);
                    startActivity(i);
                    Toast.makeText(getActivity(),apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                    Animatoo.INSTANCE.animateSlideRight(getActivity());
                }
            }
        });


        return view;
    }

    public void getAssignedCompaigns(View view,LinearLayout cardView,Driver driver)
    {
        Gson gson=new Gson();
        ImageView imgCompaign=view.findViewById(R.id.img_compaign);
        LinearLayout emptyLayout=view.findViewById(R.id.empty_state);
        NetworkManager manager=NetworkManager.getInstance(getContext());
        manager.getRequest(APIList.GET_DRIVER_COMPAIGNS_LIST, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                APIResultSingle apiResult=gson.fromJson(result,APIResultSingle.class);
                progressLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                imgCompaign.setVisibility(View.GONE);
                if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                {
                    String data=gson.toJson(apiResult.getData());
                    Compaign compaign=gson.fromJson(data, Compaign.class);
                    ImageView imgCompaign=view.findViewById(R.id.img_source);
                    TextView tvTitle=view.findViewById(R.id.tv_title);
                    TextView tvDistance=view.findViewById(R.id.tv_distance);
                    TextView tvStickerRemove=view.findViewById(R.id.tv_sticker_remove);
                    manager.setCompaignAssigned(true);
                    TextView tvImpressions=view.findViewById(R.id.tv_impressions);
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(new OnMapReadyCallback()
                    {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap)
                        {
                            googleMap.getUiSettings().setAllGesturesEnabled(false);
                            LatLng location=new LatLng(compaign.getCity().getLatitude(),compaign.getCity().getLongitude());
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
                        }
                    });

                    if(!compaign.getDesign().isEmpty())
                    {
                        Picasso.get().load(compaign.getDesign()).fit().into(imgCompaign);
                    }
                    tvTitle.setText(compaign.getName());
                    tvDistance.setText(String.valueOf(compaign.getTotalDistanceCovered()));
                    cardView.setVisibility(View.VISIBLE);
                    tvStickerRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            startActivity(new Intent(getContext(), ServiceProvidrList.class).putExtra("request_type",0)
                                    .putExtra("item",compaign));
                            Animatoo.INSTANCE.animateSlideLeft(getContext());
                        }
                    });
                    final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    MaterialButton startDrive=view.findViewById(R.id.button_start_driving);
                    startDrive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            if(Converter.compareDate(compaign.getStartTime()))
                            {
                                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                                {
                                    ((BaseActivity)getActivity()).askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100);
                                }
                                else
                                {
                                    startActivity(new Intent(getContext(), CompaignDriving.class).putExtra("campaign",compaign));
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(), "campaign not started yet", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                else
                {
                    imgCompaign.setVisibility(View.VISIBLE);
                    manager.setCompaignAssigned(false);
                    emptyLayout.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                    imgCompaign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            if(!manager.getDriver().isVerified())
                            {
                                showUpdateProfileDialog(driver);
                            }
                            else
                            {
                                startActivity(new Intent(getContext(),UpcomingCompaigns.class));
                            }
                        }
                    });

                }
            }
        });
    }

    @SuppressLint("NewApi")
    public Date getDateFromString(String dateString)
    {
        Date date=null;
        try
        {
            //SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd hh:m:s");
            date = sdf.parse(dateString);
            date.toInstant();
        }
        catch(ParseException ex)
        {

        }
        return date;
    }

    private void showUpdateProfileDialog(Driver driver)
    {
        new AlertDialog.Builder(getContext())
                .setTitle("Profile status")
                .setMessage("Please update your profile to continue")
                .setPositiveButton("Profile Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startActivity(new Intent(getContext(), DriverPersonalInformation.class).putExtra("driver",driver));
                        Animatoo.INSTANCE.animateSlideLeft(getActivity());
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_logo)
                .show();

    }
}