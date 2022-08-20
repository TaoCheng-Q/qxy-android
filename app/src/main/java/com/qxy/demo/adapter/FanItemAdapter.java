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
import com.qxy.demo.R;
import com.qxy.demo.room.entity.Fans;

import java.util.List;

public class FanItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Fans> fansList;
    public FanItemAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.follow_fan_item,parent);
        return new FanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FanHolder) holder).binding(fansList.get(position));
    }

    @Override
    public int getItemCount() {
        return fansList==null?0:fansList.size();
    }

    public List<Fans> getFansList(){
        return fansList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFansList(List<Fans> fansList) {
        this.fansList = fansList;
        notifyDataSetChanged();
    }

    public class FanHolder extends RecyclerView.ViewHolder{
        public ImageView ff_avatar;
        public TextView ff_nickname,ff_sex,ff_city,ff_province,ff_country;
        public FanHolder(@NonNull View itemView) {
            super(itemView);
            ff_avatar=itemView.findViewById(R.id.ff_avatar);
            ff_nickname=itemView.findViewById(R.id.ff_nickname);
            ff_sex=itemView.findViewById(R.id.ff_sex);
            ff_city=itemView.findViewById(R.id.ff_city);
            ff_province=itemView.findViewById(R.id.ff_province);
            ff_country=itemView.findViewById(R.id.ff_country);

        }

        public void binding(Fans fans){
            Glide
                    .with(itemView.getContext())
                    .load(fans.getAvatar())
                    .into(ff_avatar);
            ff_nickname.setText(fans.getNickname());
            switch (fans.getGender()){
                case 0:
                    ff_sex.setText("未知");
                    break;
                case 1:
                    ff_sex.setText("男");
                    break;
                case 2:
                    ff_sex.setText("女");
                    break;
            }
            ff_city.setText(fans.getCity());
            ff_province.setText(fans.getProvince());
            ff_country.setText(fans.getCountry());
        }
    }
}
