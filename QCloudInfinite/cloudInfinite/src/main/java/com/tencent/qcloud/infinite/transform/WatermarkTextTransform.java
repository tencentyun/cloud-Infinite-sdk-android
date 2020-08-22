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
 * 文字水印操作<br>
 * 处理图片原图大小不超过20MB、宽高不超过30000像素且总像素不超过1亿像素，处理结果图宽高设置不超过9999像素；针对动图，原图宽 x 高 x 帧数不超过1亿像素<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/6951">https://cloud.tencent.com/document/product/460/6951.</a>
 */
public class WatermarkTextTransform extends CITransform {
    private static final String TAG = "WatermarkImageAction";

    private String text;
    private String font;
    private int fontsize;
    private String fill;
    private int dissolve;
    private CIGravity gravity;
    private int dx;
    private int dy;
    private boolean batch;
    private int degree;

    public WatermarkTextTransform(Builder builder) {
        this.text = builder.text;
        this.font = builder.font;
        this.fontsize = builder.fontsize;
        this.fill = builder.fill;
        this.dissolve = builder.dissolve;
        this.gravity = builder.gravity;
        this.dx = builder.dx;
        this.dy = builder.dy;
        this.batch = builder.batch;
        this.degree = builder.degree;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String getTransformString() {
        String textBase64 = null;
        try {
            textBase64 = Base64Util.getUrlSafetyBase64(this.text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(textBase64)) {
            throw new IllegalArgumentException("text argument illegal");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("watermark/2/text/");
        sb.append(textBase64);

        try {
            String fontBase64 = Base64Util.getUrlSafetyBase64(this.font);
            if (!TextUtils.isEmpty(fontBase64)) {
                sb.append("/font/");
                sb.append(fontBase64);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (this.fontsize != 0) {
            sb.append("/fontsize/");
            sb.append(this.fontsize);
        }

        try {
            String fillBase64 = Base64Util.getUrlSafetyBase64(this.fill);
            if (!TextUtils.isEmpty(fillBase64)) {
                sb.append("/fill/");
                sb.append(fillBase64);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (this.dissolve != 0) {
            sb.append("/dissolve/");
            sb.append(this.dissolve);
        }

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

        if (this.batch) {
            sb.append("/batch/1");
        }

        if (this.degree != 0) {
            sb.append("/degree/");
            sb.append(this.degree);
        }

        return sb.toString();
    }

    public final static class Builder {
        private String text;
        private String font;
        private int fontsize;
        private String fill;
        private int dissolve;
        private CIGravity gravity;
        private int dx;
        private int dy;
        private boolean batch;
        private int degree;

        /**
         * 文字水印操作构造器
         *
         * @param text 水印内容
         */
        public Builder(@NonNull String text) {
            this.text = text;
        }

        /**
         * 生成文字水印操作
         *
         * @return 文字水印操作
         */
        public WatermarkTextTransform builder() {
            return new WatermarkTextTransform(this);
        }

        /**
         * 设置水印字体
         *
         * @param font 水印字体，默认值 tahoma.ttf 。水印字体列表参考 <a href="https://cloud.tencent.com/document/product/460/19568">支持字体列表</a>
         * @return 文字水印操作构造器
         */
        public Builder setFont(@NonNull String font) {
            this.font = font;
            return this;
        }

        /**
         * 设置水印文字字体大小
         *
         * @param fontsize 水印文字字体大小，单位为磅，缺省值13
         * @return 文字水印操作构造器
         */
        public Builder setFontSize(int fontsize) {
            this.fontsize = fontsize;
            return this;
        }

        /**
         * 设置水印字体颜色
         *
         * @param fill 字体颜色，缺省为灰色，需设置为十六进制 RGB 格式（如 #FF0000），默认值为 #3D3D3D
         * @return 文字水印操作构造器
         */
        public Builder setFill(@NonNull String fill) {
            this.fill = fill;
            return this;
        }

        /**
         * 设置水印文字透明度
         *
         * @param dissolve 文字透明度，取值1 - 100 ，默认90
         * @return 文字水印操作构造器
         */
        public Builder setDissolve(@IntRange(from = 1, to = 100) int dissolve) {
            this.dissolve = dissolve;
            return this;
        }

        /**
         * 设置水印九宫格位置
         *
         * @param gravity 水印九宫格位置，默认值 SouthEast
         * @return 文字水印操作构造器
         */
        public Builder setGravity(CIGravity gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * 设置水平（横轴）边距
         *
         * @param dx 水平（横轴）边距，单位为像素，缺省值为0
         * @return 文字水印操作构造器
         */
        public Builder setDistanceX(int dx) {
            this.dx = dx;
            return this;
        }

        /**
         * 设置垂直（纵轴）边距
         *
         * @param dy 垂直（纵轴）边距，单位为像素，默认值为0
         * @return 文字水印操作构造器
         */
        public Builder setDistanceY(int dy) {
            this.dy = dy;
            return this;
        }

        /**
         * 打开平铺水印功能，可将文字水印平铺至整张图片
         *
         * @return 文字水印操作构造器
         */
        public Builder openBatch() {
            this.batch = true;
            return this;
        }

        /**
         * 旋转水印
         *
         * @param degree 水印的旋转角度设置，取值范围为0 - 360，默认0
         * @return 文字水印操作构造器
         */
        public Builder rotate(@IntRange(from = 0, to = 360) int degree) {
            this.degree = degree;
            return this;
        }
    }
}
