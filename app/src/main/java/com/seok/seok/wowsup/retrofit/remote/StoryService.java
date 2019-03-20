package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StoryService {
    //서버 요청 URL

    @GET("Story/showListStory.php")
    Call<List<ResponseStoryObj>> requestStoryView();

    @GET("Story/searchTag.php")
    Call<List<ResponseStoryObj>> requestStoryTagView(@Query("text") String text);

    @GET("Story/pickStory.php")
    Call<ResponseStoryObj> requestPickStoryView(@Query("storyID") String storyID,
                                                @Query("userID") String userID);

    @GET("Story/recommendTag.php")
    Call<ResponseStoryObj> requestRecommendTag(@Query("number") int number);

    @GET("Story/likeStroy.php")
    Call<ResponseStoryObj> requestStoryLike(@Query("userID") String userID,
                                            @Query("storyID") String storyID);

}
