package com.qxy.demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.demo.FragmentView.MainPage3Fragment;
import com.qxy.demo.R;
import com.qxy.demo.room.entity.WeiBoMessage;

import java.util.ArrayList;
import java.util.List;

public class WeiBoMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<WeiBoMessage> weiBoMessageList = new ArrayList<>();
    private MainPage3Fragment.OnLoadMoreListener onLoadMoreListener;

    public WeiBoMessageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weibo_massage_item, parent, false);
        return new WeiBoMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((WeiBoMessageHolder) holder).bindingData(weiBoMessageList.get(position));
//        ((WeiBoMessageHolder) holder).tv_message_can_edit.setText(weiBoMessageList.get(position).isCan_edit()?"是":"否");
//        ((WeiBoMessageHolder) holder).tv_message_source.setText(weiBoMessageList.get(position).getSource());
//        ((WeiBoMessageHolder) holder).tv_message_text.setText(weiBoMessageList.get(position).getText());
//        ((WeiBoMessageHolder) holder).tv_message_created_at.setText(weiBoMessageList.get(position).getCreated_at());
//        ((WeiBoMessageHolder) holder).tv_message_source_type.setText(weiBoMessageList.get(position).getSource_type());
//        ((WeiBoMessageHolder) holder).tv_message_version.setText(weiBoMessageList.get(position).getVersion());
//        ((WeiBoMessageHolder) holder).tv_message_source_allow_clicked.setText(weiBoMessageList.get(position).getSource_allowclick());
//        ((WeiBoMessageHolder) holder).tv_message_text_length.setText(weiBoMessageList.get(position).getTextLength());
        if(position == weiBoMessageList.size()-1){
            onLoadMoreListener.load(true);
        }

    }

    @Override
    public int getItemCount() {
        return weiBoMessageList==null?0:weiBoMessageList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<WeiBoMessage> messageList) {
        weiBoMessageList = messageList;
        notifyDataSetChanged();
    }

    public List<WeiBoMessage> getmItems() {
        return weiBoMessageList;
    }

    public void setOnLoadMoreListener(MainPage3Fragment.OnLoadMoreListener recycleViewOnLoadMoreListener) {
        onLoadMoreListener=recycleViewOnLoadMoreListener;
    }

    private class WeiBoMessageHolder extends RecyclerView.ViewHolder{

        public TextView
                tv_message_text,
                tv_message_source,
                tv_message_can_edit,
                tv_message_created_at,
                tv_message_version,
                tv_message_source_allow_clicked,
                tv_message_source_type,
                tv_message_text_length;

        public WeiBoMessageHolder(@NonNull View itemView) {
            super(itemView);
            tv_message_text = itemView.findViewById(R.id.message_text);
            tv_message_source = itemView.findViewById(R.id.message_source);
            tv_message_can_edit = itemView.findViewById(R.id.message_can_edit);
            tv_message_created_at = itemView.findViewById(R.id.message_created_at);
            tv_message_version = itemView.findViewById(R.id.message_version);
            tv_message_source_allow_clicked = itemView.findViewById(R.id.message_source_allow_clicked);
            tv_message_source_type = itemView.findViewById(R.id.message_source_type);
            tv_message_text_length = itemView.findViewById(R.id.message_text_length);
        }

        public void bindingData(WeiBoMessage weiBoMessage) {
            tv_message_can_edit.setText(weiBoMessage.isCan_edit()?"是":"否");
            tv_message_source.setText(weiBoMessage.getSource());
            tv_message_text.setText(weiBoMessage.getText());
            tv_message_created_at.setText(weiBoMessage.getCreated_at());
            tv_message_source_type.setText(String.valueOf(weiBoMessage.getSource_type()));
            tv_message_version.setText(String.valueOf(weiBoMessage.getVersion()));
            tv_message_source_allow_clicked.setText(String.valueOf(weiBoMessage.getSource_allowclick()));
            tv_message_text_length.setText(String.valueOf(weiBoMessage.getTextLength()));
        }
    }
}
