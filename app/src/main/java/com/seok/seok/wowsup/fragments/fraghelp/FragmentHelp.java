package com.seok.seok.wowsup.fragments.fraghelp;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseChatWordObj;
import com.seok.seok.wowsup.retrofit.model.ResponseMailObj;
import com.seok.seok.wowsup.retrofit.model.ResponseWordChartObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import java.util.ArrayList;
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
    private PieChart globalHitWordChart;

    public static final int[] MY_COLORS = {
            Color.parseColor("#c9dff1"),
            Color.parseColor("#efe7cc"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#808183"),
            Color.parseColor("#efbfa8"),
            Color.parseColor("#dad3ce"),
            Color.parseColor("#64757a"),
            Color.parseColor("#94b6bb"),
            Color.parseColor("#b7bbbd"),
            Color.parseColor("#f6e0d1")
    };

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
                        wordMap.put((wordCount++) + "", word);
                    }
                }

                ApiUtils.getWordService().requestChatWord(wordMap).enqueue(new Callback<List<ResponseChatWordObj>>() {
                    @Override
                    public void onResponse(Call<List<ResponseChatWordObj>> call, Response<List<ResponseChatWordObj>> response) {
                        Log.d("mapTrans", "map trans success");
                    }

                    @Override
                    public void onFailure(Call<List<ResponseChatWordObj>> call, Throwable t) {
                        Log.d("mapTrans", t.getMessage());
                    }
                });
                wordCount = 0;
                wordMap.clear();
            }
        });
        initChart();
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
    public void initChart() {
        final PieChart pieChart = view.findViewById(R.id.fragment_help_chart);
        final ArrayList<Entry> wordValueList = new ArrayList<>();
        final ArrayList<String> wordList = new ArrayList<>();
        final ArrayList<Integer> colors = new ArrayList<>();

        ApiUtils.getWordService().requestWordChart().enqueue(new Callback<List<ResponseWordChartObj>>() {
            @Override
            public void onResponse(Call<List<ResponseWordChartObj>> call, Response<List<ResponseWordChartObj>> response) {
                Log.d("FragmentHelp_HTTP_CHART", "HTTP Transfer Success");
                if (response.isSuccessful()) {
                    Log.d("FragmentHelp_HTTP_CHART", "HTTP response Success");
                    List<ResponseWordChartObj> body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        wordValueList.add(new Entry(body.get(i).getWordCount(), i));
                        wordList.add(body.get(i).getWord());
                    }
                    PieDataSet dataSet = new PieDataSet(wordValueList, "This week's word fashion graph");
                    PieData data = new PieData(wordList, dataSet);
                    pieChart.setData(data);
                    for (int c : MY_COLORS)
                        colors.add(c);
                    dataSet.setColors(colors);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseWordChartObj>> call, Throwable t) {
                Log.d("FragmentHelp_HTTP_CHART", "HTTP Transfer Failed");
            }
        });
        pieChart.animateXY(5000, 5000);
    }
}
