package com.seok.seok.wowsup.fragments.fragstroy;

public class CardData {

    private String stroyID;
    private String userID;
    private String title;
    private String body;
    private String cntLike;

    public CardData(String stroyID, String userID, String title, String body, String cntLike) {
        this.stroyID = stroyID;
        this.userID = userID;
        this.title = title;
        this.body = body;
        this.cntLike = cntLike;
    }

    public String getStroyID() {
        return stroyID;
    }

    public void setStroyID(String stroyID) {
        this.stroyID = stroyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCntLike() {
        return cntLike;
    }

    public void setCntLike(String cntLike) {
        this.cntLike = cntLike;
    }
}
