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
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStory extends Fragment {
    private View view;
    private EditText editTextSearch;
    private Button buttonSearch;

    // Card 관련
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private ArrayList<CardData> cardViewData;

    public FragmentStory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardViewData = new ArrayList<>();
        mAdapter = new CardAdapter(cardViewData);
        initDataSet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_story, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_story_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        editTextSearch = view.findViewById(R.id.fragment_stroy_edittext_search);
        buttonSearch = view.findViewById(R.id.fragment_stroy_button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewData.clear();
                ApiUtils.getStoryService().requestStoryTagView(editTextSearch.getText().toString()).enqueue(new Callback<List<ResponseStoryObj>>() {
                    @Override
                    public void onResponse(Call<List<ResponseStoryObj>> call, Response<List<ResponseStoryObj>> response) {
                        if(response.isSuccessful()){
                            List <ResponseStoryObj> body = response.body();
                            Log.d("asdf", body.size()+"");
                            for(int i = 0;  i< body.size(); i++){
                                cardViewData.add(new CardData(body.get(i).getStoryID()+"",
                                        body.get(i).getUserID()+"", body.get(i).getTitle()+"",
                                        body.get(i).getBody()+"", body.get(i).getCntLike()+""));
                                Log.d("cardView StoryID: " , body.get(i).getStoryID()+"");
                                Log.d("cardView UserID: " , body.get(i).getUserID()+"");
                                Log.d("cardView Title: " , body.get(i).getTitle()+"");
                                Log.d("cardView Body: " , body.get(i).getBody()+"");
                                Log.d("cardView Like: " , body.get(i).getCntLike()+"");
                                if(mAdapter.getItemCount() == body.size()){
                                    Log.d("asdfasdf", mAdapter.getItemCount()+"");
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        } else {
                            Log.d("FILE", "server contact failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseStoryObj>> call, Throwable t) {
                        Toast.makeText(getActivity(), "통신오류", Toast.LENGTH_SHORT).show();
                        Log.d("fragments_Story", t.getMessage() + " < ");
                    }
                });
            }
        });
        return view;
    }
    private void initDataSet() {
        ApiUtils.getStoryService().requestStoryView().enqueue(new Callback<List<ResponseStoryObj>>() {
            @Override
            public void onResponse(Call<List<ResponseStoryObj>> call, Response<List<ResponseStoryObj>> response) {
                if(response.isSuccessful()){
                    List <ResponseStoryObj> body = response.body();
                    Log.d("asdf", body.size()+"");
                    for(int i = 0;  i< body.size(); i++){
                        cardViewData.add(new CardData(body.get(i).getStoryID()+"",
                                body.get(i).getUserID()+"", body.get(i).getTitle()+"",
                                body.get(i).getBody()+"", body.get(i).getCntLike()+""));
                        Log.d("cardView StoryID: " , body.get(i).getStoryID()+"");
                        Log.d("cardView UserID: " , body.get(i).getUserID()+"");
                        Log.d("cardView Title: " , body.get(i).getTitle()+"");
                        Log.d("cardView Body: " , body.get(i).getBody()+"");
                        Log.d("cardView Like: " , body.get(i).getCntLike()+"");
                        if(mAdapter.getItemCount() == body.size()){
                            Log.d("asdfasdf", mAdapter.getItemCount()+"");
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                } else {
                    Log.d("FILE", "server contact failed");
                }
            }
            @Override
            public void onFailure(Call<List<ResponseStoryObj>> call, Throwable t) {
                Toast.makeText(getActivity(), "통신오류", Toast.LENGTH_SHORT).show();
                Log.d("fragments_Story", t.getMessage() + " < ");
            }
        });
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
