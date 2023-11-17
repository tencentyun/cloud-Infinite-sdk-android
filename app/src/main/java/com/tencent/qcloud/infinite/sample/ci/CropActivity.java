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

public class CropActivity extends BaseActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoView view_imageinfo;
    private RadioButton rb_cut;
    private RadioButton rb_crop;
    private RadioButton rb_iradius;
    private RadioButton rb_rradius;
    private RadioButton rb_scrop;

    private SeekBar sb_width;
    private TextView tv_width;
    private LinearLayout ll_width;
    private SeekBar sb_height;
    private TextView tv_height;
    private LinearLayout ll_height;
    private SeekBar sb_dx;
    private TextView tv_dx;
    private LinearLayout ll_dx;
    private SeekBar sb_dy;
    private TextView tv_dy;
    private LinearLayout ll_dy;
    private SeekBar sb_radius;
    private TextView tv_radius;
    private LinearLayout ll_radius;

    private ImageBean imageBean;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        BaseImageListView view_imagelist = findViewById(R.id.view_imagelist);
        view_imagelist.setOnClickListener(this);

        view_imageinfo = findViewById(R.id.view_imageinfo);
        RadioGroup rg_format = findViewById(R.id.rg_crop);
        sb_width = findViewById(R.id.sb_width);
        tv_width = findViewById(R.id.tv_width);
        ll_width = findViewById(R.id.ll_width);
        sb_height = findViewById(R.id.sb_height);
        tv_height = findViewById(R.id.tv_height);
        ll_height = findViewById(R.id.ll_height);
        sb_dx = findViewById(R.id.sb_dx);
        tv_dx = findViewById(R.id.tv_dx);
        ll_dx = findViewById(R.id.ll_dx);
        sb_dy = findViewById(R.id.sb_dy);
        tv_dy = findViewById(R.id.tv_dy);
        ll_dy = findViewById(R.id.ll_dy);
        sb_radius = findViewById(R.id.sb_radius);
        tv_radius = findViewById(R.id.tv_radius);
        ll_radius = findViewById(R.id.ll_radius);

        rb_cut = findViewById(R.id.rb_cut);
        rb_crop = findViewById(R.id.rb_crop);
        rb_iradius = findViewById(R.id.rb_iradius);
        rb_rradius = findViewById(R.id.rb_rradius);
        rb_scrop = findViewById(R.id.rb_scrop);

        rg_format.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_cut:
                        ll_width.setVisibility(View.VISIBLE);
                        ll_height.setVisibility(View.VISIBLE);
                        ll_dx.setVisibility(View.VISIBLE);
                        ll_dy.setVisibility(View.VISIBLE);
                        ll_radius.setVisibility(View.GONE);
                        break;
                    case R.id.rb_crop:
                    case R.id.rb_scrop:
                        ll_width.setVisibility(View.VISIBLE);
                        ll_height.setVisibility(View.VISIBLE);
                        ll_dx.setVisibility(View.GONE);
                        ll_dy.setVisibility(View.GONE);
                        ll_radius.setVisibility(View.GONE);
                        break;
                    case R.id.rb_iradius:
                    case R.id.rb_rradius:
                        ll_width.setVisibility(View.GONE);
                        ll_height.setVisibility(View.GONE);
                        ll_dx.setVisibility(View.GONE);
                        ll_dy.setVisibility(View.GONE);
                        ll_radius.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                CITransformation ciTransformation = new CITransformation();
                if(rb_cut.isChecked()){
                    ciTransformation.cut(sb_width.getProgress(), sb_height.getProgress(), sb_dx.getProgress(), sb_dy.getProgress());
                } else if(rb_crop.isChecked()){
                    ciTransformation.cropByWH(sb_width.getProgress(), sb_height.getProgress());
                } else if(rb_iradius.isChecked()){
                    ciTransformation.iradius(sb_radius.getProgress());
                } else if(rb_rradius.isChecked()){
                    ciTransformation.rradius(sb_radius.getProgress());
                } else if(rb_scrop.isChecked()){
                    ciTransformation.scrop(sb_width.getProgress(), sb_height.getProgress());
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
        sb_dx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_dx.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_dy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_dy.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
    }

    @Override
    public void onClick(ImageBean image) {
        this.imageBean = image;
        view_imageinfo.setData(imageBean.url, imageBean.format);
    }
}