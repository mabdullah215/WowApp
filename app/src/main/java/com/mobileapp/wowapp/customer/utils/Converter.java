package com.mobileapp.wowapp.customer.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Converter
{

    public static String datePreview(String inputDate)
    {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        String convertedDate="";
        try
        {
            Date date = inputFormat.parse(inputDate);
            convertedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String getDaysDifference(String inputDateStr) {
        // Parse input date string to Date object
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date inputDate;
        try {
            inputDate = sdf.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Return -1 if parsing fails
        }

        // Get current date
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        // Get the difference in milliseconds
        long differenceMillis = inputDate.getTime()-currentDate.getTimeInMillis();

        long hoursDifference = differenceMillis / (1000 * 60 * 60);

        // Convert milliseconds to days
        long daysDifference = differenceMillis / (1000 * 60 * 60 * 24);

        if (daysDifference < 1) {
            // If less than 1 day, return hours
            return hoursDifference + " hours left";
        } else {
            // Otherwise, return days
            return daysDifference + " days left";
        }

    }

    public String getformattedDecimal(double value)
    {
        return String.format("%.2f", value);
    }

    public static String getDatewithDayandTime(String inputDate)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy HH:mm a");
        String convertedDate="";
        try
        {
            Date date = inputFormat.parse(inputDate);
            convertedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String getShortDate(Long timestamp)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return inputFormat.format(calendar.getTime());
    }

    public static String dateServer(String inputDate)
    {

        SimpleDateFormat inputFormat = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String convertedDate="";
        try
        {
            Date date = inputFormat.parse(inputDate);
            convertedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDate;
    }
    public static String getDifferenceInDaysAndMonths(String dateStr1, String dateStr2)
    {
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");

        // Parse the date strings into LocalDate objects
        LocalDate localDate1 = LocalDate.parse(dateStr1, formatter);
        LocalDate localDate2 = LocalDate.parse(dateStr2, formatter);

        // Calculate the difference
        Period period = Period.between(localDate1, localDate2);

        // Extract years, months, and days
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        // Calculate total days
        int totalDays = localDate2.getDayOfYear() - localDate1.getDayOfYear();

        // Construct the result string
        if (totalDays < 31) {
            return totalDays + " days";
        } else {
            return months + " months and " + (totalDays % 30) + " days";
        }
    }

    @SuppressLint("NewApi")
    public static Long getMinMaxDate(String dateString,int val)
    {
        Calendar calendar=Calendar.getInstance();
        Long value=calendar.getTimeInMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dateString);
            calendar.setTimeInMillis(date.getTime());
            calendar.add(Calendar.DATE,val);
            value=calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean compareDate(String inputDateString) {
        // Get the current date and time
        Calendar currentDate = Calendar.getInstance();

        // Parse the input string to Date object
        Date givenDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            givenDate = sdf.parse(inputDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Compare the two dates
        return givenDate != null && currentDate.getTime().after(givenDate);
    }

    public static String convertToCustomFormat(String inputDateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(inputDateTime);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy @ h:mm a", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String shortCustomFormat(String inputDateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(inputDateTime);

            SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String TimeConverter(String str)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String convertedTime="";
        try {
            Date time = inputFormat.parse(str);
            convertedTime = outputFormat.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
}
