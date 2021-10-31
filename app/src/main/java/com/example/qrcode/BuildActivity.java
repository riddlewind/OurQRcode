package com.example.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.king.zxing.util.CodeUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author panyunxiang
 * 生成二维码或者条形码
 * @return
 */
public class BuildActivity extends AppCompatActivity {


    private ImageView mIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);

        mIv=findViewById(R.id.iv_01);

        //获得Intent传输数据
        Intent intent=getIntent();
        String stringValue=intent.getStringExtra("input");
        String checkNum=intent.getStringExtra("checkNum");

        if(checkNum.equals("0")){
            //生成带有南邮校徽图片的二维码
            Bitmap logo = BitmapFactory.decodeResource(getResources(),R.drawable.njupt);
            Bitmap bitmap= CodeUtils.createQRCode(stringValue,700,logo);

            mIv.setImageBitmap(bitmap);
        }else{
            //生成条形码
            Bitmap bitmap= CodeUtils.createBarCode(stringValue, BarcodeFormat.CODE_128,800,200);
            mIv.setImageBitmap(bitmap);
        }

    }
}