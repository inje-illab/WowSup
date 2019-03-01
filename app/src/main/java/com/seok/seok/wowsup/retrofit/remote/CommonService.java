package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseChatObj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommonService {
    //서버 요청 URL

    @GET("Common/applyFriend.php")
    Call<ResponseChatObj> requestApplyFriend(@Query("applyer") String applyer,
                            @Query("applyed") String applyed);

}
