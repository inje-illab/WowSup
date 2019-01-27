package com.seok.seok.wowsup.fragments.fragprofile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seok.seok.wowsup.R;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private ArrayList<CardData> items;

    public CardAdapter(ArrayList<CardData> DataSet){
        items = DataSet;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        CardData item = items.get(position);
        viewHolder.TV_text.setText(item.getText());

        viewHolder.TV_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // click 시 필요한 동작 정의
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<CardData> items) {
        this.items = items;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView TV_text;

        ViewHolder(View itemView) {
            super(itemView);
            TV_text= itemView.findViewById(R.id.TV_text);
        }
    }
}
