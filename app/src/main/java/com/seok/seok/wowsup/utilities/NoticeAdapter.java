package com.seok.seok.wowsup.utilities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseCommonObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NoticeData> items;
    private View view;
    private ViewHolder viewHolder;
    private TextView userID;
    private Button btnYes, btnNo;
    public NoticeAdapter(ArrayList<NoticeData> DataSet, Context context){
        items = DataSet;
        this.context = context;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice_dialog, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        userID = viewHolder.itemView.findViewById(R.id.dialog_confirm_text_userid);
        btnYes = viewHolder.itemView.findViewById(R.id.dialog_notice_btn_yes);
        btnNo = viewHolder.itemView.findViewById(R.id.dialog_notice_btn_no);
        final NoticeData item = items.get(position);
        if(item.getStatus()==1) {
            userID.setText(item.getUserID());
        }
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getCommonService().requestConfirmFriend(GlobalWowToken.getInstance().getId(), item.getUserID()).enqueue(new Callback<ResponseCommonObj>() {
                    @Override
                    public void onResponse(Call<ResponseCommonObj> call, Response<ResponseCommonObj> response) {
                        if (response.isSuccessful()) {
                            ResponseCommonObj body = response.body();
                            if (body.getStatus() == 2) {
                                Toast.makeText(context, "Accept success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Accept failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                        ((Activity)context).finish();
                    }
                    @Override
                    public void onFailure(Call<ResponseCommonObj> call, Throwable t) {
                        Log.d("noticeAdapter_CONFIRM", "Confirm Transfer Failed");
                        ((Activity)context).finish();
                    }
                });
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
