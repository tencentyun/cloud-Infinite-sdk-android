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

package com.tencent.qcloud.infinite.sample.ci;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tencent.qcloud.infinite.CIImageLoadRequest;
import com.tencent.qcloud.infinite.CloudInfinite;
import com.tencent.qcloud.infinite.CloudInfiniteCallback;
import com.tencent.qcloud.infinite.ResponsiveTransformation;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.BaseImageInfoView;
import com.tencent.qcloud.infinite.sample.base.BaseImageListView;
import com.tencent.qcloud.infinite.sample.base.ImageBean;
import com.tencent.qcloud.infinite.sample.base.Utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ResponsiveActivity extends BaseActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoView view_imageinfo;
    private ImageView iv_responsive;
    private TextView tv_size_responsive;
    private TextView tv_format_responsive;
    private TextView tv_consume_responsive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsive);

        BaseImageListView view_imagelist = findViewById(R.id.view_imagelist);
        view_imagelist.setOnClickListener(this);

        view_imageinfo = findViewById(R.id.view_imageinfo);
        iv_responsive = findViewById(R.id.iv_responsive);
        tv_size_responsive = findViewById(R.id.tv_size_responsive);
        tv_format_responsive = findViewById(R.id.tv_format_responsive);
        tv_consume_responsive = findViewById(R.id.tv_consume_responsive);
    }

    @Override
    public void onClick(ImageBean image) {
        iv_responsive.setImageBitmap(null);
        tv_size_responsive.setText("");
        tv_format_responsive.setText("");
        tv_consume_responsive.setText("");

        view_imageinfo.setData(image.url, image.format);
        setResponsiveImage(image);
    }

    private void setResponsiveImage(final ImageBean image) {
        //创建数据万象操作器
        CloudInfinite cloudInfinite = new CloudInfinite();
        //根据原始URL和要进行的操作CITransformation，生成图片加载请求CIImageLoadRequest
        final ResponsiveTransformation responsiveTransformation = new ResponsiveTransformation(view_imageinfo.getImageView());
        cloudInfinite.requestWithBaseUrl(image.url, responsiveTransformation, new CloudInfiniteCallback() {
            @Override
            public void onImageLoadRequest(@NonNull final CIImageLoadRequest request) {
                //为了展示原始的加载速度，不适用缓存
                //使用glide加载CIImageLoadRequest
                final long start = System.nanoTime();
                if (responsiveTransformation.getImageFormat() == null && "GIF".equals(image.format)) {
                    //如果是GIF，则需要asGif()，不然设置不使用缓存时会只显示第一帧
                    Glide.with(ResponsiveActivity.this).asGif()
                            .load(request)
                            .skipMemoryCache(true) //不使用内存缓存
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用磁盘缓存
                            .addListener(new RequestListener<GifDrawable>() {
                                @SuppressLint("DefaultLocale")
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                    long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                                    tv_consume_responsive.setText(String.format("耗时：%.2fs", consume / 1000f));
                                    return false;
                                }

                                @SuppressLint("DefaultLocale")
                                @Override
                                public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                    long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                                    tv_consume_responsive.setText(String.format("耗时：%.2fs", consume / 1000f));
                                    return false;
                                }
                            })
                            .into(iv_responsive);
                } else {
                    Glide.with(ResponsiveActivity.this)
                            .load(request)
                            .skipMemoryCache(true) //不使用内存缓存
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用磁盘缓存
                            .addListener(new RequestListener<Drawable>() {
                                @SuppressLint("DefaultLocale")
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                                    tv_consume_responsive.setText(String.format("耗时：%.2fs", consume / 1000f));
                                    return false;
                                }

                                @SuppressLint("DefaultLocale")
                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                                    tv_consume_responsive.setText(String.format("耗时：%.2fs", consume / 1000f));
                                    return false;
                                }
                            })
                            .into(iv_responsive);
                }

                getFileSizeType(request.getUrl(), request.getHeaders());
            }
        });
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
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            tv_format_responsive.setText("格式：" + finalContentType);
                            tv_size_responsive.setText(String.format("大小：%s", Utils.readableStorageSize(size)));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}