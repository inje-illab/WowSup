package com.seok.seok.wowsup.fragments.fragchat;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
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


/**
 * 단순한 하위 클래스.
 *   * 이 단편을 포함하는 활동은
 *   * FragmentChat.OnFragmentInteractionListener 인터페이스
 *   * 상호 작용 이벤트를 처리합니다.
 *   * FragmentChat # newInstance 팩토리 메소드를 사용해,
 *   * 이 조각의 인스턴스를 만듭니다.
 */

public class FragmentChat extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ImageView chatUserImage;
    private TextView chatUserID;
    // TODO : 매개 변수 인수 이름 바꾸기, 일치하는 이름 선택

    // 조각 초기화 매개 변수 (예 : ARG_ITEM_NUMBER)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO : 매개 변수 유형 이름 바꾸기 및 변경
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentChat() {
        // 필수 public 생성자
        // 제일 처음 로그인 했을때 뜨는 인터넷 연결 창 해야함
        Log.d("Fragments__", "<< FragmentChat");
    }

    /**
     * 이 팩토리 메서드를 사용하여의 새 인스턴스를 만듭니다.
     * 이 단편은 제공된 매개 변수를 사용합니다.
     *
     * @param param1 매개 변수 1.
     * @param param2 매개 변수 2.
     * @return 프래그먼트 FragmentChat의 새로운 인스턴스.
     */
    // TODO : 유형 및 매개 변수의 수 이름 바꾸기 및 변경
    public static FragmentChat newInstance(String param1, String param2) {
        FragmentChat fragment = new FragmentChat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragments__", "<< onCreateFragments");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 이 단편에 대한 레이아웃을 팽창시킵니다.
        if (Common.fragmentChatTab) {
            Log.d("Fragments__", "<< onCreateViewFragments");
            view = inflater.inflate(R.layout.fragment_fragment_chat, container, false);
            recyclerView = view.findViewById(R.id.fragment_chat_listview);
            chatUserImage = view.findViewById(R.id.fragment_chat_user_image);
            chatUserID = view.findViewById(R.id.fragment_chat_user_id);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            ApiUtils.getProfileService().requestMyProfile(GlobalWowToken.getInstance().getId()).enqueue(new Callback<ResponseProfileObj>() {
                @Override
                public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                    if(response.isSuccessful()){
                        ResponseProfileObj body = response.body();
                        if(response.isSuccessful()){
                            try {
                                if (!body.getImageURL().equals(null)) {
                                    Glide.with(getActivity()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getActivity())).override(300, 300).into(chatUserImage);
                                }
                            }catch (Exception e){
                                Glide.with(getActivity()).load(Common.USER_IMAGE_BASE_URL+"basic.png").centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getActivity())).override(300, 300).into(chatUserImage);
                            }
                            chatUserID.setText("User ID : " + GlobalWowToken.getInstance().getId());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseProfileObj> call, Throwable t) {

                }
            });
            ApiUtils.getChatService().requestChatFriend(GlobalWowToken.getInstance().getId()).enqueue(new Callback<List<ResponseChatObj>>() {
                @Override
                public void onResponse(Call<List<ResponseChatObj>> call, Response<List<ResponseChatObj>> response) {
                    if(response.isSuccessful()){
                        List<ResponseChatObj> body = response.body();
                        ChatAdapter chatAdapter = new ChatAdapter(getActivity(), body);
                        recyclerView.setAdapter(chatAdapter);
                    }
                }
                @Override
                public void onFailure(Call<List<ResponseChatObj>> call, Throwable t) {
                    Log.d("fragmentChat_err : ", t.getMessage() + " < ");
                }
            });
            Common.fragmentChatTab = false;
        }
        return view;
    }

    // TODO : 메서드 이름 바꾸기, 인수 업데이트 및 메서드 후크
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Fragments__", "<< onDetachFragments");
        mListener = null;
    }

    /**
     * 이 인터페이스는 이것을 포함하는 액티비티에 의해 구현되어야합니다.
     * 이 단편과의 상호 작용을 가능하게하는 단편
     * 그 활동에 포함되어있는 잠재적 인 다른 단편들
     * 활동.
     * <p>
     * Android 교육 강의 <a href =
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * > 다른 단편과 의사 소통하기 </a>를 참조하십시오.
     */

    public interface OnFragmentInteractionListener {
        // TODO : 인수 유형 및 이름 업데이트
        void onFragmentInteraction(Uri uri);
    }
}

