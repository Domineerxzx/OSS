package com.tts.oss_1.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * Created by 37444 on 2018/3/23.
 */

public class UploadUtils {
    //需要，一个handler，一个oss服务器的存储空间名（在ConfigOfOssClient里有），一个要上传文件的key（用来在oss服务器上唯一标识这个文件），上传文件保存在本地的地址
    public static void uploadFileToOss(final Handler handler, String bucketName, String objectKey, String uploadFilePath) {
        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, uploadFilePath);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = InitOssClient.ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Message message = new Message();
                message.obj = "成功上传";
                message.what = ConfigOfOssClient.WHAT_SUCCESS_UPLOAD;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    Message message = new Message();
                    message.obj = "本地异常,上传未成功";
                    message.what = ConfigOfOssClient.WHAT_FAILED_UPLOAD;
                    handler.sendMessage(message);
                }
                if (serviceException != null) {
                    // 服务异常
                    Message message = new Message();
                    message.obj = "服务异常,上传未成功";
                    message.what = ConfigOfOssClient.WHAT_FAILED_UPLOAD;
                    handler.sendMessage(message);
                }
            }
        });
    }
}
