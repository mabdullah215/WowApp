package com.mobileapp.wowapp.network;

public class APIList
{
    public static final String BASE_URL="http://3.139.179.214/api/";
   // public static final String BASE_URL="https://8703-203-124-43-140.ngrok-free.app/";
    public static final String LOGIN ="login";
    public static final String REGISTER ="register";
    public static final String UPDATE_PROFILE ="profile/update";
    public static final String UPLOAD_DOCUMENTS ="documents/upload";
    public static final String UPLOAD_DOCUMENTS_APPOINTMENT ="appointments/upload";
    public static final String COMPLETE_APPOINTMENT ="appointments/complete";
    public static final String CANCEL_APPOINTMENT ="appointments/fail";
    public static final String START_DRIVING="driver/start";
    public static final String STOP_DRIVING="driver/driving";
    public static final String DRIVING_ANALYTICS="analytics/driver";
    public static final String ANALYTICS_CUSTOMER="analytics/customer";
    public static final String GET_SPECIAL_REQUEST="special/list";
    public static final String SUBMIT_REQUEST="special/complete";
    public static final String GET_CUSTOMER_COMPAIGN_LIST="campaign/list";
    public static final String DELETE_APPOINTMENT="appointments/delete?";
    public static final String GET_PROFILE="profile";
    public static final String GET_CITY_LIST="city/list";
    public static final String GET_BANK_LIST="bank/list";
    public static final String GET_CAMPAIGN_HISTORY="campaign/list?old=1";
    public static final String GET_DRIVER_CAMPAIGN_HISTORY="driver/history";
    public static final String GET_UPCOMING_COMPAIGN_LIST="campaign/upcoming";
    public static final String GET_APPOINTMENTS="appointments/list/driver";
    public static final String GET_APPOINTMENTS_SHOP="appointments/list/shop";
    public static final String GET_DRIVER_COMPAIGNS_LIST="campaign/assigned";
    public static final String CREATE_COMPAIGN="campaign/add";
    public static final String EXTEND_CAMPAIGN="campaign/update";
    public static final String BOOK_APPOINTMENT="appointments/book";
    public static final String GET_APPOINTMENT="appointments/list";
     // date and sp ID
    public static final String SERVICE_PROVIDER_LIST="campaign/shops";
    public static final String ASSIGNED_DRIVER_LIST="campaign/drivers/detail";

}
