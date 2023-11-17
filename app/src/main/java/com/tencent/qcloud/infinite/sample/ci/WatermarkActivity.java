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

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.CIImageLoadRequest;
import com.tencent.qcloud.infinite.CITransformation;
import com.tencent.qcloud.infinite.CloudInfinite;
import com.tencent.qcloud.infinite.CloudInfiniteCallback;
import com.tencent.qcloud.infinite.enumm.CIGravity;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.BaseImageInfoView;
import com.tencent.qcloud.infinite.sample.base.BaseImageListView;
import com.tencent.qcloud.infinite.sample.base.ImageBean;
import com.tencent.qcloud.infinite.transform.WatermarkImageTransform;
import com.tencent.qcloud.infinite.transform.WatermarkTextTransform;

/**
 * 水印使用示例，详细使用方式请参考文档
 */
public class WatermarkActivity extends BaseActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoView view_imageinfo;
    private RadioButton rb_image;
    private RadioButton rb_text;

    private ImageBean imageBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watermark);

        BaseImageListView view_imagelist = findViewById(R.id.view_imagelist);
        view_imagelist.setOnClickListener(this);

        view_imageinfo = findViewById(R.id.view_imageinfo);
        rb_image = findViewById(R.id.rb_image);
        rb_text = findViewById(R.id.rb_text);

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloudInfinite cloudInfinite = new CloudInfinite();
                CITransformation ciTransformation = new CITransformation();
                if(rb_image.isChecked()){
                    WatermarkImageTransform watermarkImageTransform = new WatermarkImageTransform.Builder("http://tpg-1253653367.file.myqcloud.com/google.jpg")
                            .setGravity(CIGravity.CENTER).builder();
                    ciTransformation.watermarkImage(watermarkImageTransform);
                } else if(rb_text.isChecked()){
                    WatermarkTextTransform watermarkTextTransform = new WatermarkTextTransform.Builder("水印").setFontSize(100).openBatch().builder();
                    ciTransformation.watermarkText(watermarkTextTransform);
                }

                cloudInfinite.requestWithBaseUrl(imageBean.url, ciTransformation, new CloudInfiniteCallback() {
                    @Override
                    public void onImageLoadRequest(@NonNull CIImageLoadRequest request) {
                        view_imageinfo.setData(request.getUrl(), imageBean.format);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(ImageBean image) {
        this.imageBean = image;
        view_imageinfo.setData(imageBean.url, imageBean.format);
    }
}