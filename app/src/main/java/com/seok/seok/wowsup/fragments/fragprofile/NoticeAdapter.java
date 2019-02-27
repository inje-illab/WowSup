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

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.StoryActivity;
import com.seok.seok.wowsup.utilities.Common;

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
        textView = view.findViewById(R.id.textView2);
        button = view.findViewById(R.id.button3);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final NoticeData item = items.get(position);
        textView.setText(item.getUserID() + "님이 친구 신청을 했습니다.");
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
