package com.seok.seok.wowsup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.seok.seok.wowsup.retrofit.model.ResponseLoginObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.retrofit.remote.LoginService;
import com.seok.seok.wowsup.utilities.Common;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private SessionCallback callback;
    private Callback retrofitCallback;
    private CallbackManager callbackManager;
    private LoginButton facebook_login;
    private Button btnLogin, btnRegister;
    private EditText edtID, edtPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        retrofitCallback = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        };
        // Layout에서 id 값 받아오기
        btnLogin = findViewById(R.id.login_button_login);
        btnRegister = findViewById(R.id.login_button_register);
        edtID = findViewById(R.id.login_edittext_id);
        edtPW = findViewById(R.id.login_edittext_pwd);

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
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

        //facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        facebook_login = findViewById(R.id.login_button);

        facebook_login.setReadPermissions("email");

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Common.loginForm = "FaceBook";
                        Log.d("onSuccess", "onSucces LoginResult=" + loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d("onCancel", "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("onError", "onError");
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //카카오톡, 페이스북 result
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data) || callbackManager.onActivityResult(requestCode, resultCode, data)) {
            requestMe();
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Common.loginForm = "KakaoTalk";
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    private void requestMe() {
        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d("KAKAO ERR", message);
                Logger.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("KAKAO ERR", "session closed" + errorResult);
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                if (Common.loginForm == "KakaoTalk") {
                    Log.d("KAKAO userProfile_", userProfile.getNickname() + "");
                    Log.d("KAKAO userProfile_", userProfile.getEmail() + "");
                    Log.d("KAKAO userProfile_", userProfile.getId() + "");
                    Log.d("KAKAO userProfile_", userProfile.getServiceUserId() + "");
                    Log.d("KAKAO userProfile_", userProfile.getUUID() + "");
                    snsRegister(userProfile.getId()+"", userProfile.getId()+"", userProfile.getEmail());
                } else if (Common.loginForm == "FaceBook") {
                    Log.d("FACEBOOK userProfile_", Profile.getCurrentProfile().getId());
                    Log.d("FACEBOOK userProfile_", Profile.getCurrentProfile().getFirstName());
                    Log.d("FACEBOOK userProfile_", Profile.getCurrentProfile().getMiddleName());
                    Log.d("FACEBOOK userProfile_", Profile.getCurrentProfile().getLastName());
                    Log.d("FACEBOOK userProfile_", Profile.getCurrentProfile().getName());
                    snsRegister(Profile.getCurrentProfile().getId(), Profile.getCurrentProfile().getId(), Profile.getCurrentProfile().getId());
                }
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onNotSignedUp() {

            }
        });
    }

    public void snsRegister(String id, String pwd, String email) {
        ApiUtils.getUserService().requestSnsLogin(id, pwd, email).enqueue(retrofitCallback);
    }

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
}
