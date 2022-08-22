package com.qxy.demo.FragmentView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.demo.R;
import com.qxy.demo.adapter.FanItemAdapter;
import com.qxy.demo.databinding.FragmentFanBinding;
import com.qxy.demo.entity.FansList;
import com.qxy.demo.room.entity.Fans;
import com.qxy.demo.viewmodels.FanViewModel;

import java.util.ArrayList;
import java.util.List;

public class FanFragment extends Fragment {

    private FanViewModel mViewModel;
    private int position;
    private FragmentFanBinding binding;
    private FanItemAdapter adapter;
    private int cursor=0;
    private List<Fans> adapterFansList =new ArrayList<>();

    private FanFragment(int position){
        this.position=position;
    }

    public static FanFragment newInstance(int position) {
        return new FanFragment(position);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_fan, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fan,container,false);
        mViewModel = new ViewModelProvider(this).get(FanViewModel.class);
        adapter = new FanItemAdapter(getContext());
        binding.followFanFragmnet.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.followFanFragmnet.setAdapter(adapter);
        loadFans();
        return binding.getRoot();
    }

    private void loadFans() {
        if(position==0){
            mViewModel.getFollowsListMutableLiveData(cursor).observe(getViewLifecycleOwner(), new Observer<FansList>() {
                @Override
                public void onChanged(FansList fansList) {
                    adapter.setFansList(fansList.getFansList());
                    cursor=fansList.getCursor();
                }
            });
        }else {
            mViewModel.getFansListMutableLiveData(cursor).observe(getViewLifecycleOwner(), new Observer<FansList>() {
                @Override
                public void onChanged(FansList fansList) {
                    if(adapterFansList.containsAll(fansList.getFansList())){
                        return;
                    }
                    adapterFansList = fansList.getFansList();
                    adapter.setFansList(adapterFansList);
                    cursor=fansList.getCursor();
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(FanViewModel.class);
        // TODO: Use the ViewModel
    }

}