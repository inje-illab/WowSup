package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.RespsonseImageObj;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
public interface ImageService {
    @Multipart
    @POST("Story/uploadStroy.php")
    Call<RespsonseImageObj> uploadFile(@Part MultipartBody.Part file,
                                       @Part("file") RequestBody name);
}
