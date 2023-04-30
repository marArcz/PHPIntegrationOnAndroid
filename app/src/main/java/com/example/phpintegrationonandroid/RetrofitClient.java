package com.example.phpintegrationonandroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String SERVER_URL = "http://192.168.1.18/android-retrofit-backend-master/";
    public static Retrofit getRetrofit(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public static AccountAPI getAccountAPI(){
        AccountAPI accountAPI = getRetrofit().create(AccountAPI.class);

        return accountAPI;
    }
}
