package com.qxy.demo.FragmentView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.qxy.demo.R;
import com.qxy.demo.activity.FanActivity;
import com.qxy.demo.adapter.ArtAdapter;
import com.qxy.demo.databinding.FragmentMainPage4Binding;
import com.qxy.demo.databinding.FragmentMainPage5Binding;
import com.qxy.demo.entity.VideoList;
import com.qxy.demo.room.entity.DouYinUser;
import com.qxy.demo.viewmodels.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPage5Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPage5Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMainPage5Binding binding;
    private UserInfoViewModel userInfoViewModel;
    private ArtAdapter artAdapter;
    private int cursor=0;

    public MainPage5Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPage5Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPage5Fragment newInstance(String param1, String param2) {
        MainPage5Fragment fragment = new MainPage5Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main_page5, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page5, container, false);
        
        artAdapter = new ArtAdapter(getContext());
        binding.artList.setLayoutManager(new LinearLayoutManager(getContext()));
        userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        
        loadArt();
        binding.artList.setAdapter(artAdapter);
        

        loadUerInfo();
        Intent intent =new Intent(getActivity(), FanActivity.class);
        Bundle bundle = new Bundle();
        binding.fansNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("position",1);
                startActivity(intent,bundle);
            }
        });
        binding.followingNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("position",0);
                startActivity(intent,bundle);
            }
        });

        return binding.getRoot();
    }

    private void loadArt() {
        userInfoViewModel.getVideoListMutableLiveData(cursor).observe(getViewLifecycleOwner(), new Observer<VideoList>() {
            @Override
            public void onChanged(VideoList videoList) {
                artAdapter.setVideoList(videoList.getVideoList());
                cursor = videoList.getCursor();
            }
        });
    }

    //    获取用户基本信息
    private void loadUerInfo() {
        userInfoViewModel.getDouYinUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DouYinUser>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(DouYinUser douYinUser) {
                
                binding.personNickname.setText(douYinUser.getNickname());
                binding.personCity.setText(douYinUser.getCity());
                binding.personCountry.setText(douYinUser.getCountry()+"/"+douYinUser.getProvince());
                Glide
                        .with(getContext())
                        .load(douYinUser.getAvatar())
                        .into(binding.personAvatar);
                switch (douYinUser.getGender()){
                    case 0:
                        binding.personSex.setText("未知");
                        break;
                    case 1:
                        binding.personSex.setText("男");
                        break;
                    case 3:
                        binding.personSex.setText("女");
                        break;
                }

            }
        });
    }
}