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

import android.annotation.SuppressLint;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * 高斯模糊操作<br>
 * 图片格式为 gif 时，不支持该操作<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/36545">https://cloud.tencent.com/document/product/460/36545.</a>
 */
public class BlurTransform extends CITransform {
    private static final String TAG = "BlurAction";

    private int radius;
    private int sigma;

    /**
     * 高斯模糊操作
     * @param radius 模糊半径，取值范围为1 - 50
     * @param sigma 正态分布的标准差，必须大于0
     */
    public BlurTransform(@IntRange(from = 1, to = 50) int radius, @IntRange(from = 1) int sigma) {
        this.radius = radius;
        this.sigma = sigma;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String getTransformString() {
        return String.format("imageMogr2/blur/%dx%d", radius, sigma);
    }
}
