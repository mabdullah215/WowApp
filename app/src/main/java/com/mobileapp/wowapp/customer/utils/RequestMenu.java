package com.mobileapp.wowapp.customer.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobileapp.wowapp.R;


public class RequestMenu extends RelativeLayout
{
    TextView requestButton,offerButton;
    PositionUpdateListener updateListener;
    int selected=1;
    Context mContext;

    public interface PositionUpdateListener
    {
        void onPositionUpdate(int pos);
    }

    public RequestMenu(Context context)
    {
        super(context);
        mContext=context;
        initView();
    }


    public RequestMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView();
    }

    public RequestMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext=context;
        initView();
    }

    public RequestMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView();
    }
    private void initView()
    {
        inflate(getContext(), R.layout.menu_request, this);
        requestButton=findViewById(R.id.button_request);
        offerButton=findViewById(R.id.button_offers);
        selected=0;

        requestButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selected=0;
                selectOption(selected);
            }
        });


        offerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {   selected=1;
                selectOption(selected);
            }
        });


        selectOption(selected);
    }


    public void setUpdateListener(PositionUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void selectOption(int num)
    {
        if(num==0)
        {
            requestButton.getBackground().setTint(mContext.getColor(R.color.purple_200));
            requestButton.setTextColor(mContext.getColor(R.color.white));
            offerButton.getBackground().setTint(mContext.getColor(R.color.light_grey));
            offerButton.setTextColor(mContext.getColor(R.color.text_color_default));
        }
        else
        {
            requestButton.getBackground().setTint(mContext.getColor(R.color.light_grey));
            requestButton.setTextColor(mContext.getColor(R.color.text_color_default));
            offerButton.getBackground().setTint(mContext.getColor(R.color.purple_200));
            offerButton.setTextColor(mContext.getColor(R.color.white));
        }

        if(updateListener!=null)
        {
            updateListener.onPositionUpdate(num);
        }
    }

    public int getSelected() {
        return selected;
    }
}
