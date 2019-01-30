package com.seok.seok.wowsup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryActivity extends AppCompatActivity {
    private String storyID;
    private TextView storyTextBody, storyTextTag1, storyTextTag2, storyTextTag3, storyTextTag4, storyTextTag5, storyTextCntlike;
    private LinearLayout storyLayoutBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyLayoutBackground = findViewById(R.id.story_laytout_background);
        storyTextBody = findViewById(R.id.story_text_body);
        storyTextTag1 = findViewById(R.id.story_text_body);
        storyTextTag2 = findViewById(R.id.story_text_body);
        storyTextTag3 = findViewById(R.id.story_text_body);
        storyTextTag4 = findViewById(R.id.story_text_body);
        storyTextTag5 = findViewById(R.id.story_text_body);
        storyTextTag5 = findViewById(R.id.story_text_body);


        Intent intent  = getIntent();
        storyID = intent.getStringExtra("storyID");


        ApiUtils.getStoryService().requestOneStoryView(storyID).enqueue(new Callback<ResponseStoryObj>() {
            @Override
            public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {

            }

            @Override
            public void onFailure(Call<ResponseStoryObj> call, Throwable t) {

            }
        });
    }
}
