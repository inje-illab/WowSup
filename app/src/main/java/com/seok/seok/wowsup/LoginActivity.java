package com.seok.seok.wowsup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.seok.seok.wowsup.retrofit.model.ResponseLoginObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.SessionCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    private Button btn_custom_login;
    private LoginButton btn_kakao_login;
    private SessionCallback sessionCallback;
    private Button btnLogin, btnRegister;
    private EditText edtID, edtPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Layout에서 id 값 받아오기
        btnLogin = findViewById(R.id.login_button_login);
        btnRegister = findViewById(R.id.login_button_register);
        edtID = findViewById(R.id.login_edittext_id);
        edtPW = findViewById(R.id.login_edittext_pwd);

        sessionCallback = new SessionCallback();

        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session session = Session.getCurrentSession();
                session.addCallback(sessionCallback);
                session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
                if(sessionCallback.getLoginSuccess()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

        /**카카오톡 로그아웃 요청**/
        //한번 로그인이 성공하면 세션 정보가 남아있어서 로그인창이 뜨지 않고 바로 onSuccess()메서드를 호출
        /*
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후 하고싶은 내용 코딩
            }
        });*/

        // 로그인 버튼 눌렀을 경우
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLogin(edtID.getText().toString(), edtPW.getText().toString())) {
                    ApiUtils.getUserService().requestLogin(edtID.getText().toString(), edtPW.getText().toString()).enqueue(new Callback<ResponseLoginObj>() {
                        @Override
                        public void onResponse(Call<ResponseLoginObj> call, Response<ResponseLoginObj> response) {
                            Log.d("login_in", "success2 < ");
                            if (response.isSuccessful()) {
                                ResponseLoginObj body = response.body();
                                if (body.getState() == 1) {
                                    Toast.makeText(LoginActivity.this, "Login 성공", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    //finish();
                                } else if (body.getState() == 2) {
                                    Toast.makeText(LoginActivity.this, "Login 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseLoginObj> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "통신오류", Toast.LENGTH_SHORT).show();
                            Log.d("login_err", t.getMessage() + " < ");
                        }
                    });
                }
            }
        });
        //회원가입 버튼을 눌렀을 경우
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                //finish();
            }
        });
    }

    // 아이디 패스워드 입력란 코드
    public boolean validateLogin(String id, String pwd) {
        if (id == null || id.trim().length() == 0) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pwd == null || pwd.trim().length() == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //카카오톡 세션 Result 반환
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
