package com.example.tiktok;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tiktok.information.InformationActivity;
import com.example.tiktok.mine.MineCollectFragment;
import com.example.tiktok.mine.MineLoveFragment;
import com.example.tiktok.mine.MinePrivateFragment;
import com.example.tiktok.mine.MineVideoFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.stream.StreamSupport;


public class MineFragment extends Fragment implements PullScrollView.OnTurnListener{

    private static final int PAGE_COUNT = 4;

    private static final int REQUEST_CODE_BACKIMG = 101;
    private static final int REQUEST_CODE_HEADIMG = 102;
    private static final int REQUEST_CODE_INFORMATION = 103;
    private final String SP_HEART = "sp_mine";

    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private PullScrollView mScrollView;
    private ImageView mHeadImg;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CircleImageView civHeadimg;
    private Button btnAge;
    private Button btnInfro;
    private Button btnSchool;
    private Button btnLocate;
    private TextView tvName;
    private TextView tvId;
    private TextView tvIntro;

    private Uri BackImgUri;
    private Uri HeadImgUri;


    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_mine, container, false);

        sp = getActivity().getSharedPreferences(SP_HEART, Context.MODE_PRIVATE);
        editor = sp.edit();


        mScrollView = view.findViewById(R.id.scroll_view);
        mHeadImg = view.findViewById(R.id.background_img);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
        civHeadimg=view.findViewById(R.id.civ_headimg);
        btnAge=view.findViewById(R.id.btn_age);
        btnInfro=view.findViewById(R.id.btn_infro);
        btnSchool=view.findViewById(R.id.btn_school);
        btnLocate=view.findViewById(R.id.btn_locate);
        tvName=view.findViewById(R.id.tv_name);
        tvId=view.findViewById(R.id.tv_id);
        tvIntro=view.findViewById(R.id.tv_intro);

        if (sp.getString("Name","")==""){
            editor.putString("Name",tvName.getText().toString());
            editor.putString("Id",tvId.getText().toString());
            editor.putString("Sex","男");
            editor.putString("Date","2000-11-20");
            editor.putInt("Year",2000);
            editor.putString("Rotation",btnLocate.getText().toString());
            editor.putString("School",btnSchool.getText().toString());
        }

        init(view);


        Button btnBackimg=view.findViewById(R.id.btn_backimg);
        btnBackimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changebackimg(v);
            }
        });

        civHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHeadimg(v);
            }
        });


        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeinformation(v);
            }
        });
        btnInfro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeinformation(v);
            }
        });
        btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeinformation(v);
            }
        });
        btnSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeinformation(v);
            }
        });

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

    private void init(View view){

        String backimguri = sp.getString("backimgbit","");
        if (backimguri != ""){
            //利用Base64将字符串转换为ByteArrayInputStream
            byte[] byteArray=Base64.decode(backimguri, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
            //利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap=BitmapFactory.decodeStream(byteArrayInputStream);
            Log.d("mine", "init: "+bitmap);
            mHeadImg.setImageBitmap(bitmap);
        }

        String headimguri = sp.getString("headimgbit","");
        if (headimguri != ""){
            //利用Base64将字符串转换为ByteArrayInputStream
            byte[] byteArray=Base64.decode(headimguri, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
            //利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap=BitmapFactory.decodeStream(byteArrayInputStream);
            Log.d("mine", "init: "+bitmap);
            civHeadimg.setImageBitmap(bitmap);
        }

        updateinformation();
    }

    private void changeinformation(View view){
        Intent intent = new Intent(getActivity(), InformationActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INFORMATION);
    }

    @Override
    public void onTurn() {
    }

    public void changebackimg(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_TITLE, "选择图片");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_BACKIMG);
    }

    public void changeHeadimg(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_TITLE, "选择图片");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_HEADIMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_BACKIMG && resultCode == Activity.RESULT_OK) {
            BackImgUri = data.getData();

            Bitmap bitmap= null;
            try {
                bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(BackImgUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //将Bitmap压缩至字节数组输出流ByteArrayOutputStream
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            //利用Base64将字节数组输出流中的数据转换成字符串String
            byte[] byteArray=byteArrayOutputStream.toByteArray();
            String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
            //将String保持至SharedPreferences
            editor.putString("backimgbit",imageString);
            editor.commit();

//            Log.d("mine", "onActivityResult: "+bitmap);
            mHeadImg.setImageBitmap(bitmap);
        }
        else if (requestCode == REQUEST_CODE_HEADIMG && resultCode == Activity.RESULT_OK) {
            HeadImgUri = data.getData();

            Bitmap bitmap= null;
            try {
                bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(HeadImgUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //将Bitmap压缩至字节数组输出流ByteArrayOutputStream
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            //利用Base64将字节数组输出流中的数据转换成字符串String
            byte[] byteArray=byteArrayOutputStream.toByteArray();
            String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
            //将String保持至SharedPreferences
            editor.putString("headimgbit",imageString);
            editor.commit();

//            Log.d("mine", "onActivityResult: "+bitmap);
            civHeadimg.setImageBitmap(bitmap);
        }
        else if (requestCode == REQUEST_CODE_INFORMATION && resultCode == Activity.RESULT_OK){
            updateinformation();
        }
    }

    private void updateinformation() {
        tvName.setText(sp.getString("Name",""));
        tvId.setText("抖音号："+sp.getString("Id",""));
        tvIntro.setText(sp.getString("Text",""));
        btnAge.setText(sp.getString("Sex","")+" "+(2021-sp.getInt("Year",2000))+"岁");
        btnLocate.setText(sp.getString("Rotation",""));
        btnSchool.setText(sp.getString("School",""));
    }

}
