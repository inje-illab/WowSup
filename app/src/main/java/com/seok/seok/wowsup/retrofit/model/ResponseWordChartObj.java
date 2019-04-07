package com.seok.seok.wowsup.retrofit.model;

public class ResponseWordChartObj {
    //글로벌 프레그먼트에 들어갈 모델 클래스
    private int wordID;
    private String word;
    private int wordCount;
    private String wordInputTime;

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getWordInputTime() {
        return wordInputTime;
    }

    public void setWordInputTime(String wordInputTime) {
        this.wordInputTime = wordInputTime;
    }
}
