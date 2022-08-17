package com.example.androiddemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androiddemo.FragmentView.topListFragment.MovieFragment;
import com.example.androiddemo.FragmentView.topListFragment.ShowsFragment;
import com.example.androiddemo.FragmentView.topListFragment.TVFragment;
import com.example.androiddemo.R;

public class TopListFragmentPagerAdapter extends FragmentStateAdapter {

    private int PAGE_COUNT = 3;
    private MovieFragment movieFragment;
    private TVFragment tvFragment;
    private ShowsFragment showsFragment;
    private LayoutInflater inflater;

    public TopListFragmentPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        movieFragment = MovieFragment.newInstance();
        tvFragment = TVFragment.newInstance();
        showsFragment = ShowsFragment.newInstance();
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
                return movieFragment;
            case 1:
                return tvFragment;
            case 2:
                return showsFragment;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}
