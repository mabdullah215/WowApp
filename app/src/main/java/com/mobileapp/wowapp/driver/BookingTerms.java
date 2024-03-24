package com.mobileapp.wowapp.driver;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.BookingConfirmationDialog;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Appointment;
import com.mobileapp.wowapp.model.Shop;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BookingTerms extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_terms);
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Terms of Agreement");
        String date=getIntent().getStringExtra("date");
        TextView tvDate=findViewById(R.id.tv_date_time);
        tvDate.setText(Converter.convertToCustomFormat(date));
        String location=getIntent().getStringExtra("location");
        TextView tvLocation=findViewById(R.id.tv_location);
        tvLocation.setText(location);
        NetworkManager manager=NetworkManager.getInstance(this);
        MaterialButton doneButton=findViewById(R.id.button_done);
        CheckBox checkBox=findViewById(R.id.checkbox);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(checkBox.isChecked())
                {
                    showLoading();
                    HashMap<String,Object>map=(HashMap<String, Object>) getIntent().getSerializableExtra("map");
                    manager.postRequest(APIList.BOOK_APPOINTMENT, map, new IResultData() {
                        @Override
                        public void notifyResult(String result)
                        {
                            hideLoading();
                            try
                            {

                                JSONObject appointmentobject=new JSONObject(result).getJSONObject("data").getJSONObject("appointment");
                                JSONObject shopobject=new JSONObject(result).getJSONObject("data").getJSONObject("shop");
                                Appointment appointment=new Gson().fromJson(appointmentobject.toString(),Appointment.class);
                                Shop shop=new Gson().fromJson(shopobject.toString(),Shop.class);
                                showBookingDialog(appointment,shop);
                            }
                            catch (JSONException e)
                            {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }

            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(BookingTerms.this);
            }
        });


    }

    public void showBookingDialog(Appointment appointment, Shop shop)
    {
        BookingConfirmationDialog dialog=new BookingConfirmationDialog(this,R.style.AppBottomSheetDialogTheme,appointment,shop);
        dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.setCancelable(false);
        dialog.show();
    }
}