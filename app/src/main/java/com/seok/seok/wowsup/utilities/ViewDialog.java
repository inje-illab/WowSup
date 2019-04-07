package com.seok.seok.wowsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.seok.seok.wowsup.R;

//스토리 백그라운드 눌렀을 경우 백그라운드 보여주기
public class ViewDialog extends Dialog implements View.OnClickListener {
    private static int LAYOUT;
    private Context context;
    private String imgURL;
    private ImageView imgBackground;
    public ViewDialog(Context context, String imgURL) {
        super(context);
        LAYOUT = R.layout.layout_background_dialog;
        this.imgURL = imgURL;
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        //글라이드 라이브러리를 사용해서 백그라운드 사진을 입힘
        imgBackground = findViewById(R.id.dialog_background);
        Glide.with(context.getApplicationContext()).load(imgURL).into(imgBackground);
    }
    @Override
    public void onClick(View v) {

    }
}
