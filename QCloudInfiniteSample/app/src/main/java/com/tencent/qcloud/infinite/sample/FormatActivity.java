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

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.qcloud.infinite.CIImageLoadRequest;
import com.tencent.qcloud.infinite.CITransformation;
import com.tencent.qcloud.infinite.CloudInfinite;
import com.tencent.qcloud.infinite.CloudInfiniteCallback;
import com.tencent.qcloud.infinite.enumm.CIImageFormat;
import com.tencent.qcloud.infinite.sample.base.BaseImageInfoView;
import com.tencent.qcloud.infinite.sample.base.BaseImageListView;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

public class FormatActivity extends AppCompatActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoView view_imageinfo;
    private RadioGroup rg_format;

    private ImageBean imageBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_format);

        BaseImageListView view_imagelist = findViewById(R.id.view_imagelist);
        view_imagelist.setOnClickListener(this);

        view_imageinfo = findViewById(R.id.view_imageinfo);
        rg_format = findViewById(R.id.rg_format);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 18) {
            findViewById(R.id.rb_webp).setVisibility(View.VISIBLE);
        }
        if(currentapiVersion >= 29) {
            findViewById(R.id.rb_heif).setVisibility(View.VISIBLE);
        }

        final CITransformation ciTransformation = new CITransformation();
        rg_format.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_tpg:
                        ciTransformation.format(CIImageFormat.TPG);
                        imageBean.format = CIImageFormat.TPG.getFormat().toUpperCase();
                        break;
                    case R.id.rb_png:
                        ciTransformation.format(CIImageFormat.PNG);
                        imageBean.format = CIImageFormat.PNG.getFormat().toUpperCase();
                        break;
                    case R.id.rb_jpg:
                        ciTransformation.format(CIImageFormat.JPEG);
                        imageBean.format = CIImageFormat.JPEG.getFormat().toUpperCase();
                        break;
                    case R.id.rb_bmp:
                        //示例图片分辨率太大，bmp格式会因为图片太大显示不出来，因此进行缩小
                        ciTransformation.thumbnailByMaxWH(840, 630).format(CIImageFormat.BMP);
                        imageBean.format = CIImageFormat.BMP.getFormat().toUpperCase();
                        break;
                    case R.id.rb_gif:
                        ciTransformation.format(CIImageFormat.GIF);
                        imageBean.format = CIImageFormat.GIF.getFormat().toUpperCase();
                        break;
                    case R.id.rb_webp:
                        ciTransformation.format(CIImageFormat.WEBP);
                        imageBean.format = CIImageFormat.WEBP.getFormat().toUpperCase();
                        break;
                    case R.id.rb_yjpeg:
                        ciTransformation.format(CIImageFormat.YJPEG);
                        imageBean.format = CIImageFormat.YJPEG.getFormat().toUpperCase();
                        break;
                    case R.id.rb_heif:
                        ciTransformation.format(CIImageFormat.HEIF);
                        imageBean.format = CIImageFormat.HEIF.getFormat().toUpperCase();
                        break;
                }
            }
        });

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloudInfinite cloudInfinite = new CloudInfinite();
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