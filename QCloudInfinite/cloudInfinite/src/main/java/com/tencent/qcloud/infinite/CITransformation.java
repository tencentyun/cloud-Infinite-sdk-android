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

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.transform.BlurTransform;
import com.tencent.qcloud.infinite.transform.CITransform;
import com.tencent.qcloud.infinite.transform.CropTransform;
import com.tencent.qcloud.infinite.transform.CustomTransform;
import com.tencent.qcloud.infinite.transform.FormatTransform;
import com.tencent.qcloud.infinite.transform.QualityTransform;
import com.tencent.qcloud.infinite.transform.RotateTransform;
import com.tencent.qcloud.infinite.transform.SharpenTransform;
import com.tencent.qcloud.infinite.transform.StripTransform;
import com.tencent.qcloud.infinite.transform.ThumbnailTransform;
import com.tencent.qcloud.infinite.transform.WatermarkImageTransform;
import com.tencent.qcloud.infinite.transform.WatermarkTextTransform;
import com.tencent.qcloud.infinite.enumm.CIGravity;
import com.tencent.qcloud.infinite.enumm.CIImageFormat;
import com.tencent.qcloud.infinite.enumm.CIImageLoadOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片转换器<br>
 * 用于进行图片格式、缩放、剪裁等数据万象功能<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/36540">https://cloud.tencent.com/document/product/460/36540.</a>
 */
public class CITransformation {
    private static final String TAG = "Transformation";

    protected List<CITransform> transforms = new ArrayList<>();

    /**
     * 指定图片的宽高为原图的 Scale%
     *
     * @param scale 百分比例
     * @return this
     */
    public CITransformation thumbnailByScale(int scale) {
        transforms.add(new ThumbnailTransform().thumbnailByScale(scale));
        return this;
    }

    /**
     * 指定图片的宽为原图的 Scale%，高度不变
     *
     * @param scale 百分比例
     * @return this
     */
    public CITransformation thumbnailByWidthScale(int scale) {
        transforms.add(new ThumbnailTransform().thumbnailByWidthScale(scale));
        return this;
    }

    /**
     * 指定图片的高为原图的 Scale%，宽度不变
     *
     * @param scale 百分比例
     * @return this
     */
    public CITransformation thumbnailByHeightScale(int scale) {
        transforms.add(new ThumbnailTransform().thumbnailByHeightScale(scale));
        return this;
    }

    /**
     * 指定目标图片宽度为 Width，高度等比压缩
     *
     * @param width 目标宽度（单位像素）
     * @return this
     */
    public CITransformation thumbnailByWidth(int width) {
        transforms.add(new ThumbnailTransform().thumbnailByWidth(width));
        return this;
    }

    /**
     * 指定目标图片高度为 Height，宽度等比压缩
     *
     * @param height 目标高度（单位像素）
     * @return this
     */
    public CITransformation thumbnailByHeight(int height) {
        transforms.add(new ThumbnailTransform().thumbnailByHeight(height));
        return this;
    }

    /**
     * 限定缩略图的宽度和高度的最大值分别为 Width 和 Height，进行等比缩放
     *
     * @param width  目标宽度（单位像素）
     * @param height 目标高度（单位像素）
     * @return this
     */
    public CITransformation thumbnailByMaxWH(int width, int height) {
        transforms.add(new ThumbnailTransform().thumbnailByMaxWH(width, height));
        return this;
    }

    /**
     * 限定缩略图的宽度和高度的最小值分别为 Width 和 Height，进行等比缩放
     *
     * @param width  目标宽度（单位像素）
     * @param height 目标高度（单位像素）
     * @return this
     */
    public CITransformation thumbnailByMinWH(int width, int height) {
        transforms.add(new ThumbnailTransform().thumbnailByMaxWH(width, height));
        return this;
    }

    /**
     * 忽略原图宽高比例，指定图片宽度为 Width，高度为 Height ，强行缩放图片，可能导致目标图片变形
     *
     * @param width  目标宽度（单位像素）
     * @param height 目标高度（单位像素）
     * @return this
     */
    public CITransformation thumbnailByWH(int width, int height) {
        transforms.add(new ThumbnailTransform().thumbnailByWH(width, height));
        return this;
    }

