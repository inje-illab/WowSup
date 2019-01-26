package com.seok.seok.wowsup.utilities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.seok.seok.wowsup.MainActivity;

public class SessionCallbackKakaoTalk implements ISessionCallback {

    private boolean loginSuccess = false;

    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
        loginSuccess = true;
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
