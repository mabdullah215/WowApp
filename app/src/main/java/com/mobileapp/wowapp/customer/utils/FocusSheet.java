package com.mobileapp.wowapp.customer.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobileapp.wowapp.R;


public class FocusSheet extends BottomSheetDialog
{
    Context mContext;

    public FocusSheet(@NonNull Context context, int theme) {
        super(context, theme);
        mContext=context;
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_focus_sheet, null);
        ImageView imgClose=sheetView.findViewById(R.id.img_close);
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