    /**
     * 等比缩放图片，缩放后的图像，总像素数量不超过 Area
     *
     * @param area 像素数量
     * @return this
     */
    public CITransformation thumbnailByPixel(int area) {
        transforms.add(new ThumbnailTransform().thumbnailByPixel(area));
        return this;
    }


    /**
     * 普通裁剪
     *
     * @param width  指定目标图片的宽
     * @param height 指定目标图片的高
     * @param dx     相对于图片左上顶点水平向右偏移 dx
     * @param dy     相对于图片左上顶点水平向下偏移 dy
     * @return this
     */
    public CITransformation cut(int width, int height, int dx, int dy) {
        transforms.add(new CropTransform().cut(width, height, dx, dy));
        return this;
    }

    /**
     * 普通裁剪
     *
     * @param width     指定目标图片的宽
     * @param height    指定目标图片的高
     * @param dx        相对于图片左上顶点水平向右偏移 dx
     * @param dy        相对于图片左上顶点水平向下偏移 dy
     * @param CIGravity 指定操作的起点位置
     * @return this
     */
    public CITransformation cut(int width, int height, int dx, int dy, CIGravity CIGravity) {
        transforms.add(new CropTransform().cut(width, height, dx, dy, CIGravity));
        return this;
    }

    /**
     * 按照指定目标宽度进行缩放裁剪<br>
     * 指定目标图片宽度为 Width ，高度不变。
     *
     * @param width 指定目标图片宽度 取值范围应大于0，小于原图宽度
     * @return this
     */
    public CITransformation cropByWidth(int width) {
        transforms.add(new CropTransform().cropByWidth(width));
        return this;
    }

    /**
     * 按照指定目标宽度进行缩放裁剪<br>
     * 指定目标图片宽度为 Width ，高度不变。
     *
     * @param width     指定目标图片宽度 取值范围应大于0，小于原图宽度
     * @param CIGravity 指定操作的起点位置
     * @return this
     */
    public CITransformation cropByWidth(int width, CIGravity CIGravity) {
        transforms.add(new CropTransform().cropByWidth(width, CIGravity));
        return this;
    }

    /**
     * 按照指定目标高度进行缩放裁剪<br>
     * 指定目标图片高度为 Height ，宽度不变。
     *
     * @param height 指定目标图片高度 取值范围应大于0，小于原图高度
     * @return this
     */
    public CITransformation cropByHeight(int height) {
        transforms.add(new CropTransform().cropByHeight(height));
        return this;
    }

    /**
     * 按照指定目标高度进行缩放裁剪<br>
     * 指定目标图片高度为 Height ，宽度不变。
     *
     * @param height    指定目标图片高度 取值范围应大于0，小于原图高度
     * @param CIGravity 指定操作的起点位置
     * @return this
     */
    public CITransformation cropByHeight(int height, CIGravity CIGravity) {
        transforms.add(new CropTransform().cropByHeight(height, CIGravity));
        return this;
    }

    /**
     * 按照指定目标宽度和高度进行缩放裁剪<br>
     *
     * @param width  指定目标图片宽度 取值范围都应大于0，小于原图宽度
     * @param height 指定目标图片高度 取值范围都应大于0，小于原图高度
     * @return this
     */
    public CITransformation cropByWH(int width, int height) {
        transforms.add(new CropTransform().cropByWH(width, height));
        return this;
    }

    /**
     * 按照指定目标宽度和高度进行缩放裁剪<br>
     *
     * @param width     指定目标图片宽度 取值范围都应大于0，小于原图宽度
     * @param height    指定目标图片高度 取值范围都应大于0，小于原图高度
     * @param CIGravity 指定操作的起点位置
     * @return this
     */
    public CITransformation cropByWH(int width, int height, CIGravity CIGravity) {
        transforms.add(new CropTransform().cropByWH(width, height, CIGravity));
        return this;
    }

