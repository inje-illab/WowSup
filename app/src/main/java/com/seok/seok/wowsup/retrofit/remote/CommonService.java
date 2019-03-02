package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseChatObj;
import com.seok.seok.wowsup.retrofit.model.ResponseCommonObj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommonService {
    //서버 요청 URL

    @GET("Common/applyFriend.php")
    Call<ResponseCommonObj> requestApplyFriend(@Query("applyer") String applyer,
                                               @Query("applyed") String applyed);

    @GET("Common/applyedFriend.php")
    Call<List<ResponseCommonObj>> requestApplyedFriend(@Query("userID") String userID);

    @GET("Common/confirmFriend.php")
    Call<ResponseCommonObj> requestConfirmFriend(@Query("userID1") String userID1,
                                                 @Query("userID2") String userID2);

}
