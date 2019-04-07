package com.seok.seok.wowsup.fragments.fragprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
//Profile 프레그먼트 클래스
public class FragmentProfile extends Fragment {
    //프로필 페이지 프레그먼트 구성하기위한 필드값
    private View view;
    private Button btnNotice;
    private TextView textLike, textFriend,textToken;
    // Card 관련
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private ImageView profileImage, iBtnWrite;
    private LinearLayout layoutStore;
    private ArrayList<CardData> cardViewData;

    private Common.ProgressbarDialog progressbarDialog;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardViewData = new ArrayList<>();
        mAdapter = new CardAdapter(cardViewData, this.getContext());
    }
    //프로필 프레그먼트 레이아웃 연결
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        progressbarDialog = new Common.ProgressbarDialog(view.getContext());
        initDataSet();
        initFindViewID();

        mRecyclerView.setAdapter(mAdapter);
        //프로필에 필요한 서버 통신
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
            //서버통신 실패
            @Override
            public void onFailure(Call<ResponseProfileObj> call, Throwable t) {
                Log.d("ProfileFragment_HTTP_GETPROFILE", "HTTP Transfer Failed");
            }
        });
        Common.fragmentProfileTab = false;
        return view;
    }
    //초기 데이터 설정 (스토리)
    private void initDataSet() {
        Log.d("ProfileFragment_INITDATASET", "DATE_SET Success");
        progressbarDialog.callFunction();
        //내가쓴 스토리 서버 통신
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
                        progressbarDialog.endWork();
                    }
                }
            }
            //서버통신 실패
            @Override
            public void onFailure(Call<List<ResponseStoryObj>> call, Throwable t) {
                Log.d("ProfileFragment_INITDATASET", "DATE_SET Transfer Failed");
                progressbarDialog.endWork();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    //각 버튼에대한 리스너
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_profile_btn_notice://친구 친성 알림
                    startActivity(new Intent(getActivity().getApplication(), NoticeActivity.class));
                    break;
                case R.id.fragment_profile_image://프로필 관리 페이지 이동
                    startActivity(new Intent(getActivity().getApplication(), SupPeopleInformationActivity.class));
                    break;
                case R.id.fragment_profile_write://스토리 쓰기 페이지 이동
                    startActivity(new Intent(getActivity().getApplication(), StoryWriteActivity.class));
                    break;
                case R.id.layout_store://스토어로 이동
                    startActivity(new Intent(getActivity().getApplication(), StoreActivity.class));
                    break;
            }
        }
    };
    //프로필 UI를 관리하기위해 필드값 레이아웃과 연결
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
