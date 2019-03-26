package com.seok.seok.wowsup.utilities;

public class NoticeData {

    private String userID;
    private int status;

    public NoticeData(String userID, int status) {
        this.userID = userID;
        this.status = status;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
