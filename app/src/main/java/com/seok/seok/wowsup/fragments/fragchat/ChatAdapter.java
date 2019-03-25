package com.seok.seok.wowsup.fragments.fragchat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseChatObj;
import com.seok.seok.wowsup.utilities.ChatOptionDialog;


import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private static int LAYOUT;
    private Context context;
    private ChatOptionDialog chatOptionDialog;
    private List<ResponseChatObj> chatApiObject;


    public ChatAdapter(Context context, List<ResponseChatObj> apiObjects) {
        this.context = context;
        this.chatApiObject = apiObjects;
        chatOptionDialog = new ChatOptionDialog(context);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_list, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, final int position) {
        final ResponseChatObj body = chatApiObject.get(position);
        Glide.with(context.getApplicationContext()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(context.getApplicationContext())).into(holder.chatFriendImage);
        // holder.chatFriendOption.setText(body.getFriend());
        holder.chatFriend.setText(body.getFriendNick());
        holder.chatFriendSelfish.setText(body.getSelfish());
        holder.chatFriendOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stFriendID = chatApiObject.get(position).getFriend();
                Intent intent = new Intent(ChatAdapter.this.context, ChatActivity.class);
                intent.putExtra("friendUid", stFriendID);
                context.startActivity(intent);
            }
        });
        holder.chatFriendOption.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                chatOptionDialog.chatOtptionFunction();

                Toast.makeText(context, body.getFriend(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatApiObject.size();
    }
}
