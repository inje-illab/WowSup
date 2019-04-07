package com.seok.seok.wowsup.fragments.fragchat;

public class Chat {
    //채팅에 필요한 모델 클래스
    public String email;
    public String text;

    public Chat(){
        //Default
    }

    public Chat(String email, String text) {
        this.email = email;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getEmail() {
        return email;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEmail(String email) { this.email = email;}
}
