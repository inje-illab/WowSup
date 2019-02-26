package com.seok.seok.wowsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;

public class BackgroundViewDialog extends Dialog implements View.OnClickListener{
    private static final int LAYOUT = R.layout.layout_story_background_dialog;

    private Context context;
    private String imageURL;
    private ImageView dialogImage;

    public BackgroundViewDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BackgroundViewDialog(Context context,String imageURL){
        super(context);
        this.context = context;
        this.imageURL = imageURL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        dialogImage = findViewById(R.id.dialog_background);
        Glide.with(this.context).load(this.imageURL).into(dialogImage);
    }

    @Override
    public void onClick(View v) {

    }
}