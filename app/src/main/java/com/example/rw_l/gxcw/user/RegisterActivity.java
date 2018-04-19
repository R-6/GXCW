package com.example.rw_l.gxcw.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rw_l.gxcw.R;
import com.example.rw_l.gxcw.myAppContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.cv_add)
    CardView cvAdd;

    @BindView(R.id.register_username)
    EditText username;

    @BindView(R.id.register_password)
    EditText password;

    @BindView(R.id.register_phone)
    EditText userPhone;

    @BindView(R.id.register_go)
    Button registerGo;

    private myAppContext app;
    private Bundle returnBundle;

    private  Intent getIntent;
    private String returnCode;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        app = (myAppContext) getApplication();
        getIntent = getIntent();
        returnCode="";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ShowEnterAnimation();

        }

        fab.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override

            public void onClick(View v) {
                animateRevealClose();
            }

        });

        registerGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().length()<=0||password.getText().length()<=0||userPhone.getText().length()<=0){
                    Toast.makeText(RegisterActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    updataToServer();
                }
            }
        });
    }

    private void updataToServer() {
        final String HTTPCustomer = "http://"+app.getIp()+"/parkingSystem/userparking/insertUserparkingJson.action";
        new Thread(new Runnable() {
            @Override
            public void run() {
                //第一步：创建HttpClient对象
                HttpClient httpCient = new DefaultHttpClient();
                //第二步：创建代表请求的对象,参数是访问的服务器地址
                HttpPost httpPost = new HttpPost(HTTPCustomer);
                try {
                    returnBundle = new Bundle();
                    String userName = username.getText().toString()+"";
                    String userPassworrd = password.getText().toString()+"";
                    String phone = userPhone.getText().toString()+"";
                    returnBundle.putString("username", userName);
                    returnBundle.putString("password", userPassworrd);
                    returnBundle.putString("userPhone", phone);
                    getIntent.putExtras(returnBundle);
                    setResult(1, getIntent);

                    //第三步：执行请求，获取服务器发还的相应对象
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("userNickName", userName);
                    jsonObject.put("userPassword", userPassworrd);
                    jsonObject.put("phone",phone);
                    StringEntity entity01 = new StringEntity(jsonObject.toString(),"utf-8");
                    //                    entity01.setContentType("application/json");
                    httpPost.setEntity(entity01);
                    HttpResponse httpResponse = httpCient.execute(httpPost);
                    //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //第五步：从相应对象当中取出数据，放到entity当中
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");//将entity当中的数据转换为字符串
                        switch (response) {
                            case "0":
                                Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                                break;
                            case "1":
                                Toast.makeText(RegisterActivity.this, "手机号已存在", Toast.LENGTH_SHORT).show();
                                break;
                            case "2":
                                RegisterActivity.this.finish();
                                break;
                        }

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


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation() {

        Transition mTransition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(mTransition);
        mTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override

            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }

        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator animator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        animator.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {

        Animator animator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);

        animator.setDuration(500);

        animator.setInterpolator(new AccelerateInterpolator());

        animator.addListener(new AnimatorListenerAdapter() {

            @Override

            public void onAnimationEnd(Animator animation) {

                cvAdd.setVisibility(View.INVISIBLE);

                super.onAnimationEnd(animation);

                fab.setImageResource(R.drawable.plus);

                RegisterActivity.super.onBackPressed();

            }


            @Override

            public void onAnimationStart(Animator animation) {

                super.onAnimationStart(animation);

            }

        });

        animator.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    public void onBackPressed() {

        animateRevealClose();

    }

}