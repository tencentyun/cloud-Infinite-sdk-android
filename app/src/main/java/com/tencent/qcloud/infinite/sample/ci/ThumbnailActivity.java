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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ThumbnailActivity extends BaseActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoView view_imageinfo;
    private RadioGroup rg_percentage;
    private RadioGroup rg_wh;

    private RadioButton rb_p_width;
    private RadioButton rb_p_height;
    private RadioButton rb_p_wh;
    private RadioButton rb_wh_width;
    private RadioButton rb_wh_height;
    private RadioButton rb_wh_wh;

    private SeekBar sb_width;
    private TextView tv_width;
    private LinearLayout ll_width;
    private SeekBar sb_height;
    private TextView tv_height;
    private LinearLayout ll_height;
    private SeekBar sb_percentage;
    private TextView tv_percentage;
    private LinearLayout ll_percentage;

    private ImageBean imageBean;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);

        BaseImageListView view_imagelist = findViewById(R.id.view_imagelist);
        view_imagelist.setOnClickListener(this);

        view_imageinfo = findViewById(R.id.view_imageinfo);
        sb_width = findViewById(R.id.sb_width);
        tv_width = findViewById(R.id.tv_width);
        ll_width = findViewById(R.id.ll_width);
        sb_height = findViewById(R.id.sb_height);
        tv_height = findViewById(R.id.tv_height);
        ll_height = findViewById(R.id.ll_height);
        sb_percentage = findViewById(R.id.sb_percentage);
        tv_percentage = findViewById(R.id.tv_percentage);
        ll_percentage = findViewById(R.id.ll_percentage);

        rg_percentage = findViewById(R.id.rg_percentage);
        rg_wh = findViewById(R.id.rg_wh);

        rb_p_width = findViewById(R.id.rb_p_width);
        rb_p_height = findViewById(R.id.rb_p_height);
        rb_p_wh = findViewById(R.id.rb_p_wh);
        rb_wh_width = findViewById(R.id.rb_wh_width);
        rb_wh_height = findViewById(R.id.rb_wh_height);
        rb_wh_wh = findViewById(R.id.rb_wh_wh);

        RadioGroup rg_type = findViewById(R.id.rg_type);
        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_percentage) {
                    rg_percentage.setVisibility(View.VISIBLE);
                    rg_wh.setVisibility(View.GONE);

                    findViewById(R.id.ll_percentage_all).setVisibility(View.VISIBLE);
                    findViewById(R.id.ll_wh_all).setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_width_height) {
                    rg_percentage.setVisibility(View.GONE);
                    rg_wh.setVisibility(View.VISIBLE);

                    findViewById(R.id.ll_percentage_all).setVisibility(View.GONE);
                    findViewById(R.id.ll_wh_all).setVisibility(View.VISIBLE);
                }
            }
        });

        rg_wh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_wh_width) {
                    ll_width.setVisibility(View.VISIBLE);
                    ll_height.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_wh_height) {
                    ll_width.setVisibility(View.GONE);
                    ll_height.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rb_wh_wh) {
                    ll_width.setVisibility(View.VISIBLE);
                    ll_height.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                CITransformation ciTransformation = new CITransformation();
                if (rg_percentage.getVisibility() == View.VISIBLE) {
                    if (rb_p_width.isChecked()) {
                        ciTransformation.thumbnailByWidthScale(sb_percentage.getProgress());
                    } else if (rb_p_height.isChecked()) {
                        ciTransformation.thumbnailByHeightScale(sb_percentage.getProgress());
                    } else if (rb_p_wh.isChecked()) {
                        ciTransformation.thumbnailByScale(sb_percentage.getProgress());
                    }
                } else if (rg_wh.getVisibility() == View.VISIBLE) {
                    if (rb_wh_width.isChecked()) {
                        ciTransformation.thumbnailByWidth(sb_width.getProgress());
                    } else if (rb_wh_height.isChecked()) {
                        ciTransformation.thumbnailByHeight(sb_height.getProgress());
                    } else if (rb_wh_wh.isChecked()) {
                        ciTransformation.thumbnailByWH(sb_width.getProgress(), sb_height.getProgress());
                    }
                }

                CloudInfinite cloudInfinite = new CloudInfinite();
                cloudInfinite.requestWithBaseUrl(imageBean.url, ciTransformation, new CloudInfiniteCallback() {
                    @Override
                    public void onImageLoadRequest(@NonNull CIImageLoadRequest request) {
                        view_imageinfo.setData(request.getUrl(), imageBean.format);
                    }
                });
            }
        });

        sb_width.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_width.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_height.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_percentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_percentage.setText(Integer.toString(progress));
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