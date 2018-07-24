package com.tts.oss_1.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 37444 on 2018/3/23.
 */

public class StreamUtils {
    public static void writeStreamToCache(InputStream in, File file) throws IOException {

        FileOutputStream fos = new FileOutputStream(file);
        int len = -1;
        byte[] buffer = new byte[1024];
        while((len = in.read(buffer))!=-1){
            fos.write(buffer,0,len);
        }
        fos.close();
    }
}
