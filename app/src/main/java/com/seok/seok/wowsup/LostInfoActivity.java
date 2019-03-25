package com.seok.seok.wowsup;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seok.seok.wowsup.retrofit.model.ResponseMailObj;
import com.seok.seok.wowsup.retrofit.remote.ApiMailUtils;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostInfoActivity extends AppCompatActivity {
    private EditText editIDEmail, editPWID, editPWEmail;
    private Button btnID, btnPW;
    private Callback retrofitCallBack;
    private LinearLayout layoutID, layoutPW, layoutIDTitle, layoutPWTitle;
    private ImageView iBtnBack, iBtnID, iBtnPW;
    private boolean checkID, checkPW, sendMail;
    private CountDownTimer countDownTimer;
    private long emailSendCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostinfo);
        init();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id, email;
            switch (v.getId()) {
                case R.id.lostinfo_btn_id:
                    email = editIDEmail.getText().toString();
                    if (sendMail) {
                        ApiMailUtils.getEmailService().requestFindID(email).enqueue(retrofitCallBack);
                        countDownTimer();
                    }
                    else
                        Toast.makeText(LostInfoActivity.this, "You can do it again in "+ emailSendCount+ " seconds.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.lostinfo_btn_pw:
                    id = editPWID.getText().toString();
                    email = editPWEmail.getText().toString();
                    if (sendMail) {
                        countDownTimer();
                        ApiMailUtils.getEmailService().requestFindPW(id, email).enqueue(retrofitCallBack);
                    }
                    else
                        Toast.makeText(LostInfoActivity.this, "You can do it again in "+ emailSendCount+ " seconds.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.lostinfo_ibtn_id:
                    layoutPW.setVisibility(View.GONE);
                    layoutPWTitle.setBackgroundResource(R.drawable.id_tab_block);
                    iBtnPW.setImageResource(R.drawable.tab_down_button);
                    checkPW = false;
                    if (!checkID) {
                        layoutID.setVisibility(View.VISIBLE);
                        layoutIDTitle.setBackgroundResource(R.drawable.find_id_block);
                        iBtnID.setImageResource(R.drawable.tab_up_button);
                        checkID = true;
                    } else {
                        layoutID.setVisibility(View.GONE);
                        layoutIDTitle.setBackgroundResource(R.drawable.id_tab_block);
                        iBtnID.setImageResource(R.drawable.tab_down_button);
                        checkID = false;
                    }
                    break;
                case R.id.lostinfo_ibtn_pw:
                    countDownTimer();
                    layoutID.setVisibility(View.GONE);
                    layoutIDTitle.setBackgroundResource(R.drawable.id_tab_block);
                    iBtnID.setImageResource(R.drawable.tab_down_button);
                    checkID = false;
                    if (!checkPW) {
                        layoutPW.setVisibility(View.VISIBLE);
                        layoutPWTitle.setBackgroundResource(R.drawable.find_password_block);
                        iBtnPW.setImageResource(R.drawable.tab_up_button);
                        checkPW = true;
                    } else {
                        layoutPW.setVisibility(View.GONE);
                        layoutPWTitle.setBackgroundResource(R.drawable.id_tab_block);
                        iBtnPW.setImageResource(R.drawable.tab_down_button);
                        checkPW = false;
                    }
                    break;
                case R.id.lostinfo_ibtn_back:
                    finish();
                    break;
            }
        }
    };

    public void init() {
        checkID = false;
        checkPW = false;
        sendMail = true;
        editIDEmail = findViewById(R.id.lostinfo_edit_id_email);
        editPWID = findViewById(R.id.lostinfo_edit_pw_id);
        editPWEmail = findViewById(R.id.lostinfo_edit_pw_email);
        layoutID = findViewById(R.id.lostinfo_layout_id);
        layoutPW = findViewById(R.id.lostinfo_layout_pw);
        iBtnBack = findViewById(R.id.lostinfo_ibtn_back);
        iBtnID = findViewById(R.id.lostinfo_ibtn_id);
        iBtnPW = findViewById(R.id.lostinfo_ibtn_pw);
        btnID = findViewById(R.id.lostinfo_btn_id);
        btnPW = findViewById(R.id.lostinfo_btn_pw);
        layoutIDTitle = findViewById(R.id.lostinfo_layout_id_title);
        layoutPWTitle = findViewById(R.id.lostinfo_layout_pw_title);
        btnID.setOnClickListener(onClickListener);
        btnPW.setOnClickListener(onClickListener);
        iBtnID.setOnClickListener(onClickListener);
        iBtnPW.setOnClickListener(onClickListener);
        iBtnBack.setOnClickListener(onClickListener);
        iBtnID.setImageResource(R.drawable.tab_down_button);
        iBtnPW.setImageResource(R.drawable.tab_down_button);

        // 서버 콜백 메서드 구현 (ResponseMailObj.class)
        retrofitCallBack = new Callback<ResponseMailObj>() {
            @Override
            public void onResponse(Call<ResponseMailObj> call, Response<ResponseMailObj> response) {
                if (response.isSuccessful()) {
                    ResponseMailObj body = response.body();
                    if (body.getState() == 0) {
                        Toast.makeText(LostInfoActivity.this, "Email not Send!", Toast.LENGTH_SHORT).show();
                    } else if (body.getState() == 1) {
                        Toast.makeText(LostInfoActivity.this, "Email Send!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMailObj> call, Throwable t) {
                Log.d("LostInfoActivity_ERROR", t.getMessage() + " <<");
            }
        };
    }

    public void countDownTimer() {
        sendMail = false;
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                emailSendCount = millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                sendMail = true;
                countDownTimer.cancel();
            }
        }.start();
    }
}
