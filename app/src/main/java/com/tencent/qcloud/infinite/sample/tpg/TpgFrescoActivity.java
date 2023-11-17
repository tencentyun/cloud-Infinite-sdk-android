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

package com.tencent.qcloud.infinite.sample.tpg;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tencent.qcloud.infinite.CIImageLoadRequest;
import com.tencent.qcloud.infinite.CITransformation;
import com.tencent.qcloud.infinite.CloudInfinite;
import com.tencent.qcloud.infinite.CloudInfiniteCallback;
import com.tencent.qcloud.infinite.enumm.CIImageFormat;
import com.tencent.qcloud.infinite.enumm.CIImageLoadOptions;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.BaseImageInfoFrescoView;
import com.tencent.qcloud.infinite.sample.base.BaseImageListView;
import com.tencent.qcloud.infinite.sample.base.ImageBean;
import com.tencent.qcloud.infinite.sample.base.Utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TpgFrescoActivity extends BaseActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoFrescoView view_imageinfo;
    private SimpleDraweeView iv_tpg;
    private TextView tv_size_tpg;
    private TextView tv_format_tpg;
    private TextView tv_consume_tpg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpg_fresco);

        BaseImageListView view_imagelist = findViewById(R.id.view_imagelist);
        view_imagelist.setOnClickListener(this);

        view_imageinfo = findViewById(R.id.view_imageinfo);
        iv_tpg = findViewById(R.id.iv_tpg);
        tv_size_tpg = findViewById(R.id.tv_size_tpg);
        tv_format_tpg = findViewById(R.id.tv_format_tpg);
        tv_consume_tpg = findViewById(R.id.tv_consume_tpg);
    }

    @Override
    @SuppressLint("DefaultLocale")
    public void onClick(final ImageBean image) {
        iv_tpg.setImageBitmap(null);
        tv_size_tpg.setText("");
        tv_format_tpg.setText("");
        tv_consume_tpg.setText("");

        view_imageinfo.setData(image.url, image.format);
        setTpgImage(image);
    }

    private void setTpgImage(final ImageBean image) {
        //创建数据万象操作器
        CloudInfinite cloudInfinite = new CloudInfinite();
        //根据原始URL和要进行的操作CITransformation，生成图片加载请求CIImageLoadRequest
        cloudInfinite.requestWithBaseUrl(image.url, new CITransformation().format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeUrlFooter), new CloudInfiniteCallback() {
            @Override
            public void onImageLoadRequest(@NonNull final CIImageLoadRequest request) {
                //为了展示原始的加载速度，不适用缓存
                final long start = System.nanoTime();
                BaseControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(
                            String id,
                            @Nullable ImageInfo imageInfo,
                            @Nullable Animatable anim) {
                        long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                        tv_consume_tpg.setText(String.format("耗时：%.2fs", consume / 1000f));
                    }

                    @Override
                    public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        throwable.printStackTrace();
                        long consume = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
                        tv_consume_tpg.setText(String.format("耗时：%.2fs", consume / 1000f));
                    }
                };

                // 设置Tpg解码器
                ImageRequest imageRequest =
                        ImageRequestBuilder.newBuilderWithSource(Uri.parse(request.getUrl().toString()))
                                .disableDiskCache()
//                                    .disableMemoryCache()
//                                    .setImageDecodeOptions(ImageDecodeOptions.newBuilder()
//                                            .setCustomImageDecoder(new FrescoTpgDecoder()) // 配置 TPG 静态解码器
//                                            .setCustomImageDecoder(new FrescoTpgAnimatedDecoder()) // 配置 TPG 静态解码器
//                                            .build()
//                                    )
                                .build();

                AbstractDraweeController controller =
                        Fresco.newDraweeControllerBuilder()
                                .setAutoPlayAnimations(true)
                                .setControllerListener(controllerListener)
                                .setImageRequest(imageRequest).build();
                iv_tpg.setController(controller);
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
                            tv_format_tpg.setText("格式：" + finalContentType);
                            tv_size_tpg.setText(String.format("大小：%s", Utils.readableStorageSize(size)));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}