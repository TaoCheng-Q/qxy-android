package com.qxy.demo.FragmentView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.demo.R;
import com.qxy.demo.adapter.TopListFragmentPagerAdapter;
import com.qxy.demo.databinding.FragmentMainPage4Binding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPage4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPage4Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentMainPage4Binding binding;
    TopListFragmentPagerAdapter pagerAdapter;

    public MainPage4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPage4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPage4Fragment newInstance(String param1, String param2) {
        MainPage4Fragment fragment = new MainPage4Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
//        TabLayout tabLayout = view.findViewById(R.id.fragment4_tab_layout);
//        ViewPager2 viewPager = view.findViewById(R.id.fragment4_viewpager);
        List<String> tabName = Arrays.asList("电影", "电视剧", "综艺");
        new TabLayoutMediator(binding.fragment4TabLayout, binding.fragment4Viewpager,
                (tab, position) -> tab.setText(tabName.get(position))
        ).attach();

        }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main_page4, container, false);
//        getActivity().getSupportFragmentManager()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page4, container, false);
        pagerAdapter = new TopListFragmentPagerAdapter(this);
//        binding.fragment4Viewpager

//        设置榜单viewpager控件的adapter
        binding.fragment4Viewpager.setAdapter(pagerAdapter);
//        设置初始化显示页面为电视剧榜单
        binding.fragment4Viewpager.setCurrentItem(TopListFragmentPagerAdapter.TV_PAGE);

        return binding.getRoot();
    }


}