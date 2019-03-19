package com.seok.seok.wowsup.retrofit.remote;

public class ApiMailUtils {
    public static final String BASE_MAIL_URL = "http://www.heywowsup.com/awsSES/";
    public static MailService getEmailService(){
        return RetrofitMailClient.getClient(BASE_MAIL_URL).create(MailService.class);
    }
}
