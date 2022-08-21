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
import com.qxy.demo.activity.ArtDetailActivity;
import com.qxy.demo.activity.FanActivity;
import com.qxy.demo.adapter.ArtAdapter;
import com.qxy.demo.databinding.FragmentMainPage4Binding;
import com.qxy.demo.databinding.FragmentMainPage5Binding;
import com.qxy.demo.entity.VideoList;
import com.qxy.demo.room.dao.DouYinUserDao;
import com.qxy.demo.room.entity.DouYinUser;
import com.qxy.demo.utils.RoomUtil;
import com.qxy.demo.viewmodels.UserInfoViewModel;

import java.util.ArrayList;
import java.util.List;

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
//    视频列表适配器
    private ArtAdapter artAdapter;
//    分页游标
    private int cursor=0;

    private OnClickArtListListener onClickArtListListener = new OnClickArtListListener(){
        @Override
        public void clickArt(int position, List<String> artList) {
//            页面跳转至详细信息页面，并将视频的item_id列表一同传递
            Intent intent =new Intent(getActivity(), ArtDetailActivity.class);
//            Bundle bundle =new Bundle();
//            bundle.putStringArrayList("artIdList", (ArrayList<String>) artList);
            startActivity(intent.putStringArrayListExtra("artIdList",(ArrayList<String>) artList));
        }
    };
    private DouYinUser user;

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
//        个人短视频展示，适配器
        artAdapter = new ArtAdapter(getContext());
//        设置列表的监听对象
        artAdapter.setOnClickArtListListener(onClickArtListListener);
        binding.artList.setLayoutManager(new LinearLayoutManager(getContext()));
        userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
//        请求获取短视频数据
        loadArt();
        binding.artList.setAdapter(artAdapter);
//        请求获取用户公开信息
        loadUerInfo();
        Intent intent =new Intent(getActivity(), FanActivity.class);
//        Bundle bundle = new Bundle();
        binding.fansNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                跳转至粉丝列表显示页面
//                bundle.putInt("position",1);
                startActivity(intent.putExtra("position",1));
            }
        });
        binding.followingNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                跳转到关注列表显示页面
//                bundle.putInt("position",0);
                startActivity(intent.putExtra("position",0));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
//        保存数据至数据库
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    DouYinUserDao userDao = RoomUtil.getInstance().getRoomInstance().getDouYinUserDao();
                    userDao.deleteDouYinUser();
                    userDao.insertDouYinUserDao(user);
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //    视频列表点击监听接口
    public interface OnClickArtListListener{
//        点击短视频后调用，获取点击视频的position以及从点击的视频开始到后面的所有视频item_id，用来获取具体的视频详情
        void clickArt(int position, List<String> artList);
    }

//    加载视频数据
    private void loadArt() {
        userInfoViewModel.getVideoListMutableLiveData(cursor).observe(getViewLifecycleOwner(), new Observer<VideoList>() {
            @Override
            public void onChanged(VideoList videoList) {
//                数据加载成功后刷新列表显示
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
//                个人信息加载成功后刷新信息显示
                user =douYinUser;
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
                    case 2:
                        binding.personSex.setText("女");
                        break;
                }

            }
        });
    }
}