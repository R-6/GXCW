package com.example.rw_l.gxcw;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.rw_l.gxcw.navigation.NaviInitActivity;
import com.example.rw_l.gxcw.parkingLot.ParkingLotActivity;
import com.example.rw_l.gxcw.user.LoginActivity;
import com.example.rw_l.gxcw.user.UserActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL;
import static com.example.rw_l.gxcw.Info.infos;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private BDLocation mLocation;
    private boolean isFirstLocate = true;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RelativeLayout bottomSheetLayout;
    private CardView bottomCard;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton buttonNavi;
    private Button buttonBack;
    private Button ParkingButton;
    private ImageButton locationButton;
    private BitmapDescriptor mIconMaker;
    private JSONArray jsonarray;
    private Marker nowMarker;
    public myAppContext app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());

        mLocationClient.registerLocationListener(new MyLocationListener());

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        app = (myAppContext) getApplication();
        app.setFirstLogin(true);        //是否登陆过了
        app.setIp("192.168.1.105:8080");    //配置服务器ip

        initView();     //初始化控件

        initListener();     //初始化监听器

        isPermissionsOk();      //判断所需权限是否授权

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setStaticDataToInfo();       //设置静态车位信息
        getDatafromServer();        //从服务器端获取数据填充到Info中

        initMarkerClickEvent();     //点击Marker点事件监听
        initMapClickEvent();        //点击地图事件监听

    }


    private void initView() {

        mapView = findViewById(R.id.bmapView);

        baiduMap = mapView.getMap();

        baiduMap.setMyLocationEnabled(true);

        toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        locationButton = findViewById(R.id.locationButton);

        bottomSheetLayout = findViewById(R.id.bottomSheetLayout);

        bottomCard = findViewById(R.id.bottom_cv);

        buttonNavi = findViewById(R.id.bottom_fab);

        buttonBack = findViewById(R.id.bottom_sheet_button_back);

        ParkingButton = findViewById(R.id.bottom_sheet_parking);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    private void initListener() {

        navigationView.setNavigationItemSelectedListener(this);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLocation();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                moveToLocation();
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (bottomSheetBehavior.getState()){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                }
            }
        });

        ParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNearHasParking()){
                    Intent intent = new Intent(MainActivity.this,ParkingActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this,"你不在此车位附近,请到达车位后再点击停车按钮",Toast.LENGTH_LONG).show();
            }
        });
    }

    //判断所需权限是否有授权
    private void isPermissionsOk() {
        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    //移动到我的位置
    private void moveToLocation() {
        LatLng ll = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        MapStatusUpdate locationUpdate = MapStatusUpdateFactory.newLatLngZoom(ll,17.5f);
        baiduMap.animateMapStatus(locationUpdate);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED
                ||bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            moveToLocation();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            //底部分享栏
            QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(this);
            builder.addItem(R.mipmap.icon_more_operation_share_friend, "分享到微信", 0)
                    .addItem(R.mipmap.icon_more_operation_share_moment, "分享到朋友圈", 0)
                    .addItem(R.mipmap.icon_more_operation_share_weibo, "分享到微博", 0)
                    .addItem(R.mipmap.icon_more_operation_share_chat, "分享到私信", 0)
                    .addItem(R.mipmap.icon_more_operation_share_link, "复制链接", 1)
                    .build().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_car) {
            Intent intent = new Intent(MainActivity.this, ParkingLotActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_user) {
            if (app.isFirstLogin()) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_github) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/R-6/GXCW"));
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setCoorType("BD09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setIsNeedLocationDescribe(true);
        option.setNeedDeviceDirect(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mapView.onPause();    //保持地图一直运行
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能继续使用", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void displayMyLocation(BDLocation location) {
        MyLocationData locationdata = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(location.getDirection())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        baiduMap.setMyLocationData(locationdata);

        MyLocationConfiguration config = new MyLocationConfiguration(NORMAL, true, null);
        baiduMap.setMyLocationConfiguration(config);
    }



    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mLocation = location;
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度:").append(location.getLatitude()).append("\n");
            currentPosition.append("经度:").append(location.getLongitude()).append("\n");
            currentPosition.append("城市:").append(location.getCity()).append("\n");
            currentPosition.append("区:").append(location.getDistrict()).append("\n");
            currentPosition.append("街道:").append(location.getStreet()).append("\n");
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("网络");
            }
            currentPosition.append("方向").append(location.getDirection()).append("\n");
            //positionText.setText(currentPosition);
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                if (isFirstLocate) {
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 17);
                    baiduMap.setMapStatus(update);
                    isFirstLocate = false;
                }
                displayMyLocation(location);
            }
        }
    }



    private class ViewHolder
    {
        ImageView infoImg;
        TextView infoName;
        TextView infoAddress;
        TextView infoPhone;
        TextView infoKcw;
        TextView infoZcw;
    }


    //点击Marker点，先存入ViewHolder，然后显示在BottomSheet控件上
    //加上IF是为了判断是否是 ！第一次！点击MARKER,从而知道布局文件中是否是有数据
    protected void setBottomInfo(RelativeLayout mMarkerLy, Info info)
    {
        //当点击initMapClickEvent后，布局消失，tag存在。
        final Info inf = info;
        ViewHolder viewHolder = null;
        if (mMarkerLy.getTag() == null)
        {
            viewHolder = new ViewHolder();

            viewHolder.infoImg = (ImageView) mMarkerLy
                    .findViewById(R.id.bottom_sheet_image);
            viewHolder.infoName = (TextView) mMarkerLy
                    .findViewById(R.id.bottom_sheet_name);
            viewHolder.infoAddress = (TextView) mMarkerLy
                    .findViewById(R.id.bottom_sheet_address);
            viewHolder.infoZcw = (TextView) mMarkerLy
                    .findViewById(R.id.bottom_sheet_zcw);
            viewHolder.infoKcw = (TextView) mMarkerLy
                    .findViewById(R.id.bottom_sheet_kcw);
            viewHolder.infoPhone = (TextView) mMarkerLy
                    .findViewById(R.id.bottom_sheet_phone);
                        mMarkerLy.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) mMarkerLy.getTag();//info.getPicture()

        final ImageView imageView = viewHolder.infoImg;
        Animation alpha= AnimationUtils.loadAnimation(this, R.anim.anim_alpha);//获取透明度变化动画资源
        //用户等待时设置默认图片的动画
        alpha.setRepeatCount(Animation.INFINITE);//循环显示
        imageView.startAnimation(alpha);

        new Thread(new Runnable() {

            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(inf.getPicture());

                try {
                    Thread.sleep(500);      //线程休眠0.5秒
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //发送一个Runnable对象
                imageView.post(new Runnable(){

                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                    }
                });
            }
        }).start();


        //        BitmapUtils bitmapUtils = new BitmapUtils(MainActivity.this);
