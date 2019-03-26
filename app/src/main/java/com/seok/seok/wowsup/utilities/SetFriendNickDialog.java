package com.seok.seok.wowsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.seok.seok.wowsup.R;

public class SetFriendNickDialog {
    private Context context;
    private Dialog dlg;
    private Button btnChatfriendNickYes, btnChatfriendNickNo;
    public SetFriendNickDialog(Context context)
    {
        this.context = context;
    }

    public void NickFunction()
    {
        dlg = new Dialog(context);
        dlg.setContentView(R.layout.layout_friendnick_dialog);

        dlg.show();

        btnChatfriendNickYes = (Button)dlg.findViewById(R.id.dialog_ChatfriendNick_btn_yes);
        btnChatfriendNickYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aaa","FFFFF");
            }
        });

        btnChatfriendNickNo = (Button)dlg.findViewById(R.id.dialog_ChatfriendNick_btn_no);
        btnChatfriendNickNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endNickFunction();
            }
        });
    }

    public void endNickFunction()
    {
        dlg.dismiss();
    }
}
