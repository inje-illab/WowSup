package com.seok.seok.wowsup.retrofit.model;

public class ResponseChatObj {
    //채팅 프래그먼트에 들어갈 모델 클래스
    private String friendNick;
    private String imageURL;
    private String friend;
    private String userID;
    private String selfish;

    public String getFriendNick() {
        return friendNick;
    }

    public void setFriendNick(String friendNick) {
        this.friendNick = friendNick;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSelfish() {
        return selfish;
    }

    public void setSelfish(String userSelfish) {
        this.selfish = userSelfish;
    }
}
