package com.seok.seok.wowsup.fragments.fragchat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
   * 단순한 하위 클래스.
   * 이 단편을 포함하는 활동은
   * FragmentChat.OnFragmentInteractionListener 인터페이스
   * 상호 작용 이벤트를 처리합니다.
   * FragmentChat # newInstance 팩토리 메소드를 사용해,
   * 이 조각의 인스턴스를 만듭니다.
 */

public class FragmentChat extends Fragment {

    private ImageView imageView;
    private TextView textView;
    private ListView listView;
    private Bitmap[] bitmaps;
    //이미지 배열 선언
    ArrayList<Bitmap> picArr = new ArrayList<Bitmap>();
    //텍스트 배열 선언
    ArrayList<String> textArr = new ArrayList<String>();

    private View view;

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
     *이 팩토리 메서드를 사용하여의 새 인스턴스를 만듭니다.
     *이 단편은 제공된 매개 변수를 사용합니다.
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
        if(Common.fragmentChatTab) {
            Log.d("Fragments__", "<< onCreateViewFragments");
            view = inflater.inflate(R.layout.fragment_fragment_chat, container, false);

            bitmaps = new Bitmap[10];
            for(int i = 0 ; i< 10;i++){
                bitmaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.test);
                picArr.add(bitmaps[i]);
                textArr.add("숫자" + Integer.toString(i));
            }
            listView = view.findViewById(R.id.fragment_chat_listview);
            listView.setAdapter(new Adapter());
            imageView = view.findViewById(R.id.imageView);
            textView = view.findViewById(R.id.textView2);
            ApiUtils.getProfileService().requestImageURL(GlobalWowToken.getInstance().getIdToken()).enqueue(new Callback<ResponseProfileObj>() {
                @Override
                //통신후 이미지 받아오기
                public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                    if(response.isSuccessful()){
                        ResponseProfileObj body = response.body();
                        try {
                            if (!body.getImageURL().equals(null)) {
                                Glide.with(getActivity()).load(body.getImageURL()).into(imageView);
                            }
                        }catch (Exception e){
                            Glide.with(getActivity()).load(Common.USER_IMAGE_BASE_URL).into(imageView);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseProfileObj> call, Throwable t) {

                }
            });
            textView.setText("User ID : " + GlobalWowToken.getInstance().getIdToken());
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
    public class Adapter extends BaseAdapter {
        LayoutInflater inflater;

        public Adapter() {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return picArr.size();    //그리드뷰에 출력할 목록 수
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return picArr.get(position);    //아이템을 호출할 때 사용하는 메소드
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;    //아이템의 아이디를 구할 때 사용하는 메소드
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_chat_list, parent, false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            TextView textView = (TextView) convertView.findViewById(R.id.textView1);
            imageView.setImageBitmap(picArr.get(position));
            textView.setText(textArr.get(position));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //이미지를 터치했을때 동작하는 곳
                    Log.d("touch Count", position + "");
                }
            });
            return convertView;
        }
    }
}
