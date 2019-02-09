package com.seok.seok.wowsup;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryActivity extends AppCompatActivity {
    private String storyID;
    private TextView storyTextBody, storyTextTag1, storyTextTag2, storyTextTag3, storyTextTag4, storyTextTag5, storyTextcntlike;
    private LinearLayout storyLayoutBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyLayoutBackground = findViewById(R.id.story_laytout_background);
        storyTextBody = findViewById(R.id.story_text_body);
        storyTextTag1 = findViewById(R.id.story_text_tag1);
        storyTextTag2 = findViewById(R.id.story_text_tag2);
        storyTextTag3 = findViewById(R.id.story_text_tag3);
        storyTextTag4 = findViewById(R.id.story_text_tag4);
        storyTextTag5 = findViewById(R.id.story_text_tag5);
        storyTextcntlike = findViewById(R.id.story_text_cntlike);


        Intent intent  = getIntent();
        storyID = intent.getStringExtra("storyID");

        ApiUtils.getStoryService().requestOneStoryView(storyID).enqueue(new Callback<ResponseStoryObj>() {
            @Override
            public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {
                ResponseStoryObj body = response.body();
                if(response.isSuccessful()){
                    try{
                        storyTextBody.setText(body.getBody());
                        storyTextTag1.setText(body.getTag1());
                        storyTextTag2.setText(body.getTag2());
                        storyTextTag3.setText(body.getTag3());
                        storyTextTag4.setText(body.getTag4());
                        storyTextTag5.setText(body.getTag5());
                        storyTextcntlike.setText(body.getCntLike()+"");
                        if(!body.getImageURL().equals(null)){
                            Glide.with(getApplicationContext()).load(Common.API_IMAGE_BASE_URL+body.getImageURL()).into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    storyLayoutBackground.setBackground(resource);
                                }
                            });
                        }
                    }catch(Exception e){
                        Glide.with(getApplicationContext()).load(Common.STORY_IMAGE_BASE_URL).into(new SimpleTarget<GlideDrawable>() {
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
}
