package com.example.tiktok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final int PAGE_COUNT = 5;

    private TabLayout tabLayout;
    private NoScrollViewPager viewPager;
    private Button btnPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        btnPhoto = findViewById(R.id.btn_photo);

        //设置ViewPager
        NoScrollViewPager pager = findViewById(R.id.view_pager);
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case 0:
                        return new MainFragment();
                    case 1:
                        return new FriendFragment();
                    case 2:
                        return new FriendFragment();
                    case 3:
                        return new NewsFragment();
                    case 4:
                        return new MineFragment();
                }
                return new MainFragment();
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "首页";
                    case 1:
                        return "朋友";
                    case 2:
                        return "";
                    case 3:
                        return "消息";
                    case 4:
                        return "我";
                }
                return "首页";
            }
        });
        viewPager.setCurrentItem(0);

        //设置TabLayout与ViewPager同步
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });


    }

//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//            Window window = getWindow();
//            DisplayCutout displayCutout = window.getDecorView().getRootWindowInsets().getDisplayCutout();
//            if (displayCutout != null) {
//                // 有刘海屏
//                Log.e("displayCutout", "Rect " + displayCutout.getBoundingRects());
//                Log.e("displayCutout", "Rect " + displayCutout.getSafeInsetLeft());
//                Log.e("displayCutout", "Rect " + displayCutout.getSafeInsetTop());
//                Log.e("displayCutout", "Rect " + displayCutout.getSafeInsetRight());
//                Log.e("displayCutout", "Rect " + displayCutout.getSafeInsetBottom());
//
//                // 让内容延伸至刘海区域
//                WindowManager.LayoutParams layoutParams = window.getAttributes();
//                layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//
//            }
//        }
//    }
}
