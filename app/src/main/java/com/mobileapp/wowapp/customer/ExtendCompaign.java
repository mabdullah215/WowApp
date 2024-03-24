package com.mobileapp.wowapp.customer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.adapter.CompaignTitleListAdapter;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendCompaign extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_compaign);
        ImageView imgClose=findViewById(R.id.img_close);
        NetworkManager manager=NetworkManager.getInstance(this);
        EditText etCarNum=findViewById(R.id.et_num_cars);
        MaterialButton buttonExtend=findViewById(R.id.button_done);
        Spinner durationSpinner=findViewById(R.id.spinner_extension);
        String [] durationList={"1 Month","2 Months","3 Months","4 Months","5 Months"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,durationList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(aa);
        Spinner campaignSpinner=findViewById(R.id.spinner_compaign);
        ArrayList<Compaign> campaigns = (ArrayList<Compaign>) getIntent().getSerializableExtra("list");
        List<String>campaignTitle=new ArrayList<>();
        for(int i=0;i<campaigns.size();i++)
        {
            campaignTitle.add(campaigns.get(i).getName());
        }
        ArrayAdapter spineradapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,campaignTitle);
        spineradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campaignSpinner.setAdapter(spineradapter);

        buttonExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String numofCars=etCarNum.getText().toString().trim();
                Compaign compaign=campaigns.get(campaignSpinner.getSelectedItemPosition());
                int campaignId=compaign.getId();
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(getDateFromString(compaign.getEndTime()).getTime());
                calendar.add(Calendar.MONTH,durationSpinner.getSelectedItemPosition()+1);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String endTime=simpleDateFormat.format(calendar.getTime());
                if(numofCars.isEmpty())
                {
                    showToast("Please enter number of cars");
                }
                else
                {
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("campaignId",campaignId);
                    map.put("endTime",endTime);
                    map.put("noOfCars",numofCars);
                    showLoading();
                    manager.postRequest(APIList.EXTEND_CAMPAIGN, map, new IResultData() {
                        @Override
                        public void notifyResult(String result)
                        {
                            hideLoading();
                            Gson gson=new Gson();
                            APIResult apiResult=gson.fromJson(result,APIResult.class);
                            if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                            {
                                Toast.makeText(ExtendCompaign.this,apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                            else
                            {
                                showToast(apiResult.getMessage());
                            }
                        }
                    });
                }
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(ExtendCompaign.this);
            }
        });
    }

    private void printHashMap(HashMap<String, Object> hashMap) {
        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }

    public Date getDateFromString(String str)
    {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date date=format.parse(str);
            return date;
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

}