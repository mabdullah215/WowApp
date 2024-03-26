package com.mobileapp.wowapp.serviceprovider.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.DriverCampaignHistory;
import com.mobileapp.wowapp.driver.DriverPersonalInformation;
import com.mobileapp.wowapp.driver.model.Driver;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.ServicePersonalInformation;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;


public class ServiceProfileFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_shop_profile, container, false);
        TextView tvLogout=view.findViewById(R.id.tv_logout);
        TextView tvTerms=view.findViewById(R.id.tv_terms);
        TextView tvSettings=view.findViewById(R.id.tv_settings);
        TextView tvPrivacy=view.findViewById(R.id.tv_privacy);
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getContext(), ServicePersonalInformation.class));
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }
        });
        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wow-ooh.com/terms-conditions/"));
                startActivity(browserIntent);
            }
        });

        tvPrivacy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wow-ooh.com/privacy-policy/"));
                startActivity(browserIntent);
            }
        });

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

    return view;
    }

}