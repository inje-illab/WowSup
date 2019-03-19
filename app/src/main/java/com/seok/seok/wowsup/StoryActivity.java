package com.seok.seok.wowsup;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
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
    private BoomMenuButton boomMenuButton;
    private LinearLayout storyLayoutBackground;
    private ImageView iBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        initFindViewByID();
        initData();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.story_layout_background:
                    ViewDialog backgroundViewDialog = new ViewDialog(StoryActivity.this, imageURL);
                    backgroundViewDialog.show();
                    break;
                case R.id.story_ibtn_back:
                    finish();
            }
        }
    };
    public void initData(){
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalColorRes(R.color.blockColor)
                    .textSize(17)
                    .imagePadding(new Rect(30, 30, 30, 30))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if(index == 0) {
                                ViewDialog applyFriendViewDialog = new ViewDialog(StoryActivity.this, 0);
                                applyFriendViewDialog.requestApplyFriend(GlobalWowToken.getInstance().getId(), otherUserID);
                                applyFriendViewDialog.setButtonText("취소", "요청");
                                applyFriendViewDialog.show();
                            }
                        }
                    });
            if (i == 0) {
                builder.normalImageRes(R.mipmap.send_icon)
                        .normalText("Friend request")
                        .subNormalText("Send a friend to SupPeople");
            } else if (i == 1) {
                builder.normalImageRes(R.mipmap.ban_icon)
                        .normalText("To ban")
                        .subNormalText("Report this post");
            }
            boomMenuButton.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Top);
            boomMenuButton.addBuilder(builder);
        }


        Intent intent = getIntent();
        storyID = intent.getStringExtra("storyID");

        ApiUtils.getStoryService().requestOneStoryView(storyID).enqueue(new Callback<ResponseStoryObj>() {
            @Override
            public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {
                ResponseStoryObj body = response.body();
                if (response.isSuccessful()) {
                    try {
                        otherUserID = body.getUserID();
                        storyTextBody.setText(body.getBody());
                        storyTextTag1.setText(body.getTag1());
                        storyTextTag2.setText(body.getTag2());
                        storyTextTag3.setText(body.getTag3());
                        storyTextTag4.setText(body.getTag4());
                        storyTextTag5.setText(body.getTag5());
                        storyTextCntLike.setText(body.getCntLike() + "");
                        if (!body.getImageURL().equals(null)) {
                            Glide.with(getApplicationContext()).load(body.getImageURL()).into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    storyLayoutBackground.setBackground(resource);
                                }
                            });
                        }
                    } catch (Exception e) {
                        imageURL = Common.STORY_IMAGE_BASE_URL + "test_background.jpg";
                        Glide.with(getApplicationContext()).load(imageURL).into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                storyLayoutBackground.setBackground(resource);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseStoryObj> call, Throwable t) {
                Log.d("StoryFragments_err ", t.getMessage());
            }
        });
    }
    public void initFindViewByID() {
        storyLayoutBackground = findViewById(R.id.story_layout_background);
        storyTextBody = findViewById(R.id.story_text_body);
        storyTextTag1 = findViewById(R.id.story_text_tag1);
        storyTextTag2 = findViewById(R.id.story_text_tag2);
        storyTextTag3 = findViewById(R.id.story_text_tag3);
        storyTextTag4 = findViewById(R.id.story_text_tag4);
        storyTextTag5 = findViewById(R.id.story_text_tag5);
        storyTextCntLike = findViewById(R.id.story_text_cntlike);
        boomMenuButton = findViewById(R.id.story_menu);
        iBtnBack = findViewById(R.id.story_ibtn_back);
        storyLayoutBackground.setOnClickListener(onClickListener);
        iBtnBack.setOnClickListener(onClickListener);
    }
}
