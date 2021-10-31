package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 通过输入的内容生成对应的二维码或条形码
 */
public class InputActivity extends AppCompatActivity {

    private Button mBtn;
    private EditText mEt;
    private RadioGroup mRg;

    private String checkNum="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mBtn=findViewById(R.id.btn_01);
        mEt=findViewById(R.id.et_01);
        mRg=findViewById(R.id.rg_1);

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_2)
                    checkNum="1";
                else
                    checkNum="0";
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(InputActivity.this, BuildActivity.class);
                intent.putExtra("input", mEt.getText().toString());
                intent.putExtra("checkNum",checkNum);
                startActivity(intent);
            }
        });
    }
}