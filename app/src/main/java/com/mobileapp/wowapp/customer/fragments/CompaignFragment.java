package com.mobileapp.wowapp.customer.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.dd.CircularProgressButton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobileapp.wowapp.LoginActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.BusinessProfile;
import com.mobileapp.wowapp.customer.CompaignList;
import com.mobileapp.wowapp.customer.MainActivity;
import com.mobileapp.wowapp.customer.adapter.CompaignListAdapter;
import com.mobileapp.wowapp.customer.model.Customer;
import com.mobileapp.wowapp.customer.utils.HelpSheet;
import com.mobileapp.wowapp.customer.utils.NewSheet;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.CompaignDetails;
import com.mobileapp.wowapp.driver.DriverHome;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.ServiceMainActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CompaignFragment extends Fragment
{
    GoogleMap mMap;
    boolean profileCompleted=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_compaign, container, false);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        ImageView imgsupport=view.findViewById(R.id.img_support);
        RecyclerView recyclerView=view.findViewById(R.id.compaign_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        List<Compaign>compaignList=new ArrayList<>();
        CompaignListAdapter adapter=new CompaignListAdapter(getContext(),compaignList);
        recyclerView.setAdapter(adapter);
        HelpSheet sheet=new HelpSheet(getContext(),R.style.AppBottomSheetDialogTheme);
        NewSheet newSheet=new NewSheet(getContext(),R.style.AppBottomSheetDialogTheme);
        MaterialCardView buttonNew=view.findViewById(R.id.button_new_dialog);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(profileCompleted)
                {
                    newSheet.show();
                }
                else
                {
                    showUpdateProfileDialog();
                }
            }
        });
        imgsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sheet.show();
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap)
            {
                mMap=googleMap;
            }
        });

        Gson gson=new Gson();
        NetworkManager manager=NetworkManager.getInstance(getContext());
        manager.sendRequest(APIList.GET_CUSTOMER_COMPAIGN_LIST, new HashMap<>(), new IResult() {
            @Override
            public void notifyResult(String result, APIResult apiResult)
            {
                String data=gson.toJson(apiResult.getData());
                TypeToken<ArrayList<Compaign>> token = new TypeToken<ArrayList<Compaign>>() {};
                List<Compaign> compaigns = gson.fromJson(data, token.getType());
                adapter.setDataList(compaigns);
                if(compaigns.size()>1)
                {
                    setMapBounds(compaigns);
                }
                adapter.notifyDataSetChanged();
            }});


        adapter.setOnItemClickListener(new CompaignListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                Compaign compaign=adapter.getDataList().get(position);
                startActivity(new Intent(getContext(), CompaignDetails.class).putExtra("item",compaign));
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }
        });

        manager.sendRequest(APIList.GET_PROFILE, new HashMap<>(), new IResult()
        {
            @Override
            public void notifyResult(String result, APIResult apiResult)
            {
                if(apiResult.getStatusCode().equalsIgnoreCase("OK"))
                {
                     String data=gson.toJson(apiResult.getSingledata());
                     Customer customer=gson.fromJson(data,Customer.class);
                     profileCompleted=customer.isVerified();
                }
            }});

        /*double lat=compaigns.get(position-1).getLatitude();
        double lon=compaigns.get(position-1).getLongitude();
        LatLng location=new LatLng(lat,lon);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));*/


        return view;
    }

    public void setMapBounds(List<Compaign> list)
    {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Compaign compaign: list)
        {
            double lat=0;
            double lon=0;
            LatLng location=new LatLng(lat,lon);
            builder.include(location);
        }
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10);
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);
    }


    private void showUpdateProfileDialog()
    {
        new AlertDialog.Builder(getContext())
                .setTitle("Profile status")
                .setMessage("Please update your profile to continue")
                .setPositiveButton("Profile Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startActivity(new Intent(getContext(), BusinessProfile.class));
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_logo)
                .show();

    }



}