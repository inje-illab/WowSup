package com.seok.seok.wowsup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.seok.seok.wowsup.retrofit.model.ResponseLoginObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;
import com.seok.seok.wowsup.utilities.SessionCallbackFacebook;
import com.seok.seok.wowsup.utilities.SessionCallbackKakaoTalk;

import java.util.Arrays;
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    private Button btnCustomKakaoLogin, btnCustomFacebookLogin;
    private SessionCallbackKakaoTalk sessionCallbackKakaoTalk;
    private SessionCallbackFacebook sessionCallbackFacebook;
    private CallbackManager callbackManager;
    private Button btnLogin, btnLost;
    private EditText edtID, edtPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Layout에서 id 값 받아오기
        btnLogin = findViewById(R.id.login_button_login);
        btnLost = findViewById(R.id.login_button_lost);
        edtID = findViewById(R.id.login_edittext_id);
        edtPW = findViewById(R.id.login_edittext_pwd);

       // btnCustomFacebookLogin = findViewById(R.id.btn_custom_facebook_login);
        btnCustomKakaoLogin = findViewById(R.id.btn_custom_kakao_login);

        sessionCallbackKakaoTalk = new SessionCallbackKakaoTalk();
        sessionCallbackFacebook = new SessionCallbackFacebook();
        /*btnCustomFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookSdk.sdkInitialize(getApplicationContext());
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager, sessionCallbackFacebook);
                onSuccess(sessionCallbackFacebook.getLoginSuccess());
            }
        });*/
        btnCustomKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session session = Session.getCurrentSession();
                session.addCallback(sessionCallbackKakaoTalk);
                session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
                onSuccess(sessionCallbackKakaoTalk.getLoginSuccess());
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
                                GlobalWowToken.getInstance().setId(body.getId());
                                GlobalWowToken.getInstance().setUserEmail(body.getEmail());
                                if (body.getState() == 1) {
                                    Toast.makeText(LoginActivity.this, "Login 성공", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                    Common.setTabFlag();
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

        //Lost ID, Password
        btnLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LostInfoActivity.class));
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

    // Session Result Kakao, Facebook
    public void onSuccess(boolean sessionCallback) {
        if (sessionCallback) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    //카카오톡 세션 Result 반환
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
