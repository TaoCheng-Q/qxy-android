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
import com.qxy.demo.FragmentView.MainPage5Fragment;
import com.qxy.demo.R;
import com.qxy.demo.room.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class ArtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Video> videoList;

    private MainPage5Fragment.OnClickArtListListener onClickArtListListener;

    public ArtAdapter(Context context){
        this.context = context;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.art_item,parent,false);
        return new ArtHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ((ArtHolder) holder).binding(videoList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickArtListListener!=null){
                    List<String> artList = new ArrayList<>();
                    for(int i=position;i<getItemCount();i++){
                        artList.add(videoList.get(position).getItem_id());
                    }
                    onClickArtListListener.clickArt(position,artList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList==null?0:videoList.size();
    }

    public void setOnClickArtListListener(MainPage5Fragment.OnClickArtListListener onClickArtListListener) {
        this.onClickArtListListener = onClickArtListListener;
    }

    public class ArtHolder extends RecyclerView.ViewHolder{

        public TextView art_title,art_comment_num,art_post_time,art_video_view,art_is_top;
        public ImageView art_thumbnail;

        public ArtHolder(@NonNull View itemView) {
            super(itemView);
            art_title= itemView.findViewById(R.id.art_title);
            art_comment_num= itemView.findViewById(R.id.art_comment_num);
            art_post_time= itemView.findViewById(R.id.art_post_time);
            art_video_view= itemView.findViewById(R.id.art_video_view);
            art_thumbnail= itemView.findViewById(R.id.art_thumbnail);
            art_is_top= itemView.findViewById(R.id.art_is_top);

        }

        public void binding(Video video){
            Glide
                    .with(itemView.getContext())
                    .load(video.getCover())
                    .into(art_thumbnail);
            art_title.setText(video.getTitle());
            art_comment_num.setText(video.getComment_count());
            art_post_time.setText(video.getCreate_time());
            art_video_view.setText(video.getPlay_count());
            art_is_top.setText(video.isIs_top()?"是":"否");
        }

    }
}
