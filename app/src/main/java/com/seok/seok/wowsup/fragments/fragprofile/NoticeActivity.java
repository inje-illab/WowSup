package com.seok.seok.wowsup.fragments.fragprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.seok.seok.wowsup.R;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoticeAdapter adapter;
    private ArrayList<NoticeData> cardData;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        recyclerView = findViewById(R.id.fragment_notice_view);
        cardData = new ArrayList<>();
        adapter = new NoticeAdapter(cardData, this);
        cardData.add(new NoticeData("1"));
        cardData.add(new NoticeData("2qwe"));
        cardData.add(new NoticeData("3dsv"));
        cardData.add(new NoticeData("4kjsodfj"));


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.scrollToPosition(0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
    }
}

