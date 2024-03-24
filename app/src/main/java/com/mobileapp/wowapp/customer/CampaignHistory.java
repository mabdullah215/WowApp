package com.mobileapp.wowapp.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.adapter.CompaignListAdapter;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class CampaignHistory extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_history);
        ImageView imgBack=findViewById(R.id.img_back);
        LinearLayout emptyLayout=findViewById(R.id.empty_state);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(CampaignHistory.this);
            }
        });
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Campaign History");
        RecyclerView recyclerView=findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Compaign>compaigns=new ArrayList<>();
        CompaignListAdapter adapter=new CompaignListAdapter(this,compaigns);
        recyclerView.setAdapter(adapter);
        NetworkManager manager=NetworkManager.getInstance(this);
        showLoading();
        manager.getRequest(APIList.GET_CAMPAIGN_HISTORY, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                Gson gson=new Gson();
                APIResult apiResult= gson.fromJson(result,APIResult.class);
                if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                {
                    hideLoading();
                    String data=gson.toJson(apiResult.getData());
                    TypeToken<ArrayList<Compaign>> token = new TypeToken<ArrayList<Compaign>>() {};
                    List<Compaign> compaigns = gson.fromJson(data, token.getType());
                    adapter.setDataList(compaigns);

                    if(compaigns.isEmpty())
                    {
                        emptyLayout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        emptyLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}