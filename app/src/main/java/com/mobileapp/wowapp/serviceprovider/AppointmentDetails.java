package com.mobileapp.wowapp.serviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.serviceprovider.model.ShopAppointment;
import com.squareup.picasso.Picasso;

public class AppointmentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        ShopAppointment appointment=(ShopAppointment) getIntent().getSerializableExtra("appointment");
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Appointment Details");
        ImageView imgBack=findViewById(R.id.img_back);
        TextView tvDriverName=findViewById(R.id.tv_driver_name);
        tvDriverName.setText(appointment.getDriver().getName());
        TextView tvTimings=findViewById(R.id.tv_time);
        tvTimings.setText(Converter.shortCustomFormat(appointment.getAppointment_time()));
        TextView tvAppointmentType=findViewById(R.id.tv_appointment_tyoe);
        TextView tvCampaignName=findViewById(R.id.tv_campaign);
        tvCampaignName.setText(appointment.getCampaign().getName());
        ImageView imgDriver=findViewById(R.id.img_driver_name);
        ImageView imgCampaign=findViewById(R.id.img_campaign);
        Picasso picasso=Picasso.get();
        if(appointment.getSticker_request()==1)
        {
            tvAppointmentType.setText("Sticker Apply");
        }
        else
        {
            tvAppointmentType.setText("Sticker Remove");
        }
        picasso.load(appointment.getDriver().getProfile_pic()).into(imgDriver);
        picasso.load(appointment.getCampaign().getDesign()).into(imgCampaign);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(AppointmentDetails.this);
            }
        });
    }
}