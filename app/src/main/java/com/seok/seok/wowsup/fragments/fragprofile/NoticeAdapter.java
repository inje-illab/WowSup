package com.seok.seok.wowsup.fragments.fragprofile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.StoryActivity;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;
import com.seok.seok.wowsup.utilities.ViewDialog;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NoticeData> items;
    private View view;
    private ViewHolder viewHolder;
    private TextView textView;
    private Button button;
    public NoticeAdapter(ArrayList<NoticeData> DataSet, Context context){
        items = DataSet;
        this.context = context;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notice_list, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        textView = viewHolder.itemView.findViewById(R.id.textView2);
        button = viewHolder.itemView.findViewById(R.id.button3);
        final NoticeData item = items.get(position);
        if(item.getStatus()==1) {
            textView.setText(item.getUserID() + "님이 친구 신청을 했습니다.");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getUserID(), Toast.LENGTH_SHORT).show();
                ViewDialog viewDialog = new ViewDialog(context, 1);
                viewDialog.setButtonText("거절", "수락");
                viewDialog.requestApplyFriend(GlobalWowToken.getInstance().getId(),item.getUserID());
                viewDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<NoticeData> items) {
        this.items = items;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
