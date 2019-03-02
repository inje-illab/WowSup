package com.seok.seok.wowsup.fragments.fragprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseCommonObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalApplication;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoticeAdapter adapter;
    private ArrayList<NoticeData> cardData;
    private int count = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        recyclerView = findViewById(R.id.fragment_notice_view);
        cardData = new ArrayList<>();
        adapter = new NoticeAdapter(cardData, this);

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

