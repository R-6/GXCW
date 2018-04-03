package com.example.rw_l.gxcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class UserActivity extends AppCompatActivity {

    private QMUICommonListItemView myitemView;
    private QMUIDialog.EditTextDialogBuilder dialogChange;
    private String nickName;
    private String userName;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //初始化用户信息，调用接口
        nickName = "Chris";
        userName = "克里斯";
        phoneNumber = "136*****666";

        // section 1
        QMUIGroupListView groupListView = findViewById(R.id.groupListView_user);
        //创建第一个itemview，用户头像
        QMUICommonListItemView itemView1 = groupListView.createItemView(null, "头像", null, 0, 3);    //无副标题，自定义右侧视图
        //创建一个图片视图，设置用户图像
        QMUIRadiusImageView imgv = new QMUIRadiusImageView(this);
        imgv.setImageResource(R.drawable.park);
        imgv.setClickable(true);
        imgv.setCircle(true);
        imgv.setLayoutParams(new LinearLayout.LayoutParams(135, 135));
        itemView1.addAccessoryCustomView(imgv);
        itemView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 230));

        final QMUICommonListItemView itemView2 = groupListView.createItemView(null, "昵称", nickName, QMUICommonListItemView.HORIZONTAL, 1);
        itemView2.showNewTip(true);
        final QMUICommonListItemView itemView3 = groupListView.createItemView(null, "姓名", userName, QMUICommonListItemView.HORIZONTAL, 1);
        itemView3.showNewTip(true);
        final QMUICommonListItemView itemView4 = groupListView.createItemView(null, "手机号", phoneNumber, QMUICommonListItemView.HORIZONTAL, 1);
        itemView4.showNewTip(true);

        QMUIGroupListView.newSection(this)
                //.setTitle("我的信息")
                //.setDescription("这是Section 1的描述")
                //groupListView.createItemView(null,"头像",null,1,3)
                .addItemView(itemView1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .addItemView(itemView2, new View.OnClickListener() {
                    public void onClick(View v) {
                        changeData(itemView2);
                    }
                })
                .addItemView(itemView3, new View.OnClickListener() {
                    public void onClick(View v) {
                        changeData(itemView3);
                    }
                })
                .addItemView(groupListView.createItemView(null,"实名认证","未认证",1,0), new View.OnClickListener() {
                    public void onClick(View v) {
                    }
                })
                .addItemView(itemView4, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeData(itemView4);
                    }
                })
                .setUseDefaultTitleIfNone(false)            //如果没有title,加上默认title【Section n】
                .setUseTitleViewForSectionSpace(true)       //默认使用TitleView的padding作section分隔,可以设置为false取消它
                .addTo(groupListView);

        // section 2
        QMUIGroupListView.newSection(this)
                //.setTitle("Section Title 2")
                //.setDescription("这是Section 2的描述")
                .addItemView(groupListView.createItemView(null,"车牌号","粤BXXXXX",1,0), new View.OnClickListener() {
                    public void onClick(View v) {
                    }
                })
                .addItemView(groupListView.createItemView(null,"车型","Tesla Modle3",1,0), new View.OnClickListener() {
                    public void onClick(View v) {
                    }
                })
                .addTo(groupListView);

        QMUIGroupListView.newSection(this)
                .addItemView(groupListView.createItemView(null,"修改密码",null,1,1), new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(UserActivity.this,UpdataPasswordActivity.class);
                        startActivity(intent);
                    }
                })
                .addTo(groupListView);
    }



    private void changeData(QMUICommonListItemView itemView) {
        myitemView = itemView;
        final String key = myitemView.getText().toString();
        dialogChange = new QMUIDialog.EditTextDialogBuilder(this);
        dialogChange.setPlaceholder("在此输入您的"+key);
        dialogChange.setTitle("更改"+key);
        dialogChange.addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.cancel();
            }
        });
        dialogChange.addAction("确定", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                String str = dialogChange.getEditText().getText().toString();
                if (str == null || str.length() <= 0)
                    Toast.makeText(UserActivity.this, "请输入"+myitemView.getText().toString(), Toast.LENGTH_SHORT).show();
                else {
                    updataMessage(key,str);
                    myitemView.setDetailText(str);
                    dialog.cancel();
                    myitemView.showNewTip(false);
                }
            }
        });
        dialogChange.show();
    }

    //更新数据到后台,调用接口
    public void updataMessage(String key,String str){

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
}