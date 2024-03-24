package com.mobileapp.wowapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.adapter.CityListAdapter;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.City;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class CityList extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Available Cities");
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(CityList.this);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.data_list);
        MaterialButton buttonDone=findViewById(R.id.button_done);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NetworkManager manager=NetworkManager.getInstance(this);
        showLoading();
        manager.getRequest(APIList.GET_CITY_LIST, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                hideLoading();
                Gson gson=new Gson();
                List<City> cities=new ArrayList<>();
                APIResult apiResult=gson.fromJson(result,APIResult.class);
                cities= Helper.toList(apiResult.getData(),City.class);
                CityListAdapter adapter=new CityListAdapter(CityList.this,cities);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new CityListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position)
                    {
                        adapter.selectPosition(position);
                    }
                });

                buttonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        City city=adapter.getDataList().get(adapter.getSelected());
                        Intent intent=new Intent();
                        intent.putExtra("title",city.getName());
                        setResult(RESULT_OK);
                        finish();
                    }
                });

            }
        });


    }
}