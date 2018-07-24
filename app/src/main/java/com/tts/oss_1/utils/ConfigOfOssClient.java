package com.tts.oss_1.utils;

/**
 * Created by 37444 on 2018/3/23.
 */

public class ConfigOfOssClient {
    //获取token令牌的地址
    public static final String TOKEN_ADDRESS = "https://thethreestooges.cn:5210/identity/oss/token.php";
    //oss服务器地址
    public static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
    //oss服务器上的存储空间名
    public static final String BUCKET_NAME = "thethreestooges";
    //massage的4种情况
    public static final int WHAT_SUCCESS_DOWNLOAD = 3;
    public static final int WHAT_SUCCESS_UPLOAD = 1;
    public static final int WHAT_FAILED_UPLOAD = 2;
    public static final int WHAT_FAILED_DOWNLOAD = 4;
}
