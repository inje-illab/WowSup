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

//알림 어댑터 리싸이클러뷰를 이용한 클래스
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    // 어댑터에 들어갈 필드값
    private Context context;
    private ArrayList<NoticeData> items;
    private View view;
    private ViewHolder viewHolder;
    private TextView userID;
    private Button btnYes, btnNo;
    //어댑터생성자
    public NoticeAdapter(ArrayList<NoticeData> DataSet, Context context){
        items = DataSet;
        this.context = context;
    }
    //레이아웃과 연결시키기위한 함수
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice_dialog, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    //레이아웃에 있는 UI 연동시킬 아이디값
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        userID = viewHolder.itemView.findViewById(R.id.dialog_confirm_text_userid);
        btnYes = viewHolder.itemView.findViewById(R.id.dialog_notice_btn_yes);
        btnNo = viewHolder.itemView.findViewById(R.id.dialog_notice_btn_no);
        final NoticeData item = items.get(position);
        if(item.getStatus()==1) {
            userID.setText(item.getUserID());
        }
        //no 버튼을 눌렀을 경우
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
        //yes 버튼을 눌렀을 경우 서버 통신
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
