package com.qxy.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.qxy.demo.FragmentView.MainPage1Fragment;
import com.qxy.demo.FragmentView.MainPage2Fragment;
import com.qxy.demo.FragmentView.MainPage3Fragment;
import com.qxy.demo.FragmentView.MainPage4Fragment;
import com.qxy.demo.FragmentView.MainPage5Fragment;
import com.qxy.demo.utils.RoomUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationBarView.OnItemSelectedListener {


    private FragmentManager fragmentManager;
//    private Fragment[] fragments = new Fragment[]{null,null,null,null,null};
//            {
//            new MainPage1Fragment(),
//            new MainPage2Fragment(),
//            new MainPage3Fragment(),
//            new MainPage4Fragment(),
//            new MainPage5Fragment()
//    };
    int PreIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AndroidDemo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottom_nav = findViewById(R.id.bottom_nav);

        bottom_nav.setOnItemSelectedListener(this);

        replaceFragment(MainPage1Fragment.newInstance("p1","p2"));

        RoomUtil.getInstance().setRoomDatabase(getApplicationContext());

    }

    public void replaceFragment(Fragment fragment){
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_content, fragment)
//                .addToBackStack(null)
                .commit();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.page1:
//                replaceFragment(R.id.fragment_content,MainPage1Fragment.newInstance("p1","p2"));
//                if(curtTextView!=textView1){
//                    curtTextView.setTextColor(getBaseContext().getResources().getColor(R.color.black));
//                    curtTextView=textView1;
//                    textView1.setTextColor(getBaseContext().getResources().getColor(android.R.color.holo_blue_bright));
//                }
//
//                break;
//            case R.id.page2:
//                replaceFragment(R.id.fragment_content, MainPage2Fragment.newInstance("p1","p2"));
//                if(curtTextView!=textView2){
//                    curtTextView.setTextColor(getBaseContext().getResources().getColor(R.color.black));
//                    curtTextView=textView2;
//                    textView2.setTextColor(getBaseContext().getResources().getColor(android.R.color.holo_blue_bright));
//                }
//
//                break;
//            case R.id.page3:
//                if(curtTextView!=textView3){
//                    curtTextView.setTextColor(getBaseContext().getResources().getColor(R.color.black));
//                    curtTextView=textView3;
//                    textView3.setTextColor(getBaseContext().getResources().getColor(android.R.color.holo_blue_bright));
//                }
//
//                break;
//            case R.id.page4:
//                if(curtTextView!=textView4){
//                    curtTextView.setTextColor(getBaseContext().getResources().getColor(R.color.black));
//                    curtTextView=textView4;
//                    textView4.setTextColor(getBaseContext().getResources().getColor(android.R.color.holo_blue_bright));
//                }
//
//                break;
//            case R.id.page5:
//                if(curtTextView!=textView5){
//                    curtTextView.setTextColor(getBaseContext().getResources().getColor(R.color.black));
//                    curtTextView=textView5;
//                    textView5.setTextColor(getBaseContext().getResources().getColor(android.R.color.holo_blue_bright));
//                }
//
//                break;
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.page1:
                if(PreIndex!= 0){
                    replaceFragment(MainPage1Fragment.newInstance("p1","p2"));
                    PreIndex=0;
                }
                break;
            case R.id.page2:
                if(PreIndex != 1){
                    replaceFragment(MainPage2Fragment.newInstance("p1","p2"));
                    PreIndex=1;
                }
                break;
            case R.id.page3:
                if(PreIndex != 2){
                    replaceFragment(MainPage3Fragment.newInstance("p1","p2"));
                    PreIndex=2;
                }
                break;
            case R.id.page4:
                if(PreIndex != 3){
                    replaceFragment(MainPage4Fragment.newInstance("p1","p2"));
                    PreIndex=3;
                }
                break;
            case R.id.page5:
                if(PreIndex != 4){
                    replaceFragment(MainPage5Fragment.newInstance("p1","p2"));
                    PreIndex=4;
                }
                break;
        }
        return false;
    }

//    private void changFragmnet(int index) {
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.hide(fragments[PreIndex]);
//        fragmentTransaction.show(fragments[index])
//                .commitAllowingStateLoss();
//        PreIndex=index;
//    }
}