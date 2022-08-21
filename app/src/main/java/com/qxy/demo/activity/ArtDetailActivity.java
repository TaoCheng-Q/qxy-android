package com.qxy.demo.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.qxy.demo.R;
import com.qxy.demo.databinding.ArtDetailBinding;
import com.qxy.demo.entity.VideoList;
import com.qxy.demo.room.entity.Video;
import com.qxy.demo.viewmodels.ArtDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArtDetailActivity extends AppCompatActivity {
    private ArtDetailBinding binding;
    private ArtDetailViewModel viewModel;
//    记录视频播放的序号
    private int index=0;
//    记录视频播放列表
    private List<Video> videos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
//        setContentView(R.layout.art_detail);
         binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.art_detail, null, false);
         viewModel = new ViewModelProvider(this).get(ArtDetailViewModel.class);
        binding.artVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.artVideo.isPlaying()){
//                    点击暂停播放
                    binding.artVideo.pause();
                }else {
//                    点击继续播放
                    binding.artVideo.resume();
                }
            }
        });
        ArrayList<String> artItemId = getIntent().getStringArrayListExtra("artIdList");
//        数据加载
        loadArts(artItemId);
    }

//    加载视频数据
    private void loadArts(ArrayList<String> artItemId) {
        viewModel.getVideoListMutableLiveData(artItemId).observe(this, new Observer<VideoList>() {
            @Override
            public void onChanged(VideoList videoList) {
//                加载成功后刷新信息显示
                videos=videoList.getVideoList();
                Video video = videoList.getVideoList().get(index);
                binding.artVideo.setVideoPath(video.getShare_url());
                binding.detailTitle.setText(video.getTitle());
                binding.detailCommentNum.setText(String.valueOf(video.getComment_count()));
                binding.detailDownloadNum.setText(String.valueOf(video.getDownload_count()));
                binding.detailForwardNum.setText(String.valueOf(video.getForward_count()));
                binding.detailPostTime.setText(video.getCreate_time());
                binding.detailShareNum.setText(String.valueOf(video.getShare_count()));
                binding.detailVideoView.setText(String.valueOf(video.getPlay_count()));
                binding.detailLikeNum.setText(String.valueOf(video.getDigg_count()));
                binding.artVideo.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.artVideo.suspend();
    }
}