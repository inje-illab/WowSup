package com.seok.seok.wowsup.fragments.fragstory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.fragments.CardAdapter;
import com.seok.seok.wowsup.fragments.CardData;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Story 프레그먼트 클래스
public class FragmentStory extends Fragment {
    //스토리 페이지 프레그먼트 구성하기위한 필드값
    private View view;
    private EditText editTextSearch;
    private Button buttonSearch;
    private LinearLayout layoutTagTitle;
    private TextView textTagTitle;

    // Card 관련
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private ArrayList<CardData> cardViewData;
    private Common.ProgressbarDialog progressbarDialog;
    public FragmentStory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardViewData = new ArrayList<>();
        mAdapter = new CardAdapter(cardViewData, this.getContext());
    }
    //스토리 프레그먼트 레이아웃 연결
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_story, container, false);
        progressbarDialog = new Common.ProgressbarDialog(view.getContext());
        initDataSet();
        initFindViewID();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
    //서버에있는 스토리 모두 들고오기
    private void initDataSet() {
        progressbarDialog.callFunction();
        ApiUtils.getStoryService().requestStoryView().enqueue(new Callback<List<ResponseStoryObj>>() {
            @Override
            public void onResponse(Call<List<ResponseStoryObj>> call, Response<List<ResponseStoryObj>> response) {
                if (response.isSuccessful()) {
                    List<ResponseStoryObj> body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        cardViewData.add(new CardData(body.get(i).getStoryID() + "",
                                body.get(i).getUserID() + "", body.get(i).getTitle() + "",
                                body.get(i).getBody() + "", body.get(i).getCntLike() + "", body.get(i).getImageURL()));
                    }
                    if (mAdapter.getItemCount() == body.size()) {
                        mRecyclerView.setAdapter(mAdapter);
                        progressbarDialog.endWork();
                    }
                }
                progressbarDialog.endWork();
            }

            @Override
            public void onFailure(Call<List<ResponseStoryObj>> call, Throwable t) {
                Toast.makeText(getActivity(), "통신오류", Toast.LENGTH_SHORT).show();
                progressbarDialog.endWork();
                Log.d("fragments_Story", t.getMessage() + " < ");
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    //UI와 레이아웃 필드값 연결
    public void initFindViewID() {
        mRecyclerView = view.findViewById(R.id.fragment_story_view);
        layoutTagTitle = view.findViewById(R.id.fragment_story_layout_topic);
        textTagTitle = view.findViewById(R.id.fragment_story_textview_topic);
        editTextSearch = view.findViewById(R.id.fragment_story_edittext_search);
        buttonSearch = view.findViewById(R.id.fragment_story_button_search);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //검색버튼을 눌렀을 경우
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewData.clear();
                mAdapter.setItems(cardViewData);
                mRecyclerView.setAdapter(mAdapter);
                //해당 해쉬태그로 서버통신시작
                ApiUtils.getStoryService().requestStoryTagView(editTextSearch.getText().toString()).enqueue(new Callback<List<ResponseStoryObj>>() {
                    @Override
                    public void onResponse(Call<List<ResponseStoryObj>> call, Response<List<ResponseStoryObj>> response) {
                        Log.d("StoryFragment_HTTP_GETSTORY", "HTTP Transfer Success");
                        if (response.isSuccessful()) {
                            Log.d("StoryFragment_HTTP_GETSTORY", "HTTP Response Success");
                            List<ResponseStoryObj> body = response.body();
                            if (body.size() == 0) {
                                textTagTitle.setText("Topic Tag : # " + Common.searchTagText);
                                layoutTagTitle.setVisibility(View.VISIBLE);
                            } else {
                                for (int i = 0; i < body.size(); i++) {
                                    cardViewData.add(new CardData(body.get(i).getStoryID() + "",
                                            body.get(i).getUserID() + "", body.get(i).getTitle() + "",
                                            body.get(i).getBody() + "", body.get(i).getCntLike() + "", body.get(i).getImageURL()));
                                }
                                if (mAdapter.getItemCount() == body.size()) {
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        }
                    }

                    //서버통신 실패
                    @Override
                    public void onFailure(Call<List<ResponseStoryObj>> call, Throwable t) {
                        Toast.makeText(getActivity(), "통신오류", Toast.LENGTH_SHORT).show();
                        Log.d("fragments_Story", t.getMessage() + " < ");
                    }
                });
            }
        });
    }
}
