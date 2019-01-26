package com.seok.seok.wowsup.utilities;

import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

public class SessionCallbackFacebook implements FacebookCallback<LoginResult> {
    private boolean loginSuccess = false;
    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e("SessionCallback :: ", "onSuccess");
        Log.d("Profile : ", Profile.getCurrentProfile().getId()+"");
        Log.d("Profile : ",  Profile.getCurrentProfile().getFirstName()+"");
        Log.d("Profile : ", Profile.getCurrentProfile().getMiddleName()+"");
        Log.d("Profile : ", Profile.getCurrentProfile().getLastName()+"");
        Log.d("Profile : ", Profile.getCurrentProfile().getName()+"");
        Log.d("Profile : ", Profile.getCurrentProfile().getLinkUri()+"");
        Log.d("Profile : ", Profile.getCurrentProfile().getProfilePictureUri(300,300)+"");
        this.loginSuccess = true;

    }

    @Override
    public void onCancel() {
        Log.d("onCancel : ", "<<<");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("onError : ", "<<<");
    }

    public boolean getLoginSuccess(){
        return loginSuccess;
    }
}
