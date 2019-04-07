package com.seok.seok.wowsup.retrofit.remote;

public class ApiMailUtils {
    //메일을 보내어 서버와 통신할 빌드 클래스
    public static final String BASE_MAIL_URL = "http://www.heywowsup.com/awsSES/";
    public static MailService getEmailService(){
        return RetrofitMailClient.getClient(BASE_MAIL_URL).create(MailService.class);
    }
}
