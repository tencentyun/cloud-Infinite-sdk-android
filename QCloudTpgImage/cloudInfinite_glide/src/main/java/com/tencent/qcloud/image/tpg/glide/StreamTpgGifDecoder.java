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

import android.util.Log;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.tencent.qcloud.infinite.glide.CloudInfiniteGlide;
import com.tencent.tpg.TPGDecoder;
import com.tencent.tpg.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static com.tencent.tpg.Utils.TPG_STATUS_OK;


public class StreamTpgGifDecoder implements ResourceDecoder<InputStream, GifDrawable> {
    private static final String TAG = "StreamTpgGifDecoder";

    private final ResourceDecoder<ByteBuffer, GifDrawable> byteBufferDecoder;

    public StreamTpgGifDecoder(ResourceDecoder<ByteBuffer, GifDrawable> byteBufferDecoder) {
        this.byteBufferDecoder = byteBufferDecoder;
    }

    @Override
    public boolean handles(InputStream source, Options options) {
        if (!CloudInfiniteGlide.isImportTPG()) return false;

        byte[] arr = inputStreamToBytes(source);
        if (arr == null) {
            return false;
        }

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
    public Resource<GifDrawable> decode(InputStream source, int width, int height,
                                        Options options) throws IOException {
        byte[] data = inputStreamToBytes(source);
        if (data == null) {
            return null;
        }

        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        return byteBufferDecoder.decode(byteBuffer, width, height, options);
    }

    private static byte[] inputStreamToBytes(InputStream is) {
        final int bufferSize = 16384;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bufferSize);
        try {
            int nRead;
            byte[] data = new byte[bufferSize];
            while ((nRead = is.read(data)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Error reading data from stream", e);
            }
            return null;
        }
        return buffer.toByteArray();
    }
}
