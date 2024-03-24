package com.mobileapp.wowapp.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.OnboardActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.fragments.AnalyticsFragment;
import com.mobileapp.wowapp.customer.fragments.CompaignFragment;
import com.mobileapp.wowapp.customer.fragments.CompaignListFragment;
import com.mobileapp.wowapp.customer.fragments.ProfileFragment;
import com.mobileapp.wowapp.customer.model.Customer;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.DriverHome;
import com.mobileapp.wowapp.driver.model.Driver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
{
    Customer currentUser;
    List<Fragment>fragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_view);
        fragmentList.add(new CompaignListFragment());
        fragmentList.add(new AnalyticsFragment());
        fragmentList.add(new ProfileFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment selectedFragment = null;
                switch (item.getItemId())
                {
                    case R.id.navigation_compaign:

                        selectedFragment = fragmentList.get(0);
                        break;
                    case R.id.navigation_analytics:
                        selectedFragment = fragmentList.get(1);
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = fragmentList.get(2);
                        break;
                }

                loadFragment(selectedFragment);
                return true;
            }
        });

        loadFragment(fragmentList.get(0));
    }

    public Customer getCurrentUser() {
        return currentUser;
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


    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId)
    {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}