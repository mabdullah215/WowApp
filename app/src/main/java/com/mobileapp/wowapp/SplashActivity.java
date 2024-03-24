package com.mobileapp.wowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.mobileapp.wowapp.customer.MainActivity;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.DriverHome;
import com.mobileapp.wowapp.serviceprovider.ServiceMainActivity;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class  SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DataSource source=DataSource.getInstance(this);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                if(source.getUserType().equalsIgnoreCase("CUSTOMER"))
                {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else if(source.getUserType().equalsIgnoreCase("DRIVER"))
                {
                    Intent i = new Intent(SplashActivity.this, DriverHome.class);
                    startActivity(i);
                }
                else if(source.getUserType().equalsIgnoreCase("SHOP"))
                {
                    Intent i = new Intent(SplashActivity.this, ServiceMainActivity.class);
                    startActivity(i);
                }

                else
                {
                    Intent i = new Intent(SplashActivity.this,WelcomeActivity.class);
                    startActivity(i);
                }

                Animatoo.INSTANCE.animateSlideLeft(SplashActivity.this);
                finish();
            }
        };
        new Timer().schedule(task, 3000);
    }
}