package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StoryService {
    //서버 요청 URL

    @GET("Login/showStory.php")
    Call<List<ResponseStoryObj>> requestStoryView();

}
