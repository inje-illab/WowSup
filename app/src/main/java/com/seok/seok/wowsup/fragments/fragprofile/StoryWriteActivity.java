package com.seok.seok.wowsup.fragments.fragprofile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryWriteActivity extends AppCompatActivity {
    private Button btnSave;
    private EditText editTextTitle, editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_write);
        btnSave = findViewById(R.id.story_write_btn_save);
        editTextTitle = findViewById(R.id.story_write_edit_title);
        editTextBody = findViewById(R.id.story_write_edit_body);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getWriteService().requestWriteStory(GlobalWowToken.getInstance().getId(), editTextTitle.getText().toString(), editTextBody.getText().toString(),"","tag1", "tag2", "tag3", "tag4", "tag5").enqueue(new Callback<ResponseWriteObj>() {
                    @Override
                    public void onResponse(Call<ResponseWriteObj> call, Response<ResponseWriteObj> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseWriteObj> call, Throwable t) {

                    }
                });
            }
        });
    }
}
