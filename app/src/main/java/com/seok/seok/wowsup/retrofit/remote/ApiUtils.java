package com.seok.seok.wowsup.retrofit.remote;

public class ApiUtils {
    //Server /var/www/html 의 주소
    public static final String BASE_URL = "http://203.241.228.121/WowSup/";
    public static LoginService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(LoginService.class);
    }
    public static ProfileService getProfileService(){
        return RetrofitClient.getClient(BASE_URL).create(ProfileService.class);
    }
    public static ChatService getChatService(){
        return RetrofitClient.getClient(BASE_URL).create(ChatService.class);
    }
    public static WriteService getWriteService(){
        return  RetrofitClient.getClient(BASE_URL).create(WriteService.class);
    }
    public static StoryService getStoryService(){
        return  RetrofitClient.getClient(BASE_URL).create(StoryService.class);
    }
}
