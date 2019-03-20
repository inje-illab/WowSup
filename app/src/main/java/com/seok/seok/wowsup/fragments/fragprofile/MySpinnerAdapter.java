package com.seok.seok.wowsup.fragments.fragprofile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseCountry;

import java.util.ArrayList;

public class MySpinnerAdapter extends BaseAdapter {
    Context _context;
    ArrayList<ResponseCountry> _list;

    public MySpinnerAdapter(Context context, ArrayList<ResponseCountry> list)
    {
        _context = context;
        _list = list;
    }

    @Override
    public int getCount() {
        return (_list == null)?0:  _list.size();
    }

    @Override
    public Object getItem(int i) {
        return _list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)_context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_item,viewGroup,false);
        }
        ResponseCountry country = _list.get(i);

        TextView txtTitle = (TextView)view.findViewById(R.id.txtTitle);
        ImageView imgView = (ImageView)view.findViewById(R.id.imageView);
        txtTitle.setText(country.get_name());
        imgView.setImageResource(country.get_flagId());
        return view;
    }
}
