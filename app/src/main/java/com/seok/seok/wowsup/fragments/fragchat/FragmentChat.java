package com.seok.seok.wowsup.fragments.fragchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.SupPeopleInformationActivity;
import com.seok.seok.wowsup.retrofit.model.ResponseChatObj;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Chat 프레그먼트 클래스
public class FragmentChat extends Fragment {
    //채팅 페이지 프레그먼트 구성하기위한 필드값
    private View view;
    private RecyclerView recyclerView;
    private ImageView chatUserImage;
    private TextView chatUserID, chatUserSelfish;
    private LinearLayout layoutBanner;
    private OnFragmentInteractionListener mListener;
    private Common.ProgressbarDialog progressbarDialog;

    public FragmentChat() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //채팅 프레그먼트 레이아웃 연결
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_chat, container, false);
        progressbarDialog = new Common.ProgressbarDialog(view.getContext());
        initFindViewID();
        initDataSet();
        Common.fragmentChatTab = false;
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    // 채팅 UI를 관리하기위해 필드값 레이아웃과 연결
    public void initFindViewID() {
        recyclerView = view.findViewById(R.id.fragment_chat_listview);
        chatUserImage = view.findViewById(R.id.fragment_chat_user_image);
        chatUserID = view.findViewById(R.id.fragment_chat_user_id);
        layoutBanner = view.findViewById(R.id.fragment_chat_lay_banner);
        chatUserSelfish = view.findViewById(R.id.fragment_chat_user_selfish);
        chatUserImage.setOnClickListener(onClickListener);
    }

    // 서버에서 받아올 친구 목록들을 뿌려줄 함수 채팅시 모르는 어휘가 나왔을 경우 번역페이지로 이동
    public void initDataSet() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        progressbarDialog.callFunction();

        //서버통신 시작
        ApiUtils.getProfileService().requestMyProfile(GlobalWowToken.getInstance().getId()).enqueue(new Callback<ResponseProfileObj>() {
            @Override
            public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                Log.d("FragmentChat_HTTP_MYPROFILE", "HTTP Transfer Success");
                if (response.isSuccessful()) {
                    ResponseProfileObj body = response.body();
                    if (response.isSuccessful()) {
                        try {
                            Glide.with(getActivity()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getActivity())).into(chatUserImage);
                            layoutBanner.setBackgroundColor(Common.NONPICK_BANNER[body.getBanner()]);
                            chatUserID.setText("User ID : " + GlobalWowToken.getInstance().getId());
                            chatUserSelfish.setText(body.getSelfish());
                        } catch (Exception e) {
                            Toast.makeText(getContext().getApplicationContext(), "Communication error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                progressbarDialog.endWork();
            }
            //서버통신 실패
            @Override
            public void onFailure(Call<ResponseProfileObj> call, Throwable t) {
                progressbarDialog.endWork();
                Log.d("FragmentChat_HTTP_MYPROFILE", "HTTP Transfer Failed");
            }
        });
        progressbarDialog.callFunction();

        //서버통신 시작
        ApiUtils.getChatService().requestChatFriend(GlobalWowToken.getInstance().getId()).enqueue(new Callback<List<ResponseChatObj>>() {
            @Override
            public void onResponse(Call<List<ResponseChatObj>> call, Response<List<ResponseChatObj>> response) {
                if (response.isSuccessful()) {
                    List<ResponseChatObj> body = response.body();
                    ChatAdapter chatAdapter = new ChatAdapter(getActivity(), body);
                    recyclerView.setAdapter(chatAdapter);
                }
                progressbarDialog.endWork();
            }
            //서버통신 실패
            @Override
            public void onFailure(Call<List<ResponseChatObj>> call, Throwable t) {
                Log.d("fragmentChat_err : ", t.getMessage() + " < ");
                progressbarDialog.endWork();
            }
        });
    }
    //개인정보관리하기위해 개인정보 관리페이지로 이동
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_chat_user_image:
                    startActivity(new Intent(view.getContext(), SupPeopleInformationActivity.class));
                    break;
            }
        }
    };
}

