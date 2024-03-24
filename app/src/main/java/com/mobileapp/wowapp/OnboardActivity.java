package com.mobileapp.wowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;

public class OnboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        TextView tvIntro=findViewById(R.id.tv_intro);
        String text = "<font color=#2c2b2a>An</font> <font color=#006cab> advertisement agency</font><font color=#2c2b2a> that provides new and</font><font color=#006cab> unique marketing techniques</font>";
        tvIntro.setText(Html.fromHtml(text));
        MaterialButton loginButton=findViewById(R.id.button_signin);
        MaterialButton registerButton=findViewById(R.id.button_register);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                Animatoo.INSTANCE.animateSlideLeft(OnboardActivity.this);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),RegisterActivity.class));
                Animatoo.INSTANCE.animateSlideLeft(OnboardActivity.this);
            }
        });

    }
}