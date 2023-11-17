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

package com.tencent.qcloud.infinite.sample.tpg;

import android.content.res.Resources;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.tencent.qcloud.image.decoder.utils.ResourcesUtil;
import com.tencent.qcloud.image.tpg.subsampling.TpgSubsamplingImageDecoder;
import com.tencent.qcloud.image.tpg.subsampling.TpgSubsamplingImageRegionDecoder;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.tpg.TPGDecoder;
import com.tencent.tpg.Tpg;

import java.io.IOException;

public class SuperLargeImageTpgActivity extends BaseActivity {
    private static final String assetName = "super_large_image.tpg";

    private int imageWidth;
    private int imageHeight;
    private float defaultScale;
    private float maxScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_large_image);
        SubsamplingScaleImageView imageView = findViewById(R.id.imageView);

        // 获取缩放配置
        configImageScale();

        // 设置普通图片解码器
//        imageView.setBitmapDecoderClass(SkiaImageDecoder.class);
//        imageView.setRegionDecoderClass(SkiaImageRegionDecoder.class);

        // 设置TPG图片解码器
        imageView.setBitmapDecoderClass(TpgSubsamplingImageDecoder.class);
        imageView.setRegionDecoderClass(TpgSubsamplingImageRegionDecoder.class);

        imageView.setImage(
                ImageSource.asset(assetName),
                new ImageViewState(defaultScale, new PointF(0,0), 0));
        imageView.setMaxScale(maxScale);
        imageView.setMinScale(defaultScale);
        imageView.setDoubleTapZoomScale(maxScale);
    }

    /**
     * 自适应屏幕 缩放
     */
    private void configImageScale() {
        try {
            byte[] bytes = ResourcesUtil.readFileFromAssets(SuperLargeImageTpgActivity.this, assetName);
            TPGDecoder tpgDecoder = Tpg.prepareDecode(bytes);
            imageWidth = tpgDecoder.getWidth();
            imageHeight = tpgDecoder.getHeight();
            tpgDecoder.closeDecode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float scale = 1.0f;
        float maxScale = 2.0f;
        int dw = imageWidth;
        int dh = imageHeight;

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        scale = screenWidth * 1.0f / dw;
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > screenWidth && dh <= screenHeight) {
            maxScale = screenHeight * 1.0f / dh;
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= screenWidth && dh > screenHeight) {
            maxScale = scale * 2;
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < screenWidth && dh < screenHeight) {
            maxScale = scale * 2;
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > screenWidth && dh > screenHeight) {
            maxScale = 2f;
        }
        if (dw / dh >= 2) {
            maxScale = screenHeight * 1.0f / dh;
        }
        defaultScale = scale;
        if (maxScale < defaultScale) {
            maxScale = defaultScale * 2;
        }
        this.maxScale = maxScale;
    }
}
