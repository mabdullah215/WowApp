package com.mobileapp.wowapp.driver.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.mobileapp.wowapp.Helper;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.RequestMenu;
import com.mobileapp.wowapp.driver.RequestDetails;
import com.mobileapp.wowapp.driver.adapter.RequestListAdapter;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.SystemRequest;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends Fragment
{

    RequestListAdapter requestListAdapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_request, container, false);
        RequestMenu menu=view.findViewById(R.id.req_menu);
        RecyclerView recyclerView=view.findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<SystemRequest>requests=new ArrayList<>();
        requestListAdapter=new RequestListAdapter(getActivity(),requests);
        recyclerView.setAdapter(requestListAdapter);

        menu.setUpdateListener(new RequestMenu.PositionUpdateListener() {
            @Override
            public void onPositionUpdate(int pos)
            {
                if(pos==0)
                {
                    recyclerView.setAdapter(requestListAdapter);
                }
                else
                {
                    //recyclerView.setAdapter(offerListAdapter);
                }
            }
        });


        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        TextView tvEmpty=view.findViewById(R.id.tv_empty);
        ConstraintLayout progressLayout=view.findViewById(R.id.progress_layout);
        NetworkManager manager=NetworkManager.getInstance(getContext());
        progressLayout.setVisibility(View.VISIBLE);
        manager.getRequest(APIList.GET_SPECIAL_REQUEST, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                progressLayout.setVisibility(View.GONE);
                APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                List<SystemRequest> list= Helper.toList(apiResult.getData(),SystemRequest.class);
                requestListAdapter.updateList(list);

                if(list.isEmpty())
                {
                    tvEmpty.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvEmpty.setVisibility(View.GONE);
                }
            }
        });

        requestListAdapter.setOnItemClickListener(new RequestListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                SystemRequest request=requestListAdapter.getDataList().get(position);
                startActivity(new Intent(getContext(), RequestDetails.class).putExtra("request",request));
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }
        });
    }
}