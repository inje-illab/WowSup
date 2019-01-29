package com.seok.seok.wowsup.fragments.fragchat;

public class Chat {
    public String email;
    public String text;

    public Chat(){
        //Default
    }

    public Chat(String email, String author, String text){
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

    public void setEmail(String email) {
        this.email = email;
    }
}
