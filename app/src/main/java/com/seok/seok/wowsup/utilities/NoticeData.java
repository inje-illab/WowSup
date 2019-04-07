package com.seok.seok.wowsup.utilities;

public class NoticeData {
//친구 목록을 어댑터에 담기위한 클래스
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
