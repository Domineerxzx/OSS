package com.tts.oss_1.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 37444 on 2018/3/23.
 */

public class HttpUtils {

    public static void sendOkHttpRequest(final String address, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequest(final String address, final com.zhy
            .http.okhttp.callback.Callback callback, HashMap<String, String>
            param) {
        PostFormBuilder url = OkHttpUtils.post().url(address);
        Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            url.addParams(next.getKey(), next.getValue());
        }
        url.build().execute(callback);
    }
}
