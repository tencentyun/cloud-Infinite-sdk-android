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

package com.tencent.qcloud.infinite;

/**
 * 图片转换器<br>
 * 用于配置图片格式、缩放、剪裁等数据万象功能
 */
public class CITransformation {
    private static final String TAG = "Transformation";

    //格式
    private CIImageFormat format = CIImageFormat.Default;
    private CIImageLoadOptions options = CIImageLoadOptions.LoadTypeAcceptHeader;

    //缩放
    private int width;
    private int height;
    private CIImageZoomType zoomType = CIImageZoomType.Default;

    /**
     * 设置图片格式
     *
     * @param format  图片格式
     * @param options 图片加载配置
     * @return 转换器
     */
    public CITransformation format(CIImageFormat format, CIImageLoadOptions options) {
        this.format = format;
        this.options = options;
        return this;
    }

    /**
     * 图片缩放(暂未开启)
     *
     * @param width    目标宽度（单位像素）
     * @param height   目标高度（单位像素）
     * @param zoomType 缩放类型
     * @return 转换器
     */
    public CITransformation zoom(int width, int height, CIImageZoomType zoomType) {
//        this.width = width;
//        this.height = height;
//        this.zoomType = zoomType;
        return this;
    }

    /**
     * 图片剪裁(暂未开启)
     *
     * @param width  宽度（单位像素）
     * @param height 高度（单位像素）
     * @return 转换器
     */
    public CITransformation crop(int width, int height) {
        return this;
    }

    /**
     * 获取图片格式
     *
     * @return 图片格式
     */
    public CIImageFormat getFormat() {
        return format;
    }

    /**
     * 获取图片加载配置
     *
     * @return 图片加载配置
     */
    public CIImageLoadOptions getOptions() {
        return options;
    }

    /**
     * 获取图片宽度
     *
     * @return 图片宽度（单位像素）
     */
    public int getWidth() {
        return width;
    }

    /**
     * 获取图片高度
     *
     * @return 图片宽度（单位像素）
     */
    public int getHeight() {
        return height;
    }

    /**
     * 获取图片缩放类型
     *
     * @return 缩放类型
     */
    public CIImageZoomType getZoomType() {
        return zoomType;
    }
}
