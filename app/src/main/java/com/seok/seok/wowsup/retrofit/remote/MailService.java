package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseMailObj;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MailService {
    //서버 요청 URL

    @GET("emailAuthentication.php")
    Call<ResponseMailObj> requestEmailAuthentication(@Query("userEmail") String email,
                                                     @Query("rand") int rand);
    
}