package com.seok.seok.wowsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

public class StoryBanDialog extends Dialog implements View.OnClickListener {
    private static int LAYOUT;
    private Context context;
    private String userID, storyID;
    private Button btnYes, btnNo;
    public StoryBanDialog(Context context) {
        super(context);
        LAYOUT = R.layout.layout_story_ban_dialog;
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        btnYes = findViewById(R.id.dialog_story_ban_btn_yes);
        btnNo = findViewById(R.id.dialog_story_ban_btn_no);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_story_ban_btn_yes:
                ApiUtils.getStoryService().requestBanStory(userID,storyID).enqueue(new Callback<ResponseStoryObj>() {
                    @Override
                    public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {
                        if (response.isSuccessful()) {
                            ResponseStoryObj body = response.body();
                            if (body.getState() == 1) {
                                Toast.makeText(context, "Your report has been reported!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseStoryObj> call, Throwable t) {

                    }
                });

                break;
            case R.id.dialog_story_ban_btn_no:
                dismiss();
                break;
        }
    }
    public void requestStoryBan(String userID, String storyID) {
        this.userID = userID;
        this.storyID = storyID;
    }
}
