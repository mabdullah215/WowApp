package com.mobileapp.wowapp.database;
import android.content.Context;
import android.content.SharedPreferences;

public class DataSource
{
    private static DataSource single_instance = null;
    Context mContext;
    private SharedPreferences pref;
    private static final String PREF_NAME = "wowapp";
    private static final String USER_TYPE="userType";
    private static final String USER_ID="userID";
    private static final String USER_TOKEN="token";
    private SharedPreferences.Editor editor;
    private int PRIVATE_MODE = 0;

    public static DataSource getInstance(Context context)
    {
        if (single_instance == null) single_instance = new DataSource(context);
        return single_instance;
    }

    public void setUserId(String id)
    {
        editor.putString(USER_ID,id);
        editor.apply();
    }

    public String getUserId()
    {
        return pref.getString(USER_ID,"");
    }

    public void setUserToken(String token)
    {
        editor.putString(USER_TOKEN,token);
        editor.apply();
    }

    public  String getUserToken()
    {
       return pref.getString(USER_TOKEN,"-1");
    }

    public void setUsertype(String type)
    {
        editor.putString(USER_TYPE,type);
        editor.apply();
    }

    public String getUserType()
    {
        return pref.getString(USER_TYPE,"");
    }


    private DataSource(Context context)
    {
        mContext=context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

}
