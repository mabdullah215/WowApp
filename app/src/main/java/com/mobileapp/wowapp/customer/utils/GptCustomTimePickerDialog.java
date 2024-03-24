package com.mobileapp.wowapp.customer.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

public class GptCustomTimePickerDialog extends TimePickerDialog {

    private int interval;

    public GptCustomTimePickerDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listener, hourOfDay, minute, is24HourView);
        this.interval = 30; // Set the interval to 30 minutes
        fixSpinner(context, hourOfDay, minute, is24HourView);
    }

    private void fixSpinner(Context context, int hourOfDay, int minute, boolean is24HourView) {
        if (!is24HourView) {
            // If it's not 24-hour view, we need to adjust the spinner values
            try {
                TimePicker timePicker = (TimePicker) findField(TimePickerDialog.class, TimePicker.class, "mTimePicker").get(this);
                setTimePickerInterval(timePicker);
                /*NumberPicker minuteSpinner = (NumberPicker) findField(TimePicker.class, NumberPicker.class, "mMinuteSpinner").get(timePicker);
                minuteSpinner.setMinValue(0);
                minuteSpinner.setMaxValue(1);
                minuteSpinner.setDisplayedValues(new String[]{"00", "30"});*/
            } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }


    private void setTimePickerInterval(TimePicker timePicker) {
        try {
            int TIME_PICKER_INTERVAL = 5;
            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
        } catch (Exception e) {

        }
    }


    private static java.lang.reflect.Field findField(Class<?> klass, Class<?> type, String name) throws NoSuchFieldException {
        for (java.lang.reflect.Field field : klass.getDeclaredFields()) {
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                return field;
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found for class " + type);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        super.onTimeChanged(view, hourOfDay, minute);
        // Round the minute to the nearest interval
        int minuteAdjusted = minute / interval * interval;
        if (minute != minuteAdjusted) {
            minute = minuteAdjusted;
            view.setCurrentMinute(minute);
        }
    }
}