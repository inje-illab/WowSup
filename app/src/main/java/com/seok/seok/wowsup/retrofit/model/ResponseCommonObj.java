package com.seok.seok.wowsup.retrofit.model;

public class ResponseCommonObj {
    //채팅신청에 들어갈 모델 클래스
    private int status;
    private String applyer;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }
}
