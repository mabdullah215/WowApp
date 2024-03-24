package com.mobileapp.wowapp.customer.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomTimePickerDialog extends TimePickerDialog {

    private final static int TIME_PICKER_INTERVAL = 30;
    private TimePicker mTimePicker;
    private final OnTimeSetListener mTimeSetListener;

    public CustomTimePickerDialog(Context context, OnTimeSetListener listener,
                                  int hourOfDay, int minute, boolean is24HourView) {
        super(context, TimePickerDialog.THEME_HOLO_LIGHT, null, hourOfDay,
                minute / TIME_PICKER_INTERVAL, false);
        mTimeSetListener = listener;
    }

    public TimePicker getTimePicker() {
        return mTimePicker;
    }

    private void setTimePickerInterval(TimePicker timePicker) {
        try {

            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
        } catch (Exception e)
        {

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if(which==BUTTON_POSITIVE)
        {
            if (mTimeSetListener != null)
            {
                mTimeSetListener.onTimeSet(mTimePicker,mTimePicker.getHour(),mTimePicker.getMinute());
            }
        }
        super.onClick(dialog, which);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Class<?> superClass = getClass().getSuperclass();
            Field TimePickerField = superClass.getDeclaredField("mTimePicker");
            TimePickerField.setAccessible(true);
            mTimePicker = (TimePicker) TimePickerField.get(this);
            setTimePickerInterval(mTimePicker);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}