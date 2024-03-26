package com.mobileapp.wowapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.dd.CircularProgressButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.customer.MainActivity;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.DriverHome;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.interations.IResultSingle;
import com.mobileapp.wowapp.model.LoginModel;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.ServiceMainActivity;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText etEmail=findViewById(R.id.et_email);
        EditText etPass=findViewById(R.id.et_pass);
        CircularProgressButton loginButton=findViewById(R.id.button_progress);
        loginButton.setIndeterminateProgressMode(true);
        NetworkManager manager=NetworkManager.getInstance(this);
        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        ImageView imgBack=findViewById(R.id.img_back);

        etEmail.setText("asalhuwail@gmail.com");
        etPass.setText("a123123");

        RadioGroup group=findViewById(R.id.radio_group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                RadioButton radioButton=(RadioButton)findViewById(i);
                if(radioButton.getText().toString().equalsIgnoreCase("customer"))
                {
                    etEmail.setText("asalhuwail@gmail.com");
                    etPass.setText("a123123");
                }
                else if(radioButton.getText().toString().equalsIgnoreCase("driver"))
                {
                    etEmail.setText("naif.khalidsh8@gmail.com");
                    etPass.setText("Test1234");
                }
                else
                {
                    etEmail.setText("alihuwail@hotmail.com");
                    etPass.setText("a123123");
                }
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(view.getContext());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                hideKeyboard();
                String email=etEmail.getText().toString().trim();
                String pass=etPass.getText().toString().trim();
                if(loginButton.getProgress()==0)
                {
                    if(email.isEmpty()||pass.isEmpty())
                    {
                        showToast("input field is missing");
                    }
                    else
                    {
                        HashMap<String,Object> map=new HashMap<>();
                        map.put("email",email);
                        map.put("password",pass);
                        map.put("deviceMac",android_id);
                        loginButton.setProgress(50);

                        manager.networkRequest(APIList.LOGIN, map, new IResultData() {
                            @Override
                            public void notifyResult(String result)
                            {
                                Gson gson=new Gson();
                                APIResultSingle apiResult=gson.fromJson(result,APIResultSingle.class);
                                if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                                {

                                    DataSource source=DataSource.getInstance(LoginActivity.this);
                                    String data=gson.toJson(apiResult.getData());
                                    LoginModel model=gson.fromJson(data,LoginModel.class);
                                    source.setUserToken(model.getToken());
                                    source.setUsertype(model.getType());
                                    loginButton.setProgress(100);

                                    new Handler().postDelayed(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            if(source.getUserType().equalsIgnoreCase("DRIVER"))
                                            {
                                                Intent intent=new Intent(getBaseContext(),DriverHome.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                            else if(source.getUserType().equalsIgnoreCase("SHOP"))
                                            {
                                                Intent intent=new Intent(getBaseContext(),ServiceMainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                            else if(source.getUserType().equalsIgnoreCase("CUSTOMER"))
                                            {
                                                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                            Animatoo.INSTANCE.animateSlideLeft(LoginActivity.this);
                                            loginButton.setProgress(0);
                                        }
                                    }, 1500);

                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                                    loginButton.setProgress(-1);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run()
                                        {
                                            loginButton.setProgress(0);
                                        }
                                    }, 2000);

                                }
                            }
                        });

                    }
                }
            }
        });

    }


}