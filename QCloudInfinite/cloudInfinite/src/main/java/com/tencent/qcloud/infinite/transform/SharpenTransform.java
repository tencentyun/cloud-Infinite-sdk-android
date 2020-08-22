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
 * 锐化操作<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/36546">https://cloud.tencent.com/document/product/460/36546.</a>
 */
public class SharpenTransform extends CITransform {
    private static final String TAG = "SharpenAction";

    private int value;

    /**
     * 锐化操作
     * @param value 锐化参数值，取值范围为10 - 300间的整数。参数值越大，锐化效果越明显。（推荐使用70）
     */
    public SharpenTransform(@IntRange(from = 10, to = 300) int value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String getTransformString() {
        return "imageMogr2/sharpen/" + value;
    }
}
