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

package com.tencent.qcloud.infinite.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudResultListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpResult;
import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.http.QCloudHttpRequest;
import com.tencent.qcloud.core.http.ResponseBodyConverter;
import com.tencent.qcloud.infinite.CIImageLoadRequest;
import com.tencent.qcloud.infinite.utils.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import static com.tencent.qcloud.core.http.HttpConstants.RequestMethod.GET;

/**
 * 图片加载器
 */
public class CIImageLoader {
    private static final String TAG = "CIImageLoader";

    private QCloudHttpClient client;

    public CIImageLoader() {
        client = new QCloudHttpClient.Builder().build();
    }

    /**
     * 显示图片到imageview
     *
     * @param imageLoadRequest 图片加载请求
     * @param imageView        图片控件
     */
    public void display(@NonNull CIImageLoadRequest imageLoadRequest, @NonNull ImageView imageView) {
        display(imageLoadRequest, imageView, 0);
    }

    /**
     * 显示图片到imageview
     *
     * @param imageLoadRequest 图片加载请求
     * @param imageView        图片控件
     * @param placeholder      占位图资源id
     */
    public void display(@NonNull CIImageLoadRequest imageLoadRequest, @NonNull ImageView imageView, @DrawableRes final int placeholder) {
        final Reference<ImageView> riv = new WeakReference<>(imageView);

        HttpTask<byte[]> httpTask = buildHttpTask(imageLoadRequest);
        httpTask.schedule();
        httpTask.addResultListener(new QCloudResultListener<HttpResult<byte[]>>() {
            @Override
            public void onSuccess(HttpResult<byte[]> result) {
                if (riv.get() == null) return;
                final ImageView imageView = riv.get();

                if (result.isSuccessful()) {
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(result.content(), 0, result.content().length);
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap);
                            } else {
                                Log.w(TAG, "byte array decode failure");
                                imageView.setImageResource(placeholder);
                            }
                        }
                    });
                } else {
                    Log.w(TAG, String.format("http request failure, code:%d, message:%s", result.code(), result.message()));
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageResource(placeholder);
                        }
                    });
                }
            }

            @Override
            public void onFailure(QCloudClientException clientException, QCloudServiceException serviceException) {
                if (clientException != null) {
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    serviceException.printStackTrace();
                }

                if (riv.get() == null) return;
                final ImageView imageView = riv.get();

                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(placeholder);
                    }
                });
            }
        });
    }

    /**
     * 加载图片数据，回调返回字节数组
     *
     * @param imageLoadRequest        图片加载请求
     * @param ciImageLoadDataCallBack 加载图片结果回调
     */
    public void loadData(@NonNull CIImageLoadRequest imageLoadRequest, @NonNull final CIImageLoadDataCallBack ciImageLoadDataCallBack) {
        HttpTask<byte[]> httpTask = buildHttpTask(imageLoadRequest);
        httpTask.schedule();
        httpTask.addResultListener(new QCloudResultListener<HttpResult<byte[]>>() {
            @Override
            public void onSuccess(HttpResult<byte[]> result) {
                if (result.isSuccessful()) {
                    ciImageLoadDataCallBack.onLoadData(result.content());
                } else {
                    Log.w(TAG, String.format("http request failure, code:%d, message:%s", result.code(), result.message()));
                    ciImageLoadDataCallBack.onLoadData(null);
                }
            }

            @Override
            public void onFailure(QCloudClientException clientException, QCloudServiceException serviceException) {
                ciImageLoadDataCallBack.onFailure(clientException, serviceException);
            }
        });
    }

    /**
     * imageview预加载图片主色
     *
     * @param imageView 图片控件
     * @param objectUrl 图片URL
     */
    public void preloadWithAveColor(@NonNull ImageView imageView, @NonNull URL objectUrl) {
        String url = UrlUtil.attachGetParams(objectUrl.toString(), "imageAve");
        try {
            objectUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final Reference<ImageView> riv = new WeakReference<>(imageView);

        HttpTask<String> httpTask = buildHttpTask(objectUrl);
        httpTask.schedule();
        httpTask.addResultListener(new QCloudResultListener<HttpResult<String>>() {
            @Override
            public void onSuccess(HttpResult<String> result) {
                if (result.isSuccessful()) {
                    if (riv.get() == null) return;
                    final ImageView imageView = riv.get();

                    final String rgb = parseRgb(result.content());
                    if (TextUtils.isEmpty(rgb)) return;

                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setBackgroundColor(Color.parseColor(rgb));
                        }
                    });
                } else {
                    Log.w(TAG, String.format("http request failure, code:%d, message:%s", result.code(), result.message()));
                }
            }

            @Override
            public void onFailure(QCloudClientException clientException, QCloudServiceException serviceException) {
                if (clientException != null) {
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    serviceException.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取图片主色
     *
     * @param objectUrl 图片URL
     * @param callBack  结果回调
     */
    public void getImageAveColor(@NonNull URL objectUrl, final CIImageAveColorCallBack callBack) {
       String url = UrlUtil.attachGetParams(objectUrl.toString(), "imageAve");
        try {
            objectUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpTask<String> httpTask = buildHttpTask(objectUrl);
        httpTask.schedule();
        httpTask.addResultListener(new QCloudResultListener<HttpResult<String>>() {
            @Override
            public void onSuccess(HttpResult<String> result) {
                if (result.isSuccessful()) {
                    callBack.onImageAveColor(parseRgb(result.content()));
                } else {
                    Log.w(TAG, String.format("http request failure, code:%d, message:%s", result.code(), result.message()));
                    callBack.onImageAveColor("");
                }
            }

            @Override
            public void onFailure(QCloudClientException clientException, QCloudServiceException serviceException) {
                callBack.onFailure(clientException, serviceException);
            }
        });
    }

    private String parseRgb(String jsonStr) {
        try {
            JSONTokener jsonParser = new JSONTokener(jsonStr);
            JSONObject jsonObject = (JSONObject) jsonParser.nextValue();

            if (!jsonObject.has("RGB")) return "";
            String rgb = jsonObject.getString("RGB");
            if (TextUtils.isEmpty(rgb)) return "";

            return rgb.replaceFirst("0x","#");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private HttpTask<byte[]> buildHttpTask(@NonNull CIImageLoadRequest imageLoadRequest) {
        QCloudHttpRequest.Builder<byte[]> httpRequestBuilder = new QCloudHttpRequest.Builder<byte[]>()
                .method(GET);
        httpRequestBuilder.addHeaders(imageLoadRequest.getHeaders());
        httpRequestBuilder.url(imageLoadRequest.getUrl());
        httpRequestBuilder.converter(ResponseBodyConverter.bytes());
        QCloudHttpRequest<byte[]> httpRequest = httpRequestBuilder.build();

        return client.resolveRequest(httpRequest);
    }

    private HttpTask<String> buildHttpTask(@NonNull URL url) {
        QCloudHttpRequest.Builder<String> httpRequestBuilder = new QCloudHttpRequest.Builder<String>()
                .method(GET);
        httpRequestBuilder.url(url);
        httpRequestBuilder.converter(ResponseBodyConverter.string());
        QCloudHttpRequest<String> httpRequest = httpRequestBuilder.build();

        return client.resolveRequest(httpRequest);
    }
}
