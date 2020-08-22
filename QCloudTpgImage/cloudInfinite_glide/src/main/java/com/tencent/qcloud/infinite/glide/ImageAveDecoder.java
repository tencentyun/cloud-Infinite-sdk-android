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

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

/**
 * 万象图片主色解码器<br>
 * 用于将主色响应的json串 {"RGB": "0x736246"} 解析为对应的纯色Bitmap
 */
public class ImageAveDecoder implements ResourceDecoder<InputStream, Bitmap> {
    private final BitmapPool bitmapPool;

    public ImageAveDecoder(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;
    }

    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
        int lenght = source.available();
        byte[] buffer = new byte[lenght];
        source.read(buffer);
        String str = new String(buffer);

        try {
            JSONTokener jsonParser = new JSONTokener(str);
            JSONObject jsonObject = (JSONObject) jsonParser.nextValue();

            if (!jsonObject.has("RGB")) return false;
            String rgb = jsonObject.getString("RGB");
            if (TextUtils.isEmpty(rgb)) return false;

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    @Override
    public Resource<Bitmap> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        int lenght = source.available();
        byte[] buffer = new byte[lenght];
        source.read(buffer);
        String str = new String(buffer);

        try {
            JSONTokener jsonParser = new JSONTokener(str);
            JSONObject jsonObject = (JSONObject) jsonParser.nextValue();

            if (!jsonObject.has("RGB")) return null;
            String rgb = jsonObject.getString("RGB");
            if (TextUtils.isEmpty(rgb)) return null;
            rgb = rgb.replaceFirst("0x","#");

            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.parseColor(rgb));
            return new BitmapResource(bitmap, bitmapPool);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
