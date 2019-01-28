package com.seok.seok.wowsup.fragments.fragstroy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.fragments.fragprofile.CardAdapter;
import com.seok.seok.wowsup.fragments.fragprofile.CardData;

import java.util.ArrayList;

public class FragmentStory extends Fragment {
    private View view;

    // Card 관련
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CardData> TestData;

    public FragmentStory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initDataset();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_story, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_story_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mAdapter = new CardAdapter(TestData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }
    private void initDataset() {
        //for Test
        TestData = new ArrayList<>();
        TestData.add(new CardData("1111", "TEST1"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("4444", "TEST4"));
        TestData.add(new CardData("5555", "TEST5"));
        TestData.add(new CardData("6666", "TEST6"));
        TestData.add(new CardData("7777", "TEST7"));
        TestData.add(new CardData("2222", "TEST8"));
        TestData.add(new CardData("3333", "TEST9"));
        TestData.add(new CardData("1111", "TEST"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("1111", "TEST1"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));

        TestData.add(new CardData("6666", "TEST6"));
        TestData.add(new CardData("7777", "TEST7"));
        TestData.add(new CardData("2222", "TEST8"));
        TestData.add(new CardData("3333", "TEST9"));
        TestData.add(new CardData("1111", "TEST"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("1111", "TEST1"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("6666", "TEST6"));
        TestData.add(new CardData("7777", "TEST7"));
        TestData.add(new CardData("2222", "TEST8"));
        TestData.add(new CardData("3333", "TEST9"));
        TestData.add(new CardData("1111", "TEST"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("1111", "TEST1"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("6666", "TEST6"));
        TestData.add(new CardData("7777", "TEST7"));
        TestData.add(new CardData("2222", "TEST8"));
        TestData.add(new CardData("3333", "TEST9"));
        TestData.add(new CardData("1111", "TEST"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("1111", "TEST1"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("6666", "TEST6"));
        TestData.add(new CardData("7777", "TEST7"));
        TestData.add(new CardData("2222", "TEST8"));
        TestData.add(new CardData("3333", "TEST9"));
        TestData.add(new CardData("1111", "TEST"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("1111", "TEST1"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("6666", "TEST6"));
        TestData.add(new CardData("7777", "TEST7"));
        TestData.add(new CardData("2222", "TEST8"));
        TestData.add(new CardData("3333", "TEST9"));
        TestData.add(new CardData("1111", "TEST"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("1111", "TEST1"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("6666", "TEST6"));
        TestData.add(new CardData("7777", "TEST7"));
        TestData.add(new CardData("2222", "TEST8"));
        TestData.add(new CardData("3333", "TEST9"));
        TestData.add(new CardData("1111", "TEST"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
        TestData.add(new CardData("1111", "TEST1"));
        TestData.add(new CardData("2222", "TEST2"));
        TestData.add(new CardData("3333", "TEST3"));
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
