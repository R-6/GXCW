package com.example.rw_l.gxcw.parkingLot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rw_l.gxcw.R;
import com.example.rw_l.gxcw.StatusBarUtil;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotActivity extends AppCompatActivity {

//    @BindView(R.id.materialViewPager)
//    MaterialViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);
        setTitle("");
        //        ButterKnife.bind(this);
        MaterialViewPager mViewPager = findViewById(R.id.materialViewPager233);


        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        StatusBarUtil.immersive(this);


        //        MaterialViewPagerHelper.registerScrollView(this, mScrollView);

        List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(new ScrollFragment_parkingLot());
        fragments.add(new ScrollFragment_singleLot());
        mFragAdapter adapter = new mFragAdapter(getSupportFragmentManager(), fragments);
        mViewPager.getViewPager().setAdapter(adapter);

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green_teal,
//                                "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg"
                                "http://sc.68design.net/photofiles/201107/YOBuP5Byd0.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                }
                //execute others actions if needed (ex : modify your header logo)
                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        //        final View logo = findViewById(R.id.logo_white);
        //        if (logo != null) {
        //            logo.setOnClickListener(new View.OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //                    mViewPager.notifyHeaderChanged();
        //                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
        //                }
        //            });
        //        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    //
    //-----自定义Fragment适配器-----//
    //
    public class mFragAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        public mFragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments=fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            return mFragments.get(arg0);
        }
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position % 2) {
                case 0:
                    return "我的停车场";
                case 1:
                    return "我的车位";
            }
            return "";
        }
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }

//        @Override
//        public Fragment getItem(int position) {
//            switch (position % 2) {
//                case 0:
//                    Fragment fragment=new ScrollFragment_parkingLot();
//                    Bundle bundle=new Bundle();
//                    fragment.setArguments(bundle);
//                    return fragment;
//                //                    return new ScrollFragment_parkingLot().newInstance();
//                case 1:
//                    return new ScrollFragment_singleLot().newInstance();
//                default:
//                    return null;
//            }
//        }
    }

}
