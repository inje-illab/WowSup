package com.seok.seok.wowsup.utilities;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.seok.seok.wowsup.R;

// 프로젝트에 공통적으로 필요에 의한 클래스
public class Common {

    //서버 기본 주소
    public static final String API_BASE_URL = "http://www.heywowsup.com/wowsup/";
    public static final String API_IMAGE_BASE_URL = "http://www.heywowsup.com/wowsup/Image/";
    public static final String USER_IMAGE_BASE_URL = "http://www.heywowsup.com/wowsup/Image/Userprofile/";
    public static final String STORY_IMAGE_BASE_URL = "http://www.heywowsup.com/wowsup/Image/Background/";
    public static final String STORY_IMAGE_USER_URL = "http://www.heywowsup.com/wowsup/Image/UserStory/";

    //탭을 클릭한 필드값
    public static boolean fragmentProfileTab = true;
    public static boolean fragmentChatTab = true;
    public static boolean fragmentStoryTab = true;
    public static boolean fragmentHelpTab = true;

    //검색 및 번역에 필요한 필드값
    public static String searchTagText = null;
    public static int translateOption = 0;
    public static int option = 0;

    //기본적인 10개 색
    public static final int[] NONPICK_BANNER = {
            Color.rgb(201,223,241),
            Color.rgb(239,231,204),
            Color.rgb(240,244,244),
            Color.rgb(126,128,130),
            Color.rgb(239,191,168),
            Color.rgb(218,211,206),
            Color.rgb(100,117,122),
            Color.rgb(146,182,187),
            Color.rgb(183,187,189),
            Color.rgb(246,224,209)
    };

    // 업데이트시 변경되어야할 배너 색
    public static final int[] PICK_BANNER = {
            R.drawable.click_color_1_st,
            R.drawable.click_color_2_nd,
            R.drawable.click_color_3_rd,
            R.drawable.click_color_4_th,
            R.drawable.click_color_5_th,
            R.drawable.click_color_6_th,
            R.drawable.click_color_7_th,
            R.drawable.click_color_8_th,
            R.drawable.click_color_9_th,
            R.drawable.click_color_10_th,
    };

    // 탭 선택 플래그 함수수
    public static void setTabFlag(){
        fragmentProfileTab = true;
        fragmentChatTab = true;
        fragmentStoryTab = true;
        fragmentHelpTab = true;
    }

    // 이미지 사이즈 구하는 함수
    public static Drawable fixImageSize(Resources resources, Drawable image, int width, int height){
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        return new BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, width, height, true));
    }

    // 서버통신시 실행되어야할 프로그래스 바 클래스
    public static class ProgressbarDialog {
        private Context context;
        final Dialog dlg;
        public ProgressbarDialog(Context context)
        {
            this.context = context;
            dlg= new Dialog(this.context);
            dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dlg.setCanceledOnTouchOutside(false);
        }

        public void callFunction()
        {
            dlg.setContentView(R.layout.progresbar_dialog);
            dlg.show();
        }
        public void endWork()
        {
            dlg.dismiss();
        }
    }
}
