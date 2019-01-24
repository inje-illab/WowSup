package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseLoginObj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {
    //서버 요청 URL

    @POST("Login/doLogin.php")
    Call<ResponseLoginObj> requestLogin(@Query("ID") String id,
                                        @Query("PW") String pwd);

    @POST("Login/snsLogin.php")
    Call<ResponseLoginObj> requestSnsLogin(@Query("ID") String id,
                                        @Query("PW") String pwd,
                                        @Query("Email") String email);
}
