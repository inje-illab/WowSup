package com.seok.seok.wowsup.utilities;

import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.seok.seok.wowsup.LoginActivity;
import com.seok.seok.wowsup.retrofit.model.ResponseLoginObj;
import com.seok.seok.wowsup.retrofit.model.ResponseRegisterObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionCallbackFacebook implements FacebookCallback<LoginResult> {
    private boolean loginSuccess = false;

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e("SessionCallback :: ", "onSuccess");
        Log.d("Profile : ", Profile.getCurrentProfile().getId() + "");
        Log.d("Profile : ", Profile.getCurrentProfile().getFirstName() + "");
        Log.d("Profile : ", Profile.getCurrentProfile().getMiddleName() + "");
        Log.d("Profile : ", Profile.getCurrentProfile().getLastName() + "");
        Log.d("Profile : ", Profile.getCurrentProfile().getName() + "");
        Log.d("Profile : ", Profile.getCurrentProfile().getLinkUri() + "");
        Log.d("Profile : ", Profile.getCurrentProfile().getProfilePictureUri(300, 300) + "");
        this.loginSuccess = true;
        ApiUtils.getUserService().requestSnsLogin(Profile.getCurrentProfile().getId() + "",
                Profile.getCurrentProfile().getId() + "", Profile.getCurrentProfile().getId() + "@facebook.com",2)
                .enqueue(new Callback<ResponseLoginObj>() {
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
                });
    }

    @Override
    public void onCancel() {
        Log.d("onCancel : ", "<<<");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("onError : ", "<<<");
    }

    public boolean getLoginSuccess() {
        return loginSuccess;
    }
}
