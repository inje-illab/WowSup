package com.seok.seok.wowsup.retrofit.model;

public class ResponseLoginObj {
    //로그인 페이지에 들어갈 모델 클래스
    private int state;
    private int token;
    private String id;
    private String email;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
