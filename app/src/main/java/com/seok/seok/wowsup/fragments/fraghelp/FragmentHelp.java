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
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseWordChartObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.DeveloperDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHelp extends Fragment {

    private EditText editText;
    private Button button, iBtnMore;
    private View view;
    private String delimiter;
    private int wordCount;
    private Map<String, String> wordMap;
    private OnFragmentInteractionListener mListener;
    private PieChart globalHitWordChart;

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
        iBtnMore = view.findViewById(R.id.fragment_help_more);
        iBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeveloperDialog dialog = new DeveloperDialog(view.getContext());
                dialog.show();
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

    public static final int[] MY_COLORS = {
            Color.rgb(201,223,241),
            Color.rgb(239,231,204),
            Color.rgb(255,255,255),
            Color.rgb(126,128,130),
            Color.rgb(239,191,168),
            Color.rgb(218,211,206),
            Color.rgb(100,117,122),
            Color.rgb(146,182,187),
            Color.rgb(183,187,189),
            Color.rgb(246,224,209)
    };

    public void initChart() {
        globalHitWordChart = view.findViewById(R.id.fragment_help_chart);

        globalHitWordChart.setUsePercentValues(true);
        globalHitWordChart.setDescription("");
        globalHitWordChart.setRotationEnabled(true);

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
                    PieDataSet dataSet = new PieDataSet(wordValueList, "");
                    //This week's word fashion graph



                    //그래프 크기 조절
                    dataSet.setSliceSpace(3f);
                    dataSet.setSelectionShift(10f);

                    PieData data = new PieData(wordList, dataSet);
                    globalHitWordChart.setData(data);
                    globalHitWordChart.invalidate();
                    globalHitWordChart.getLegend ().setEnabled ( false );



                    //글짜 크기 하고 색
                    data.setValueFormatter(new MyValueFormatter());
                    data.setValueTextSize(20f);
                    data.setValueTextColor(Color.rgb(65,170,112));



                    for (int c : MY_COLORS)
                        colors.add(c);
                    dataSet.setColors(colors);

                    //밑에꺼 건들기.
                    Legend l = globalHitWordChart.getLegend();

                    l.setEnabled(false);
/*                    l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER); // 범례 위치조정
                    l.setForm(Legend.LegendForm.CIRCLE);
                    l.setStackSpace(5);
                            //setWordWrapEnable(boolean enabled)
                    l.setFormToTextSpace(3); //범례 레이블과 해당 범례 양식 사이의 간격을 설정합니다.
                    l.setXEntrySpace(5); // x 축에서 범례 항목 사이의 공백을 설정
                    l.setYEntrySpace(3); // y 축에서 범례 항목 사이의 공백을 설정
                    l.setTextSize(12f);  // 글짜 크기조절
                    l.setFormSize (12f); // 도형의 크기 조절*/

                    globalHitWordChart.animateXY(3000, 3000);
                }
            }
            @Override
            public void onFailure(Call<List<ResponseWordChartObj>> call, Throwable t) {
                Log.d("FragmentHelp_HTTP_CHART", "HTTP Transfer Failed");
            }
        });
    }
    public class MyValueFormatter implements ValueFormatter {
        private DecimalFormat mFormat;
        public MyValueFormatter() {
            // use one decimal if needed
            mFormat = new DecimalFormat("###,###,##0");
        }
        @Override
        public String getFormattedValue(float value) {
            // e.g. append a dollar-sign
            return mFormat.format(value) + "";
        }
    }
}
