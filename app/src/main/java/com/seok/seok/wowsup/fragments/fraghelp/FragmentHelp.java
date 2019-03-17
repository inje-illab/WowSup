package com.seok.seok.wowsup.fragments.fraghelp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseChatWordObj;
import com.seok.seok.wowsup.retrofit.model.ResponseMailObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHelp extends Fragment {

    private EditText editText;
    private Button button;
    private View view;
    private String delimiter;
    private int wordCount;
    private Map<String, String> wordMap;
    private OnFragmentInteractionListener mListener;

    public FragmentHelp() {
        wordMap = new HashMap<>();
        wordCount = 0;
        delimiter = " ";
    }

    public static FragmentHelp newInstance(String param1, String param2) {
        FragmentHelp fragment = new FragmentHelp();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_help, container, false);
        editText = view.findViewById(R.id.fragment_help_edit_word);
        button = view.findViewById(R.id.fragment_help_btn_word);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] words = editText.getText().toString().replaceAll("[^a-zA-Z]", " ").split(delimiter);
                for (String word : words) {
                    if (!word.equals("")) {
                        wordMap.put((wordCount++)+"", word);
                    }
                }

                ApiUtils.getWordService().requestChatWord(wordMap).enqueue(new Callback<List<ResponseChatWordObj>>() {
                    @Override
                    public void onResponse(Call<List<ResponseChatWordObj>> call, Response<List<ResponseChatWordObj>> response) {
                        if (response.isSuccessful()) {
                            List<ResponseChatWordObj> body = response.body();
                            for(int i = 0 ; i < body.size(); i++) {
                                Log.d("asdffdsa", body.get(i).getKey() + "    " + body.get(i).getValue());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseChatWordObj>> call, Throwable t) {
                        Log.d("asdffdsa" , t.getMessage());
                    }
                });
                wordCount = 0;
            }
        });
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
}
