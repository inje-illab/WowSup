package com.seok.seok.wowsup.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                ApiUtils.getWriteService().requestWriteStory(GlobalWowToken.getInstance().getId(), title, body,image, tag1, tag2, tag3, tag4, tag5).enqueue(new Callback<ResponseWriteObj>() {
                    @Override
                    public void onResponse(Call<ResponseWriteObj> call, Response<ResponseWriteObj> response) {

                        Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show();
                        dismiss();
                        ((Activity)context).finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseWriteObj> call, Throwable t) {
                        Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show();
                        dismiss();
                        ((Activity)context).finish();
                    }
                });
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
}
