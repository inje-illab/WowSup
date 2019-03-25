package com.seok.seok.wowsup.fragments.fragprofile;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.StoreActivity;
import com.seok.seok.wowsup.SupPeopleInformationActivity;
import com.seok.seok.wowsup.fragments.CardAdapter;
import com.seok.seok.wowsup.fragments.CardData;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;
import com.seok.seok.wowsup.utilities.NoticeActivity;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {
    private View view;
    private Button btnNotice;
    private TextView textLike, textFriend,textToken;
    // Card 관련
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private ImageView profileImage, iBtnWrite;
    private LinearLayout layoutStore;
    private ArrayList<CardData> cardViewData;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardViewData = new ArrayList<>();
        mAdapter = new CardAdapter(cardViewData, this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        initDataSet();
        initFindViewID();

        mRecyclerView.setAdapter(mAdapter);
        ApiUtils.getProfileService().requestMyProfile(GlobalWowToken.getInstance().getId()).enqueue(new Callback<ResponseProfileObj>() {
            @Override
            public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                Log.d("ProfileFragment_HTTP_GETPROFILE", "HTTP Transfer Success");
                if (response.isSuccessful()) {
                    Log.d("ProfileFragment_HTTP_GETPROFILE", "HTTP Response Success");
                    ResponseProfileObj body = response.body();
                    textLike.setText(body.getCntLike() + "");
                    textFriend.setText(body.getCntFriend() + "");
                    btnNotice.setText(body.getCntNotice() + "");
                    textToken.setText(body.getToken()+"");
                    Common.option = body.getCntNotice();
                    Glide.with(getActivity()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getActivity())).into(profileImage);
                }
            }

            @Override
            public void onFailure(Call<ResponseProfileObj> call, Throwable t) {
                Log.d("ProfileFragment_HTTP_GETPROFILE", "HTTP Transfer Failed");
            }
        });
        Common.fragmentProfileTab = false;
        return view;
    }

    private void initDataSet() {
        Log.d("ProfileFragment_INITDATASET", "DATE_SET Success");
        ApiUtils.getStoryService().requestMyStory(GlobalWowToken.getInstance().getId()).enqueue(new Callback<List<ResponseStoryObj>>() {
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
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseStoryObj>> call, Throwable t) {
                Log.d("ProfileFragment_INITDATASET", "DATE_SET Transfer Failed");
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_profile_btn_notice:
                    startActivity(new Intent(getActivity().getApplication(), NoticeActivity.class));
                    break;
                case R.id.fragment_profile_image:
                    startActivity(new Intent(getActivity().getApplication(), SupPeopleInformationActivity.class));
                    break;
                case R.id.fragment_profile_write:
                    startActivity(new Intent(getActivity().getApplication(), StoryWriteActivity.class));
                    break;
                case R.id.layout_store:
                    startActivity(new Intent(getActivity().getApplication(), StoreActivity.class));
                    break;
            }
        }
    };

    public void initFindViewID() {
        mRecyclerView = view.findViewById(R.id.fragment_profile_view);
        profileImage = view.findViewById(R.id.fragment_profile_image);
        btnNotice = view.findViewById(R.id.fragment_profile_btn_notice);
        textLike = view.findViewById(R.id.fragment_profile_text_like);
        textFriend = view.findViewById(R.id.fragment_profile_text_firend);
        textToken = view.findViewById(R.id.fragment_profile_text_token);
        iBtnWrite = view.findViewById(R.id.fragment_profile_write);
        layoutStore = view.findViewById(R.id.layout_store);
        iBtnWrite.setOnClickListener(onClickListener);
        btnNotice.setOnClickListener(onClickListener);
        profileImage.setOnClickListener(onClickListener);
        layoutStore.setOnClickListener(onClickListener);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
