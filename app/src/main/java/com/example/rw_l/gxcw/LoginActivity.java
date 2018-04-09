package com.example.rw_l.gxcw;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_username)

    EditText etUsername;

    @BindView(R.id.et_password)

    EditText etPassword;

    @BindView(R.id.bt_go)

    Button btGo;

    @BindView(R.id.cv)

    CardView cv;

    @BindView(R.id.fab)

    FloatingActionButton fab;

    @BindView(R.id.toolbar_login)
    Toolbar toolbar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @OnClick({R.id.bt_go, R.id.fab})

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab:

                getWindow().setExitTransition(null);

                getWindow().setEnterTransition(null);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    ActivityOptions options =

                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());

                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());

                } else {

                    startActivity(new Intent(this, RegisterActivity.class));

                }

                break;

            case R.id.bt_go:

//                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);

                Intent i2 = new Intent(this, UserActivity.class);
//                startActivity(i2, oc2.toBundle());
//                TransitionsHeleper.startAcitivty(this,UserActivity.class,findViewById(R.id.bt_go));
                startActivity(i2);
                this.finish();
                break;

        }

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