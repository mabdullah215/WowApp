package com.mobileapp.wowapp.driver;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobileapp.wowapp.Helper;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.adapter.CompaignListAdapter;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpcomingCompaigns extends AppCompatActivity
{
    ConstraintLayout progressLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_compaigns);
        progressLayout=findViewById(R.id.progress_layout);
        ImageView imgRight=findViewById(R.id.img_right);
        imgRight.setImageResource(R.drawable.ic_refresh);
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Upcoming Campaigns");
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getUpcomingCompaigns();
            }
        });

        getUpcomingCompaigns();

    }

    public void getUpcomingCompaigns()
    {

        RecyclerView recyclerView=findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Compaign> compaignList=new ArrayList<>();
        CompaignListAdapter adapter=new CompaignListAdapter(this,compaignList);
        recyclerView.setAdapter(adapter);

        progressLayout.setVisibility(View.VISIBLE);
        LinearLayout layoutEmpty=findViewById(R.id.empty_state);
        NetworkManager manager=NetworkManager.getInstance(this);
        manager.getRequest(APIList.GET_UPCOMING_COMPAIGN_LIST, new IResultData() {
                    @Override
                    public void notifyResult(String result)
                    {
                        APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                        progressLayout.setVisibility(View.GONE);
                        List<Compaign> compaignList= Helper.toList(apiResult.getData(),Compaign.class);
                        if(compaignList.isEmpty())
                        {
                            layoutEmpty.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            layoutEmpty.setVisibility(View.GONE);
                        }
                        adapter.updateList(compaignList);
                    }
                });

                adapter.setOnItemClickListener(new CompaignListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position)
                    {
                        if (!manager.isCompaignAssigned())
                        {
                            Compaign compaign = adapter.getDataList().get(position);
                            startActivity(new Intent(getBaseContext(), ServiceProvidrList.class).putExtra("item", compaign));
                        }
                        else
                        {
                            Toast.makeText(UpcomingCompaigns.this, "Campaign already assigned", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}