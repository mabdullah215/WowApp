package com.mobileapp.wowapp.network;
import static com.mobileapp.wowapp.network.APIList.BASE_URL;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobileapp.wowapp.Helper;
import com.mobileapp.wowapp.customer.model.Bank;
import com.mobileapp.wowapp.customer.model.Customer;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.model.Driver;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.interations.IResultSingle;
import com.mobileapp.wowapp.model.City;
import com.mobileapp.wowapp.model.Compaign;
import com.starry.file_utils.FileUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class NetworkManager
{
    List<City>cityList=new ArrayList<>();
    List<Bank>bankList=new ArrayList<>();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static NetworkManager instance = null;
    OkHttpClient client;
    ArrayAdapter cityListAdapter;
    ArrayAdapter bankListAdapter;
    boolean compaignAssigned=false;
    Context mContext;
    Handler mainHandler;
    Customer customer;
    Driver driver;
    public OkHttpClient getClient() {
        return client;
    }

    public ArrayAdapter getBankListAdapter() {
        return bankListAdapter;
    }

    public List<Bank> getBankList() {
        return bankList;
    }

    public void setCompaignAssigned(boolean compaignAssigned) {
        this.compaignAssigned = compaignAssigned;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public ArrayAdapter getCityListAdapter() {
        return cityListAdapter;
    }

    public boolean isCompaignAssigned() {
        return compaignAssigned;
    }

    public int getCityID(int index)
    {
        return cityList.get(index).getId();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String [] cityTitles()
    {
        List<String>citytitles=new ArrayList<>();
        for(int i=0;i<cityList.size();i++)
        {
            citytitles.add(cityList.get(i).getName());
        }
        return citytitles.toArray(new String[0]);
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList()
    {
        getRequest(APIList.GET_CITY_LIST, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                Gson gson=new Gson();
                APIResult apiResult=gson.fromJson(result,APIResult.class);
                String data=gson.toJson(apiResult.getData());
                TypeToken<ArrayList<City>> token = new TypeToken<ArrayList<City>>() {};
                cityList= gson.fromJson(data, token.getType());
                List<String>citytitles=new ArrayList<>();
                for(int i=0;i<cityList.size();i++)
                {
                    citytitles.add(cityList.get(i).getName());
                }
                cityListAdapter = new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,citytitles);
                cityListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }
        });
    }

    public void setBankList()
    {
        getRequest(APIList.GET_BANK_LIST, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                Gson gson=new Gson();
                APIResult apiResult=gson.fromJson(result,APIResult.class);
                String data=gson.toJson(apiResult.getData());
                TypeToken<ArrayList<Bank>> token = new TypeToken<ArrayList<Bank>>() {};
                bankList= gson.fromJson(data, token.getType());
                List<String>titles=new ArrayList<>();
                for(int i=0;i<bankList.size();i++)
                {
                    titles.add(bankList.get(i).getName());
                }
                bankListAdapter = new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,titles);
                bankListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }
        });
    }

    private NetworkManager(Context context)
    {
        client=new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS).cache(null).build();
        mContext=context;
        mainHandler = new Handler(context.getMainLooper());
        customer=new Customer();
    }

    public void sendRequest(String url, HashMap<String,Object> params, IResult result)
    {
        DataSource source=DataSource.getInstance(mContext);
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for ( Map.Entry<String, Object> entry : params.entrySet())
        {
            if(entry.getKey().contains("File"))
            {
                String timestamp=String.valueOf(System.currentTimeMillis());
                FileUtils fileUtils=new FileUtils(mContext);
                String path=fileUtils.getPath(Uri.parse(entry.getValue().toString()));
                File file=new File(path);
                builder.addFormDataPart(entry.getKey(), timestamp+".jpg", RequestBody.create(MediaType.parse("application/octet-stream"),file));
            }
            else
            {
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder().addHeader("Authorization", "bearer "+source.getUserToken()).url(BASE_URL+url).post(body)
                .cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Log.i("responseAPI",jsonData);
                Gson gson=new Gson();
                APIResult apiResult=gson.fromJson(jsonData,APIResult.class);
                mainHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        result.notifyResult(jsonData,apiResult);
                    }
                });
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }


    public void uploadImages(String url,HashMap<String,String>params,IResultData data)
    {
        DataSource source=DataSource.getInstance(mContext);
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for ( Map.Entry<String, String> entry : params.entrySet())
        {
            String timestamp=String.valueOf(System.currentTimeMillis());
            FileUtils fileUtils=new FileUtils(mContext);
            String path=fileUtils.getPath(Uri.parse(entry.getValue()));
            File file=new File(path);
            builder.addFormDataPart(entry.getKey(), timestamp+".jpg",RequestBody.create(MediaType.parse("image/*"), file));
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().addHeader("Authorization", "Bearer "+source.getUserToken()).url(BASE_URL+url).post(body)
                .cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Log.i("responseAPI",jsonData);
                mainHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        data.notifyResult(jsonData);
                    }
                });
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }
    public void postRequest(String url, HashMap<String,Object> params, IResultData data)
    {
        DataSource source=DataSource.getInstance(mContext);
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for ( Map.Entry<String, Object> entry : params.entrySet())
        {
            if(entry.getKey().contains("design")||entry.getKey().contains("profilePic"))
            {
                String timestamp=String.valueOf(System.currentTimeMillis());
                FileUtils fileUtils=new FileUtils(mContext);
                String path=fileUtils.getPath(Uri.parse(entry.getValue().toString()));
                File file=new File(path);
                builder.addFormDataPart(entry.getKey(), timestamp+".jpg",RequestBody.create(MediaType.parse("image/*"), file));
            }
            else
            {
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().addHeader("Authorization", "Bearer "+source.getUserToken()).url(BASE_URL+url).post(body)
                .cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Log.i("responseAPI",jsonData);
                if(data!=null)
                {
                    mainHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            data.notifyResult(jsonData);
                        }
                    });
                }
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }

    public void postWithoutBodyRequest(String url, IResultData data)
    {
        RequestBody body = RequestBody.create(null, new byte[]{});
        DataSource source=DataSource.getInstance(mContext);
        Request request = new Request.Builder().addHeader("Authorization", "Bearer "+source.getUserToken()).url(BASE_URL+url).post(body)
                .cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Log.i("responseAPI",jsonData);
                if(data!=null)
                {
                    mainHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            data.notifyResult(jsonData);
                        }
                    });
                }
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }

    public void networkRequest(String url, HashMap<String,Object> params, IResultData result)
    {
        DataSource source=DataSource.getInstance(mContext);
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for ( Map.Entry<String, Object> entry : params.entrySet())
        {
            if(entry.getKey().contains("File"))
            {
                String timestamp=String.valueOf(System.currentTimeMillis());
                FileUtils fileUtils=new FileUtils(mContext);
                String path=fileUtils.getPath(Uri.parse(entry.getValue().toString()));
                File file=new File(path);
                builder.addFormDataPart(entry.getKey(), timestamp+".jpg", RequestBody.create(MediaType.parse("application/octet-stream"),file));
            }
            else
            {
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder().addHeader("Authorization", "bearer "+source.getUserToken()).url(BASE_URL+url).post(body)
                .cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Log.i("responseAPI",jsonData);
                mainHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        result.notifyResult(jsonData);
                    }
                });
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }

    public void signupRequest(String url, HashMap<String,Object> params, IResultData result)
    {
        OkHttpClient client=new OkHttpClient();
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for ( Map.Entry<String, Object> entry : params.entrySet())
        {
            builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(BASE_URL+url).post(body)
                .cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Gson gson=new Gson();
                APIResult apiResult=gson.fromJson(jsonData,APIResult.class);
                Log.i("responseAPI",jsonData);
                mainHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        result.notifyResult(jsonData);
                    }
                });
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }

    public void deleteRequest(String url,IResultData data)
    {
        DataSource source=DataSource.getInstance(mContext);
        Request request = new Request.Builder().addHeader("Authorization", "Bearer "+source.getUserToken()).url(BASE_URL+url)
                .cacheControl(CacheControl.FORCE_NETWORK).delete().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Log.i("responseAPI",jsonData);
                mainHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        data.notifyResult(jsonData);
                    }
                });

            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }

    public void tokenRequest(String url,IResultData data)
    {
        RequestBody body = RequestBody.create(null, new byte[]{});
        DataSource source=DataSource.getInstance(mContext);
        Request request = new Request.Builder().addHeader("Authorization", "Bearer "+source.getUserToken()).url(BASE_URL+url).post(body)
                .cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Log.i("responseAPI",jsonData);
                mainHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        data.notifyResult(jsonData);
                    }
                });

            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }
    public void getRequest(String url,IResultData data)
    {
        DataSource source=DataSource.getInstance(mContext);
        Request request = new Request.Builder().addHeader("Authorization", "Bearer "+source.getUserToken()).url(BASE_URL+url)
                .cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String jsonData = response.body().string();
                Log.i("responseAPI",jsonData);
                mainHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        data.notifyResult(jsonData);
                    }
                });

            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }
        });
    }

    public static synchronized NetworkManager getInstance(Context context)
    {
        if (instance == null) {
            instance = new NetworkManager(context);
        }
        return instance;
    }
}
