package com.mobileapp.wowapp.driver.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobileapp.wowapp.OnboardActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.MainActivity;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.DriverCampaignHistory;
import com.mobileapp.wowapp.driver.DriverPersonalInformation;
import com.mobileapp.wowapp.driver.model.Driver;
import com.mobileapp.wowapp.network.NetworkManager;


public class DriverProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_driver_profile, container, false);
        TextView tvLogout=view.findViewById(R.id.tv_logout);
        TextView tvCampaignHistory=view.findViewById(R.id.tv_compaign_history);
        tvCampaignHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getContext(), DriverCampaignHistory.class));
                Animatoo.INSTANCE.animateSlideLeft(view.getContext());
            }
        });

        TextView tvSettings=view.findViewById(R.id.tv_settings);
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                NetworkManager manager=NetworkManager.getInstance(getContext());
                startActivity(new Intent(getContext(), DriverPersonalInformation.class).putExtra("driver",manager.getDriver()));
                Animatoo.INSTANCE.animateSlideLeft(view.getContext());
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                tvLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Logout")
                                .setMessage("Are you sure you want to logout?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        DataSource source=DataSource.getInstance(getActivity());
                                        source.setUsertype("");
                                        Intent i = new Intent(getContext(), OnboardActivity.class);
                                        startActivity(i);
                                        getActivity().finish();
                                        Animatoo.INSTANCE.animateSlideRight(getActivity());
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(R.drawable.ic_logo)
                                .show();
                    }
                });
            }
        });
        return view;
    }
}