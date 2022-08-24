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
import com.qxy.demo.room.entity.topListEntity.Show;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
//    show榜单需要显示的数据
    private List<Show> showList;

    public ShowAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_item, parent, false);
        return new ShowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ShowHolder) holder).binding(showList.get(position));

    }

    @Override
    public int getItemCount() {
        return showList==null?0:showList.size();
    }

    public List<Show> getShowList() {
        return showList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setShowList(List<Show> showList) {
        this.showList = showList;
        notifyDataSetChanged();
    }

    public class ShowHolder extends RecyclerView.ViewHolder{
        public ImageView shows_cover;
        public TextView
                shows_name,
                shows_en_name,
                shows_director,
                shows_discuss_pop,
                shows_theme_pop,
                shows_search_pop,
                shows_influence_pop,
                shows_data;

        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            shows_cover = itemView.findViewById(R.id.shows_cover);
            shows_name = itemView.findViewById(R.id.shows_name);
            shows_en_name = itemView.findViewById(R.id.shows_en_name);
            shows_director = itemView.findViewById(R.id.shows_director);
            shows_discuss_pop = itemView.findViewById(R.id.shows_discuss_pop);
            shows_theme_pop = itemView.findViewById(R.id.shows_theme_pop);
            shows_search_pop = itemView.findViewById(R.id.shows_search_pop);
            shows_influence_pop = itemView.findViewById(R.id.shows_influence_pop);
            shows_data=itemView.findViewById(R.id.shows_data);
        }

        public void binding(Show show){
            Glide
                    .with(itemView.getContext())
                    .load(show.getPoster())
                    .into(shows_cover);
            shows_name.setText("片名："+show.getName());
            shows_en_name.setText(show.getName_en());
            shows_director.setText("导演"+show.getDirectors());
            shows_discuss_pop.setText("讨论热度："+show.getDiscussion_hot());
            shows_theme_pop.setText("话题热度："+show.getTopic_hot());
            shows_search_pop.setText("搜索指数："+show.getSearch_hot());
            shows_influence_pop.setText("影响力："+show.getInfluence_hot());
            shows_data.setText("发布时间："+show.getRelease_date());
        }
    }
}
