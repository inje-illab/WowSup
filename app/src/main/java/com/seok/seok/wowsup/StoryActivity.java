package com.seok.seok.wowsup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.ViewDialog;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryActivity extends AppCompatActivity {
    private String storyID, imageURL, otherUserID;
    private TextView storyTextBody, storyTextTag1, storyTextTag2, storyTextTag3, storyTextTag4, storyTextTag5, storyTextCntLike;
    private LinearLayout storyLayoutBackground;
    private Button storyBtnChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        init();

        Intent intent  = getIntent();
        storyID = intent.getStringExtra("storyID");

        ApiUtils.getStoryService().requestOneStoryView(storyID).enqueue(new Callback<ResponseStoryObj>() {
            @Override
            public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {
                ResponseStoryObj body = response.body();
                if(response.isSuccessful()){
                    try{
                        otherUserID = body.getUserID();
                        storyTextBody.setText(body.getBody());
                        storyTextTag1.setText(body.getTag1());
                        storyTextTag2.setText(body.getTag2());
                        storyTextTag3.setText(body.getTag3());
                        storyTextTag4.setText(body.getTag4());
                        storyTextTag5.setText(body.getTag5());
                        storyTextCntLike.setText(body.getCntLike()+"");
                        if(!body.getImageURL().equals(null)){
                            imageURL = Common.STORY_IMAGE_BASE_URL+body.getImageURL();
                            Glide.with(getApplicationContext()).load(imageURL).into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    storyLayoutBackground.setBackground(resource);
                                }
                            });
                        }
                    }catch(Exception e){
                        imageURL = Common.STORY_IMAGE_BASE_URL+"test_background.jpg";
                        Glide.with(getApplicationContext()).load(imageURL).into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                storyLayoutBackground.setBackground(resource);
                            }
                        });
                    }
                    if(!otherUserID.equals(GlobalWowToken.getInstance().getId()))
                        storyBtnChat.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseStoryObj> call, Throwable t) {
                Log.d("StoryFragments_err ", t.getMessage());
            }
        });
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.story_laytout_background:
                    ViewDialog backgroundViewDialog = new ViewDialog(StoryActivity.this, imageURL);
                    backgroundViewDialog.show();
                    break;
                case R.id.story_btn_chat:
                    ViewDialog applyFriendViewDialog = new ViewDialog(StoryActivity.this, 0);
                    applyFriendViewDialog.requestApplyFriend(GlobalWowToken.getInstance().getId(), otherUserID);
                    applyFriendViewDialog.setButtonText("취소", "요청");
                    applyFriendViewDialog.show();
                    break;
            }
        }
    };
    public void init(){
        storyLayoutBackground = findViewById(R.id.story_laytout_background);
        storyTextBody = findViewById(R.id.story_text_body);
        storyTextTag1 = findViewById(R.id.story_text_tag1);
        storyTextTag2 = findViewById(R.id.story_text_tag2);
        storyTextTag3 = findViewById(R.id.story_text_tag3);
        storyTextTag4 = findViewById(R.id.story_text_tag4);
        storyTextTag5 = findViewById(R.id.story_text_tag5);
        storyTextCntLike = findViewById(R.id.story_text_cntlike);
        storyBtnChat = findViewById(R.id.story_btn_chat);

        storyLayoutBackground.setOnClickListener(onClickListener);
        storyBtnChat.setOnClickListener(onClickListener);
    }
}
