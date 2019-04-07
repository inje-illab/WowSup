package com.seok.seok.wowsup.utilities;

import android.util.Log;

//프로그램에서 저장되어야할 싱글톤 패턴의 클래스

public class GlobalWowToken {
    private static GlobalWowToken globalWowToken = new GlobalWowToken();
    private int token;
    private String id;
    private String userEmail;
    private String imageURL;

    private GlobalWowToken(){
        Log.d("Start","싱글톤 시작");
    }

    public static GlobalWowToken getInstance(){
        return globalWowToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
