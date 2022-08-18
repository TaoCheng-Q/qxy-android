package com.qxy.demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qxy.demo.FragmentView.MainPage2Fragment;
import com.qxy.demo.R;
import com.qxy.demo.entity.WeiboEmotions;

import java.util.List;

public class WeiboEmotionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<WeiboEmotions> mItems;
    private MainPage2Fragment.OnLoadMoreListener onLoadMoreListener;

    public WeiboEmotionsAdapter(Context context){
        this.context=context;
    }

    public void setOnLoadMoreListener(MainPage2Fragment.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDate(List<WeiboEmotions> items){
        mItems=items;
        notifyDataSetChanged();
    }

    public List<WeiboEmotions> getmItems() {
        return mItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.weibo_emotions_item,parent,false);
        return new EmotionsViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        holder.itemView.setTag(mItems.get(position));

        ((EmotionsViewHoder) holder).tv_type.setText(mItems.get(position).getType());
        ((EmotionsViewHoder) holder).tv_common.setText(mItems.get(position).isCommon()?"是":"否");
        ((EmotionsViewHoder) holder).tv_hot.setText(mItems.get(position).isHot()?"是":"否");
        ((EmotionsViewHoder) holder).tv_value.setText(mItems.get(position).getValue());
        Glide.with(holder.itemView.getContext())
                .load(mItems.get(position).getIcon())
                .into(((EmotionsViewHoder) holder).imageView);

        if(position==getItemCount()-1){
            onLoadMoreListener.load(true);
        }

    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0:mItems.size();
    }

    private static class  EmotionsViewHoder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView tv_type,tv_hot,tv_common,tv_value;

        private EmotionsViewHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.emotion_icon);
            tv_type = itemView.findViewById(R.id.emotion_type);
            tv_hot = itemView.findViewById(R.id.emotion_hot);
            tv_common = itemView.findViewById(R.id.emotion_common);
            tv_value = itemView.findViewById(R.id.emotion_value);
        }
    }


}
