package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileService {
    //서버 요청 URL

    @GET("Profile/myProfile.php")
    Call<ResponseProfileObj> requestMyProfile(@Query("userID") String id);

    @GET("Profile/myStory.php")
    Call<List<ResponseStoryObj>> requestMyStory(@Query("ID") String id);

}
