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
    private View view;
    private ViewHolder viewHolder;
    private TextView title;
    private TextView like;
    public CardAdapter(ArrayList<CardData> DataSet){
        items = DataSet;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_story_list, parent, false);

        title = view.findViewById(R.id.story_view_title);
        like = view.findViewById(R.id.story_view_like);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        CardData item = items.get(position);
        title.setText(item.getTitle());
        like.setText(item.getCntLike());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<CardData> items) {
        this.items = items;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
