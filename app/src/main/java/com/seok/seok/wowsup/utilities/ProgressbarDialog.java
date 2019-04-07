package com.seok.seok.wowsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.seok.seok.wowsup.R;

//서버통신을 하기시작할때 실행되는 프로그래스 바 클래스
public class ProgressbarDialog {
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