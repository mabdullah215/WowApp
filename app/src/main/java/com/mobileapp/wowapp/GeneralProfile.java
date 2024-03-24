package com.mobileapp.wowapp;

import static com.mobileapp.wowapp.network.APIList.BASE_URL;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.mobileapp.wowapp.customer.MainActivity;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.DriverHome;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Dummy;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.ServiceMainActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GeneralProfile extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_profile);
        ImageView imgBack=findViewById(R.id.img_back);
        EditText etFullname=findViewById(R.id.et_fullname);
        EditText etEmail=findViewById(R.id.et_email);
        EditText etPassword=findViewById(R.id.et_password);
        NetworkManager manager=NetworkManager.getInstance(this);
        MaterialButton doneButton=findViewById(R.id.button_done);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                hideKeyboard();
                String fullname=etFullname.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                String pass=etPassword.getText().toString().trim();

                if(fullname.isEmpty()||email.isEmpty()||pass.isEmpty())
                {
                    showToast("Information missing");
                }
                else
                {
                    doneButton.setEnabled(false);
                    String number=getIntent().getStringExtra("number");
                    showLoading();
                    String [] roles={"customer","driver","shop"};
                    int position=getIntent().getIntExtra("position",0);
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("name",fullname);
                    map.put("email",email);
                    map.put("password",pass);
                    map.put("number",number);
                    map.put("type",roles[position]);

                    manager.signupRequest(APIList.REGISTER, map, new IResultData() {
                        @Override
                        public void notifyResult(String result)
                        {
                            doneButton.setEnabled(true);
                            hideLoading();
                            Gson gson=new Gson();
                            APIResultSingle apiResult=gson.fromJson(result,APIResultSingle.class);
                            if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                            {
                                Intent i = new Intent(getBaseContext(),LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                Animatoo.INSTANCE.animateSlideLeft(GeneralProfile.this);
                            }

                            Toast.makeText(GeneralProfile.this, apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(GeneralProfile.this);
            }
        });
    }

    public String getRandomID()
    {
        return UUID.randomUUID().toString();
    }

}