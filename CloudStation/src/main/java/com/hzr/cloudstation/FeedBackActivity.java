package com.hzr.cloudstation;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzr.cloudstation.myView.MyToast;

public class FeedBackActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText et_context;
    private Button btn_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_fb);
        if (toolbar != null) {
            toolbar.setTitle("反馈");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FeedBackActivity.this.finish();
                }
            });
        }
        btn_send = (Button) findViewById(R.id.btn_sendFb);
        et_context = (EditText) findViewById(R.id.et_feedBackContext);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String context =et_context.getText().toString();
                if (TextUtils.isEmpty(context)){
                    et_context.setError("反馈内容不能为空");
                }else{
                    MyToast.MyToast(FeedBackActivity.this,"发送成功");
                    FeedBackActivity.this.finish();
                }
            }
        });
    }
}
