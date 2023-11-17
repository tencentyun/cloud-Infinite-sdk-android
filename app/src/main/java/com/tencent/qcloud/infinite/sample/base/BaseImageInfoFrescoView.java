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

package com.tencent.qcloud.infinite.sample.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tencent.qcloud.infinite.sample.R;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 图片以及信息展示控件封装
 */
public class BaseImageInfoFrescoView extends LinearLayout {
    private static final String TAG = "BaseImageInfoView";

    private SimpleDraweeView iv_image;
    private TextView tv_size;
    private TextView tv_format;
    private TextView tv_consume;

    public BaseImageInfoFrescoView(Context context) {
        this(context, null);
    }

    public BaseImageInfoFrescoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseImageInfoFrescoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_base_imageinfo_fresco, this, true);

        iv_image = findViewById(R.id.iv_image);
        tv_size = findViewById(R.id.tv_size);
        tv_format = findViewById(R.id.tv_format);
        tv_consume = findViewById(R.id.tv_consume);
    }

    public ImageView getImageView() {
        return iv_image;
    }

    @SuppressLint("SetTextI18n")
    public void setData(final URL url, String format) {
        iv_image.setImageBitmap(null);
        tv_size.setText("");
        tv_format.setText("");
        tv_consume.setText("");

        final long start = System.nanoTime();
        BaseControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                tv_consume.setText(String.format("耗时：%.2fs", consume / 1000f));
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                tv_consume.setText(String.format("耗时：%.2fs", consume / 1000f));
            }
        };

        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(url.toString()))
                        .disableDiskCache()
                        .disableMemoryCache()
                        .build();
        AbstractDraweeController controller =
                Fresco.newDraweeControllerBuilder()
                        .setAutoPlayAnimations(true)
                        .setControllerListener(controllerListener)
                        .setImageRequest(imageRequest).build();
        iv_image.setController(controller);

        getFileSizeType(url, null);
    }

    /**
     * 获取并设置远程文件大小和type（仅做演示）
     */
    private void getFileSizeType(final URL url, final Map<String, List<String>> header) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URLConnection connection = url.openConnection();
                    if (header != null && header.size() > 0) {
                        for (String key : header.keySet())
                            connection.addRequestProperty(key, header.get(key).get(0));
                    }
                    final int size = connection.getContentLength();
                    String contentType = connection.getContentType();
                    if (!TextUtils.isEmpty(contentType) && contentType.startsWith("image/")) {
                        contentType = contentType.replace("image/", "").toUpperCase();
                    }

                    final String finalContentType = contentType;
                    post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            tv_format.setText("格式：" + finalContentType);
                            tv_size.setText(String.format("大小：%s", Utils.readableStorageSize(size)));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
