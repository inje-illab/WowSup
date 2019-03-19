package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseChatWordObj;
import com.seok.seok.wowsup.retrofit.model.ResponseWordChartObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ChatWordChart {
    //서버 요청 URL

    @POST("Help/chatWord.php")
    Call<List<ResponseChatWordObj>> requestChatWord(@Body Map<String, String> wordMap);

    @GET("Help/globalWordChart.php")
    Call<List<ResponseWordChartObj>> requestWordChart();

}