package com.seok.seok.wowsup.retrofit.model;

public class ResponseRegisterObj {
    //회원가입 페이지에 들어갈 모델 클래스
    private int state;
    private String id;

    public ResponseRegisterObj(String id) {
        this.id = id;
    }

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
}

