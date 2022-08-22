package com.qxy.demo.FragmentView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.demo.R;
import com.qxy.demo.adapter.WeiBoMessageAdapter;
import com.qxy.demo.databinding.FragmentMainPage3Binding;
import com.qxy.demo.entity.WeiBoMessageList;
import com.qxy.demo.room.dao.WeiBoMessageDao;
import com.qxy.demo.room.entity.WeiBoMessage;
import com.qxy.demo.utils.RoomUtil;
import com.qxy.demo.viewmodels.WeiBoMessagesFactory;
import com.qxy.demo.viewmodels.WeiBoMessagesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPage3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPage3Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentMainPage3Binding binding;
    private WeiBoMessageAdapter adapter;
    private WeiBoMessagesViewModel weiBoMessagesViewModel;
    private boolean needOnLoad = false;

    public MainPage3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPage3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPage3Fragment newInstance(String param1, String param2) {
        MainPage3Fragment fragment = new MainPage3Fragment();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main_page3, container, false);

//        初始化databinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page3, container, false);
        binding.fragment3RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeiBoMessageAdapter(getContext());
        binding.fragment3RecyclerView.setAdapter(adapter);
        weiBoMessagesViewModel = new ViewModelProvider(this,new WeiBoMessagesFactory()).get(WeiBoMessagesViewModel.class);

//        加载数据
        loadMessage();

        binding.refreshLayoutMessages.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayoutMessages.setRefreshing(false);
                page=0;
                hasNextPage=true;
                loadMessage();
            }
        });

        MainPage3Fragment.OnLoadMoreListener recycleViewOnLoadMoreListener = new MainPage3Fragment.OnLoadMoreListener() {
            @Override
            public void load(boolean isOnload) {
                needOnLoad =isOnload;
            }
        };
        adapter.setOnLoadMoreListener(recycleViewOnLoadMoreListener);
        binding.fragment3RecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        if(needOnLoad){
                            loadMessage();
                            needOnLoad=false;
                        }
                        break;
                }
                return false;
            }
        });

        return  binding.getRoot();
    }

    private int page = 0;
    private boolean hasNextPage = true;
    private List<WeiBoMessage> messageList = new ArrayList<>();

    @SuppressLint("FragmentLiveDataObserve")
    private void loadMessage() {
        if(hasNextPage){
            weiBoMessagesViewModel.getWeiBoMessageMutableLiveData(page).observe(this, new Observer<WeiBoMessageList>() {
                @Override
                public void onChanged(WeiBoMessageList weiBoMessageList) {
                    if(page==0){
                        messageList.clear();
                    }
                    if(messageList.containsAll(weiBoMessageList.getWeiBoMessageList())){
                        return;
                    }
                    messageList.addAll(weiBoMessageList.getWeiBoMessageList());
                    if (weiBoMessageList.getWeiBoMessageList().size()<weiBoMessagesViewModel.PAGE_COUNT){
                        hasNextPage=false;
                    }
                    adapter.setData(messageList);
                    page++;
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

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<WeiBoMessage> weiBoMessageList = adapter.getmItems();
                WeiBoMessageDao weiBoMessageDao = RoomUtil.getInstance().getRoomInstance().getWeiBoMessageDao();
                weiBoMessageDao.deleteWeiBoMessages();
                if (weiBoMessageList != null && !weiBoMessageList.isEmpty()) {
                    for (WeiBoMessage weiBoMessage : weiBoMessageList) {
                        weiBoMessageDao.insertWeiBoMessages(weiBoMessage);
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