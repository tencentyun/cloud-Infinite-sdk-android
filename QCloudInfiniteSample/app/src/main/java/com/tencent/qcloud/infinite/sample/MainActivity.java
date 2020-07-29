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

package com.tencent.qcloud.infinite.sample;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tencent.qcloud.infinite.CIImageFormat;
import com.tencent.qcloud.infinite.CIImageLoadOptions;
import com.tencent.qcloud.infinite.CIImageLoadRequest;
import com.tencent.qcloud.infinite.CITransformation;
import com.tencent.qcloud.infinite.CloudInfinite;
import com.tencent.qcloud.infinite.CloudInfiniteCallback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv_imagelist;
    private ImageView iv_original;
    private ImageView iv_tpg;
    private TextView tv_original;
    private TextView tv_tpg;
    private TextView tv_original_consume;
    private TextView tv_tpg_consume;

    private ImageListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_imagelist = findViewById(R.id.rv_imagelist);
        iv_original = findViewById(R.id.iv_original);
        iv_tpg = findViewById(R.id.iv_tpg);
        tv_original = findViewById(R.id.tv_original);
        tv_tpg = findViewById(R.id.tv_tpg);
        tv_original_consume = findViewById(R.id.tv_original_consume);
        tv_tpg_consume = findViewById(R.id.tv_tpg_consume);

        //创建默认的线性横向LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_imagelist.setLayoutManager(linearLayoutManager);
        rv_imagelist.setHasFixedSize(true);
        rv_imagelist.addItemDecoration(new ImageListDecoration());

        setData();
    }

    @SuppressLint("DefaultLocale")
    public void setData() {
        //填充测试数据
        ArrayList<ImageBean> list = new ArrayList<>(8);
        try {
            list.add(new ImageBean(new URL("https://cidemo-1251668577.cos.ap-guangzhou.myqcloud.com/images/01.jpg"), "JPG"));
            list.add(new ImageBean(new URL("https://cidemo-1251668577.cos.ap-guangzhou.myqcloud.com/images/02.jpg"), "JPG"));
            list.add(new ImageBean(new URL("https://cidemo-1251668577.cos.ap-guangzhou.myqcloud.com/images/03.jpg"), "JPG"));
            list.add(new ImageBean(new URL("https://cidemo-1251668577.cos.ap-guangzhou.myqcloud.com/images/04.jpg"), "JPG"));
            list.add(new ImageBean(new URL("https://cidemo-1251668577.cos.ap-guangzhou.myqcloud.com/images/05.png"), "PNG"));
            list.add(new ImageBean(new URL("https://cidemo-1251668577.cos.ap-guangzhou.myqcloud.com/images/06.png"), "PNG"));
            list.add(new ImageBean(new URL("https://cidemo-1251668577.cos.ap-guangzhou.myqcloud.com/images/07.png"), "PNG"));
            list.add(new ImageBean(new URL("https://cidemo-1251668577.cos.ap-guangzhou.myqcloud.com/images/08.png"), "PNG"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mAdapter = new ImageListAdapter(list);
        mAdapter.setOnClickListener(new ImageListAdapter.OnClickListener() {
            @Override
            public void onClick(ImageBean image) {
                onItemClick(image);
            }
        });
        rv_imagelist.setAdapter(mAdapter);
        onItemClick(list.get(0));
    }

    @SuppressLint("DefaultLocale")
    private void onItemClick(final ImageBean image) {
        iv_original.setImageBitmap(null);
        iv_tpg.setImageBitmap(null);
        tv_original.setText("");
        tv_tpg.setText("");
        tv_original_consume.setText("");
        tv_tpg_consume.setText("");

        //为了展示原始的加载速度，不适用缓存
        final long start = System.nanoTime();
        Glide.with(MainActivity.this)
                .load(new CIImageLoadRequest(image.url))
                .skipMemoryCache(true) //不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用磁盘缓存
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                        tv_original_consume.setText(String.format("耗时：%.2fs", consume / 1000f));
                        return false;
                    }
                })
                .into(iv_original);

        //获取并展示图片大小（仅做演示）
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取原始文件大小
                final int originalSize = getFileSzie(image.url);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置原始图片信息
                        tv_original.setText(String.format("格式：%s   原大小:%s", image.format, Utils.readableStorageSize(originalSize)));
                    }
                });
            }
        }).start();

        //创建数据万象操作器
        CloudInfinite cloudInfinite = new CloudInfinite();
        //根据原始URL和要进行的操作CITransformation，生成图片加载请求CIImageLoadRequest
        cloudInfinite.requestWithBaseUrl(image.url, new CITransformation().format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeUrlFooter), new CloudInfiniteCallback() {
            @Override
            public void onImageLoadRequest(@NonNull final CIImageLoadRequest request) {
                //为了展示原始的加载速度，不适用缓存
                //使用glide加载CIImageLoadRequest
                final long start = System.nanoTime();
                Glide.with(MainActivity.this)
                        .load(request)
                        .skipMemoryCache(true) //不使用内存缓存
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用磁盘缓存
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                                tv_tpg_consume.setText(String.format("耗时：%.2fs", consume / 1000f));
                                return false;
                            }
                        })
                        .into(iv_tpg);

                //获取并展示图片大小（仅做演示）
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //获取TPG文件大小
                        final int tpgSize = getFileSzie(request.getUrl());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //设置TPG图片信息
                                tv_tpg.setText(String.format("格式：%s   图片大小:%s", "TPG", Utils.readableStorageSize(tpgSize)));
                            }
                        });
                    }
                }).start();

            }
        });
    }

    /**
     * 获取远程文件大小
     */
    private int getFileSzie(URL url) {
        int originalSize = 0;
        try {
            URLConnection connection = url.openConnection();
            originalSize = connection.getContentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return originalSize;
    }
}