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

import com.tencent.qcloud.infinite.enumm.CIImageFormat;
import com.tencent.qcloud.infinite.enumm.CIImageLoadOptions;

/**
 * 格式转换操作<br>
 * 提供格式转换、gif 格式优化、渐进显示功能<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/36543">https://cloud.tencent.com/document/product/460/36543.</a>
 */
public class FormatTransform extends CITransform {
    private static final String TAG = "FormatTransform";

    private int type;
    private CIImageFormat format;
    private CIImageLoadOptions formatOptions;
    private int cgifFrameNumber = 1;
    private boolean interlaceMode = false;

    /**
     * 格式转换：目标缩略图的图片格式可为：jpg，bmp，gif，png，webp，yjpeg 等，其中 yjpeg 为数据万象针对 jpeg 格式进行的优化，本质为 jpg 格式；缺省为原图格式。
     *
     * @param format  图片格式
     * @param options 图片格式转换加载配置
     */
    public FormatTransform(@NonNull CIImageFormat format, @NonNull CIImageLoadOptions options) {
        this.format = format;
        this.formatOptions = options;
        this.type = 1;
    }

    /**
     * gif 格式优化：只针对原图为 gif 格式，对 gif 图片格式进行的优化，降帧降颜色。分为以下两种情况：
     * FrameNumber=1，则按照默认帧数30处理，如果图片帧数大于该帧数则截取。
     * FrameNumber 取值( 1,100 ]，则将图片压缩到指定帧数 （FrameNumber）。
     *
     * @param cgifFrameNumber 图片压缩帧数
     */
    public FormatTransform(@IntRange(from = 1, to = 100) int cgifFrameNumber) {
        this.cgifFrameNumber = cgifFrameNumber;
        this.type = 2;
    }

    /**
     * 输出为渐进式 jpg 格式。Mode 可为0或1。0：表示不开启渐进式；1：表示开启渐进式。该参数仅在输出图片格式为 jpg 格式时有效。如果输出非 jpg 图片格式，会忽略该参数，默认值0。
     *
     * @param interlaceMode 是否开启 0：表示不开启渐进式；1：表示开启渐进式
     */
    public FormatTransform(boolean interlaceMode) {
        this.interlaceMode = interlaceMode;
        this.type = 3;
    }

    /**
     * 将图片格式转换加载配置设置为UrlFooter类型<br>
     * 如果图片转换操作中不光只有格式转换，则格式转换操作的加载配置被转换为UrlFooter类型
     */
    public void formatOptionsToUrlFooter() {
        this.formatOptions = CIImageLoadOptions.LoadTypeUrlFooter;
    }

    /**
     * 获取图片格式转换时的目标格式
     * @return 目标转换格式
     */
    public CIImageFormat getFormat() {
        return format;
    }

    /**
     * 获取图片格式转换加载配置
     * @return 图片格式转换加载配置
     */
    public CIImageLoadOptions getFormatOptions() {
        return formatOptions;
    }

    @NonNull
    @Override
    public String getTransformString() {
        switch (type) {
            case 1:
                if (formatOptions == CIImageLoadOptions.LoadTypeUrlFooter) {
                    return "imageMogr2/format/" + format.getFormat();
                } else {
                    return "";
                }
            case 2:
                return "imageMogr2/cgif/" + cgifFrameNumber;
            case 3:
                return "imageMogr2/interlace/" + (interlaceMode ? 1 : 0);
            default:
                return "";
        }
    }
}
