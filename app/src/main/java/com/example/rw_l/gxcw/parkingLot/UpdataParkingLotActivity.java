package com.example.rw_l.gxcw.parkingLot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rw_l.gxcw.R;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdataParkingLotActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_updata_parking_lot)
    Toolbar toolbar;

    private QMUICommonListItemView myitemView;
    private QMUIDialog.EditTextDialogBuilder dialogChange;
    private QMUIDialog.CheckBoxMessageDialogBuilder dialogBack;
    private Bundle returnBundle;

    private QMUICommonListItemView itemView1;
    private QMUICommonListItemView itemView2;
    private QMUICommonListItemView itemView3;
    private QMUICommonListItemView itemView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_parking_lot);
        ButterKnife.bind(this);

        returnBundle = new Bundle();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String location = bundle.getString("location");
        String total = bundle.getString("total");
        String number = bundle.getString("number");

        QMUIGroupListView groupListView = findViewById(R.id.groupListView_updata_parking_lot);

        itemView1 = groupListView.createItemView(null, "停车场名称", name, QMUICommonListItemView.HORIZONTAL, 1);
        if (itemView1.getDetailText().length()<=0) itemView1.showRedDot(true);
        itemView2 = groupListView.createItemView(null, "停车场地址", location, QMUICommonListItemView.VERTICAL, 1);
        if (itemView2.getDetailText().length()<=0) itemView2.showRedDot(true);
        itemView3 = groupListView.createItemView(null, "总车位", total, QMUICommonListItemView.HORIZONTAL, 1);
        if (itemView3.getDetailText().length()<=0) itemView3.showRedDot(true);
        itemView4 = groupListView.createItemView(null, "空车位", number, QMUICommonListItemView.HORIZONTAL, 1);
        if (itemView4.getDetailText().length()<=0) itemView4.showRedDot(true);

        QMUIGroupListView.newSection(this)
                //.setTitle("我的信息")
                //.setDescription("这是Section 1的描述")
                //groupListView.createItemView(null,"头像",null,1,3)
                .addItemView(itemView1, new View.OnClickListener() {
                    public void onClick(View v) {
                        changeData(itemView1);
                    }
                })
                .addItemView(itemView2, new View.OnClickListener() {
                    public void onClick(View v) {
                        changeData(itemView2);
                    }
                })
                .addItemView(groupListView.createItemView(null,"审核状态","未审核",1,0), new View.OnClickListener() {
                    public void onClick(View v) {
                    }
                })
                .setUseDefaultTitleIfNone(false)            //如果没有title,加上默认title【Section n】
                .setUseTitleViewForSectionSpace(true)       //默认使用TitleView的padding作section分隔,可以设置为false取消它
                .addTo(groupListView);

        // section 2
        QMUIGroupListView.newSection(this)
                //.setTitle("Section Title 2")
                //.setDescription("这是Section 2的描述")
                .addItemView(itemView3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeData(itemView3);
                    }
                })
                .addItemView(itemView4, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeData(itemView4);
                    }
                })

                .addTo(groupListView);

        QMUIGroupListView.newSection(this)
                .addItemView(groupListView.createItemView(null,"保存",null,1,0), new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        returnBundle.putString("name",itemView1.getDetailText().toString()+"");
                        returnBundle.putString("location",itemView2.getDetailText().toString()+"");
                        returnBundle.putString("total",itemView3.getDetailText().toString()+"");
                        returnBundle.putString("number",itemView4.getDetailText().toString()+"");
                        intent.putExtras(returnBundle);
                        setResult(1, intent);
                        finish();
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
                if (str.length() <= 0)
                    Toast.makeText(UpdataParkingLotActivity.this, "请输入"+myitemView.getText().toString(), Toast.LENGTH_SHORT).show();
                else {
                    myitemView.setDetailText(str);
                    dialog.cancel();
                    myitemView.showRedDot(false);
                }
            }
        });
        dialogChange.show();
    }


    @Override
    public void onBackPressed() {
        showDialogBack();
//        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showDialogBack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void showDialogBack(){
        dialogBack = new QMUIDialog.CheckBoxMessageDialogBuilder(this);
        dialogBack.setTitle("退出后是否保存修改？?");
        dialogBack.setChecked(false);
        dialogBack.setMessage("保存修改");
        dialogBack.addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.cancel();
            }
        });
        dialogBack.addAction("退出", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                if(dialogBack.isChecked()){
                    Intent intent = getIntent();
                    returnBundle.putString("name",itemView1.getDetailText()+"");
                    returnBundle.putString("location",itemView2.getDetailText()+"");
                    returnBundle.putString("total",itemView3.getDetailText()+"");
                    returnBundle.putString("number",itemView4.getDetailText()+"");

                    intent.putExtras(returnBundle);
                    setResult(1, intent);
                    UpdataParkingLotActivity.this.finish();
                }else UpdataParkingLotActivity.this.finish();
            }
        });
        dialogBack.show();
    }

}
