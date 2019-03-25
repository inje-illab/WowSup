package com.seok.seok.wowsup.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseCommonObj;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryDeleteDialog extends Dialog implements View.OnClickListener {
    private static int LAYOUT;
    private Context context;
    private String userID, otherUserID, storyID;
    private Button btnYes, btnNo;

    public StoryDeleteDialog(Context context) {
        super(context);
        LAYOUT = R.layout.layout_story_delete_dialog;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        btnYes = findViewById(R.id.dialog_story_delete_btn_yes);
        btnNo = findViewById(R.id.dialog_story_delete_btn_no);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_story_delete_btn_yes:
                ApiUtils.getStoryService().requestDeleteStory(userID, storyID).enqueue(new Callback<ResponseStoryObj>() {
                    @Override
                    public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {
                        if (response.isSuccessful()) {
                            ResponseStoryObj body = response.body();
                            if(body.getState() == 1){
                                Toast.makeText(context, "The post has been deleted!", Toast.LENGTH_SHORT).show();
                                ((Activity)context).finish();
                            }
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseStoryObj> call, Throwable t) {
                        dismiss();
                    }
                });
                break;
            case R.id.dialog_story_delete_btn_no:
                dismiss();
                break;
        }
    }

    public boolean requestStoryDelete(String userID, String otherUserID, String storyID) {
        if (userID.equals(otherUserID)) {
            this.userID = userID;
            this.storyID = storyID;
            return true;
        } else
            return false;
    }
}
