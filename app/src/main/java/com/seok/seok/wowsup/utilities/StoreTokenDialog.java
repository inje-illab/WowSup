package com.seok.seok.wowsup.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreTokenDialog extends Dialog implements View.OnClickListener {
    private static int LAYOUT;
    private Context context;
    private TextView title;
    private String strTitle;
    private Button btnYes, btnNo;

    public StoreTokenDialog(Context context) {
        super(context);
        LAYOUT = R.layout.layout_store_token_dialog;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        title = findViewById(R.id.store_txt_title);
        btnYes = findViewById(R.id.dialog_story_delete_btn_yes);
        btnNo = findViewById(R.id.dialog_story_delete_btn_no);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_story_delete_btn_yes:

                break;
            case R.id.dialog_story_delete_btn_no:
                dismiss();
                break;
        }
    }

    public boolean requestStoryDelete(String userID, String otherUserID, String storyID) {
        if (userID.equals(otherUserID)) {
            return true;
        } else
            return false;
    }
}
