package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseLoginObj;
import com.seok.seok.wowsup.retrofit.model.ResponseRegisterObj;


import retrofit2.Call;
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
                                        @Query("Email") String email,
                                           @Query("Type") int type);

    @POST("Login/register.php")
    Call<ResponseRegisterObj> requestRegister(@Query("ID") String id,
                                           @Query("PW") String pwd,
                                           @Query("Email") String email);

    @POST("Login/confirmID.php")
    Call<ResponseRegisterObj> requestConfirmID(@Query("ID") String id);

}
