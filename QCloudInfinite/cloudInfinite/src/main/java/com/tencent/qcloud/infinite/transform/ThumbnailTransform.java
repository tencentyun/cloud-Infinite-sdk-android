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
 * 缩放操作<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/36540">https://cloud.tencent.com/document/product/460/36540.</a>
 */
public class ThumbnailTransform extends CITransform {
    private static final String TAG = "ThumbnailAction";

    private int type;

    private int scaleWH;
    private int scaleW;
    private int scaleH;

    private int width;
    private int height;

    private int area;

    public ThumbnailTransform thumbnailByScale(int scale) {
        this.type = 1;
        this.scaleWH = scale;
        return this;
    }

    public ThumbnailTransform thumbnailByWidthScale(int scale) {
        this.type = 2;
        this.scaleW = scale;
        return this;
    }

    public ThumbnailTransform thumbnailByHeightScale(int scale) {
        this.type = 3;
        this.scaleH = scale;
        return this;
    }

    public ThumbnailTransform thumbnailByWidth(int width) {
        this.type = 4;
        this.width = width;
        return this;
    }

    public ThumbnailTransform thumbnailByHeight(int height) {
        this.type = 5;
        this.height = height;
        return this;
    }

    public ThumbnailTransform thumbnailByMaxWH(int width, int height) {
        this.type = 6;
        this.width = width;
        this.height = height;
        return this;
    }

    public ThumbnailTransform thumbnailByMinWH(int width, int height) {
        this.type = 7;
        this.width = width;
        this.height = height;
        return this;
    }

    public ThumbnailTransform thumbnailByWH(int width, int height) {
        this.type = 8;
        this.width = width;
        this.height = height;
        return this;
    }

    public ThumbnailTransform thumbnailByPixel(int area) {
        this.type = 9;
        this.area = area;
        return this;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String getTransformString() {
        switch (type) {
            case 1:
                return String.format("imageMogr2/thumbnail/!%dp", scaleWH);
            case 2:
                return String.format("imageMogr2/thumbnail/!%dpx", scaleW);
            case 3:
                return String.format("imageMogr2/thumbnail/!x%dp", scaleH);
            case 4:
                return String.format("imageMogr2/thumbnail/%dx", width);
            case 5:
                return String.format("imageMogr2/thumbnail/x%d", height);
            case 6:
                return String.format("imageMogr2/thumbnail/%dx%d", width, height);
            case 7:
                return String.format("imageMogr2/thumbnail/!%dx%dr", width, height);
            case 8:
                return String.format("imageMogr2/thumbnail/%dx%d!", width, height);
            case 9:
                return String.format("imageMogr2/thumbnail/%d@", area);
            default:
                return "";
        }
    }
}
