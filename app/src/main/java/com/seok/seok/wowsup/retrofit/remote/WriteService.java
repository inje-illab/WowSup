package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
//글쓰기 서비스 인터페이스
public interface WriteService {
    @GET("Profile/writeStory.php")
    Call<ResponseWriteObj> requestWriteStory(@Query("userID") String id,
                                             @Query("title") String title,
                                             @Query("body") String body,
                                             @Query("image") String image,
                                             @Query("tag1") String tag1,
                                             @Query("tag2") String tag2,
                                             @Query("tag3") String tag3,
                                             @Query("tag4") String tag4,
                                             @Query("tag5") String tag5);

    @Multipart
    @POST("Story/uploadStory.php")
    Call<ResponseWriteObj> requestImageWriteStory(@Query("userID") String id,
                                                  @Query("title") String title,
                                                  @Query("body") String body,
                                                  @Query("tag1") String tag1,
                                                  @Query("tag2") String tag2,
                                                  @Query("tag3") String tag3,
                                                  @Query("tag4") String tag4,
                                                  @Query("tag5") String tag5,
                                                  @Part MultipartBody.Part file,
                                                  @Part("file") RequestBody name);
}
