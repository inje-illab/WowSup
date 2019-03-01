package com.seok.seok.wowsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDialog extends Dialog implements View.OnClickListener{
    private static int LAYOUT;
    private Context context;
    private String imageURL, userID, otherUserID, leftText, rightText;
    private ImageView dialogImage;
    private Button btnConfirmNo, btnConfirmYes;
    private TextView txtConfirmQna;

    public ViewDialog(Context context){
        super(context);
        LAYOUT = R.layout.layout_confirm_dialog;
        this.context = context;
    }

    public ViewDialog(Context context, String imageURL){
        super(context);
        LAYOUT = R.layout.layout_story_background_dialog;
        this.context = context;
        this.imageURL = imageURL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        switch (LAYOUT){
            case R.layout.layout_story_background_dialog:
                dialogImage = findViewById(R.id.dialog_background);
                Glide.with(this.context).load(this.imageURL).into(dialogImage);
                break;
            case R.layout.layout_confirm_dialog:
                txtConfirmQna = findViewById(R.id.dialog_confirm_text_qna);
                btnConfirmYes = findViewById(R.id.dialog_confirm_btn_yes);
                btnConfirmNo = findViewById(R.id.dialog_confirm_btn_no);
                btnConfirmNo.setText(leftText);
                btnConfirmYes.setText(rightText);
                txtConfirmQna.setText("친구 요청 하시겠습니까?");
                btnConfirmYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApiUtils.getCommonService().requestApplyFriend(userID, otherUserID).enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                dismiss();
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(context, "친구요청을 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                btnConfirmNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                break;
        }
    }

    public void requestApplyFriend(String userID, String otherUserID){
        this.userID = userID;
        this.otherUserID = otherUserID;
    }
    public void setButtonText(String leftText, String rightText){
        this.leftText = leftText;
        this.rightText = rightText;
    }


    @Override
    public void onClick(View v) {

    }
}