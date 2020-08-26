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

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.gif.GifDrawable;

public class TpgGifDrawable extends GifDrawable {

    private TpgGifDecoder gifDecoder;

    public TpgGifDrawable(
            Context context,
            TpgGifDecoder gifDecoder,
            BitmapPool bitmapPool,
            Transformation<Bitmap> frameTransformation,
            int targetFrameWidth,
            int targetFrameHeight,
            Bitmap firstFrame) {
        super(context, gifDecoder,bitmapPool, frameTransformation, targetFrameWidth, targetFrameHeight, firstFrame);
        this.gifDecoder = gifDecoder;
    }

    @Override
    public ConstantState getConstantState() {
        return super.getConstantState();
    }

    @Override
    public void stop() {
        Log.d(TpgGifDecoder.TAG, "Drawable stop");
//        if(gifDecoder != null) {
//            gifDecoder.closeDecoder();
//        }
        super.stop();
    }

    @Override
    public void start() {
        Log.d(TpgGifDecoder.TAG, "Drawable start");
//        if(gifDecoder != null) {
//            gifDecoder.openDecoder();
//        }
        super.start();
    }
}
