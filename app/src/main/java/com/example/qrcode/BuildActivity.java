package com.example.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.king.zxing.util.CodeUtils;

import androidx.appcompat.app.AppCompatActivity;

public class BuildActivity extends AppCompatActivity {


    private ImageView mIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);

        mIv=findViewById(R.id.iv_01);


        Intent intent=getIntent();
        String stringValue=intent.getStringExtra("input");
        String checkNum=intent.getStringExtra("checkNum");

        if(checkNum.equals("0")){

            Bitmap logo = BitmapFactory.decodeResource(getResources(),R.drawable.njupt);
            Bitmap bitmap= CodeUtils.createQRCode(stringValue,700,logo);

            mIv.setImageBitmap(bitmap);
        }else{
            Bitmap bitmap= CodeUtils.createBarCode(stringValue, BarcodeFormat.CODE_128,800,200);
            mIv.setImageBitmap(bitmap);
        }

    }
}