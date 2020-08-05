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

package com.tencent.qcloud.image.tpg.glide;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.tencent.tpg.TPGDecoder;
import com.tencent.tpg.TPGDecoderUtil;
import com.tencent.tpg.Utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * TPG图片解码器<br>
 * 如果需要支持动图GIF，则需要注册{@link ByteBufferTpgGifDecoder}，否则动图将展示为首帧静态图
 */
public class TpgDecoder implements ResourceDecoder<InputStream, Bitmap> {
    private final BitmapPool bitmapPool;

    public TpgDecoder(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;
    }

    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
        int lenght = source.available();
        byte[] buffer = new byte[lenght];
        source.read(buffer);

        //是否是tpg
        TPGDecoder pTPG = new TPGDecoder();
        int status = pTPG.parseHeader(buffer);
        return Utils.TPG_STATUS_OK == status;
    }

    @Nullable
    @Override
    public Resource<Bitmap> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        //获取文件的字节数
        int lenght = source.available();
        //创建byte数组
        byte[] buffer = new byte[lenght];
        //将文件中的数据读到byte数组中
        source.read(buffer);
        Bitmap bitmap = TPGDecoderUtil.decode(buffer, width);
        return new BitmapResource(bitmap, bitmapPool);
    }
}
