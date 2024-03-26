package com.mobileapp.wowapp.customer.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.mobileapp.wowapp.OnboardActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.BusinessProfile;
import com.mobileapp.wowapp.customer.CampaignHistory;
import com.mobileapp.wowapp.customer.model.Customer;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        TextView tvLogout=view.findViewById(R.id.tv_logout);
        TextView tvAccountSettings=view.findViewById(R.id.tv_settings);
        TextView tvDeleteAccount=view.findViewById(R.id.tv_delete_account);
        TextView tvCampaignHistory=view.findViewById(R.id.tv_compaign_history);
        TextView tvTerms=view.findViewById(R.id.tv_terms);
        TextView tvPrivacy=view.findViewById(R.id.tv_privacy);
        ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            updateProfile();
                        }
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
        tvAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getContext(),BusinessProfile.class);
                launchSomeActivity.launch(intent);
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }
        });
        tvCampaignHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getContext(), CampaignHistory.class));
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }
        });
        tvDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                new AlertDialog.Builder(getContext())
                        .setTitle("Delete user account")
                        .setMessage("Are you sure you want to delete your account?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_logo)
                        .show();

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
                                getActivity().finish();
                                Intent i = new Intent(getContext(), OnboardActivity.class);
                                startActivity(i);
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

    public void updateProfile()
    {
        NetworkManager manager=NetworkManager.getInstance(getContext());
        manager.getRequest(APIList.GET_PROFILE, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                Gson gson=new Gson();
                APIResultSingle apiResult=gson.fromJson(result,APIResultSingle.class);
                if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                {
                    String data=gson.toJson(apiResult.getData());
                    Customer customer=gson.fromJson(data,Customer.class);
                    manager.setCustomer(customer);
                }
            }});
    }
}