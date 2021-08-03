package com.example.tiktok;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tiktok.mine.MineCollectFragment;
import com.example.tiktok.mine.MineLoveFragment;
import com.example.tiktok.mine.MinePrivateFragment;
import com.example.tiktok.mine.MineVideoFragment;
import com.google.android.material.tabs.TabLayout;


public class MineFragment extends Fragment implements PullScrollView.OnTurnListener{

    private static final int PAGE_COUNT = 4;

    private PullScrollView mScrollView;
    private ImageView mHeadImg;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_mine, container, false);

        mScrollView = view.findViewById(R.id.scroll_view);
        mHeadImg = view.findViewById(R.id.background_img);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);

        mTabLayout = view.findViewById(R.id.tl_mine);
        mViewPager = view.findViewById(R.id.vp_mine);
        setViewPager();

        return view;
    }

    private void  setViewPager(){
        //设置ViewPager
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case 0:
                        return new MineVideoFragment();
                    case 1:
                        return new MinePrivateFragment();
                    case 2:
                        return new MineLoveFragment();
                    case 3:
                        return new MineCollectFragment();
                }
                return new MineVideoFragment();
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "作品";
                    case 1:
                        return "私密";
                    case 2:
                        return "喜欢";
                    case 3:
                        return "收藏";
                }
                return "作品";
            }
        });
        mViewPager.setCurrentItem(0);

        //设置TabLayout与ViewPager同步
        mTabLayout.setupWithViewPager(mViewPager);
    }
    @Override
    public void onTurn() {
    }
}
