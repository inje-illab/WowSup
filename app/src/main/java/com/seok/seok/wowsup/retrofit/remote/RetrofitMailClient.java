package com.seok.seok.wowsup.retrofit.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//메일을 보내어 서버와 통신할 빌드 클래스
public class RetrofitMailClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String url){
        Gson gson = new GsonBuilder().setLenient().create();
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
