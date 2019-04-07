package com.seok.seok.wowsup.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seok.seok.wowsup.R;

import java.util.ArrayList;

//드롭박스 어댑터
public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ResponseCountry> list;

    public SpinnerAdapter(Context context, ArrayList<ResponseCountry> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list == null)?0:  list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_item,viewGroup,false);
        }
        ResponseCountry country = list.get(i);

        TextView txtTitle = (TextView)view.findViewById(R.id.txtTitle);
        ImageView imgView = (ImageView)view.findViewById(R.id.imageView);
        txtTitle.setText(country.getName());
        imgView.setImageResource(country.getFlagId());
        return view;
    }
}
