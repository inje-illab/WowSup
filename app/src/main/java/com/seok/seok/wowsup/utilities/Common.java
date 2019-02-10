package com.seok.seok.wowsup.utilities;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Common {
    public static final String API_BASE_URL = "http://203.241.228.121/";
    public static final String API_IMAGE_BASE_URL = "http://203.241.228.121/WowSup/Image/";
    public static final String USER_IMAGE_BASE_URL = "http://203.241.228.121/WowSup/Image/basic.png";
    public static final String STORY_IMAGE_BASE_URL = "http://203.241.228.121/WowSup/Image/test_background.jpg";

    public static boolean fragmentProfileTab = true;
    public static boolean fragmentChatTab = true;
    public static boolean fragmentStoryTab = true;
    public static boolean fragmentHelpTab = true;

    public static void setTabFlag(){
        fragmentProfileTab = true;
        fragmentChatTab = true;
        fragmentStoryTab = true;
        fragmentHelpTab = true;
    }
    public static Drawable fixImageSize(Resources resources, Drawable image, int width, int height){
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        return new BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, width, height, true));
    }
}
