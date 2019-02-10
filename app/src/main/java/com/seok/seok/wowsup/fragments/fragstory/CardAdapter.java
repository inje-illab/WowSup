package com.seok.seok.wowsup.fragments.fragstory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.StoryActivity;
import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CardData> items;
    private View view;
    private ViewHolder viewHolder;
    private TextView title;
    private TextView like;
    private LinearLayout layoutStoryTitle;
    public CardAdapter(ArrayList<CardData> DataSet, Context context){
        items = DataSet;
        this.context = context;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_story_list, parent, false);

        title = view.findViewById(R.id.story_view_title);
        like = view.findViewById(R.id.story_view_like);
        layoutStoryTitle = view.findViewById(R.id.layout_story_title);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final CardData item = items.get(position);

        // 서버에서 받아온 테스트 데이터 삽입
        title.setText(item.getTitle() + "\n" +  item.getStoryID());

        like.setText(item.getCntLike());
        //레이아웃 제목을 클릭할 경우 해당 storyID 값을 다음 엑티비티에 넘겨줌
        layoutStoryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), StoryActivity.class);
                intent.putExtra("storyID", item.getStoryID());
                context.startActivity(intent);
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
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
