package com.seok.seok.wowsup.utilities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.Profile;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.seok.seok.wowsup.MainActivity;
import com.seok.seok.wowsup.retrofit.model.ResponseLoginObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.retrofit.remote.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SessionCallbackKakaoTalk implements ISessionCallback {

    private boolean loginSuccess = false;
    private Callback<ResponseLoginObj> callback;
    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
        loginSuccess = true;
        callback = new Callback<ResponseLoginObj>() {
            @Override
            public void onResponse(Call<ResponseLoginObj> call, Response<ResponseLoginObj> response) {
                if(response.isSuccessful()){
                    ResponseLoginObj body = response.body();
                    if(body.getState() == 1){
                        Log.d("snsRegister_", "success < ");
                    }else if(body.getState() == 0){
                        Log.d("snsRegister_", "fail < ");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLoginObj> call, Throwable t) {
                Log.d("snsRegister_err", t.getMessage() + " < ");
            }
        };
        requestMe();
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }
    // 사용자 정보 요청
    public void requestMe() {
        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            // 세션 오픈 실패. 세션이 삭제된 경우,
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
            }
            // 회원이 아닌 경우,
            @Override
            public void onNotSignedUp() {
                Log.e("SessionCallback :: ", "onNotSignedUp");
            }
            // 사용자정보 요청에 성공한 경우,
            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.e("SessionCallback :: ", "onSuccess");
                Log.d("Profile : ", userProfile.getNickname()+"");
                Log.d("Profile : ",  userProfile.getEmail()+"");
                Log.d("Profile : ", userProfile.getProfileImagePath()+"");
                Log.d("Profile : ", userProfile.getThumbnailImagePath()+"");
                Log.d("Profile : ", userProfile.getUUID()+"");
                Log.d("Profile : ", userProfile.getId()+"");
                LoginService loginService = ApiUtils.getUserService();
                if((userProfile.getEmail()).equals("null")) {
                    loginService.requestSnsLogin(userProfile.getId() + "",
                            userProfile.getId() + "", userProfile.getId() + "@kakao.com", 1).enqueue(callback);
                }
                else {
                    loginService.requestSnsLogin(userProfile.getId() + "",
                            userProfile.getId() + "", userProfile.getEmail(), 1).enqueue(callback);
                }
            }
            // 사용자 정보 요청 실패
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
            }
        });
    }
    public boolean getLoginSuccess(){
        return loginSuccess;
    }

}
