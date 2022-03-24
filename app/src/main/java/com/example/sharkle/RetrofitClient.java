package com.example.sharkle;

//retrofit 전체 이용하는 클래스

import androidx.annotation.RestrictTo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
    private static final String BASE_URL = "http://sharkle-server.kro.kr/api/v1/";

    private static RetrofitClient instance = null;
    private static RetrofitAPI retrofitAPI;

    private RetrofitClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //retrofit 초기화
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static RetrofitClient getInstance(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static RetrofitAPI getRetrofitInterface(){
        return retrofitAPI;
    }

}
