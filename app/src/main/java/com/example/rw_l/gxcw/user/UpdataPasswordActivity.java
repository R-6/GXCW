package com.example.rw_l.gxcw.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rw_l.gxcw.R;

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
                if(password_updata_origin.length()!=0
                        &&password_updata_new.length()!=0
                        &&password_updata_new_again.length()!=0) {
                    if(password_updata_new.getText().toString()
                            .equals(password_updata_new_again.getText().toString())) {
                        changepassword();
                    }
                    else Toast.makeText(UpdataPasswordActivity.this,"新密码不一致",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(UpdataPasswordActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
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
