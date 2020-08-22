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
import android.text.TextUtils;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.enumm.CIGravity;
import com.tencent.qcloud.infinite.utils.Base64Util;

import java.io.UnsupportedEncodingException;

/**
 * 图片水印操作<br>
 * 目前水印图片必须指定为已存储于数据万象中的图片。处理图片原图大小不超过20MB、宽高不超过30000像素且总像素不超过1亿像素，处理结果图宽高设置不超过9999像素；针对动图，原图宽 x 高 x 帧数不超过1亿像素<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/6930">https://cloud.tencent.com/document/product/460/6930.</a>
 */
public class WatermarkImageTransform extends CITransform {
    private static final String TAG = "WatermarkImageAction";

    private String imageUrl;
    private CIGravity gravity;
    private int dx;
    private int dy;
    private int blogo;

    public WatermarkImageTransform(Builder builder) {
        this.imageUrl = builder.imageUrl;
        this.gravity = builder.gravity;
        this.dx = builder.dx;
        this.dy = builder.dy;
        this.blogo = builder.blogo;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String getTransformString() {
        String url = null;
        try {
            url = Base64Util.getUrlSafetyBase64(this.imageUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("imageUrl argument illegal");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("watermark/1/image/");
        sb.append(url);
        if (this.gravity != null) {
            sb.append("/gravity/");
            sb.append(this.gravity.getGravity());
        }
        if (this.dx != 0) {
            sb.append("/dx/");
            sb.append(this.dx);
        }
        if (this.dy != 0) {
            sb.append("/dy/");
            sb.append(this.dy);
        }
        if (this.blogo != 0) {
            sb.append("/blogo/");
            sb.append(this.blogo);
        }

        return sb.toString();
    }

    public final static class Builder {
        private String imageUrl;
        private CIGravity gravity;
        private int dx;
        private int dy;
        private int blogo;

        /**
         * 图片水印操作构造器
         *
         * @param imageUrl 水印图片地址，指定的水印图片必须同时满足如下3个条件：
         *                 1、水印图片与源图片必须位于同一个存储桶下。
         *                 2、URL 需使用 COS 域名（不能使用 CDN 加速域名，例如 examplebucket-1250000000.file.myqcloud.com/shuiyin_2.png 不可用 ），且需保证水印图可访问（如果水印图读取权限为私有，则需要携带有效签名）。
         *                 3、URL 必须以http://开始，不能省略 HTTP 头，也不能填 HTTPS 头，例如examplebucket-1250000000.cos.ap-shanghai.myqcloud.com/shuiyin_2.png，https://examplebucket-1250000000.cos.ap-shanghai.myqcloud.com/shuiyin_2.png 为非法的水印 URL。
         */
        public Builder(@NonNull String imageUrl) {
            this.imageUrl = imageUrl;
        }

        /**
         * 生成图片水印操作
         *
         * @return 图片水印操作
         */
        public WatermarkImageTransform builder() {
            return new WatermarkImageTransform(this);
        }

        /**
         * 设置水印九宫格位置
         *
         * @param gravity 水印九宫格位置，默认值 SouthEast
         * @return 图片水印操作构造器
         */
        public Builder setGravity(CIGravity gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * 设置水平（横轴）边距
         *
         * @param dx 水平（横轴）边距，单位为像素，缺省值为0
         * @return 图片水印操作构造器
         */
        public Builder setDistanceX(int dx) {
            this.dx = dx;
            return this;
        }

        /**
         * 设置垂直（纵轴）边距
         *
         * @param dy 垂直（纵轴）边距，单位为像素，默认值为0
         * @return 图片水印操作构造器
         */
        public Builder setDistanceY(int dy) {
            this.dy = dy;
            return this;
        }

        /**
         * 设置水印图适配功能，适用于水印图尺寸过大的场景（如水印墙）
         *
         * @param blogo 共有两种类型：
         *              当 blogo 设置为1时，水印图会被缩放至与原图相似大小后添加
         *              当 blogo 设置为2时，水印图会被直接裁剪至与原图相似大小后添加
         * @return 图片水印操作构造器
         */
        public Builder setBlogo(@IntRange(from = 1, to = 2) int blogo) {
            this.blogo = blogo;
            return this;
        }
    }
}
