package com.mobileapp.wowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText etInput=findViewById(R.id.et_input);
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(view.getContext());
            }
        });
        MaterialButton submitButton=findViewById(R.id.button_proceed);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String number=etInput.getText().toString().trim();
                if(!number.isEmpty())
                {
                    startActivity(new Intent(getBaseContext(),SelectRole.class).putExtra("number",number));
                    Animatoo.INSTANCE.animateSlideLeft(RegisterActivity.this);
                }
            }
        });
    }
}