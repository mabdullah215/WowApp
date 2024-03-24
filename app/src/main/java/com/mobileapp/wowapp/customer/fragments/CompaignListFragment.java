package com.mobileapp.wowapp.customer.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobileapp.wowapp.OnboardActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.BusinessProfile;
import com.mobileapp.wowapp.customer.ExtendCompaign;
import com.mobileapp.wowapp.customer.MainActivity;
import com.mobileapp.wowapp.customer.NewCompaignActivity;
import com.mobileapp.wowapp.customer.OfferActivity;
import com.mobileapp.wowapp.customer.adapter.CompaignListAdapter;
import com.mobileapp.wowapp.customer.model.Customer;
import com.mobileapp.wowapp.customer.utils.HelpSheet;
import com.mobileapp.wowapp.customer.utils.NewSheet;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.CompaignDetails;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CompaignListFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_compaign_list, container, false);
        RelativeLayout toolbarLayout=view.findViewById(R.id.toolbar_layout);
        ImageView imgRight=toolbarLayout.findViewById(R.id.img_right);
        RecyclerView recyclerView=view.findViewById(R.id.compaign_list);
        ConstraintLayout progressLayout=view.findViewById(R.id.progress_layout);
        TextView tvName=view.findViewById(R.id.tv_name);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        ArrayList<Compaign>compaignList=new ArrayList<>();
        CompaignListAdapter adapter=new CompaignListAdapter(getContext(),compaignList);
        recyclerView.setAdapter(adapter);
        HelpSheet sheet=new HelpSheet(getContext(),R.style.AppBottomSheetDialogTheme);
        NewSheet newSheet=new NewSheet(getContext(),R.style.AppBottomSheetDialogTheme);
        LinearLayout layoutExtend=newSheet.findViewById(R.id.layout_extend);
        LinearLayout layoutSpread=newSheet.findViewById(R.id.layout_spread);
        LinearLayout layoutFocus=newSheet.findViewById(R.id.layout_focus);
        MaterialCardView buttonNew=view.findViewById(R.id.button_new_dialog);
        LinearLayout emptyLayout=view.findViewById(R.id.empty_state);
        Gson gson=new Gson();
        NetworkManager manager=NetworkManager.getInstance(getContext());
        if(manager.getCityList().isEmpty())
        {
            manager.setCityList();
            manager.setBankList();
        }

        ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            getCompaigs(progressLayout,manager,adapter,emptyLayout);
                        }
                    }
                });

        layoutExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                newSheet.hide();

                if(adapter.getDataList().size()>0)
                {
                    ArrayList<Compaign>list=new ArrayList<>();
                    list.addAll(adapter.getDataList());
                    Intent intent=new Intent(getContext(), ExtendCompaign.class).putExtra("list",list);
                    launchSomeActivity.launch(intent);
                    Animatoo.INSTANCE.animateSlideLeft(getContext());
                }
                else
                {
                    Toast.makeText(getContext(), "No campaigns yet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        layoutSpread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                newSheet.hide();
                Intent intent=new Intent(getContext(), NewCompaignActivity.class);
                launchSomeActivity.launch(intent);
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }
        });

        layoutFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ArrayList<Compaign>list=new ArrayList<>();
                list.addAll(adapter.getDataList());
                newSheet.hide();
                startActivity(new Intent(getContext(), OfferActivity.class).putExtra("list",list));
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }
        });


        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(manager.getCustomer().isVerified())
                {
                    newSheet.show();
                }
                else
                {
                    showUpdateProfileDialog(launchSomeActivity);
                }
            }
        });
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sheet.show();
            }
        });

        ImageView imgRefresh=toolbarLayout.findViewById(R.id.img_left);
        imgRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getCompaigs(progressLayout,manager,adapter,emptyLayout);
            }
        });

        adapter.setOnItemClickListener(new CompaignListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                Compaign compaign=adapter.getDataList().get(position);
                startActivity(new Intent(getContext(), CompaignDetails.class).putExtra("item",compaign));
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }
        });


        manager.getRequest(APIList.GET_PROFILE, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                Log.i("profileresult",result);
                APIResultSingle apiResult=gson.fromJson(result,APIResultSingle.class);
                if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                {
                    String data=gson.toJson(apiResult.getData());
                    Customer customer=gson.fromJson(data,Customer.class);
                    tvName.setText(customer.getName());
                    manager.setCustomer(customer);
                    if(customer.isVerified())
                    {
                        getCompaigs(progressLayout,manager,adapter,emptyLayout);
                    }
                    else
                    {
                        showUpdateProfileDialog(launchSomeActivity);
                    }
                }
                else
                {
                    DataSource source=DataSource.getInstance(getActivity());
                    source.setUsertype("");
                    source.setUserToken("");
                    Intent i = new Intent(getContext(), OnboardActivity.class);
                    startActivity(i);
                    Toast.makeText(getActivity(),apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                    Animatoo.INSTANCE.animateSlideRight(getActivity());
                }
            }});

        return view;
    }



    private void getCompaigs(ConstraintLayout progresslayout,NetworkManager manager,CompaignListAdapter adapter,LinearLayout emptyLayout)
    {
        progresslayout.setVisibility(View.VISIBLE);

        manager.getRequest(APIList.GET_CUSTOMER_COMPAIGN_LIST, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                if(!result.isEmpty())
                {
                    Gson gson=new Gson();
                    APIResult apiResult=gson.fromJson(result,APIResult.class);
                    progresslayout.setVisibility(View.GONE);
                    String data=gson.toJson(apiResult.getData());
                    TypeToken<ArrayList<Compaign>> token = new TypeToken<ArrayList<Compaign>>() {};
                    List<Compaign> compaigns = gson.fromJson(data, token.getType());
                    adapter.setDataList(compaigns);
                    adapter.notifyDataSetChanged();

                    if(compaigns.isEmpty())
                        emptyLayout.setVisibility(View.VISIBLE);
                    else
                        emptyLayout.setVisibility(View.GONE);
                }
                else
                {
                    emptyLayout.setVisibility(View.VISIBLE);
                    progresslayout.setVisibility(View.GONE);
                }
            }
        });
    }


    private void showUpdateProfileDialog(ActivityResultLauncher<Intent> launcher)
    {
        new AlertDialog.Builder(getContext())
                .setTitle("Profile status")
                .setMessage("Please update your profile to continue")
                .setPositiveButton("Profile Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent= new Intent(getContext(), BusinessProfile.class);
                        launcher.launch(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_logo)
                .show();

    }



}