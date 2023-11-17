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

package com.tencent.qcloud.ci.sample.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.tencent.libavif.AvifSequenceDrawable;
import com.tencent.qcloud.image.avif.glide.avif.ByteBufferAvifDecoder;
import com.tencent.qcloud.image.avif.glide.avif.ByteBufferAvifSequenceDecoder;
import com.tencent.qcloud.image.avif.glide.avif.StreamAvifDecoder;
import com.tencent.qcloud.image.avif.glide.avif.StreamAvifSequenceDecoder;
import com.tencent.qcloud.image.decoder.glide.GlideOriginalImageRetry;
import com.tencent.qcloud.image.decoder.glide.GlideOriginalImageRetryCallback;
import com.tencent.qcloud.image.decoder.network.QCloudHttpConfig;
import com.tencent.qcloud.image.decoder.network.glide.QCloudHttpUrlLoader;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 注册自定义GlideModule<br>
 * 开发者应该创建此类注册CIImageRequestModelLoader和TpgDecoder、ByteBufferTpgGifDecoder<br>
 * 类库开发者可以继承LibraryGlideModule创建类似的注册类
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    private static final String TAG = "MyAppGlideModule";

    // 图片缓存最大容量，30M，根据自己的需求进行修改
    public static final int GLIDE_CATCH_SIZE = 250 * 1024 * 1024;
    // 图片缓存子目录
    public static final String GLIDE_CARCH_DIR = "image_catch";

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        //设置glide超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        // 使用QCloudHttpUrlLoader加载GlideUrl
        QCloudHttpConfig qCloudHttpConfig = new QCloudHttpConfig.Builder()
                .setDebuggable(BuildConfig.DEBUG)
                .enableQuic(false)
                .setDownloadMaxThreadCount(10)
//                .setDnsFetch(new QCloudHttpClient.QCloudDnsFetch() {
//                    @Override
//                    public List<InetAddress> fetch(String hostname) throws UnknownHostException {
//                        String ips = MSDKDnsResolver.getInstance().getAddrByName(hostname);
//                        String[] ipArr = ips.split(";");
//                        if (0 == ipArr.length) {
//                            return Collections.emptyList();
//                        }
//                        List<InetAddress> inetAddressList = new ArrayList<>(ipArr.length);
//                        for (String ip : ipArr) {
//                            if ("0".equals(ip)) {
//                                continue;
//                            }
//                            try {
//                                InetAddress inetAddress = InetAddress.getByName(ip);
//                                inetAddressList.add(inetAddress);
//                            } catch (UnknownHostException ignored) {
//                            }
//                        }
//                        return inetAddressList;
//                    }
//                })
                .builder();
//        registry.prepend(GlideUrl.class, InputStream.class, new QCloudHttpUrlLoader.Factory(context.getApplicationContext()));
        registry.prepend(GlideUrl.class, InputStream.class,
                new QCloudHttpUrlLoader.Factory(context.getApplicationContext(), qCloudHttpConfig, url -> {
//                    boolean urlContainsTpg =  url.toStringUrl().contains("format/tpg");
//                    boolean headerContainsTpg = false;
//                    if(url.getHeaders().containsKey("Accept")){
//                        headerContainsTpg = "image/tpg".equals(url.getHeaders().get("Accept"));
//                    }
//                    return urlContainsTpg || headerContainsTpg;
                    return true;
                })
        );
//        registry.prepend(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));

        /*------------------解码器 开始-------------------------*/
        /*如果云端图片已经是TPG格式，不需要进行原图到tpg的转换，则只需要注册解码器即可，不需要注册loader*/
//        registry.prepend(Registry.BUCKET_BITMAP, InputStream.class, Bitmap.class, new StreamTpgDecoder(glide.getBitmapPool(), glide.getArrayPool()));
//        registry.prepend(Registry.BUCKET_BITMAP, ByteBuffer.class, Bitmap.class, new ByteBufferTpgDecoder(glide.getBitmapPool()));
//        registry.prepend(InputStream.class, TpgSequenceDrawable.class, new StreamTpgSequenceDecoder(glide.getBitmapPool(), glide.getArrayPool()));
//        registry.prepend(ByteBuffer.class, TpgSequenceDrawable.class, new ByteBufferTpgSequenceDecoder(glide.getBitmapPool()));

        /*如果云端图片已经是AVIF格式，不需要进行原图到avif的转换，则只需要注册解码器即可，不需要注册loader*/
        registry.prepend(Registry.BUCKET_BITMAP, InputStream.class, Bitmap.class, new StreamAvifDecoder(glide.getBitmapPool(), glide.getArrayPool()));
        registry.prepend(Registry.BUCKET_BITMAP, ByteBuffer.class, Bitmap.class, new ByteBufferAvifDecoder(glide.getBitmapPool()));
        registry.prepend(InputStream.class, AvifSequenceDrawable.class, new StreamAvifSequenceDecoder(glide.getBitmapPool(), glide.getArrayPool()));
        registry.prepend(ByteBuffer.class, AvifSequenceDrawable.class, new ByteBufferAvifSequenceDecoder(glide.getBitmapPool()));
        /*------------------解码器 结束-------------------------*/

    }

    @Override
    public void applyOptions(@NonNull final Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        ImageViewTarget.setTagId(R.id.glide_custom_view_target_tag);

        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, GLIDE_CARCH_DIR, GLIDE_CATCH_SIZE));
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));

        // 原图兜底监听器
        GlideOriginalImageRetry.GlobalOriginalImageRetryRequestListener originalImageRetry = new GlideOriginalImageRetry.GlobalOriginalImageRetryRequestListener();
        originalImageRetry.setOriginalImageRetryCallback(new GlideOriginalImageRetryCallback() {
            @Nullable
            @Override
            public String buildOriginalImageUrl(String urlStr) {
                // 使用默认的原图格式
                return null;
            }

            @Override
            public void onFailureBeforeRetry(@Nullable GlideException e, Object model, Target<?> target, boolean isFirstResource) {
                // tpg加载失败在这里上报，统计原图兜底次数和tpg解码异常信息(不影响真正的图片加载失败率)
                Log.d(TAG, "TPG onLoadFailed："+ model);
                if (e != null) {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadFailed(@Nullable GlideException e, Object model, Target<?> target, boolean isFirstResource) {
                //真正的加载失败在这里上报(影响真正的图片加载成功失败率)
                Log.d(TAG, "Image onLoadFailed："+ model);
                if (e != null) {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onResourceReady(Object resource, Object model, Target<?> target, DataSource dataSource, boolean isFirstResource) {
                //真正的加载成功在这里上报(影响真正的图片加载成功失败率)
                Log.d(TAG, "Image onLoadSuccess："+ model);
            }
        });

//        builder.addGlobalRequestListener(originalImageRetry);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
