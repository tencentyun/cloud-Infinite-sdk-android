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

import com.tencent.qcloud.infinite.enumm.CIGravity;

/**
 * 裁剪操作<br>
 * 包括普通裁剪、缩放裁剪、内切圆裁剪、圆角裁剪和人脸智能裁剪<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/36541">https://cloud.tencent.com/document/product/460/36541.</a>
 */
public class CropTransform extends CITransform {
    private static final String TAG = "CropTransform";

    private int type;

    private int width;
    private int height;
    private int dx;
    private int dy;
    private int radius;
    private CIGravity gravity = null;

    public CropTransform cut(int width, int height, int dx, int dy) {
        this.type = 1;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dy = dy;
        return this;
    }

    public CropTransform cut(int width, int height, int dx, int dy, CIGravity gravity) {
        this.type = 1;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dy = dy;
        this.gravity = gravity;
        return this;
    }

    public CropTransform cropByWidth(int width) {
        this.type = 2;
        this.width = width;
        return this;
    }

    public CropTransform cropByWidth(int width, CIGravity gravity) {
        this.type = 2;
        this.width = width;
        this.gravity = gravity;
        return this;
    }

    public CropTransform cropByHeight(int height) {
        this.type = 3;
        this.height = height;
        return this;
    }

    public CropTransform cropByHeight(int height, CIGravity gravity) {
        this.type = 3;
        this.height = height;
        this.gravity = gravity;
        return this;
    }

    public CropTransform cropByWH(int width, int height) {
        this.type = 4;
        this.width = width;
        this.height = height;
        return this;
    }

    public CropTransform cropByWH(int width, int height, CIGravity gravity) {
        this.type = 4;
        this.width = width;
        this.height = height;
        this.gravity = gravity;
        return this;
    }

    public CropTransform iradius(int radius) {
        this.type = 5;
        this.radius = radius;
        return this;
    }

    public CropTransform rradius(int radius) {
        this.type = 6;
        this.radius = radius;
        return this;
    }

    public CropTransform scrop(int width, int height) {
        this.type = 7;
        this.width = width;
        this.height = height;
        return this;
    }


    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String getTransformString() {
        switch (type) {
            case 1:
                if (gravity == null) {
                    return String.format("imageMogr2/cut/%dx%dx%dx%d", width, height, dx, dy);
                } else {
                    return String.format("imageMogr2/cut/%dx%dx%dx%d/gravity/%s", width, height, dx, dy, gravity.getGravity());
                }
            case 2:
                if (gravity == null) {
                    return String.format("imageMogr2/crop/%dx", width);
                } else {
                    return String.format("imageMogr2/crop/%dx/gravity/%s", width, gravity.getGravity());
                }
            case 3:
                if (gravity == null) {
                    return String.format("imageMogr2/crop/x%d", height);
                } else {
                    return String.format("imageMogr2/crop/x%d/gravity/%s", height, gravity.getGravity());
                }
            case 4:
                if (gravity == null) {
                    return String.format("imageMogr2/crop/%dx%d", width, height);
                } else {
                    return String.format("imageMogr2/crop/%dx%d/gravity/%s", width, height, gravity.getGravity());
                }
            case 5:
                return String.format("imageMogr2/iradius/%d", radius);
            case 6:
                return String.format("imageMogr2/rradius/%d", radius);
            case 7:
                return String.format("imageMogr2/scrop/%dx%d", width, height);
            default:
                return "";
        }
    }
}
