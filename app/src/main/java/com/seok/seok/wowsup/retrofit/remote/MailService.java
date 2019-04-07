package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseMailObj;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//메일 서비스 인터페이스
public interface MailService {
    //서버 요청 URL

    @GET("emailAuthentication.php")
    Call<ResponseMailObj> requestEmailAuthentication(@Query("userEmail") String email,
                                                     @Query("rand") int rand);
    @GET("forgetID.php")
    Call<ResponseMailObj> requestFindID(@Query("userEmail") String email);

    @GET("forgetPW.php")
    Call<ResponseMailObj> requestFindPW(@Query("userID") String id,
                                        @Query("userEmail") String email);
}