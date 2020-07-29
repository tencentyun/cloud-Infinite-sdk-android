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

package com.tencent.qcloud.image.tpg;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;

import com.tencent.qcloud.image.tpg.utils.ResourcesUtil;
import com.tencent.tpg.TPGDecoderUtil;

import java.io.IOException;
import java.net.URI;

/**
 * TPG图片加载器
 */
public class TpgImageLoader {
    private static final String TAG = "TpgImageLoader";

    /**
     * 显示字节数据图片
     * @param imageView 图片控件
     * @param bytes 字节数据
     */
    public static void displayWithData(@NonNull ImageView imageView, @NonNull byte[] bytes) {
        displayWithData(imageView, bytes, 0);
    }

    /**
     * 显示字节数据图片
     * @param imageView 图片控件
     * @param bytes 字节数据
     * @param placeholder 占位资源ID
     */
    public static void displayWithData(@NonNull ImageView imageView, @NonNull byte[] bytes, @DrawableRes int placeholder) {
        Bitmap bitmap = TPGDecoderUtil.decode(bytes);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(placeholder);
        }
    }

    /**
     * 显示文件图片
     * @param imageView 图片控件
     * @param fileUri 图片Uri
     */
    public static void displayWithFileUri(@NonNull ImageView imageView, @NonNull URI fileUri) {
        displayWithFileUri(imageView, fileUri, 0);
    }

    /**
     * 显示文件图片
     * @param imageView 图片控件
     * @param fileUri 图片Uri
     * @param placeholder 占位资源ID
     */
    public static void displayWithFileUri(@NonNull ImageView imageView, @NonNull URI fileUri, @DrawableRes int placeholder) {
        byte[] bytes = new byte[0];
        try {
            bytes = ResourcesUtil.readFile(fileUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = TPGDecoderUtil.decode(bytes);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(placeholder);
        }
    }

    /**
     * 显示资源图片
     * @param imageView 图片控件
     * @param resource 资源ID
     */
    public static void displayWithResource(@NonNull ImageView imageView, @RawRes @DrawableRes int resource) {
        displayWithResource(imageView, resource, 0);
    }

    /**
     * 显示资源图片
     * @param imageView 图片控件
     * @param resource 资源ID
     * @param placeholder 占位资源ID
     */
    public static void displayWithResource(@NonNull ImageView imageView, @DrawableRes int resource, @DrawableRes int placeholder) {
        byte[] bytes = new byte[0];
        try {
            bytes = ResourcesUtil.readFileFromResource(imageView.getContext(), resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = TPGDecoderUtil.decode(bytes);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(placeholder);
        }
    }

    /**
     * 显示assets图片
     * @param imageView 图片控件
     * @param assetsName assets文件名称
     */
    public static void displayWithAssets(@NonNull ImageView imageView, @NonNull String assetsName) {
        displayWithAssets(imageView, assetsName, 0);
    }

    /**
     * 显示assets图片
     * @param imageView 图片控件
     * @param assetsName assets文件名称
     * @param placeholder 占位资源ID
     */
    public static void displayWithAssets(@NonNull ImageView imageView, @NonNull String assetsName, @DrawableRes int placeholder) {
        byte[] bytes = new byte[0];
        try {
            bytes = ResourcesUtil.readFileFromAssets(imageView.getContext(), assetsName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = TPGDecoderUtil.decode(bytes);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(placeholder);
        }
    }

    /**
     * 使用字节数组解码得到bitmap<br>
     * 支持tpg和其他普通普通
     *
     * @param bytes 图片的字节数组
     * @return 结果bitmap
     */
    public static Bitmap decode(byte[] bytes) {
        return decode(bytes, -1);
    }

    /**
     * 使用字节数组解码得到bitmap<br>
     * 支持tpg和其他普通普通
     *
     * @param bytes    图片的字节数组
     * @param dstWidth tpg解码目标宽度（仅支持等比缩放）
     * @return 结果bitmap
     */
    public static Bitmap decode(byte[] bytes, int dstWidth) {
        return TPGDecoderUtil.decode(bytes, dstWidth);
    }

}
