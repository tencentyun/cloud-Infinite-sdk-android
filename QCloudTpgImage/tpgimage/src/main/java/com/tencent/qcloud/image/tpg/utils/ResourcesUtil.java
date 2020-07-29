/*
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.tencent.qcloud.image.tpg.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * 资源处理工具类
 */
public class ResourcesUtil {
    private static final String TAG = "ResourcesUtil";

    /**
     * 从URI读取文件
     */
    public static byte[] readFile(URI fileUri) throws IOException {
        File f = new File(fileUri);
        int length = (int) f.length();
        byte[] buff = new byte[length];
        FileInputStream fin = new FileInputStream(f);
        fin.read(buff);
        fin.close();
        return buff;
    }

    /**
     * 读取assets文件
     */
    public static byte[] readFileFromAssets(Context context, String fileName) throws IOException {
        InputStream inputStream = context.getResources().getAssets().open(fileName);
        //获取文件的字节数
        int lenght = inputStream.available();
        //创建byte数组
        byte[] buffer = new byte[lenght];
        //将文件中的数据读到byte数组中
        inputStream.read(buffer);
        inputStream.close();
        return buffer;
    }

    /**
     * 读取Resource文件
     */
    public static byte[] readFileFromResource(Context context, int resourceId) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        // 获取文件的字节数
        int length = inputStream.available();
        // 创建byte数组
        byte[] buffer = new byte[length];
        // 将文件中的数据读到byte数组中
        inputStream.read(buffer);
        inputStream.close();
        return buffer;
    }
}
