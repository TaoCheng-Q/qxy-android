package com.example.androiddemo.FragmentView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.androiddemo.R;
import com.example.androiddemo.adapter.WeiboEmotionsAdapter;
import com.example.androiddemo.databinding.FragmentMainPage2Binding;
import com.example.androiddemo.entity.WeiboEmotions;
import com.example.androiddemo.room.dao.EmotionDao;
import com.example.androiddemo.room.entity.Emotion;
import com.example.androiddemo.utils.RoomUtil;
import com.example.androiddemo.viewmodels.WeiboEmotionsFactory;
import com.example.androiddemo.viewmodels.WeiboEmotionsViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPage2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPage2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean needOnLoad = false;

    public MainPage2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPage2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String param1, String param2) {
        MainPage2Fragment fragment = new MainPage2Fragment();
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

    private FragmentMainPage2Binding binding;
    private WeiboEmotionsViewModel weiboEmotionsViewModel;
    private WeiboEmotionsAdapter adapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main_page2, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page2, container, false);
        binding.fragment2RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeiboEmotionsAdapter(getContext());
        binding.fragment2RecyclerView.setAdapter(adapter);
        weiboEmotionsViewModel = new ViewModelProvider(this,new WeiboEmotionsFactory()).get(WeiboEmotionsViewModel.class);

        loadEmotions();

        binding.refreshLayoutEmotions.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayoutEmotions.setRefreshing(false);
                page=0;
                hasNextPage=true;
                loadEmotions();
            }
        });

        OnLoadMoreListener recycleViewOnLoadMoreListener = new OnLoadMoreListener() {
            @Override
            public void load(boolean isOnload) {
                needOnLoad =isOnload;
//                if(!binding.fragment2RecyclerView.isComputingLayout()){
//                    loadEmotions();
//                }

            }
        };
//        weiboEmotionsViewModel.setOnLoadMoreListener(recycleViewOnLoadMoreListener);
        adapter.setOnLoadMoreListener(recycleViewOnLoadMoreListener);
//        binding.fragment2SmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshLayout) {
//                page=0;
//                hasNextPage=true;
//                loadEmotions();
//            }
//        });
//
//        binding.fragment2SmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
//                loadEmotions();
//            }
//        });

        binding.fragment2RecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        if(needOnLoad){
                            loadEmotions();
                            needOnLoad=false;
                        }
                }

                return false;
            }
        });

        return  binding.getRoot();
    }

    private int page = 0;
    private boolean hasNextPage = true;
    private List<WeiboEmotions> emotionsList = new ArrayList<>();

    @SuppressLint("FragmentLiveDataObserve")
    private void loadEmotions() {
        if(hasNextPage){
            weiboEmotionsViewModel.getEmotions(page).observe(this, new Observer<WeiboEmotions>() {
                @Override
                public void onChanged(WeiboEmotions weiboEmotions) {
                    if(page==0){
                        emotionsList.clear();
                    }
                    emotionsList.addAll(weiboEmotions.getWeiboEmotionsList());
                    if (weiboEmotions.getWeiboEmotionsList().size()<weiboEmotionsViewModel.PAGE_COUNT){
                        hasNextPage=false;
                    }
                    adapter.setDate(emotionsList);
                    page++;
//                    binding.fragment2SmartRefreshLayout.finishRefresh();
//                    binding.fragment2SmartRefreshLayout.finishLoadMore();
                }
            });

        }
    }

//    上拉加载更多需要加载的列表数据的时候调用
    public interface OnLoadMoreListener{
        void load(boolean isOnLoad);
    }

    @Override
    public void onPause() {

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                List<WeiboEmotions> weiboEmotionsList = adapter.getmItems();
                EmotionDao myRoomDatabase = RoomUtil.getInstance().getRoomInstance().getEmotionDao();
                myRoomDatabase.deleteEmotions();
                if(weiboEmotionsList!=null && !weiboEmotionsList.isEmpty()){
                    for(WeiboEmotions weiboEmotions : weiboEmotionsList){
                        Emotion emotion = new Emotion();
                        emotion.setType(weiboEmotions.getType());
                        emotion.setHot(weiboEmotions.isHot());
                        emotion.setCommon(weiboEmotions.isCommon());
                        emotion.setValue(weiboEmotions.getValue());
                        emotion.setIcon(weiboEmotions.getIcon());
                        myRoomDatabase.insertEmotions(emotion);

                    }
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.onPause();
    }
}