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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.tencent.tpg.TPGDecoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.tencent.tpg.Utils.TPG_STATUS_OK;

/**
 * TPG 动图解码器
 */
public class TpgGifDecoder implements GifDecoder {
    private static final String TAG = "TpgGifDecoder";

    private static final int INITIAL_FRAME_POINTER = -1;

    private TPGDecoder pTPG;

    private int framePointer = -1;

    @GifDecodeStatus
    private int status;

    private int sampleSize;
    private int downsampledHeight;
    private int downsampledWidth;

    private ByteBuffer rawData;
    private byte[] rawBytes;

    @NonNull
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

    public TpgGifDecoder(ByteBuffer rawData, int sampleSize) {
        setData(null, rawData, sampleSize);
    }

    @Override
    public int getWidth() {
        return pTPG.getWidth();
    }

    @Override
    public int getHeight() {
        return pTPG.getHeight();
    }

    @NonNull
    @Override
    public ByteBuffer getData() {
        return rawData;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void advance() {
        framePointer = (framePointer + 1) % pTPG.getFrameCount();
    }

    @Override
    public int getDelay(int n) {
        int delay = -1;
        if ((n >= 0) && (n < pTPG.getFrameCount())) {
            delay = pTPG.getDelayTime(rawBytes, n);
        }
        //pTPG.getDelayTime返回的时间需要乘以10才对
        return delay * 10;
    }

    @Override
    public int getNextDelay() {
        if (pTPG.getFrameCount() <= 0 || framePointer < 0) {
            return 0;
        }

        return getDelay(framePointer);
    }

    @Override
    public int getFrameCount() {
        return pTPG.getFrameCount();
    }

    @Override
    public int getCurrentFrameIndex() {
        return framePointer;
    }

    @Override
    public void resetFrameIndex() {
        framePointer = INITIAL_FRAME_POINTER;
    }

    @Override
    public int getLoopCount() {
        return 1;
    }

    @Override
    public int getNetscapeLoopCount() {
        return GifHeader.NETSCAPE_LOOP_COUNT_DOES_NOT_EXIST;
    }

    @Override
    public int getTotalIterationCount() {
        return 1;
    }

    @Override
    public int getByteSize() {
        return rawData.limit();
    }

    @Nullable
    @Override
    public Bitmap getNextFrame() {
        if (getFrameCount() <= 0 || framePointer < 0) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Unable to decode frame"
                        + ", frameCount=" + getFrameCount()
                        + ", framePointer=" + framePointer
                );
            }
            status = STATUS_FORMAT_ERROR;
        }
        if (status == STATUS_FORMAT_ERROR || status == STATUS_OPEN_ERROR) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Unable to decode frame, status=" + status);
            }
            return null;
        }
        status = STATUS_OK;

        final Bitmap bm = Bitmap.createBitmap(downsampledWidth,
                downsampledHeight, bitmapConfig);
        int[] outData = new int[downsampledWidth * downsampledHeight];
        int[] delayTime = new int[1];
        int tpgStatus = pTPG.decodeOneFrame(rawBytes, framePointer, outData, bm,
                delayTime);
        if (tpgStatus != TPG_STATUS_OK) {
            this.status = STATUS_PARTIAL_DECODE;
        }
        return bm;
    }

    @Override
    public int read(@Nullable InputStream is, int contentLength) {
        if (is != null) {
            try {
                int capacity = (contentLength > 0) ? (contentLength + 4 * 1024) : 16 * 1024;
                ByteArrayOutputStream buffer = new ByteArrayOutputStream(capacity);
                int nRead;
                byte[] data = new byte[16 * 1024];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();

                read(buffer.toByteArray());
            } catch (IOException e) {
                Log.w(TAG, "Error reading data from stream", e);
            }
        } else {
            status = STATUS_OPEN_ERROR;
        }

        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            Log.w(TAG, "Error closing stream", e);
        }

        return status;
    }

    @Override
    public void clear() {
        pTPG.closeDecode();
        pTPG = null;
    }

    @Override
    public void setData(GifHeader header, @NonNull byte[] data) {
        setData(header, ByteBuffer.wrap(data));
    }

    @Override
    public void setData(GifHeader header, @NonNull ByteBuffer buffer) {
        setData(header, buffer, 1);
    }

    @Override
    public void setData(GifHeader header, @NonNull ByteBuffer buffer, int sampleSize) {
        if (sampleSize <= 0) {
            throw new IllegalArgumentException("Sample size must be >=0, not: " + sampleSize);
        }
        // Make sure sample size is a power of 2.
        sampleSize = Integer.highestOneBit(sampleSize);
        framePointer = INITIAL_FRAME_POINTER;
        // Initialize the raw data buffer.
        rawData = buffer.asReadOnlyBuffer();
        rawData.position(0);
        rawData.order(ByteOrder.LITTLE_ENDIAN);

        rawBytes = new byte[rawData.remaining()];
        rawData.get(rawBytes);

        this.pTPG = new TPGDecoder();
        int tpgStatus = pTPG.parseHeader(rawBytes);
        if (tpgStatus != TPG_STATUS_OK) {
            this.status = STATUS_FORMAT_ERROR;
        }
        pTPG.startDecode(rawBytes);

        this.sampleSize = sampleSize;
        downsampledWidth = getWidth() / sampleSize;
        downsampledHeight = getHeight() / sampleSize;
    }

    @Override
    @GifDecodeStatus
    public int read(@Nullable byte[] data) {
        if (data != null) {
            setData(null, data);
        }
        return status;
    }

    public void setDefaultBitmapConfig(@NonNull Bitmap.Config config) {
        if (config != Bitmap.Config.ARGB_8888 && config != Bitmap.Config.RGB_565) {
            throw new IllegalArgumentException("Unsupported format: " + config
                    + ", must be one of " + Bitmap.Config.ARGB_8888 + " or " + Bitmap.Config.RGB_565);
        }

        bitmapConfig = config;
    }
}
