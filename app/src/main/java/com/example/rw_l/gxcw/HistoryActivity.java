package com.example.rw_l.gxcw;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tyrantgit.explosionfield.ExplosionField;

public class HistoryActivity extends AppCompatActivity{

    @BindView(R.id.history_toolbar)
    Toolbar toolbar;

    @BindView(R.id.history_cv1)
    CardView cardView1;

    @BindView(R.id.history_cv2)
    CardView cardView2;

    @BindView(R.id.rl_animation)
    LinearLayout rl_animation;

    @BindView(R.id.img_card1)
    CardView img_card1;

    @BindView(R.id.img_card2)
    CardView img_card2;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBar;

    @BindView(R.id.empty_view)
    View empty_view;

//    @BindView(R.id.nav_image)
//    ImageView imageView;

    @BindView(R.id.bt_add)
    Button bt_add;

    @BindView(R.id.bt_del)
    Button bt_del;

    @BindView(R.id.lottie_ani)
    LottieAnimationView lottieAnimationView;

    @BindView(R.id.lottie_bt)
    LottieAnimationView lottieAnimationViewbt;

    private ExplosionField explosionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this,toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)&0x00ffffff;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                empty_view.setBackgroundColor(((180 * Math.abs(verticalOffset)/appBarLayout.getTotalScrollRange() ) << 24) | color);
                if ((Math.abs(verticalOffset)*1.0f/appBarLayout.getTotalScrollRange()) < 0.5){
//                    imageView.setScaleX(1.25f-(Math.abs(verticalOffset)*1.0f/appBarLayout.getTotalScrollRange())/2);
//                    imageView.setScaleY(1.25f-(Math.abs(verticalOffset)*1.0f/appBarLayout.getTotalScrollRange())/2);
                    lottieAnimationView.setScaleX(1.25f-(Math.abs(verticalOffset)*1.0f/appBarLayout.getTotalScrollRange())/2);
                    lottieAnimationView.setScaleY(1.25f-(Math.abs(verticalOffset)*1.0f/appBarLayout.getTotalScrollRange())/2);
                }
            }
        });

        lottieAnimationViewbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieAnimationViewbt.playAnimation();
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView2.setVisibility(cardView2.getVisibility()==View.VISIBLE? View.GONE : View.VISIBLE);
            }
        });
//        img_card1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                img_card2.setVisibility(img_card2.getVisibility()==View.VISIBLE? View.GONE : View.VISIBLE);
//            }
//        });
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_card1.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img_card2.setVisibility(View.VISIBLE);

                    }
                },200);
//                for (int i=0;i<rl_animation.getChildCount();i++){
//                    if (rl_animation.getChildAt(i).getVisibility()==View.GONE){
//                        rl_animation.getChildAt(i).setVisibility(View.VISIBLE);
//                        return;
//                    }
//                }

//                if (img_card1.getVisibility()==View.GONE){
//                    img_card1.setVisibility(View.VISIBLE);
//                }else {
//                    img_card2.setVisibility(View.VISIBLE);
//                }
            }
        });
        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_card2.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img_card1.setVisibility(View.VISIBLE);

                    }
                },200);
//                for (int i=0;i<rl_animation.getChildCount();i++){
//                    if (rl_animation.getChildAt(i).getVisibility()==View.VISIBLE){
//                        rl_animation.getChildAt(i).setVisibility(View.GONE);
//                        return;
//                    }
//                }

//                if (img_card1.getVisibility()==View.VISIBLE){
//                    img_card1.setVisibility(View.GONE);
//                }else {
//                    img_card2.setVisibility(View.GONE);
//                }
            }
        });


//        initAnim();
    }

    private void initAnim(){
        LayoutTransition mTransition = new LayoutTransition();
        mTransition.setDuration(LayoutTransition.APPEARING,400);
        mTransition.setDuration(LayoutTransition.DISAPPEARING,400);
        //-----------------------设置动画--------------------
        mTransition.setAnimator(LayoutTransition.APPEARING,getInAnim());
        mTransition.setAnimator(LayoutTransition.DISAPPEARING,getOutAnim());
        //---------------------------------------------------
        mTransition.setStartDelay(LayoutTransition.APPEARING,0);
        mTransition.setStartDelay(LayoutTransition.DISAPPEARING,0);
        //---------------------------------------------------
        mTransition.setInterpolator(LayoutTransition.APPEARING, new DecelerateInterpolator());
        mTransition.setInterpolator(LayoutTransition.DISAPPEARING,new DecelerateInterpolator());
        //----viewgroup绑定----
        rl_animation.setLayoutTransition(mTransition);
    }
    private Animator getInAnim() {
//        Animator appearAnim = ObjectAnimator
//                .ofFloat(null, "translationX", 90f, 0)
//                .setDuration(200);
//        return appearAnim;
        PropertyValuesHolder trX = PropertyValuesHolder.ofFloat("translationX",200f, 0f);
        PropertyValuesHolder trY = PropertyValuesHolder.ofFloat("translationY",0f, 0f);
        PropertyValuesHolder trAlpha = PropertyValuesHolder.ofFloat("alpha", 0f,1f);
        return ObjectAnimator.ofPropertyValuesHolder(this,trY,trAlpha,trX);
    }
    private Animator getOutAnim() {
//        Animator appearAnim = ObjectAnimator
//                .ofFloat(null, "translationX", 0, 90f)
//                .setDuration(200);
//        return appearAnim;
        PropertyValuesHolder trY2 = PropertyValuesHolder.ofFloat("translationY",0f, 0f);
        PropertyValuesHolder trX = PropertyValuesHolder.ofFloat("translationX",0f, -200f);
        PropertyValuesHolder trAlpha2 = PropertyValuesHolder.ofFloat("alpha",1f, 0f);
        return ObjectAnimator.ofPropertyValuesHolder(this,trY2,trAlpha2,trX);
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
