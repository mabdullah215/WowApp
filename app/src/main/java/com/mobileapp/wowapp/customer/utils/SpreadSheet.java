package com.mobileapp.wowapp.customer.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobileapp.wowapp.R;


public class SpreadSheet extends BottomSheetDialog
{
    Context mContext;


    public SpreadSheet(@NonNull Context context, int theme)
    {
        super(context, theme);
        mContext=context;
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_spread_sheet, null);
        EditText etTitle=sheetView.findViewById(R.id.et_title);
        EditText etCity=sheetView.findViewById(R.id.et_city);
        TextView tvDuration=sheetView.findViewById(R.id.tv_duration);
        TextView tvStartDate=sheetView.findViewById(R.id.tv_start_date);
        ImageView imgClose=sheetView.findViewById(R.id.img_close);
        TextView tvUpload=sheetView.findViewById(R.id.tv_upload);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });


        getBehavior().setDraggable(false);
        setContentView(sheetView);
    }

}