    /**
     * 内切圆裁剪，内切圆的圆心为图片的中心，图片格式为 gif 时，不支持该参数
     *
     * @param radius 内切圆的半径，取值范围为大于0且小于原图最小边一半的整数
     * @return this
     */
    public CITransformation iradius(int radius) {
        transforms.add(new CropTransform().iradius(radius));
        return this;
    }

    /**
     * 圆角裁剪 圆角与原图边缘相切，图片格式为 gif 时，不支持该参数
     *
     * @param radius 图片圆角边缘的半径，取值范围为大于0且小于原图最小边一半的整数
     * @return this
     */
    public CITransformation rradius(int radius) {
        transforms.add(new CropTransform().rradius(radius));
        return this;
    }

    /**
     * 基于图片中的人脸位置进行缩放裁剪
     *
     * @param width  目标图片的宽度
     * @param height 目标图片的高度
     * @return this
     */
    public CITransformation scrop(int width, int height) {
        transforms.add(new CropTransform().scrop(width, height));
        return this;
    }


    /**
     * 普通旋转：图片顺时针旋转角度，取值范围0 - 360 ，默认不旋转
     *
     * @param degree 旋转角度，取值范围0 - 360
     * @return this
     */
    public CITransformation rotate(@IntRange(from = 0, to = 360) int degree) {
        transforms.add(new RotateTransform(degree));
        return this;
    }

    /**
     * 自适应旋转：根据原图 EXIF 信息将图片自适应旋转回正
     *
     * @return this
     */
    public CITransformation rotate() {
        transforms.add(new RotateTransform());
        return this;
    }


    /**
     * 图片格式转换<br>
     * 目标缩略图的图片格式可为：jpg，bmp，gif，png，webp，yjpeg 等，其中 yjpeg 为数据万象针对 jpeg 格式进行的优化，本质为 jpg 格式
     *
     * @param format 目标图片格式
     * @return this
     */
    public CITransformation format(@NonNull CIImageFormat format) {
        transforms.add(new FormatTransform(format, CIImageLoadOptions.LoadTypeUrlFooter));
        return this;
    }

    /**
     * 图片格式转换<br>
     * 目标缩略图的图片格式可为：jpg，bmp，gif，png，webp，yjpeg 等，其中 yjpeg 为数据万象针对 jpeg 格式进行的优化，本质为 jpg 格式
     *
     * @param format  目标图片格式
     * @param options 图片加载配置
     * @return this
     */
    public CITransformation format(@NonNull CIImageFormat format, @NonNull CIImageLoadOptions options) {
        transforms.add(new FormatTransform(format, options));
        return this;
    }

    /**
     * gif 格式优化：只针对原图为 gif 格式，对 gif 图片格式进行的优化，降帧降颜色。分为以下两种情况：
     * FrameNumber=1，则按照默认帧数30处理，如果图片帧数大于该帧数则截取。
     * FrameNumber 取值( 1,100 ]，则将图片压缩到指定帧数 （FrameNumber）。
     *
     * @param cgifFrameNumber 图片压缩帧数
     * @return this
     */
    public CITransformation gifOptimization(@IntRange(from = 1, to = 100) int cgifFrameNumber) {
        transforms.add(new FormatTransform(cgifFrameNumber));
        return this;
    }

    /**
     * 输出为渐进式 jpg 格式。Mode 可为0或1。0：表示不开启渐进式；1：表示开启渐进式。该参数仅在输出图片格式为 jpg 格式时有效。如果输出非 jpg 图片格式，会忽略该参数，默认值0。
     *
     * @param interlaceMode 是否开启 0：表示不开启渐进式；1：表示开启渐进式
     * @return this
     */
    public CITransformation jpegInterlaceMode(boolean interlaceMode) {
        transforms.add(new FormatTransform(interlaceMode));
        return this;
    }


    /**
     * 设置图片的绝对质量，取值范围0 - 100 ，默认值为原图质量；取原图质量和指定质量的最小值
     * 该操作仅适用于 jpg 和 webp 格式的图片
     *
     * @param quality 绝对质量数值
     * @return this
     */
    public CITransformation quality(@IntRange(from = 0, to = 100) int quality) {
        transforms.add(new QualityTransform().quality(quality));
        return this;
    }

