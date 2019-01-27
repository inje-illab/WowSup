package com.seok.seok.wowsup.fragments.fragprofile;

public class CardData {

    private String id;
    private String text;

    public CardData(String id, String text){
        this.id = id;
        this.text = text;
    }

    public String getId(){
        return id;
    }

    public String getText(){
        return text;
    }
}
