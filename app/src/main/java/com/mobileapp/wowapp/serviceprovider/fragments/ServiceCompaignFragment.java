package com.mobileapp.wowapp.serviceprovider.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.adapter.CompaignListAdapter;
import com.mobileapp.wowapp.model.Compaign;

import java.util.ArrayList;


public class ServiceCompaignFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_service_provider_compaign, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Compaign> compaignList=new ArrayList<>();
        CompaignListAdapter adapter=new CompaignListAdapter(getContext(),compaignList);
        recyclerView.setAdapter(adapter);
        TextView tvEmpty=view.findViewById(R.id.tv_empty);
        return view;
    }

}