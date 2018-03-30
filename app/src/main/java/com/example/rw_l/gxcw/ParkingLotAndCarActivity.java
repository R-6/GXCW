package com.example.rw_l.gxcw;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ParkingLotAndCarActivity extends AppCompatActivity {

    @InjectView(R.id.tabSegment)
    QMUITabSegment mTabSegment;

    @InjectView(R.id.contentViewPager)
    ViewPager mViewPager;

    @InjectView(R.id.toolbar_parkingLot_and_car)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot_and_car);

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //构建ViewPager的Adapter
        MyPagerAdapter mPagerAdapter = new MyPagerAdapter();
        ArrayList<View> aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.view_car,null,false));
        aList.add(li.inflate(R.layout.view_parking_lot,null,false));
//        aList.add(li.inflate(R.layout.view_three,null,false));
        mPagerAdapter = new MyPagerAdapter(aList);
        mViewPager.setAdapter(mPagerAdapter);

        //设置TabSegment的属性
        mTabSegment.setupWithViewPager(mViewPager, false); //第二个参数要为false,表示不从adapter拿数据
        mTabSegment.addTab(new QMUITabSegment.Tab("我的车辆"));
        mTabSegment.addTab(new QMUITabSegment.Tab("我的车位"));
        mTabSegment.setHasIndicator(true);  //是否需要显示indicator
        mTabSegment.setIndicatorPosition(false);//true 时表示 indicator 位置在 Tab 的上方, false 时表示在下方
        mTabSegment.setIndicatorWidthAdjustContent(true);//设置 indicator的宽度是否随内容宽度变化
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED); //固定宽度，item内容均分
        //        mTabSegment.reset();

        mTabSegment.notifyDataChanged();

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

    //VIEW的PagerAdapter，重写4个必要方法和两个构造方法

    public class MyPagerAdapter extends PagerAdapter {
        private ArrayList<View> viewLists;

        public MyPagerAdapter() {
        }

        public MyPagerAdapter(ArrayList<View> viewLists) {
            super();
            this.viewLists = viewLists;
        }

        @Override
        public int getCount() {
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewLists.get(position));
            return viewLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewLists.get(position));
        }
    }
}
