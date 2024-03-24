package com.mobileapp.wowapp.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.FocusSheet;

public class AnalyticsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_details);
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Analytics Details");
        MaterialButton filterButton=findViewById(R.id.button_filter);
        FocusSheet focusSheet=new FocusSheet(AnalyticsDetails.this,R.style.AppBottomSheetDialogTheme);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                focusSheet.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
                focusSheet.show();
            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(AnalyticsDetails.this);
            }
        });
    }
}