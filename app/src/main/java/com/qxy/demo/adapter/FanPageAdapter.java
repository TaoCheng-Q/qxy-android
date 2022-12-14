package com.qxy.demo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qxy.demo.FragmentView.FanFragment;

public class FanPageAdapter extends FragmentStateAdapter {
//    关注列表页
    public static int FOLLOWS_PAGE=0;
//    粉丝列表页
    public static int FANS_PAGE=1;

    private int PAG_COUNT=2;
    private FanFragment followFragment;
    private FanFragment fanFragment;


    public FanPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
//        fanFragment=FanFragment.newInstance();
//        followFragment=FanFragment.newInstance();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return FanFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return PAG_COUNT;
    }
}
