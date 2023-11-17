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

package com.tencent.qcloud.infinite.sample.ci;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.CIImageLoadRequest;
import com.tencent.qcloud.infinite.CITransformation;
import com.tencent.qcloud.infinite.CloudInfinite;
import com.tencent.qcloud.infinite.CloudInfiniteCallback;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.BaseImageInfoView;
import com.tencent.qcloud.infinite.sample.base.BaseImageListView;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

public class BlurActivity extends BaseActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoView view_imageinfo;
    private SeekBar sb_radius;
    private TextView tv_radius;
    private SeekBar sb_sigma;
    private TextView tv_sigma;

    private ImageBean imageBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        BaseImageListView view_imagelist = findViewById(R.id.view_imagelist);
        view_imagelist.setOnClickListener(this);

        view_imageinfo = findViewById(R.id.view_imageinfo);
        sb_radius = findViewById(R.id.sb_radius);
        tv_radius = findViewById(R.id.tv_radius);
        sb_sigma = findViewById(R.id.sb_sigma);
        tv_sigma = findViewById(R.id.tv_sigma);

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloudInfinite cloudInfinite = new CloudInfinite();
                cloudInfinite.requestWithBaseUrl(imageBean.url, new CITransformation().blur(sb_radius.getProgress(), sb_sigma.getProgress()), new CloudInfiniteCallback() {
                    @Override
                    public void onImageLoadRequest(@NonNull CIImageLoadRequest request) {
                        view_imageinfo.setData(request.getUrl(), imageBean.format);
                    }
                });
            }
        });

        sb_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_radius.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_sigma.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_sigma.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(ImageBean image) {
        this.imageBean = image;
        view_imageinfo.setData(imageBean.url, imageBean.format);
    }
}