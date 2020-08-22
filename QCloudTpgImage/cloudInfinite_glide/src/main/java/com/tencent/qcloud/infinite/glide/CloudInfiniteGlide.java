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

package com.tencent.qcloud.infinite.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.tencent.qcloud.infinite.utils.UrlUtil;

/**
 * CloudInfinite Glide工具类
 */
public class CloudInfiniteGlide {
    private static final String TAG = "CloudInfiniteGlide";

    /**
     * 获取图片主色缩略图请求
     * @param context 上下文
     * @param url 图片URL
     * @return 图片主色缩略图Request
     */
    public static RequestBuilder<Drawable> getImageAveThumbnail(Context context, String url){
        return Glide.with(context).load(UrlUtil.attachGetParams(url, "imageAve"));
    }

    /**
     * 是否引入了tpg sdk
     * @return 是否引入了tpg sdk
     */
    public static boolean isImportTPG(){
        try {
            Class clazz = Class.forName("com.tencent.tpg.TPGDecoder");
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "如需TPG相关功能，请引入tpg sdk：implementation 'com.tencent.qcloud:tpg:lastversion'");
            return false;
        }

        return true;
    }
}
