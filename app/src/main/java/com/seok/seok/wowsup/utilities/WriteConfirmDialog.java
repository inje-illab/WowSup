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

//스토리를 업로드 하기위한 다이얼로그 클래스
public class WriteConfirmDialog extends Dialog implements View.OnClickListener {
    private static int LAYOUT;
    private Context context;
    private Button btnYes, btnNo;
    private String title, body, image, tag1, tag2, tag3, tag4, tag5;    // 스토리 업로드
    private Common.ProgressbarDialog progressbarDialog;

    public WriteConfirmDialog(Context context) {
        super(context);
        LAYOUT = R.layout.layout_write_confirm_dialog;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        // 프로그래스 바 생성
        progressbarDialog = new Common.ProgressbarDialog(context);
        btnYes = findViewById(R.id.dialog_notice_btn_yes);
        btnNo = findViewById(R.id.dialog_notice_btn_no);
        // Yes 버튼을 눌렀을 경우 서버와 통신 시작
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalWowToken.getInstance().getToken() >= 1) {
                    if (Common.option == 11) {
                        progressbarDialog.callFunction();
                        ApiUtils.getWriteService().requestWriteStory(GlobalWowToken.getInstance().getId(), title, body, image, tag1, tag2, tag3, tag4, tag5).enqueue(new Callback<ResponseWriteObj>() {
                            @Override
                            public void onResponse(Call<ResponseWriteObj> call, Response<ResponseWriteObj> response) {
                                Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show();
                                dismiss();
                                ((Activity) context).finish();
                                progressbarDialog.endWork();
                            }

                            @Override
                            public void onFailure(Call<ResponseWriteObj> call, Throwable t) {
                                Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show();
                                dismiss();
                                ((Activity) context).finish();
                                progressbarDialog.endWork();
                            }
                        });
                    } else if (Common.option == 10) {
                        uploadFile();
                    }
                } else {
                    context.startActivity(new Intent(context.getApplicationContext(), StoreActivity.class));
                }
            }
        });
        //No 버튼을 눌렀을 경우 다이얼로그 종료
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

    //업로드를 하기위해 필드값에 데이터 저장
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
        // 사진 업로드를 하기위함
        progressbarDialog.callFunction();
        File file = new File(this.image);
        //사진 업로드 생성
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", GlobalWowToken.getInstance().getId() + file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        //서버 통신 시작
        Call<ResponseWriteObj> call = ApiUtils.getWriteService().requestImageWriteStory(GlobalWowToken.getInstance().getId(), title, body, tag1, tag2, tag3, tag4, tag5, fileToUpload, filename);
        call.enqueue(new Callback<ResponseWriteObj>() {
            @Override
            public void onResponse(Call<ResponseWriteObj> call, Response<ResponseWriteObj> response) {
                Log.d("WriteConfirmDialog_HTTP_UPLOAD", "HTTP Transfer Success");
                if (response.isSuccessful()) {
                    Log.d("WriteConfirmDialog_HTTP_UPLOAD", "HTTP Response Success");
                    ResponseWriteObj body = response.body();
                    if (body.getState() == 1) {
                        Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show();
                        dismiss();
                        ((Activity) context).finish();
                        progressbarDialog.endWork();
                    } else if (body.getState() == 0) {
                        Toast.makeText(getApplicationContext(), body.getMe(), Toast.LENGTH_SHORT).show();
                    } else if (body.getState() == 2) {
                        Toast.makeText(context, "Upload Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<ResponseWriteObj> call, Throwable t) {
                Log.d("WriteConfirmDialog_HTTP_UPLOAD", "HTTP Transfer Failed");
                progressbarDialog.endWork();
            }
        });
    }
}
