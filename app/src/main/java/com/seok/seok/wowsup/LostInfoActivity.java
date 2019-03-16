package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seok.seok.wowsup.retrofit.model.ResponseMailObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostInfoActivity extends AppCompatActivity {
    private EditText editIDEmail, editPWID, editPWEmail;
    private Button btnID, btnPW;
    private Callback retrofitCallBack;

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
            switch (v.getId()){
                case R.id.lostinfo_btn_id:
                    email = editIDEmail.getText().toString();
                    ApiUtils.getEmailService().requestFindID(email).enqueue(retrofitCallBack);
                    break;
                case R.id.lostinfo_btn_pw:
                    id = editPWID.getText().toString();
                    email = editPWEmail.getText().toString();
                    ApiUtils.getEmailService().requestFindPW(id, email).enqueue(retrofitCallBack);
                    break;
            }
        }
    };
    public void init(){
        editIDEmail = findViewById(R.id.lostinfo_edit_id_email);
        editPWID = findViewById(R.id.lostinfo_edit_pw_id);
        editPWEmail = findViewById(R.id.lostinfo_edit_pw_email);
        btnID = findViewById(R.id.lostinfo_btn_id);
        btnPW = findViewById(R.id.lostinfo_btn_pw);
        btnID.setOnClickListener(onClickListener);
        btnPW.setOnClickListener(onClickListener);

        // 서버 콜백 메서드 구현 (ResponseMailObj.class)
        retrofitCallBack = new Callback<ResponseMailObj>() {
            @Override
            public void onResponse(Call<ResponseMailObj> call, Response<ResponseMailObj> response) {
                if(response.isSuccessful()){
                    ResponseMailObj body = response.body();
                    if(body.getState() == 0){
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
}
