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

package com.tencent.qcloud.infinite.enumm;

/**
 * 图片格式
 */
public enum CIImageFormat {
    /**
     * TPG 是腾讯推出的自研图片格式，大幅减小图片大小
     */
    TPG("tpg"),
    JPEG("jpeg"),
    /**
     * yjpeg 为数据万象针对 jpeg 格式进行的优化，本质为 jpg 格式
     */
    YJPEG("yjpeg"),
    PNG("png"),
    BMP("bmp"),
    WEBP("webp"),
    HEIF("heif"),
    GIF("gif");

    private String format;

    CIImageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}