package com.mobileapp.wowapp.driver;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.fragment.DriverHomeFragment;
import com.mobileapp.wowapp.driver.fragment.DriverProfileFragment;
import com.mobileapp.wowapp.driver.fragment.AppointmentFragment;
import com.mobileapp.wowapp.driver.fragment.RequestFragment;
import com.mobileapp.wowapp.driver.model.Driver;

public class DriverHome extends BaseActivity
{
    Driver currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment selectedFragment = null;
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        selectedFragment = new DriverHomeFragment();
                        break;
                    case R.id.navigation_compaign:
                        selectedFragment = new AppointmentFragment();
                        break;
                    case R.id.nav_requests:
                        selectedFragment = new RequestFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new DriverProfileFragment();
                        break;
                }

                loadFragment(selectedFragment);
                return true;
            }
        });


        loadFragment(new DriverHomeFragment());

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