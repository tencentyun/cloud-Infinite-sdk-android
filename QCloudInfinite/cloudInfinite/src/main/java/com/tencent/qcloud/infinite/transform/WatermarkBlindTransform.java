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

import androidx.annotation.NonNull;

/**
 * 盲水印操作<br>
 * 盲水印功能是腾讯云数据万象提供的全新水印模式。通过该功能，您可将水印图以不可见的形式添加到原图信息中，并不会对原图质量产生太大影响。在图片被盗取后，您可对疑似被盗取的资源进行盲水印提取，验证图片归属<br>
 * 下载时添加盲水印文档：<a href="https://cloud.tencent.com/document/product/460/19017#.E4.B8.8B.E8.BD.BD.E6.97.B6.E6.B7.BB.E5.8A.A0">下载时添加盲水印文档.</a><br>
 * 盲水印 API 文档：<a href="https://cloud.tencent.com/document/product/460/19017">https://cloud.tencent.com/document/product/460/19017.</a>
 */
public class WatermarkBlindTransform extends CITransform {

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String getTransformString() {
        return "";
    }
}
