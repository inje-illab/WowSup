package com.seok.seok.wowsup.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.StoreActivity;
import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;
import com.seok.seok.wowsup.retrofit.model.RespsonseImageObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class WriteConfirmDialog extends Dialog implements View.OnClickListener {
    private static int LAYOUT;
    private Context context;
    private Button btnYes, btnNo;
    private String title, body, image, tag1, tag2, tag3, tag4, tag5;    // 스토리 업로드

    public WriteConfirmDialog(Context context) {
        super(context);
        LAYOUT = R.layout.layout_write_confirm_dialog;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        btnYes = findViewById(R.id.dialog_notice_btn_yes);
        btnNo = findViewById(R.id.dialog_notice_btn_no);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalWowToken.getInstance().getToken() >= 1) {
                    if (Common.option == 11)
                        ApiUtils.getWriteService().requestWriteStory(GlobalWowToken.getInstance().getId(), title, body, image, tag1, tag2, tag3, tag4, tag5).enqueue(new Callback<ResponseWriteObj>() {
                            @Override
                            public void onResponse(Call<ResponseWriteObj> call, Response<ResponseWriteObj> response) {
                                Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show();
                                dismiss();
                                ((Activity) context).finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseWriteObj> call, Throwable t) {
                                Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show();
                                dismiss();
                                ((Activity) context).finish();
                            }
                        });
                    else if(Common.option == 10){
                        uploadFile();
                    }
                } else {
                    context.startActivity(new Intent(context.getApplicationContext(), StoreActivity.class));
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void requestStoryUpload(String title, String body, String image, String tag1, String tag2, String tag3, String tag4, String tag5) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
        this.tag5 = tag5;
    }

    public void uploadFile() {
        File file = new File(this.image);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", GlobalWowToken.getInstance().getId() + file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<ResponseWriteObj> call = ApiUtils.getWriteService().requestImageWriteStory(GlobalWowToken.getInstance().getId(), title, body, tag1, tag2, tag3, tag4, tag5, fileToUpload, filename);
        call.enqueue(new Callback<ResponseWriteObj>() {
            @Override
            public void onResponse(Call<ResponseWriteObj> call, Response<ResponseWriteObj> response) {
                Log.d("WriteConfirmDialog_HTTP_UPLOAD", "HTTP Transfer Success");
                if (response.isSuccessful()) {
                    Log.d("WriteConfirmDialog_HTTP_UPLOAD", "HTTP Response Success");
                    ResponseWriteObj body = response.body();
                    if (body.getState() == 1) {
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                        Log.d("asdf", body.getMe());
                    } else if (body.getState() == 0) {
                        Toast.makeText(getApplicationContext(), body.getMe(), Toast.LENGTH_SHORT).show();
                    } else if (body.getState() == 2) {
                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<ResponseWriteObj> call, Throwable t) {
                Log.d("WriteConfirmDialog_HTTP_UPLOAD", "HTTP Transfer Failed");
            }
        });
    }
}
