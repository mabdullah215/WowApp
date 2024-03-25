package com.mobileapp.wowapp;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Helper
{
    public static <T> List<T> toList(List<Object> object, Class<T> desiredClass) {
        List<T> transformedList = new ArrayList<>();
        if (object != null) {
            for (Object result : object) {
                String json = new Gson().toJson(result);
                T model = new Gson().fromJson(json, desiredClass);
                transformedList.add(model);
            }
        }
        return transformedList;
    }
}