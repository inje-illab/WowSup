package com.seok.seok.wowsup.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.StoryActivity;
import com.seok.seok.wowsup.fragments.fragprofile.StoryWriteActivity;
import com.seok.seok.wowsup.utilities.Common;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//스토리를 뿌려주기위한 어댑터 클래스
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    //어댑터에 넣을 필드값
    private Context context;
    private ArrayList<CardData> items;
    private View view;
    private TextView txtTitle, txtLike;
    private LinearLayout layoutStoryBackground, layoutStoryTitle;
    private RelativeLayout layoutBackTitle;
    private ImageView imgHeart;
    public CardAdapter(ArrayList<CardData> DataSet, Context context) {
        items = DataSet;
        this.context = context;
    }


    //레이아웃과 연결한 바인딩 뷰
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_story_card, parent, false);
        return new ViewHolder(view);
    }
    //본 함수에서 연결한 레이아웃과 필드값 연결
    @Override
    public void onBindViewHolder(@NonNull final CardAdapter.ViewHolder viewHolder, int i) {
        final CardData item = items.get(i);
        // 서버에서 받아온 테스트 데이터 삽입
        viewHolder.txtTitle.setText(item.getTitle());
        viewHolder.txtLike.setText(item.getCntLike());
        Glide.with(viewHolder.itemView.getRootView().getContext())
                .load(item.getImageURL())
                .into(new ViewTarget<LinearLayout, GlideDrawable>((LinearLayout) viewHolder.itemView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        viewHolder.layoutStoryBackground.setBackground(resource);
                    }
                });

        //레이아웃 제목을 클릭할 경우 해당 storyID 값을 다음 엑티비티에 넘겨줌
        viewHolder.layoutStoryBackground.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.story_view_like) TextView txtLike;
        @BindView(R.id.story_view_title) TextView txtTitle;
        @BindView(R.id.layout_story_img_like) ImageView imgHeart;
        @BindView(R.id.layout_story_background) LinearLayout layoutStoryBackground;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
