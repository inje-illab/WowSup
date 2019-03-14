package com.seok.seok.wowsup.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class RespsonseImageObj {

    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){this.message = message;}

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success){this.success=success;}
}
