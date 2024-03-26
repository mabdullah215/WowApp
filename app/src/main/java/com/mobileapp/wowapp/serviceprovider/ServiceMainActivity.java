package com.mobileapp.wowapp.serviceprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.fragments.AnalyticsFragment;
import com.mobileapp.wowapp.customer.fragments.ProfileFragment;
import com.mobileapp.wowapp.serviceprovider.fragments.ServiceCompaignFragment;
import com.mobileapp.wowapp.serviceprovider.fragments.ServiceHomeFragment;
import com.mobileapp.wowapp.serviceprovider.fragments.ServiceProfileFragment;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

public class ServiceMainActivity extends AppCompatActivity {

    ServiceProvider serviceProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_home);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment selectedFragment = null;
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        selectedFragment = new ServiceHomeFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new ServiceProfileFragment();
                        break;
                }

                loadFragment(selectedFragment);
                return true;
            }
        });

        loadFragment(new ServiceHomeFragment());
    }


    public void loadFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.container_view, fragment).commit();
        }
    }

}