//        bitmapUtils.display(viewHolder.infoImg, info.getPicture());//网络图片
        viewHolder.infoAddress.setText(info.getAddress());
        viewHolder.infoName.setText(info.getName());
        viewHolder.infoKcw.setText(info.getKcw());
        viewHolder.infoZcw.setText(info.getZcw());
        viewHolder.infoPhone.setText(info.getPhone());

        naviInit(info);
    }


    public void setStaticDataToInfo() {
//        IDNA.Info.infos.clear();
        //增加停车点
        infos.add(new Info("22.47862532", "113.4204848", "http://imgsrc.baidu.com/forum/w%3D580/sign=a64442e2c9bf6c81f7372ce08c3fb1d7/9a1c3f12b31bb051c2df15a2337adab44bede028.jpg",null, "广药教学楼停车场", "中山市五桂山广东药科大学", "80", "62","13612345678"));
        infos.add(new Info("22.4855104", "113.419230", "http://imgsrc.baidu.com/forum/w%3D580/sign=cd14ef99ae18972ba33a00c2d6cc7b9d/a3399e22720e0cf3cac89d760f46f21fbf09aaef.jpg", null, "广药体育场", "中山市五桂山广东药科大学", "50", "37","10806"));
//        Info.infos.add(new Info("116.254322", "22.2940235", "http://images.juheapi.com/park/6202.jpg", "http://images.juheapi.com/park/P1003.png", "北京化工大学电教楼", "北京市朝阳区北京化工大学", "123", "66"));
        displayMarker(infos);//把存储在Info中的信息填充到Marker点中，显示在地图上。
    }


    public void getDatafromServer() {//调用API传值，并接受JSON数据；只查询北化附近500m的停车场，获得结果JSONArray
        jsonarray = new JSONArray();//每次获取数据时需要把array清空

        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        final String  HTTPCustomer="http://"+app.getIp()+"/parkingSystem/parkingLotInfo/selectAllParkingLotJson.action";
//        try {
//            URL url = new URL(HTTPCustomer);
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
////            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
////            conn.setRequestProperty("Accept", "application/json");
//            conn.setRequestMethod("GET");
//            conn.connect();
//
//            InputStream is = conn.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            String strRead = null;
//            while ((strRead = reader.readLine()) != null) {
//                sbf.append(strRead);
//                sbf.append("\r\n");
//            }
//            reader.close();
//
//            result = sbf.toString();
//
//            JSONObject jsonobject = new JSONObject(result);
//            JSONArray indexJsonArray = jsonobject.getJSONArray("result");
//            jsonarray = indexJsonArray;
//            setDatatoInfo();//把得到的数据先存储到Info中
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                //第一步：创建HttpClient对象
                HttpClient httpCient = new DefaultHttpClient();
                //第二步：创建代表请求的对象,参数是访问的服务器地址
                HttpPost httpPost = new HttpPost(HTTPCustomer);
                try {
                    //第三步：执行请求，获取服务器发还的相应对象
//                    JSONObject jsonObject=new JSONObject();
//                    jsonObject.put("userNickName", "admin");
//                    jsonObject.put("userPassword", "admin");
//                    StringEntity entity01 = new StringEntity(jsonObject.toString(),"utf-8");
//                    entity01.setContentType("application/json");
//                    httpPost.setEntity(entity01);
                    HttpResponse httpResponse = httpCient.execute(httpPost);
                    //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //第五步：从相应对象当中取出数据，放到entity当中
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");//将entity当中的数据转换为字符串
//                        JSONObject jsonObject = new JSONObject(response);
//                        JSONArray indexJsonArray = jsonObject.getJSONArray("result");
                        JSONArray indexJsonArray = new JSONArray(response);

                        jsonarray = indexJsonArray;
                        setDatatoInfo();//把得到的数据先存储到Info中
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void setDatatoInfo() {//把得到的数据先存储到Info中
        //Info.infos.clear();
        String jd = "";
        String wd = "";
        String ccmc = "";
        String ccdz = "";
        String zcw = "10";//默认
        String kcw = "8";
        String cctp = "";
        String kcwzt = "";
        String phone = "";

        for (int i = 0; i < jsonarray.length(); i++) {
            try {
                //tv.append("CCMC:" + jsonobject2.getString("CCMC") +"KCW:"+ jsonobject2.getString("KCW")+ "\n");
                JSONObject jsonobject = jsonarray.getJSONObject(i);

                jd = jsonobject.getString("longitude");//经度
                wd = jsonobject.getString("latitude");//维度

                ccmc = jsonobject.getString("parkingLotName");//车位名称
                ccdz = jsonobject.getString("parkingLotSite");//车位地址

//                zcw = jsonobject.getString("ZCW");//总车位
//                kcw = jsonobject.getString("KCW");//空车位
//                cctp = "http://images.juheapi.com/park/" + jsonobject.getString("CCTP");//停车场图片
//                kcwzt = "http://images.juheapi.com/park/"+ jsonobject.getString("KCWZT");//车位状态图片
//                phone = jsonobject.getString("PHONE");

                infos.add(new Info(wd, jd, cctp, kcwzt, ccmc, ccdz, zcw, kcw,phone));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("TAG", "Finish set data to Infos");
        displayMarker(infos);//把存储在Info中的信息填充到Marker点中，显示在地图上。
    }


    //把存储在Info中的信息填充到Marker点中，显示在地图上。
    public void displayMarker(final List<Info> infos){      //初始化，根据INFO增加Marker点,

        //mBaiduMap.clear();
        LatLng latLng = null;
        OverlayOptions overlayOptions = null;
        Marker marker = null;
        for (Info info : infos)
        {
            Double latitude = Double.parseDouble(info.getLatitude());//转化为Double经度
            Double longitude = Double.parseDouble(info.getLongitude());//转化为Double纬度
            latLng = new LatLng(latitude, longitude);       //纬度，经度
            mIconMaker = BitmapDescriptorFactory.fromResource(R.mipmap.location);//把图片转化为BitmapDescriptor格式
//                    try {
//                        Log.d("TAG", "123" + info.getState());
////                        URL url = new URL(info.getState().toString());
////                        //BitmapFactory.Options options = new BitmapFactory.Options();
////                        bm = BitmapFactory.decodeStream(url.openStream());//把网上的图片（URL地址）转化成Bitmap只需要这一句
////                        Bitmap bitmap = bm;//放大图片,其实是新建了一个bitmap
////                        mIconMaker = BitmapDescriptorFactory.fromBitmap(bitmap);//把图片转化为BitmapDescriptor格式
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

            overlayOptions = new MarkerOptions().position(latLng)
                    .icon(mIconMaker).zIndex(10);//zIndex没什么用
            marker = (Marker) (baiduMap.addOverlay(overlayOptions));
            //初始化marker的时候，只需要填充两个信息：1.经纬度2.显示图片
            //额外的信息需要用Bundle存储该marker点的Info数据源
            //存入的信息放入键值对中，每次都NEW一个Bundle对象
            //取出时只需要用marker.getExtraInfo().get("info");
            Bundle bundle = new Bundle();//bundel类似于session
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);//把INFO信息存入marker点中
        }

        }



    //Marker点击事件
    private void initMarkerClickEvent() {
        //对 marker 添加点击相应事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                nowMarker = marker;
                LatLng ll = marker.getPosition();
                MapStatusUpdate markerlocationUpdate = MapStatusUpdateFactory.newLatLngZoom(ll,18.5f);
                baiduMap.animateMapStatus(markerlocationUpdate);
                Info info = (Info) marker.getExtraInfo().get("info");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                setBottomInfo(bottomSheetLayout, info);       //把存在marker点中的信息存入布局框架中
                return false;
            }
        });
    }


    //点击地图，使窗口消失
    private void initMapClickEvent()
    {
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener()
        {
            @Override
            public boolean onMapPoiClick(MapPoi arg0)
            {
                return false;
            }
            @Override
            public void onMapClick(LatLng arg0)
            {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        baiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                addParkingLot(latLng);
            }
        });
    }


    //选点添加车位
    private void addParkingLot(LatLng latLng){
        final LatLng ll = latLng;
        QMUIDialog.MessageDialogBuilder dialogAddparkingLot = new QMUIDialog.MessageDialogBuilder(this);
        dialogAddparkingLot.setTitle("添加车位");
        dialogAddparkingLot.setMessage("确定在此添加车位？");
        dialogAddparkingLot.addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.cancel();
            }
        });
        dialogAddparkingLot.addAction("确定", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                Info inf = new Info();
                inf.setLatitude(ll.latitude+"");
                inf.setLongitude(ll.longitude+"");
                infos.add(inf);
                displayMarker(infos);
                dialog.cancel();
            }
        });
        dialogAddparkingLot.show();
    }

