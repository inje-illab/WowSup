package com.seok.seok.wowsup.utilities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseCommonObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//알림 엑티비티 다이얼로그로 띄우기위한 클래스
public class NoticeActivity extends AppCompatActivity {
    private LinearLayout background;
    private RecyclerView recyclerView;
    private NoticeAdapter adapter;
    private ArrayList<NoticeData> cardData;
    private int count = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        recyclerView = findViewById(R.id.fragment_notice_view);
        background = findViewById(R.id.layout_notice_back);
        cardData = new ArrayList<>();
        adapter = new NoticeAdapter(cardData, this);
        if(Common.option == 0) {
            background.setBackgroundResource(R.drawable.no_alarms_arrived);
            Common.option++;
        }
        //서버에서 받아올 친구목록
        ApiUtils.getCommonService().requestApplyedFriend(GlobalWowToken.getInstance().getId()).enqueue(new Callback<List<ResponseCommonObj>>() {
            @Override
            public void onResponse(Call<List<ResponseCommonObj>> call, Response<List<ResponseCommonObj>> response) {
                if(response.isSuccessful()){
                    List<ResponseCommonObj> body = response.body();
                    for(int i = 0; i<body.size(); i++){
                        if(body.get(i).getStatus()==1) {
                            count++;
                            cardData.add(new NoticeData(body.get(i).getApplyer(), body.get(i).getStatus()));
                        }
                    }
                    if (adapter.getItemCount() == count) {
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
            //통신 실패
            @Override
            public void onFailure(Call<List<ResponseCommonObj>> call, Throwable t) {

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.scrollToPosition(0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
    }
}

