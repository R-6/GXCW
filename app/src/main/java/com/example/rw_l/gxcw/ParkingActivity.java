package com.example.rw_l.gxcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParkingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_parking)
    Toolbar toolbar;

    @BindView(R.id.end_parking)
    Button endButton;

    @BindView(R.id.parking_name)
    TextView parkingName;
//
//    @BindView(R.id.imageParking)
//    ImageView imgview;


    private String name;
    private String picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        bundle = intent.getExtras();
        name = bundle.getString("name");
        picture = bundle.getString("picture");
        parkingName.setText(name);

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        Animation alpha= AnimationUtils.loadAnimation(this, R.anim.anim_alpha);//获取透明度变化动画资源
//        //用户等待时设置默认图片的动画
//        alpha.setRepeatCount(Animation.INFINITE);//循环显示
//        imgview.startAnimation(alpha);
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                //从网络上获取图片
//                final Bitmap bitmap=getPicture(picture);
//
//                try {
//                    Thread.sleep(500);      //线程休眠0.5秒
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                //发送一个Runnable对象
//                imgview.post(new Runnable(){
//
//                    @Override
//                    public void run() {
//                        imgview.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
//                    }
//                });
//            }
//        }).start();

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

//    public Bitmap getPicture(String path){
//        Bitmap bm=null;
//        URL url;
//        try {
//            url = new URL(path);//创建URL对象
//            URLConnection conn=url.openConnection();//获取URL对象对应的连接
//            conn.connect();//打开连接
//            InputStream is=conn.getInputStream();//获取输入流对象
//            bm= BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
//        } catch (MalformedURLException e1) {
//            e1.printStackTrace();//输出异常信息
//        }catch (IOException e) {
//            e.printStackTrace();//输出异常信息
//        }
//
//        return bm;
//    }
}
