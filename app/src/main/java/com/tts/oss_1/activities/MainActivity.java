package com.tts.oss_1.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.tts.oss_1.utils.ConfigOfOssClient;
import com.tts.oss_1.utils.DownloadUtils;
import com.tts.oss_1.R;
import com.tts.oss_1.utils.InitOssClient;
import com.tts.oss_1.utils.UploadUtils;

import java.io.File;

/**
 * Created by 37444 on 2018/3/23.
 */

public class MainActivity extends Activity {


    private ImageView iv_download;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String success_upload = (String) msg.obj;
                    Toast.makeText(MainActivity.this, success_upload, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String failed_upload = (String) msg.obj;
                    Toast.makeText(MainActivity.this, failed_upload, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    iv_download.setImageBitmap(bitmap);
                    String success_download = "成功下载";
                    Toast.makeText(MainActivity.this, success_download, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    String failed_download = (String) msg.obj;
                    Toast.makeText(MainActivity.this, failed_download, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };
    private EditText et_filepath;
    private EditText et_upload_key;
    private EditText et_download_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        et_filepath = (EditText) findViewById(R.id.et_filepath);//上传的文件本地地址（手机里的）
        et_upload_key = (EditText) findViewById(R.id.et_upload_key);//上传文件的key
        et_download_key = (EditText) findViewById(R.id.et_download_key);//要下载文件的key
        //先初始化oss客户端
        InitOssClient.initOssClient(this, ConfigOfOssClient.TOKEN_ADDRESS, ConfigOfOssClient.ENDPOINT);
    }

    public void upload(View v) {
        String upload_objectKey = et_upload_key.getText().toString().trim();
        String uploadFilePath = et_filepath.getText().toString().trim();
        UploadUtils.uploadFileToOss(handler, ConfigOfOssClient.BUCKET_NAME, upload_objectKey, uploadFilePath);
    }

    public void download(View v) {
        new Thread() {
            @Override
            public void run() {
                String download_objectKey = et_download_key.getText().toString().trim();
                final File cache_image = new File(getCacheDir(), Base64.encodeToString(download_objectKey.getBytes(), Base64.DEFAULT));
                DownloadUtils.downloadFileFromOss(cache_image, handler, ConfigOfOssClient.BUCKET_NAME, download_objectKey);
            }
        }.start();
    }
}
