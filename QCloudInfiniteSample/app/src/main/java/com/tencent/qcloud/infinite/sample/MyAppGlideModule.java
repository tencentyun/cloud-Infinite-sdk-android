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

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.module.AppGlideModule;
import com.tencent.qcloud.image.tpg.glide.ByteBufferTpgGifDecoder;
import com.tencent.qcloud.image.tpg.glide.CIImageRequestModelLoader;
import com.tencent.qcloud.image.tpg.glide.TpgDecoder;
import com.tencent.qcloud.infinite.CIImageLoadRequest;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * 注册自定义GlideModule<br>
 * 开发者应该创建此类注册CIImageRequestModelLoader和TpgDecoder、ByteBufferTpgGifDecoder<br>
 * 类库开发者可以继承LibraryGlideModule创建类似的注册类
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, Registry registry) {
        //注册支持CIImageLoadRequest的loader
        registry.prepend(CIImageLoadRequest.class, InputStream.class, new CIImageRequestModelLoader.Factory());

        /*------------------解码器 开始-------------------------*/
        /*如果云端图片已经是TPG格式，不需要进行原图到tpg的转换，则只需要注册解码器即可，不需要注册loader*/
        //注册TPG静态图片解码器
        registry.prepend(InputStream.class, Bitmap.class, new TpgDecoder(glide.getBitmapPool()));
        //注册TPG动图解码器
        registry.prepend(ByteBuffer.class, GifDrawable.class, new ByteBufferTpgGifDecoder(context, glide.getBitmapPool()));
        /*------------------解码器 结束-------------------------*/
    }
}
