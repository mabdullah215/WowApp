package com.mobileapp.wowapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.tabs.TabLayout;
public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        tabLayout=findViewById(R.id.tabLayoutDots);
        viewPager = findViewById(R.id.viewPagerInformation);
        viewPager.setPageTransformer(true,new DepthTransformation());
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        ImageView imgNext=findViewById(R.id.img_next);
        tabLayout.setupWithViewPager(viewPager);
        TextView tvSkip=findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getBaseContext(),OnboardActivity.class));
                Animatoo.INSTANCE.animateSlideLeft(view.getContext());
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int currentItem=viewPager.getCurrentItem();
                if(currentItem<2)
                {
                    viewPager.setCurrentItem(currentItem+1,true);
                }
                else
                {
                    startActivity(new Intent(getBaseContext(),OnboardActivity.class));
                    Animatoo.INSTANCE.animateSlideLeft(v.getContext());
                    finish();
                }
            }
        });
    }

    public class MyViewPagerAdapter extends PagerAdapter
    {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter()
        {

        }

        public int [] slide_images=
                {
                        R.drawable.ic_compaign,
                        R.drawable.ic_tracking,
                        R.drawable.ic_first
                };

        public String[] slide_desc=
                {
                        "Create a compaign or drive with Wow",
                        "Track your compaigns data in realtime",
                        "Market your brand with us"
                };

        public String[] slide_Title= { "Intelligent Automotive Marketing", "Realtime Tracking", "Market Your Brand"};

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_layout, container, false);
            ImageView image=view.findViewById(R.id.imgview);
            TextView tvTitle=view.findViewById(R.id.tv_heading);
            TextView tvDesc=view.findViewById(R.id.tv_subheading);
            image.setImageResource(slide_images[position]);
            tvDesc.setText(slide_desc[position]);
            tvTitle.setText(slide_Title[position]);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return slide_desc.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}