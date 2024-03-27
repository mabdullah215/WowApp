package com.mobileapp.wowapp.serviceprovider;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ServiceBankInformation extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_bank_information);
        Spinner bankSpinner=findViewById(R.id.spinner_bank);
        NetworkManager manager=NetworkManager.getInstance(this);
        bankSpinner.setAdapter(manager.getBankListAdapter());
        EditText ibanNumber=findViewById(R.id.et_iban_number);
        EditText etAccountName=findViewById(R.id.et_account_name);
        MaterialButton buttonNext=findViewById(R.id.button_done);
        ibanNumber.setText(manager.getServiceProvider().getIban());
        etAccountName.setText(manager.getServiceProvider().getAccountName());
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ServiceProvider serviceProvider=(ServiceProvider) getIntent().getSerializableExtra("item");
                int bankid=manager.getBankList().get(bankSpinner.getSelectedItemPosition()).getId();
                String iban=ibanNumber.getText().toString().trim();
                String accountName=etAccountName.getText().toString().trim();
                if(iban.isEmpty()||accountName.isEmpty())
                {
                    showToast("Information Missing");
                }
                else
                {
                    serviceProvider.setBankName(String.valueOf(bankid));
                    serviceProvider.setAccountName(accountName);
                    serviceProvider.setIban(iban);
                    uploadData(serviceProvider);
                }
            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(ServiceBankInformation.this);
            }
        });
    }

    public void uploadData(ServiceProvider provider)
    {
        NetworkManager manager=NetworkManager.getInstance(this);
        showLoading();

        HashMap<String,Object> map=new HashMap<>();
        map.put("accountName",provider.getAccountName());
        map.put("name",provider.getName());
        map.put("address",provider.getAddress());
        map.put("businessStartTime",provider.getBusinessStartTime());
        map.put("businessEndTime",provider.getBusinessEndTime());
        map.put("breakStartTime",provider.getBreakStartTime());
        map.put("breakEndTime",provider.getBreakEndTime());
        map.put("city",provider.getCity());
        map.put("businessName",provider.getBusinessName());
        map.put("registrationNo",provider.getRegistrationNo());
        map.put("businessAddress",provider.getBusinessAddress());
        map.put("bankId","1");
        map.put("nationality","Saudia Arabia");
        map.put("birthday",getformattedDate(provider.getBirthday()));
        map.put("iban",provider.getIban());

        if(!provider.getProfilePic().startsWith("https://wow-bucket.s3.us-east-2.amazonaws.com"))
        {
            map.put("profilePic",provider.getProfilePic());
        }
        manager.postRequest(APIList.UPDATE_PROFILE, map, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                hideLoading();
                Gson gson=new Gson();
                APIResultSingle apiResultSingle=gson.fromJson(result,APIResultSingle.class);
                if(apiResultSingle.getStatusCode().equalsIgnoreCase("200"))
                {
                    Toast.makeText(ServiceBankInformation.this, apiResultSingle.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ServiceBankInformation.this, ServiceMainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Animatoo.INSTANCE.animateSlideRight(ServiceBankInformation.this);
                    startActivity(i);
                }
            }
        });

    }

    public String getformattedDate(String str)
    {
        String res="";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleFormat=new SimpleDateFormat("dd-MMM-yyyy");
        try
        {
            Date date=simpleFormat.parse(str);
            date.setSeconds(0);
            date.setMinutes(0);
            date.setHours(0);
            res=format.format(date);
            return res;
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

}