package com.seok.seok.wowsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.WowsupWebsiteActivity;

public class DeveloperDialog extends Dialog implements View.OnClickListener {
    private static int LAYOUT;
    private Context context;
    private ImageView iBtnWeb;
    public DeveloperDialog(Context context) {
        super(context);
        LAYOUT = R.layout.layout_stack_developer_dialog;
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        iBtnWeb = findViewById(R.id.dialog_ibtn_web);
        iBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context.getApplicationContext(), WowsupWebsiteActivity.class));
            }
        });
    }
    @Override
    public void onClick(View v) {

    }
}
