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
import android.widget.Toast;

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
import com.seok.seok.wowsup.utilities.FriendConfirmDialog;
import com.seok.seok.wowsup.utilities.GlobalWowToken;
import com.seok.seok.wowsup.utilities.StoryBanDialog;
import com.seok.seok.wowsup.utilities.StoryDeleteDialog;
import com.seok.seok.wowsup.utilities.ViewDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryActivity extends AppCompatActivity {

    private String storyID, imageURL, otherUserID;
    private TextView storyTextBody, storyTextTag1, storyTextTag2, storyTextTag3, storyTextTag4, storyTextTag5, storyTextCntLike;
    private BoomMenuButton boomMenuButton;
    private LinearLayout storyLayoutBackground;
    private ImageView iBtnBack, iBtnLike;
    private HamButton.Builder builder;
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
                case R.id.story_ibtn_like:
                    ApiUtils.getStoryService().requestStoryLike(GlobalWowToken.getInstance().getId(), storyID).enqueue(new Callback<ResponseStoryObj>() {
                        @Override
                        public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {
                            Log.d("RegisterActivity_HTTP_LIKE", "HTTP Transfer Success");
                            if (response.isSuccessful()) {
                                ResponseStoryObj body = response.body();
                                if (body.getState() == 0) {
                                    iBtnLike.setImageResource(R.mipmap.unllike_icon);
                                } else if (body.getState() == 1) {
                                    iBtnLike.setImageResource(R.mipmap.like_icon);
                                }
                                storyTextCntLike.setText(body.getCntLike() + "");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseStoryObj> call, Throwable t) {
                            Log.d("RegisterActivity_HTTP_LIKE", "HTTP Transfer Failed");
                        }
                    });
                    break;
            }
        }
    };

    public void initData() {
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            builder = new HamButton.Builder()
                    .normalColorRes(R.color.blockColor)
                    .textSize(17)
                    .imagePadding(new Rect(30, 30, 30, 30))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (index == 0) {
                                FriendConfirmDialog friendConfirmDialog = new FriendConfirmDialog(StoryActivity.this);
                                friendConfirmDialog.requestApplyFriend(GlobalWowToken.getInstance().getId(), otherUserID);
                                friendConfirmDialog.show();
                            }else if(index==1){
                                StoryDeleteDialog storyDeleteDialog = new StoryDeleteDialog(StoryActivity.this);
                                if(storyDeleteDialog.requestStoryDelete(GlobalWowToken.getInstance().getId(), otherUserID, storyID))
                                    storyDeleteDialog.show();
                                else
                                    Toast.makeText(StoryActivity.this, "Other 'SupPeople's writings.", Toast.LENGTH_SHORT).show();
                            }
                            else if(index == 2){
                                StoryBanDialog storyBanDialog = new StoryBanDialog(StoryActivity.this);
                                storyBanDialog.requestStoryBan(GlobalWowToken.getInstance().getId(), storyID);
                                storyBanDialog.show();
                            }
                        }
                    });
            if (i == 0) {
                builder.normalImageRes(R.mipmap.send_icon)
                        .normalText("Friend request")
                        .subNormalText("Send a friend to SupPeople");
            } else if (i == 1) {
                builder.normalImageRes(R.drawable.delete)
                        .normalText("Delete Post")
                        .subNormalText("Delete my posts");
            } else if(i == 2){
                builder.normalImageRes(R.mipmap.ban_icon)
                        .normalText("To ban")
                        .subNormalText("Report this post");
            }
            boomMenuButton.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Top);
            boomMenuButton.addBuilder(builder);
        }

        Intent intent = getIntent();
        storyID = intent.getStringExtra("storyID");

        ApiUtils.getStoryService().requestPickStoryView(storyID, GlobalWowToken.getInstance().getId()).enqueue(new Callback<ResponseStoryObj>() {
            @Override
            public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {
                Log.d("StoryActivity_HTTP_PICK", "HTTP Transfer Success");
                ResponseStoryObj body = response.body();
                if (response.isSuccessful()) {
                    otherUserID = body.getUserID();
                    storyTextBody.setText(body.getBody());
                    storyTextTag1.setText("# " + body.getTag1());
                    storyTextTag2.setText("# " + body.getTag2());
                    storyTextTag3.setText("# " + body.getTag3());
                    storyTextTag4.setText("# " + body.getTag4());
                    storyTextTag5.setText("# " + body.getTag5());
                    storyTextCntLike.setText(body.getCntLike() + "");
                    if (body.getState() == 0) {
                        iBtnLike.setImageResource(R.mipmap.unllike_icon);
                    } else if (body.getState() == 1) {
                        iBtnLike.setImageResource(R.mipmap.like_icon);
                    }
                    imageURL = body.getImageURL();
                    Glide.with(getApplicationContext())
                            .load(body.getImageURL()).into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            storyLayoutBackground.setBackground(resource);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseStoryObj> call, Throwable t) {
                Log.d("StoryActivity_HTTP_PICK", "HTTP Transfer Failed");
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
        iBtnLike = findViewById(R.id.story_ibtn_like);
        storyLayoutBackground.setOnClickListener(onClickListener);
        iBtnBack.setOnClickListener(onClickListener);
        iBtnLike.setOnClickListener(onClickListener);
    }
}
