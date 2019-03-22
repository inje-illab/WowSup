package com.seok.seok.wowsup.fragments;

public class CardData {

    private String storyID;
    private String userID;
    private String title;
    private String body;
    private String cntLike;
    private String imageURL;

    public CardData(String storyID, String userID, String title, String body, String cntLike, String imageURL) {
        this.storyID = storyID;
        this.userID = userID;
        this.title = title;
        this.body = body;
        this.cntLike = cntLike;
        this.imageURL = imageURL;
    }

    public String getStoryID() {
        return storyID;
    }

    public void setStoryID(String storyID) {
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

    public String getCntLike() {
        return cntLike;
    }

    public void setCntLike(String cntLike) {
        this.cntLike = cntLike;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

