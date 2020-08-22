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

package com.tencent.qcloud.infinite;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.enumm.CIImageLoadOptions;
import com.tencent.qcloud.infinite.transform.CITransform;
import com.tencent.qcloud.infinite.transform.FormatTransform;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 数据万象<br>
 * 数据万象（Cloud Infinite，CI）是腾讯云为客户提供的专业一体化的图片解决方案，涵盖图片上传、下载、存储、处理、识别等功能，将 QQ 空间相册积累的十年图片服务运作经验开放给开发者。
 * 目前数据万象提供图片缩放、裁剪、水印、转码、内容审核等多种功能，提供高效准确的图像识别及处理服务，减少人力投入，真正地实现人工智能。
 * 详细介绍：<a href="https://cloud.tencent.com/document/product/460">https://cloud.tencent.com/document/product/460.</a>
 */
public class CloudInfinite {

    /**
     * 异步获取图片加载请求
     *
     * @param url            原始URL
     * @param transformation 转换参数
     * @param callback       图片加载请求结果回调
     */
    public void requestWithBaseUrl(@NonNull URL url, @NonNull CITransformation transformation, @NonNull CloudInfiniteCallback callback) {
        callback.onImageLoadRequest(requestWithBaseUrlSync(url, transformation));
    }

    /**
     * 同步获取图片加载请求
     *
     * @param url            原始URL
     * @param transformation 转换参数
     * @return 图片加载请求
     */
    @SuppressLint("DefaultLocale")
    public CIImageLoadRequest requestWithBaseUrlSync(@NonNull URL url, @NonNull CITransformation transformation) {
        CIImageLoadRequest imageLoadRequest = new CIImageLoadRequest(url);

        if (transformation.getTransforms() == null || transformation.getTransforms().size() == 0) {
            return imageLoadRequest;
        }

        StringBuilder param = new StringBuilder();
        //对单独格式转换操作进行特殊处理
        if (transformation.getTransforms().size() == 1 && transformation.getTransforms().get(0) instanceof FormatTransform
                && ((FormatTransform) (transformation.getTransforms().get(0))).getFormatOptions() == CIImageLoadOptions.LoadTypeAcceptHeader) {
            imageLoadRequest.addHeader("Accept", "image/" + ((FormatTransform) (transformation.getTransforms().get(0))).getFormat().getFormat());
        } else {
            for (CITransform transform : transformation.getTransforms()) {
                //多个操作时，图片格式转换操作的formatOptions必须为UrlFooter，否则会失效
                if (transform instanceof FormatTransform) {
                    ((FormatTransform) transform).formatOptionsToUrlFooter();
                }
                param.append(transform.getTransformString()).append("|");
            }
            //删除最后一个|
            param.deleteCharAt(param.length() - 1);
        }

        //构造新的URL(由于重复参数会以第一个为准，因此此处先不考虑去重，只是将新的转换参数插入到第一个URL参数)
        String urlStr = url.toString();
        int questionMarkIndex = urlStr.indexOf("?");
        if (questionMarkIndex == -1) {
            if (!TextUtils.isEmpty(param)) {
                urlStr = urlStr + "?" + param.toString();
            }
        } else {
            StringBuilder urlBuilder = new StringBuilder(urlStr);
            urlBuilder.insert(questionMarkIndex + 1, param.toString() + "&");
            urlStr = urlBuilder.toString();
        }

        URL newUrl = null;
        try {
            newUrl = new URL(urlStr);
        } catch (MalformedURLException e) {
            //此处参数URL已经保证格式正确，拼接参数后格式也为正确，因此不对外抛异常
            e.printStackTrace();
        }
        imageLoadRequest.setUrl(newUrl);

        return imageLoadRequest;
    }
}
