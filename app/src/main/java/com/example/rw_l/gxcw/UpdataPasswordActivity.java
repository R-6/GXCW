package com.example.rw_l.gxcw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdataPasswordActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_updata_password)
    Toolbar toolbar;

    @BindView(R.id.bt_updata_password_done)
    Button button_done;

    @BindView(R.id.password_updata_origin)
    EditText password_updata_origin;

    @BindView(R.id.password_updata_new)
    EditText password_updata_new;

    @BindView(R.id.password_updata_new_again)
    EditText password_updata_new_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_password);
        ButterKnife.bind(this);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepassword();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    //更改密码到后台
    public void changepassword(){

    }
}
