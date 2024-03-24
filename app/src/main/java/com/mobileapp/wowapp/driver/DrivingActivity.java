package com.mobileapp.wowapp.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;

import java.util.HashMap;

public class DrivingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);
        ConstraintLayout progressLayout=findViewById(R.id.progress_layout);
        MaterialButton buttonRequest=findViewById(R.id.button_continue);
        Compaign compaign=(Compaign) getIntent().getSerializableExtra("compaign");
        NetworkManager manager=NetworkManager.getInstance(this);
        buttonRequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressLayout.setVisibility(View.VISIBLE);
                HashMap<String,Object> map=new HashMap<>();
                map.put("newCompaignId",compaign.getId());
                map.put("totalDistance",15);
                manager.sendRequest(APIList.UPDATE_DISTANCE, map, new IResult() {
                    @Override
                    public void notifyResult(String result, APIResult apiResult)
                    {
                        Log.i("itemcompaigndrive",apiResult.getMessage());
                        progressLayout.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}