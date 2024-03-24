package com.mobileapp.wowapp.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.DriverHome;
import com.mobileapp.wowapp.model.Appointment;
import com.mobileapp.wowapp.model.Shop;


public class BookingConfirmationDialog extends BottomSheetDialog
{
    Context mContext;

    public BookingConfirmationDialog(@NonNull Context context, int theme, Appointment appointment, Shop shop)
    {
        super(context, theme);
        mContext=context;
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_booking_confirm, null);
        TextView tvDate=sheetView.findViewById(R.id.tv_date_time);
        TextView tvLocation=sheetView.findViewById(R.id.tv_location);
        MaterialButton buttonDone=sheetView.findViewById(R.id.button_done);
        String date=Converter.getDatewithDayandTime(appointment.getAppointment_time());
        String location=shop.getName()+","+shop.getCity();
        tvDate.setText(date);
        tvLocation.setText(location);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(mContext, DriverHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
                Animatoo.INSTANCE.animateSlideRight(mContext);
            }
        });
        getBehavior().setDraggable(false);
        setContentView(sheetView);
    }
}