    /**
     * 设置图片的相对质量，取值范围0 - 100 ，数值以原图质量为标准。例如原图质量为80，将 rquality 设置为80后，得到处理结果图的图片质量为64（80x80%）
     * 该操作仅适用于 jpg 和 webp 格式的图片
     *
     * @param quality 相对质量数值
     * @return this
     */
    public CITransformation relativelyQuality(@IntRange(from = 0, to = 100) int quality) {
        transforms.add(new QualityTransform().relativelyQuality(quality));
        return this;
    }

    /**
     * 图片的最低质量，取值范围0 - 100 ，设置结果图的质量参数最小值。
     * 例如原图质量为85，将 lquality 设置为80后，处理结果图的图片质量为85。
     * 例如原图质量为60，将 lquality 设置为80后，处理结果图的图片质量会被提升至80
     * 该操作仅适用于 jpg 和 webp 格式的图片
     *
     * @param quality 最低质量数值
     * @return this
     */
    public CITransformation lowestQuality(@IntRange(from = 0, to = 100) int quality) {
        transforms.add(new QualityTransform().lowestQuality(quality));
        return this;
    }


    /**
     * 高斯模糊
     * 图片格式为 gif 时，不支持该操作
     *
     * @param radius 模糊半径，取值范围为1 - 50
     * @param sigma  正态分布的标准差，必须大于0
     * @return this
     */
    public CITransformation blur(@IntRange(from = 1, to = 50) int radius, @IntRange(from = 1) int sigma) {
        transforms.add(new BlurTransform(radius, sigma));
        return this;
    }

    /**
     * 锐化
     *
     * @param value 锐化参数值，取值范围为10 - 300间的整数。参数值越大，锐化效果越明显。（推荐使用70）
     * @return this
     */
    public CITransformation sharpen(@IntRange(from = 10, to = 300) int value) {
        transforms.add(new SharpenTransform(value));
        return this;
    }

    /**
     * 设置图片水印
     * 目前水印图片必须指定为已存储于数据万象中的图片。处理图片原图大小不超过20MB、宽高不超过30000像素且总像素不超过1亿像素，处理结果图宽高设置不超过9999像素；针对动图，原图宽 x 高 x 帧数不超过1亿像素
     * API 文档：<a href="https://cloud.tencent.com/document/product/460/6930">https://cloud.tencent.com/document/product/460/6930.</a>
     *
     * @param transform 图片水印操作 通过{@link WatermarkImageTransform.Builder}构建
     * @return this
     */
    public CITransformation watermarkImage(WatermarkImageTransform transform) {
        transforms.add(transform);
        return this;
    }

    /**
     * 设置文字水印
     * 处理图片原图大小不超过20MB、宽高不超过30000像素且总像素不超过1亿像素，处理结果图宽高设置不超过9999像素；针对动图，原图宽 x 高 x 帧数不超过1亿像素<br>
     * API 文档：<a href="https://cloud.tencent.com/document/product/460/6951">https://cloud.tencent.com/document/product/460/6951.</a>
     *
     * @param transform 图片水印操作 通过{@link WatermarkTextTransform.Builder}构建
     * @return this
     */
    public CITransformation watermarkText(WatermarkTextTransform transform) {
        transforms.add(transform);
        return this;
    }


    /**
     * 去除图片元信息操作，包括 exif 信息
     *
     * @return this
     */
    public CITransformation strip() {
        transforms.add(new StripTransform());
        return this;
    }


    /**
     * 设置自定义图片转换操作
     *
     * @param actionString 万象原始的操作字符串，比如：imageMogr2/strip
     * @return this
     */
    public CITransformation customAction(String actionString) {
        transforms.add(new CustomTransform(actionString));
        return this;
    }

    /**
     * 获取用户进行的所有图片操作
     *
     * @return 图片操作列表
     */
    public List<CITransform> getTransforms() {
        return transforms;
    }
}
