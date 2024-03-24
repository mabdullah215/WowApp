package com.mobileapp.wowapp.customer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobileapp.wowapp.customer.AnalyticsDetails;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.adapter.AnalyticsListAdapter;
import com.mobileapp.wowapp.customer.model.Analytics;

import java.util.ArrayList;
import java.util.List;


public class AnalyticsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_analytics, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        List<Analytics>list=new ArrayList<>();
        list.add(new Analytics("Distance Graph","The chart shows daily kms campaign has covered.",1));
        list.add(new Analytics("Impressions Graph","The chart shows your campaign's daily impressions.",2));

        AnalyticsListAdapter adapter=new AnalyticsListAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AnalyticsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}