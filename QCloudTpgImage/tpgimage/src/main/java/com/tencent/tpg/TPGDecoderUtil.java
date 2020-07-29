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

package com.tencent.tpg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import static com.tencent.tpg.TPGDecoder.FORMAT_RGBA;

/**
 * TPG解码工具类
 */
public class TPGDecoderUtil {
    private static final String TAG = "TPGDecoderUtil";

    /**
     * 使用字节数组解码得到bitmap<br>
     * 支持tpg和其他普通普通
     *
     * @param bytes 图片的字节数组
     * @return 结果bitmap
     */
    public static Bitmap decode(byte[] bytes) {
        return decode(bytes, -1);
    }

    /**
     * 使用字节数组解码得到bitmap<br>
     * 支持tpg和其他普通普通
     *
     * @param bytes 图片的字节数组
     * @param dstWidth tpg解码目标宽度（仅支持等比缩放）
     * @return 结果bitmap
     */
    public static Bitmap decode(byte[] bytes, int dstWidth) {
        // TODO: 2020/7/27 待支持指定宽高
        Bitmap bitmap;
        if (isTPGType(bytes)) {
            TPGDecoder pTPG = new TPGDecoder();
            if (dstWidth == -1) {
                bitmap = pTPG.decodeTPG(bytes, FORMAT_RGBA);
            } else {
                bitmap = pTPG.decodeTPG(bytes, FORMAT_RGBA, dstWidth);
            }
        } else {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        if (bitmap == null) {
            Log.w(TAG, "byte array decode failure");
        }

        return bitmap;
    }

    /**
     * 判断图片是否是tpg格式
     *
     * @param b 图片的字节数组
     * @return 是否是tpg格式
     */
    public static boolean isTPGType(byte[] b) {
        if (b == null || b.length < 3) return false;

        byte b0 = b[0];
        byte b1 = b[1];
        byte b2 = b[2];

        return b0 == (byte) 'T' && b1 == (byte) 'P' && b2 == (byte) 'G';
    }
}
