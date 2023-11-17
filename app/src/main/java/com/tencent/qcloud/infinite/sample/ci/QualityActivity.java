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
import android.widget.CompoundButton;
import android.widget.RadioButton;
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

/**
 * 水印使用示例，详细使用方式请参考文档
 */
public class QualityActivity extends BaseActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoView view_imageinfo;
    private RadioButton rb_quality;
    private RadioButton rb_relatively;
    private RadioButton rb_lowest;
    private SeekBar sb_value;
    private TextView tv_value;
    private TextView tv_tip;

    private ImageBean imageBean;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality);

        BaseImageListView view_imagelist = findViewById(R.id.view_imagelist);
        view_imagelist.setOnClickListener(this);

        view_imageinfo = findViewById(R.id.view_imageinfo);
        rb_quality = findViewById(R.id.rb_quality);
        rb_relatively = findViewById(R.id.rb_relatively);
        rb_lowest = findViewById(R.id.rb_lowest);
        sb_value = findViewById(R.id.sb_value);
        tv_value = findViewById(R.id.tv_value);
        tv_tip = findViewById(R.id.tv_tip);

        tv_tip.setText("图片的绝对质量，取值范围0 - 100 ，默认值为原图质量；取原图质量和指定质量的最小值；");

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                CITransformation ciTransformation = new CITransformation();
                if(rb_quality.isChecked()){
                    ciTransformation.quality(sb_value.getProgress());
                } else if(rb_relatively.isChecked()){
                    ciTransformation.relativelyQuality(sb_value.getProgress());
                } else if(rb_lowest.isChecked()){
                    ciTransformation.lowestQuality(sb_value.getProgress());
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

        rb_quality.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_tip.setText("图片的绝对质量，取值范围0 - 100 ，默认值为原图质量；取原图质量和指定质量的最小值；");
                }
            }
        });
        rb_relatively.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_tip.setText("图片的相对质量，取值范围0 - 100 ，数值以原图质量为标准。例如原图质量为80，将 quality 设置为80后，得到处理结果图的图片质量为64（80x80%）。");                }
            }
        });
        rb_lowest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_tip.setText("图片的最低质量，取值范围0 - 100 ，设置结果图的质量参数最小值。\n" +
                            "例如原图质量为85，将 quality 设置为80后，处理结果图的图片质量为85。\n" +
                            "例如原图质量为60，将 quality 设置为80后，处理结果图的图片质量会被提升至80。");                }
            }
        });

        sb_value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_value.setText(Integer.toString(progress));
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