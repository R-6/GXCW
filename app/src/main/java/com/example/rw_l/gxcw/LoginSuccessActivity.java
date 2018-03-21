package com.example.rw_l.gxcw;

/**
 * Created by RW_L on 2018/3/17 0017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class LoginSuccessActivity extends AppCompatActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.user);


        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        Explode explode = new Explode();

        explode.setDuration(500);

        getWindow().setExitTransition(explode);

        getWindow().setEnterTransition(explode);

        // section 1
        QMUIGroupListView groupListView = findViewById(R.id.groupListView_user);
        QMUIGroupListView.newSection(this)
                .setTitle("我的信息")
                .setDescription("这是Section 1的描述")
                .addItemView(groupListView.createItemView("item 1"), new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(LoginSuccessActivity.this, "section 1 item 1", Toast.LENGTH_SHORT).show();
                    }
                })
                .addItemView(groupListView.createItemView("item 2"), new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(LoginSuccessActivity.this, "section 1 item 2", Toast.LENGTH_SHORT).show();
                    }
                })
                // 设置分隔线的样式
                //                 .setSeparatorDrawableRes(
                //                                         R.drawable.list_group_item_single_bg,
                //                         R.drawable.personal_list_group_item_top_bg,
                //                         R.drawable.list_group_item_bottom_bg,
                //                         R.drawable.personal_list_group_item_middle_bg)
                // 如果没有title,加上默认title【Section n】
                .setUseDefaultTitleIfNone(true)
                // 默认使用TitleView的padding作section分隔,可以设置为false取消它
                .setUseTitleViewForSectionSpace(false)
                .addTo(groupListView);

        // section 2
        QMUIGroupListView.newSection(this)
                .setTitle("Section Title 2")
                .setDescription("这是Section 2的描述")
                .addItemView(groupListView.createItemView("item 1"), new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(LoginSuccessActivity.this, "section 2 item 1", Toast.LENGTH_SHORT).show();
                    }
                })
                .addItemView(groupListView.createItemView("item 2"), new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(LoginSuccessActivity.this, "section 2 item 2", Toast.LENGTH_SHORT).show();
                    }
                })
                .addTo(groupListView);
    }

}