package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 生成新的页面显示扫描二维码得到的文本
 */
public class ShowActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        mTv=findViewById(R.id.tv_1);

        //获得Intent传输数据
        Intent intent=getIntent();
        String content = intent.getStringExtra("content");

        mTv.setText(content);
    }
}