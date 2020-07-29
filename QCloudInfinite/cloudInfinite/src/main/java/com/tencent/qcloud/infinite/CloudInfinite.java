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

import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.utils.UrlUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 数据万象<br>
 * 用于对外提供数据万象服务
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

        //格式转换
        String formatParam = "";
        if (transformation.getFormat() != CIImageFormat.Default) {
            if (transformation.getOptions() == CIImageLoadOptions.LoadTypeAcceptHeader) {
                imageLoadRequest.addHeader("Accept", "image/" + transformation.getFormat().getFormat());
            } else if (transformation.getOptions() == CIImageLoadOptions.LoadTypeUrlFooter) {
                formatParam = "/format/" + transformation.getFormat().getFormat();
            }
        }

        //缩放
        String zoomParam = "";
        if (transformation.getZoomType() == CIImageZoomType.WithWidth) {
            if (transformation.getWidth() != 0) {
                zoomParam = "/thumbnail/" + transformation.getWidth() + "x";
            }
        } else if (transformation.getZoomType() == CIImageZoomType.WithHeight) {
            if (transformation.getHeight() != 0) {
                zoomParam = "/thumbnail/x" + transformation.getHeight();
            }
        } else if (transformation.getZoomType() == CIImageZoomType.WithWidthHeight) {
            if (transformation.getWidth() != 0 && transformation.getHeight() != 0) {
                zoomParam = String.format("%dx%d!", transformation.getWidth(), transformation.getHeight());
            }
        }

        //构造新的URL(由于重复参数会以第一个为准，因此此处先不考虑去重，只是将新的参数 插入到imageMogr2最前面)
        String urlStr = url.toString();
        int imageMogr2Index = urlStr.indexOf("imageMogr2");
        if (imageMogr2Index == -1) {
            urlStr = UrlUtil.attachGetParams(urlStr, "imageMogr2" + formatParam + zoomParam);
        } else {
            StringBuilder urlBuilder = new StringBuilder(urlStr);
            urlBuilder.insert(imageMogr2Index+10, formatParam + zoomParam);
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