//    private void TheMarker() {//Marker
//        //定义Maker坐标点
//        LatLng point = new LatLng(39.9768880000,116.4270740000);
//        LatLng point1 = new LatLng(39.9789740000,116.4268280000);
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//          .fromResource(R.mipmap.location);
//        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
//              .fromResource(R.mipmap.location);
//        //生长动画
//        MarkerOptions option = new MarkerOptions().position(point).icon(bitmap)
//              .zIndex(5).period(10);
////        option.animateType(MarkerAnimateType.grow);
//        MarkerOptions option1 = new MarkerOptions().position(point1).icon(bitmap1)
//              .zIndex(5).period(10);
////        option1.animateType(MarkerAnimateType.grow);
//        final Marker markera = (Marker) baiduMap.addOverlay(option);
//        final Marker markerb = (Marker) baiduMap.addOverlay(option1);
//    }

//    private Handler handler = new Handler() {
//
//        public void handleMessage(Message msg) {
////            bottomSheetLayout.setVisibility(View.VISIBLE);
//            Info info = (Info) msg.getData().getSerializable("info8");
//            if (info == null) {
//                Log.d("TAG", "info is null");
//            } else {
//                setBottomInfo(bottomSheetLayout, info);//把存在marker点中的信息存入布局框架中
//            }
//        }
//
//    };



    private void naviInit(final Info info) {//点击”到这去“按钮，跳转到导航初始化界面

        buttonNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {//要先点击定位按钮！！！！
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                Intent intent = new Intent(MainActivity.this, NaviInitActivity.class);
                Bundle bundle = new Bundle();
                 String end_latitude = info.getLatitude() + "";//转化为Double纬度
                String end_longitude = info.getLongitude() + "";//转化为Double经度
                bundle.putString("end_latitude", end_latitude);
                bundle.putString("end_longitude", end_longitude);
                if (mLocation != null) {    //获取到我的位置才能开始导航
                    String start_longitude = mLocation.getLongitude() + "";
                    String start_latitude = mLocation.getLatitude() + "";

                    bundle.putString("start_latitude", start_latitude);
                    bundle.putString("start_longitude", start_longitude);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }


    //从url地址中拿出图片转化为bitmap格式
    public Bitmap getPicture(String path){
        Bitmap bm=null;
        URL url;
        try {
            url = new URL(path);//创建URL对象
            URLConnection conn=url.openConnection();//获取URL对象对应的连接
            conn.connect();//打开连接
            InputStream is=conn.getInputStream();//获取输入流对象
            bm=BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
        } catch (MalformedURLException e1) {
            e1.printStackTrace();//输出异常信息
        }catch (IOException e) {
            e.printStackTrace();//输出异常信息
        }

        return bm;
    }

    //判断当前位置与选中的位置距离是否小于100m
    public boolean isNearHasParking() {
        LatLng locationll = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        nowMarker.getPosition();
        if (DistanceUtil.getDistance(locationll, nowMarker.getPosition()) <= 100) {
            return true;
        } else
            return false;
    }
}