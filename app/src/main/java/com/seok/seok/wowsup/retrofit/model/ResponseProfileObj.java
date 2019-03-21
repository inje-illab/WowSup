package com.seok.seok.wowsup.retrofit.model;

public class ResponseProfileObj {
    private int cntLike;
    private int cntFriend;
    private int cntNotice;
    private int state;
    private int age;
    private int banner;
    private int change;
    private String imageURL;
    private String nick;
    private String gender;
    private String nationality;
    private String selfish;
    private String userMessage;

    public int getCntLike() {
        return cntLike;
    }

    public void setCntLike(int cntLike) {
        this.cntLike = cntLike;
    }

    public int getCntFriend() {
        return cntFriend;
    }

    public void setCntFriend(int cntFriend) {
        this.cntFriend = cntFriend;
    }

    public int getCntNotice() {
        return cntNotice;
    }

    public void setCntNotice(int cntNotice) {
        this.cntNotice = cntNotice;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getSelfish() {
        return selfish;
    }

    public void setSelfish(String selfish) {
        this.selfish = selfish;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
