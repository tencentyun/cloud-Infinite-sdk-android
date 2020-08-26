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

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawableResource;
import com.tencent.qcloud.infinite.glide.CloudInfiniteGlide;
import com.tencent.tpg.TPGDecoder;
import com.tencent.tpg.Utils;

import java.nio.ByteBuffer;

import static com.tencent.tpg.Utils.TPG_STATUS_OK;

/**
 * TPG 动图解码器
 */
public class ByteBufferTpgGifDecoder implements ResourceDecoder<ByteBuffer, GifDrawable> {
    private static final String TAG = "ByteBufferTpgGifDecoder";

    private final Context context;
    private final BitmapPool bitmapPool;
    private final ArrayPool arrayPool;
    private final GifBitmapProvider provider;

    public ByteBufferTpgGifDecoder(Context context, BitmapPool bitmapPool) {
        this.context = context;
        this.bitmapPool = bitmapPool;
        this.arrayPool = null;
        this.provider = null;
    }

    public ByteBufferTpgGifDecoder(Context context, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this.context = context;
        this.bitmapPool = bitmapPool;
        this.arrayPool = arrayPool;
        this.provider = new GifBitmapProvider(bitmapPool, arrayPool);
    }

    @Override
    public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) {
        if (!CloudInfiniteGlide.isImportTPG()) return false;

        byte[] arr = new byte[source.remaining()];
        source.get(arr);

        TPGDecoder pTPG = new TPGDecoder();
        //是否是tpg
        int status = pTPG.parseHeader(arr);
        if (TPG_STATUS_OK != status)
            return false;

        //是否是tpg gif
        int imageMode = pTPG.getTPGType();
        if (Utils.IMAGE_MODE_Animation == imageMode
                || Utils.IMAGE_MODE_AnimationWithAlpha == imageMode) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public GifDrawableResource decode(
            @NonNull ByteBuffer source, int width, int height, @NonNull Options options) {
        byte[] arr = new byte[source.remaining()];
        source.get(arr);
        TPGDecoder pTPG = new TPGDecoder();
        int status = pTPG.parseHeader(arr);
        if (pTPG.getFrameCount() <= 0 || status != TPG_STATUS_OK) {
            return null;
        }

        //需要依赖4.11才支持
//        Bitmap.Config config =
//                options.get(GifOptions.DECODE_FORMAT) == DecodeFormat.PREFER_RGB_565
//                        ? Bitmap.Config.RGB_565
//                        : Bitmap.Config.ARGB_8888;

        int sampleSize = getSampleSize(width, height, pTPG.getWidth(), pTPG.getHeight());

        TpgGifDecoder gifDecoder = new TpgGifDecoder(source, sampleSize, provider);
        gifDecoder.advance();
        Bitmap firstFrame = gifDecoder.getNextFrame();
        if (firstFrame == null) {
            return null;
        }

        Transformation<Bitmap> unitTransformation = UnitTransformation.get();

        com.tencent.qcloud.image.tpg.glide.TpgGifDrawable gifDrawable =
                new com.tencent.qcloud.image.tpg.glide.TpgGifDrawable(context, gifDecoder,this.bitmapPool, unitTransformation, width, height, firstFrame);

        return new GifDrawableResource(gifDrawable);
    }

    private int getSampleSize(int targetWidth, int targetHeight, int width, int height) {
        int exactSampleSize =
                Math.min(height / targetHeight, width / targetWidth);
        int powerOfTwoSampleSize = exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize);
        // Although functionally equivalent to 0 for BitmapFactory, 1 is a safer default for our code
        // than 0.
        int sampleSize = Math.max(1, powerOfTwoSampleSize);
        if (Log.isLoggable(TAG, Log.VERBOSE) && sampleSize > 1) {
            Log.v(
                    TAG,
                    "Downsampling GIF"
                            + ", sampleSize: "
                            + sampleSize
                            + ", target dimens: ["
                            + targetWidth
                            + "x"
                            + targetHeight
                            + "]"
                            + ", actual dimens: ["
                            + width
                            + "x"
                            + height
                            + "]");
        }
        return sampleSize;
    }
}
