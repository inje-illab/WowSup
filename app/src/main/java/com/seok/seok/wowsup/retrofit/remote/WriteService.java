package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WriteService {
    @POST("test/dbtest.php")
    Call<ResponseWriteObj> requestText(@Query("id") String id,
                                       @Query("text") String text);
}
