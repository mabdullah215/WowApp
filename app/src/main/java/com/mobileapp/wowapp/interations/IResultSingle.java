package com.mobileapp.wowapp.interations;


import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;

public interface IResultSingle
{
    public void notifyResult(String result, APIResultSingle apiResult);
}
