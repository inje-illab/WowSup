package com.seok.seok.wowsup.utilities;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Common {
    public static final String API_BASE_URL = "http://www.heywowsup.com/";
    public static final String API_IMAGE_BASE_URL = "http://www.heywowsup.com/wowsup/Image/";
    public static final String USER_IMAGE_BASE_URL = "http://www.heywowsup.com/wowsup/Image/basic.png";
    public static final String STORY_IMAGE_BASE_URL = "http://www.heywowsup.com/wowsup/Image/Background/";

    public static boolean fragmentProfileTab = true;
    public static boolean fragmentChatTab = true;
    public static boolean fragmentStoryTab = true;
    public static boolean fragmentHelpTab = true;

    public static String searchTagText = null;


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
