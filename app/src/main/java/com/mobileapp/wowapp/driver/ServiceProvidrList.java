package com.mobileapp.wowapp.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobileapp.wowapp.Helper;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.driver.adapter.ServiceProvierListAdapter;
import com.mobileapp.wowapp.driver.model.ServiceProviderCompaign;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

import java.util.List;

public class ServiceProvidrList extends AppCompatActivity {

    ConstraintLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providr_list);
        progressLayout = findViewById(R.id.progress_layout);
        RecyclerView recyclerView = findViewById(R.id.data_list);
        TextView tvTitle = findViewById(R.id.tv_title);
        int requestType=getIntent().getIntExtra("request_type",1);
        TextView tvSubtitle = findViewById(R.id.tv_subtitle);
        ImageView imgBack = findViewById(R.id.img_back);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressLayout.setVisibility(View.VISIBLE);
        Compaign compaign = (Compaign) getIntent().getSerializableExtra("item");
        tvTitle.setText(compaign.getName());
        tvSubtitle.setText(Converter.datePreview(compaign.getStartTime()) + " - " + Converter.datePreview(compaign.getEndTime()));
        NetworkManager manager = NetworkManager.getInstance(this);
        manager.getRequest(APIList.SERVICE_PROVIDER_LIST + "?campaignId=" + compaign.getId(), new IResultData() {
            @Override
            public void notifyResult(String result)
            {

                progressLayout.setVisibility(View.GONE);
                APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                List<ServiceProvider> list = Helper.toList(apiResult.getData(), ServiceProvider.class);
                ServiceProvierListAdapter adapter = new ServiceProvierListAdapter(getBaseContext(), list);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new ServiceProvierListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ServiceProvider item = adapter.getDataList().get(position);
                        startActivity(new Intent(getBaseContext(),CompaignBooking.class)
                                .putExtra("request_type",requestType)
                                .putExtra("campaign",compaign)
                                .putExtra("item", item));
                    }
                });
            }
        });
    }

}