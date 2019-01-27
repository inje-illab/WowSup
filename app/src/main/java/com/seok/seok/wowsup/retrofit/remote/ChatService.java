package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseChatObj;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatService {
    //서버 요청 URL

    @POST("Login/chatList.php")
    Call<List<ResponseChatObj>> requestChatFriend(@Query("ID") String id);

}
