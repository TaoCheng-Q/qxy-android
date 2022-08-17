package com.example.androiddemo.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.androiddemo.BR;
import com.example.androiddemo.R;
import com.example.androiddemo.entity.RecycleViewItem;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private List<RecycleViewItem> lists;

    public UserAdapter(List<RecycleViewItem> userList){
        lists=userList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.main_item,
                parent,
                false);
        UserHolder holder = new UserHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
//        holder.bind(lists.get(position));
//        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
//        if(binding!=null){
//            binding.setVariable(BR.user,lists.get(position));
//            binding.executePendingBindings();
//        }
        holder.getBinding().setVariable(BR.user_text,lists.get(position));
        holder.getBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ViewDataBinding dataBinding;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setBinding(ViewDataBinding binding) {
            dataBinding=binding;
        }

//        public void bind(RecycleViewItem recycleViewItem) {
//            dataBinding.setVariable(BR.user,recycleViewItem);
//            dataBinding.executePendingBindings();
//        }

        public ViewDataBinding getBinding(){
            return dataBinding;
        }

        @Override
        public void onClick(View view) {
//            点击列表数据
            if(view!=null){
                Toast.makeText(view.getContext(),"点击了"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onClick: "+"点击了"+getAdapterPosition());
            }

        }
    }
}
