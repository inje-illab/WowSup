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
import com.seok.seok.wowsup.retrofit.model.ResponseCommonObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDialog extends Dialog implements View.OnClickListener {
    private static int LAYOUT;
    private Context context;
    private String imageURL, userID, otherUserID, leftText, rightText;
    private int contextNumber;
    private ImageView dialogImage;
    private Button btnConfirmNo, btnConfirmYes;
    private TextView txtConfirmQna;

    public ViewDialog(Context context, int contextNumber) {
        super(context);
        LAYOUT = R.layout.layout_confirm_dialog;
        this.contextNumber = contextNumber;
        this.context = context;
    }

    public ViewDialog(Context context, String imageURL) {
        super(context);
        LAYOUT = R.layout.layout_story_background_dialog;
        this.context = context;
        this.imageURL = imageURL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        switch (LAYOUT) {
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
                optionViewRequest(this.contextNumber);
                btnConfirmNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                break;
        }
    }

    public void requestApplyFriend(String userID, String otherUserID) {
        this.userID = userID;
        this.otherUserID = otherUserID;
    }

    public void setButtonText(String leftText, String rightText) {
        this.leftText = leftText;
        this.rightText = rightText;
    }

    public void optionViewRequest(int option) {

        if (option == 0) {
            txtConfirmQna.setText("친구 요청 하시겠습니까?");
            btnConfirmYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApiUtils.getCommonService().requestApplyFriend(userID, otherUserID).enqueue(new Callback<ResponseCommonObj>() {
                        @Override
                        public void onResponse(Call<ResponseCommonObj> call, Response<ResponseCommonObj> response) {
                            if (response.isSuccessful()) {
                                ResponseCommonObj body = response.body();
                                if (body.getStatus() == 1) {
                                    Toast.makeText(context, "친구요청 성공", Toast.LENGTH_SHORT).show();
                                } else if (body.getStatus() == 0) {
                                    Toast.makeText(context, "이미 친구 요청 한 사람 입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseCommonObj> call, Throwable t) {
                            Toast.makeText(context, "친구요청을 실패", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
                }
            });
        } else if (option == 1) {
            txtConfirmQna.setText("친구 요청 받으시겠습니까?");
            btnConfirmYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApiUtils.getCommonService().requestConfirmFriend(userID, otherUserID).enqueue(new Callback<ResponseCommonObj>() {
                        @Override
                        public void onResponse(Call<ResponseCommonObj> call, Response<ResponseCommonObj> response) {
                            if(response.isSuccessful()){
                                ResponseCommonObj body = response.body();
                                if(body.getStatus()==2){
                                    Toast.makeText(context, "수락 성공", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseCommonObj> call, Throwable t) {
                            Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
                }
            });
        }

    }

    @Override
    public void onClick(View v) {

    }
}