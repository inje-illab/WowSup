package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileService {
    //서버 요청 URL

    @GET("Profile/profileImage.php")
    Call<ResponseProfileObj> requestImageURL(@Query("ID") String id);



}
