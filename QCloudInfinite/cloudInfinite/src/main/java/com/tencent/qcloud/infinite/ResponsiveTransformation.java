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

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.enumm.CIImageFormat;
import com.tencent.qcloud.infinite.enumm.CIImageLoadOptions;
import com.tencent.qcloud.infinite.transform.FormatTransform;
import com.tencent.qcloud.infinite.transform.ThumbnailTransform;

/**
 * 根据ImageView和系统版本自适应图片
 */
public class ResponsiveTransformation extends CITransformation {
    private static final String TAG = "ResponsiveTransformation";

    private CIImageFormat imageFormat = null;

    public ResponsiveTransformation(@NonNull ImageView imageView) {
        switch (imageView.getScaleType()) {
            case FIT_CENTER:
            case FIT_START:
            case FIT_END:
                transforms.add(new ThumbnailTransform().thumbnailByMaxWH(imageView.getWidth(), imageView.getHeight()));
                break;
            case FIT_XY:
                transforms.add(new ThumbnailTransform().thumbnailByWH(imageView.getWidth(), imageView.getHeight()));
                break;
            case CENTER_CROP:
                transforms.add(new ThumbnailTransform().thumbnailByMinWH(imageView.getWidth(), imageView.getHeight()));
                break;
        }

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 18 && currentapiVersion < 29) {
            //Android 4.3到10转webp
            transforms.add(new FormatTransform(CIImageFormat.WEBP, CIImageLoadOptions.LoadTypeUrlFooter));
            this.imageFormat = CIImageFormat.WEBP;
        } else if(currentapiVersion >= 29) {
            //Android10以及以上转heif
            transforms.add(new FormatTransform(CIImageFormat.HEIF, CIImageLoadOptions.LoadTypeUrlFooter));
            this.imageFormat = CIImageFormat.HEIF;
        }
    }

    /**
     * 获取自适应后图片格式
     * @return 图片格式（null值代表为原格式，没有进行格式转换）
     */
    public CIImageFormat getImageFormat() {
        return imageFormat;
    }
}
