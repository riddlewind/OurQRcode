package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;
import com.king.zxing.util.CodeUtils;
import com.king.zxing.util.LogUtils;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 主程序
 */
public class MainActivity extends AppCompatActivity {

    public static final String KEY_TITLE = "key_title";

    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;
    public static final int RC_CAMERA = 0X01;

    private Class<?> cls;
    private String title;

    //判断是否为网址的正则表达式
    Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");


    private Button mBtnBuild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnBuild=findViewById(R.id.btn_02);
        mBtnBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InputActivity.class));
            }
        });

    }

    /**
     * 检测拍摄权限
     */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions(){
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startScan(cls,title);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    RC_CAMERA, perms);
        }
    }

    /**
     * 扫码
     */
    private void startScan(Class<?> cls,String title){
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this,R.anim.in,R.anim.out);
        Intent intent = new Intent(this, cls);
        intent.putExtra(KEY_TITLE,title);

        ActivityCompat.startActivityForResult(this,intent,REQUEST_CODE_SCAN,optionsCompat.toBundle());
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_01:
                this.cls = CaptureActivity.class;
                this.title = ((Button)v).getText().toString();
                checkCameraPermissions();
                break;

        }

    }

    /**
     * @author panyunxiang
     * @param requestCode
     * @param resultCode
     * @param data
     * 检测扫描到的内容是网址还是文本
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data!=null){
            switch (requestCode){
                case REQUEST_CODE_SCAN:
                    String result = CameraScan.parseScanResult(data);
                    if(pattern.matcher(result).matches())
                    {
                        //使用浏览器打开扫码获得的网址
                        Intent intent= new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(result);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                    else {
                        //调用ShowActivity显示扫描获得的文本
                        Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                        intent.putExtra("content",result);
                        startActivity(intent);
                    }
                    break;
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }

        }
    }


    private void parsePhoto(Intent data){


        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
            //异步解析
            asyncThread(() -> {
                final String result = CodeUtils.parseCode(bitmap);
                runOnUiThread(() -> {
                    LogUtils.d("result:" + result);
                    Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();

                });

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void asyncThread(Runnable runnable){
        new Thread(runnable).start();
    }

    private Context getContext(){
        return this;
    }

}