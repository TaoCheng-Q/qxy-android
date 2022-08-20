package com.qxy.demo.adapter;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qxy.demo.FragmentView.topListFragment.MovieFragment;
import com.qxy.demo.FragmentView.topListFragment.ShowsFragment;
import com.qxy.demo.FragmentView.topListFragment.TVFragment;

public class TopListFragmentPagerAdapter extends FragmentStateAdapter {

    private int PAGE_COUNT = 3;
    private MovieFragment movieFragment;
    private TVFragment tvFragment;
    private ShowsFragment showsFragment;
    private LayoutInflater inflater;

    public TopListFragmentPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
//        movieFragment = MovieFragment.newInstance();
//        tvFragment = TVFragment.newInstance();
//        showsFragment = ShowsFragment.newInstance();
    }


//    public TopListFragmentPagerAdapter(Context context){
//        inflater = LayoutInflater.from(context);
//        movieFragment = MovieFragment.newInstance();
//        tvFragment = TVFragment.newInstance();
//        showsFragment = ShowsFragment.newInstance();
//
//    }
//


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return MovieFragment.newInstance();
            case 1:
                return TVFragment.newInstance();
            case 2:
                return ShowsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}
