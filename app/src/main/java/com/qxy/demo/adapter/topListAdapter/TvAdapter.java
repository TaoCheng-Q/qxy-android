package com.qxy.demo.adapter.topListAdapter;

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
import com.qxy.demo.R;
import com.qxy.demo.room.entity.topListEntity.Television;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
//    电视剧榜单显示数据
    private List<Television> televisionList;

    public TvAdapter(Context context){
        this.context=context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.tv_item,parent,false);
        return new TvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TvHolder) holder).binding(televisionList.get(position));

    }

    @Override
    public int getItemCount() {
        return televisionList==null?0:televisionList.size();
    }

    public List<Television> getTelevisionList() {
        return televisionList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTelevisionList(List<Television> televisionList) {
        this.televisionList = televisionList;
        notifyDataSetChanged();
    }

    public class TvHolder extends RecyclerView.ViewHolder{

        public ImageView tv_cover;
        public TextView
                tv_actor,
                tv_name,
                tv_en_name,
                tv_director,
//                tv_rel_area,
                tv_rel_time,
//                tv_tag,
                tv_discuss_pop,
                tv_influence_pop,
                tv_search_pop,
                tv_theme_pop;

        public TvHolder(@NonNull View itemView) {
            super(itemView);
            tv_actor=itemView.findViewById(R.id.tv_actor);
            tv_cover=itemView.findViewById(R.id.tv_cover);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_en_name=itemView.findViewById(R.id.tv_en_name);
            tv_director=itemView.findViewById(R.id.tv_director);
//            tv_rel_area=itemView.findViewById(R.id.tv_rel_area);
            tv_rel_time=itemView.findViewById(R.id.tv_rel_time);
//            tv_tag=itemView.findViewById(R.id.tv_tag);
            tv_discuss_pop=itemView.findViewById(R.id.tv_discuss_pop);
            tv_influence_pop=itemView.findViewById(R.id.tv_influence_pop);
            tv_search_pop=itemView.findViewById(R.id.tv_search_pop);
            tv_theme_pop=itemView.findViewById(R.id.tv_theme_pop);
        }

        public void binding(Television television){
            Glide
                    .with(itemView.getContext())
                    .load(television.getPoster())
                    .into(tv_cover);
            tv_actor.setText("演员："+television.getActors());
            tv_name.setText("片名："+television.getName());
            tv_en_name.setText(television.getName_en());
            tv_director.setText("导演："+television.getDirectors());
//                   tv_rel_area.setText(television.get);
            tv_rel_time.setText("发布时间"+television.getRelease_date());
//                   tv_tag.setText(television.);
            tv_discuss_pop.setText("讨论热度："+television.getDiscussion_hot());
            tv_influence_pop.setText("影响力："+television.getInfluence_hot());
            tv_search_pop.setText("搜索指数："+television.getSearch_hot());
            tv_theme_pop.setText("话题热度："+television.getTopic_hot());

        }
    }
}
