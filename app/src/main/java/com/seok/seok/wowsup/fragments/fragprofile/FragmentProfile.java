package com.seok.seok.wowsup.fragments.fragprofile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.seok.seok.wowsup.R;

import java.util.ArrayList;

public class FragmentProfile extends Fragment {
    private View view;

    // Card 관련
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CardData> TestData;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataSet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mAdapter = new CardAdapter(TestData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Button bu  = view.findViewById(R.id.button2);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext().getApplicationContext(), WriteActivity.class));
            }
        });
        return view;
    }

    private void initDataSet() {
        //for Test
        TestData = new ArrayList<>();
        TestData.add(new CardData("1111", "TEST1","TEST1","TEST1","TEST1"));
        TestData.add(new CardData("1111", "TEST1","TEST1","TEST1","TEST1"));
        TestData.add(new CardData("1111", "TEST1","TEST1","TEST1","TEST1"));
        TestData.add(new CardData("1111", "TEST1","TEST1","TEST1","TEST1"));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
