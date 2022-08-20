package com.qxy.demo.adapter.topListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qxy.demo.R;
import com.qxy.demo.entity.topList.VersionList;

import java.util.ArrayList;
import java.util.List;

public class VersionAdapter extends BaseAdapter {

    private int VERSION_TYPE=0;
    private int MORE_TYPE=1;
    private Context context;
    private List<Integer> integerList=new ArrayList<>(1);

    public VersionAdapter(Context context){
        this.context=context;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return integerList==null?0:integerList.size();
    }

    @Override
    public Object getItem(int i) {
        return integerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.top_list_version_item,viewGroup,false);
        TextView version=view.findViewById(R.id.top_list_version);
        version.setText(String.valueOf(integerList.get(i)));
        return view;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }
}
