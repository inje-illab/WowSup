package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WriteService {
    @GET("Profile/writeStory.php")
    Call<ResponseWriteObj> requestWriteStory(@Query("ID") String id,
                                             @Query("title") String title,
                                             @Query("body") String body,
                                             @Query("image") String image,
                                             @Query("tag1") String tag1,
                                             @Query("tag2") String tag2,
                                             @Query("tag3") String tag3,
                                             @Query("tag4") String tag4,
                                             @Query("tag5") String tag5);
}
