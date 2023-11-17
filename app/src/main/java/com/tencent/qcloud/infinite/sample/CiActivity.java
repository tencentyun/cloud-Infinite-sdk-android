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

package com.tencent.qcloud.infinite.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.ci.BlurActivity;
import com.tencent.qcloud.infinite.sample.ci.CropActivity;
import com.tencent.qcloud.infinite.sample.ci.FormatActivity;
import com.tencent.qcloud.infinite.sample.ci.GifOptActivity;
import com.tencent.qcloud.infinite.sample.ci.ImageAveActivity;
import com.tencent.qcloud.infinite.sample.ci.QualityActivity;
import com.tencent.qcloud.infinite.sample.ci.ResponsiveActivity;
import com.tencent.qcloud.infinite.sample.ci.RotateActivity;
import com.tencent.qcloud.infinite.sample.ci.SharpenActivity;
import com.tencent.qcloud.infinite.sample.ci.StripActivity;
import com.tencent.qcloud.infinite.sample.ci.ThumbnailActivity;
import com.tencent.qcloud.infinite.sample.ci.WatermarkActivity;

public class CiActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ci);

        findViewById(R.id.btn_responsive).setOnClickListener(this);
        findViewById(R.id.btn_thumbnail).setOnClickListener(this);
        findViewById(R.id.btn_crop).setOnClickListener(this);
        findViewById(R.id.btn_rotate).setOnClickListener(this);
        findViewById(R.id.btn_format).setOnClickListener(this);
        findViewById(R.id.btn_gif_opt).setOnClickListener(this);
        findViewById(R.id.btn_quality).setOnClickListener(this);
        findViewById(R.id.btn_blur).setOnClickListener(this);
        findViewById(R.id.btn_sharpen).setOnClickListener(this);
        findViewById(R.id.btn_watermark).setOnClickListener(this);
        findViewById(R.id.btn_ave).setOnClickListener(this);
        findViewById(R.id.btn_strip).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_responsive:
                startActivity(new Intent(this, ResponsiveActivity.class));
                break;
            case R.id.btn_thumbnail:
                startActivity(new Intent(this, ThumbnailActivity.class));
                break;
            case R.id.btn_crop:
                startActivity(new Intent(this, CropActivity.class));
                break;
            case R.id.btn_rotate:
                startActivity(new Intent(this, RotateActivity.class));
                break;
            case R.id.btn_format:
                startActivity(new Intent(this, FormatActivity.class));
                break;
            case R.id.btn_gif_opt:
                startActivity(new Intent(this, GifOptActivity.class));
                break;
            case R.id.btn_quality:
                startActivity(new Intent(this, QualityActivity.class));
                break;
            case R.id.btn_blur:
                startActivity(new Intent(this, BlurActivity.class));
                break;
            case R.id.btn_sharpen:
                startActivity(new Intent(this, SharpenActivity.class));
                break;
            case R.id.btn_watermark:
                startActivity(new Intent(this, WatermarkActivity.class));
                break;
            case R.id.btn_ave:
                startActivity(new Intent(this, ImageAveActivity.class));
                break;
            case R.id.btn_strip:
                startActivity(new Intent(this, StripActivity.class));
                break;
        }
    }
}