package com.seok.seok.wowsup.fragments.fragprofile;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {
    private View view;
    private Button button;
    // Card 관련
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView profileImage;
    private ArrayList<CardData> cardViewData;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardViewData = new ArrayList<>();
        cardViewData.add(new CardData("","","","",""));
        mAdapter = new CardAdapter(cardViewData, this.getContext());
        initDataSet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Common.fragmentProfileTab) {
            view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_profile_view);
            mRecyclerView.setAdapter(mAdapter);
            profileImage = view.findViewById(R.id.fragment_profile_image);

            button = view.findViewById(R.id.button2);   // 알림 버튼 눌르기 구현
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity().getApplication(), NoticeActivity.class));
                }
            });

            //서버에서 이미지 받아올것
            ApiUtils.getProfileService().requestImageURL(GlobalWowToken.getInstance().getId()).enqueue(new Callback<ResponseProfileObj>() {
                @Override
                public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                    if(response.isSuccessful()){
                        ResponseProfileObj body = response.body();
                        if(response.isSuccessful()){
                            try {
                                if (!body.getImageURL().equals(null)) {
                                    Glide.with(getActivity()).load(Common.API_IMAGE_BASE_URL+body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getActivity())).override(300, 300).into(profileImage);
                                }
                            }catch (Exception e){
                                Glide.with(getActivity()).load(Common.USER_IMAGE_BASE_URL).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getActivity())).override(300, 300).into(profileImage);
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseProfileObj> call, Throwable t) {

                }
            });
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            mRecyclerView.scrollToPosition(0);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            Common.fragmentProfileTab = false;
        }
        return view;
    }

    private void initDataSet() {
        ApiUtils.getProfileService().requestMyStory(GlobalWowToken.getInstance().getId()).enqueue(new Callback<List<ResponseStoryObj>>() {
            @Override
            public void onResponse(Call<List<ResponseStoryObj>> call, Response<List<ResponseStoryObj>> response) {
                if (response.isSuccessful()) {
                    List<ResponseStoryObj> body = response.body();
                    Log.d("fragment_Profile : ", body.size() + "");
                    for (int i = 0; i <body.size(); i++) {
                        cardViewData.add(new CardData(body.get(i).getStoryID() + "",
                                body.get(i).getUserID() + "", body.get(i).getTitle() + "",
                                body.get(i).getBody() + "", body.get(i).getCntLike() + ""));
                        Log.d("cardView ProfileID: ", body.get(i).getStoryID() + "");
                        Log.d("cardView UserID: ", body.get(i).getUserID() + "");
                        Log.d("cardView Title: ", body.get(i).getTitle() + "");
                        Log.d("cardView Body: ", body.get(i).getBody() + "");
                        Log.d("cardView Like: ", body.get(i).getCntLike() + "");
                        if (mAdapter.getItemCount()-1 == body.size()) {
                            Log.d("profile Adapter : ", mAdapter.getItemCount() + "");
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
                Log.d("fragments_Profile", t.getMessage() + " < ");
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
