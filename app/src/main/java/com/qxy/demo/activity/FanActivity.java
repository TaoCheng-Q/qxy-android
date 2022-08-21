package com.qxy.demo.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qxy.demo.R;
import com.qxy.demo.adapter.FanPageAdapter;

import java.util.Arrays;
import java.util.List;

public class FanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_fan_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

//        获取上个页面传递的数据，确定最先加载的页面
        int index = getIntent().getIntExtra("position",0);

        TabLayout tabLayout = findViewById(R.id.follow_fan_tab);
        ViewPager2 viewPager2 = findViewById(R.id.follow_fan_viewpager);
        viewPager2.setAdapter(new FanPageAdapter(this));
        List<String> tabName = Arrays.asList("关注", "粉丝");
        new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> tab.setText(tabName.get(position))).attach();
        viewPager2.setCurrentItem(index);
    }
}