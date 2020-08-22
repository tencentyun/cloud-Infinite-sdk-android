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

package com.tencent.qcloud.infinite.transform;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * 旋转操作<br>
 * 包括普通旋转和自适应旋转<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/36542">https://cloud.tencent.com/document/product/460/36542.</a>
 */
public class RotateTransform extends CITransform {
    private static final String TAG = "RotateTransform";

    private int degree = -1;

    /**
     * 自适应旋转：根据原图 EXIF 信息将图片自适应旋转回正
     */
    public RotateTransform() {
    }

    /**
     * 普通旋转：图片顺时针旋转角度，取值范围0 - 360
     * @param degree 旋转角度
     */
    public RotateTransform(@IntRange(from = 0, to = 360) int degree) {
        this.degree = degree;
    }

    @NonNull
    @Override
    public String getTransformString() {
        String rotateStr = "imageMogr2/rotate/";
        if (degree == -1) {
            return rotateStr + "auto-orient";
        } else {
            return rotateStr + degree;
        }
    }
}
