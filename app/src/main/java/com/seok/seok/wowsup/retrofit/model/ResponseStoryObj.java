package com.seok.seok.wowsup.retrofit.model;

public class ResponseStoryObj {
    private int storyID;
    private String userID;
    private String title;
    private String body;
    private int cntLike;

    public int getStoryID() {
        return storyID;
    }

    public void setStoryID(int storyID) {
        this.storyID = storyID;
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

    public int getCntLike() {
        return cntLike;
    }

    public void setCntLike(int cntLike) {
        this.cntLike = cntLike;
    }
}
