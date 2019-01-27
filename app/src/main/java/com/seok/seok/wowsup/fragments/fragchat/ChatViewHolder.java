package com.seok.seok.wowsup.fragments.fragchat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seok.seok.wowsup.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public ImageView chatFriendImage;
    public TextView chatFriend;
    public Button chatFriendOption;

    public ChatViewHolder(View itemView) {
        super(itemView);
        this.chatFriendImage = itemView.findViewById(R.id.fragment_chat_firend_image);
        this.chatFriend = itemView.findViewById(R.id.fragment_chat_firend);
        this.chatFriendOption = itemView.findViewById(R.id.fragment_chat_firend_option);
    }
}