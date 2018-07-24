package com.tts.oss_1.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 37444 on 2018/3/23.
 */

public class DownloadUtils {
    //需要一个保存在本地的地址，一个handler，一个oss服务器的存储空间名（在ConfigOfOssClient里有），一个要下载文件的key
    public static void downloadFileFromOss(final File file, final Handler handler, String bucketName, String objectKey){

        GetObjectRequest get = new GetObjectRequest(bucketName, objectKey);
        OSSAsyncTask task = InitOssClient.ossClient.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                InputStream inputStream = result.getObjectContent();

                try {
                    StreamUtils.writeStreamToCache(inputStream, file);
                    inputStream.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Message message = new Message();
                    message.obj = bitmap;
                    message.what = ConfigOfOssClient.WHAT_SUCCESS_DOWNLOAD;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    Message message = new Message();
                    message.obj = "本地异常,下载未成功";
                    message.what = ConfigOfOssClient.WHAT_FAILED_DOWNLOAD;
                    handler.sendMessage(message);
                }
                if (serviceException != null) {
                    // 服务异常
                    Message message = new Message();
                    message.obj = "服务异常,下载未成功";
                    message.what = ConfigOfOssClient.WHAT_FAILED_DOWNLOAD;
                    handler.sendMessage(message);
                }
            }
        });
    }
}
