package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseChatObj;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//채팅 서비스 인터페이스
public interface ChatService {
    //서버 요청 URL
    @GET("Chat/chatList.php")
    Call<List<ResponseChatObj>> requestChatFriend(@Query("ID") String id);

}
