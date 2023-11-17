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
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.CIImageLoadRequest;
import com.tencent.qcloud.infinite.CITransformation;
import com.tencent.qcloud.infinite.CloudInfinite;
import com.tencent.qcloud.infinite.CloudInfiniteCallback;
import com.tencent.qcloud.infinite.enumm.CIImageFormat;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.BaseImageInfoView;
import com.tencent.qcloud.infinite.sample.base.BaseImageListView;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

public class FormatActivity extends BaseActivity implements BaseImageListView.OnClickListener {
    private BaseImageInfoView view_imageinfo;
    private RadioGroup rg_format;

    private ImageBean imageBean;
    private CIImageFormat selectFormat;

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
        if (currentapiVersion >= 29) {
            findViewById(R.id.rb_heif).setVisibility(View.VISIBLE);
        }

        rg_format.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_tpg:
                        selectFormat = CIImageFormat.TPG;
                        break;
                    case R.id.rb_png:
                        selectFormat = CIImageFormat.PNG;
                        break;
                    case R.id.rb_jpg:
                        selectFormat = CIImageFormat.JPEG;
                        break;
                    case R.id.rb_bmp:
                        selectFormat = CIImageFormat.BMP;
                        break;
                    case R.id.rb_gif:
                        selectFormat = CIImageFormat.GIF;
                        break;
                    case R.id.rb_webp:
                        selectFormat = CIImageFormat.WEBP;
                        break;
                    case R.id.rb_yjpeg:
                        selectFormat = CIImageFormat.YJPEG;
                        break;
                    case R.id.rb_heif:
                        selectFormat = CIImageFormat.HEIF;
                        break;
                }
            }
        });

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("GIF".equals(imageBean.format) && selectFormat == CIImageFormat.HEIF){
                    Toast.makeText(FormatActivity.this, "不支持将 GIF 格式图片转换为 HEIF 格式", Toast.LENGTH_LONG).show();
                    return;
                }

                CloudInfinite cloudInfinite = new CloudInfinite();
                CITransformation ciTransformation = new CITransformation();
                if (selectFormat != null) {
                    ciTransformation.format(selectFormat);

                    //示例图片分辨率太大，bmp格式会因为图片太大显示不出来，因此进行缩小
                    if(selectFormat == CIImageFormat.BMP){
                        ciTransformation.thumbnailByMaxWH(840, 630);
                    }
                }
                cloudInfinite.requestWithBaseUrl(imageBean.url, ciTransformation, new CloudInfiniteCallback() {
                    @Override
                    public void onImageLoadRequest(@NonNull CIImageLoadRequest request) {
                        if("GIF".equals(imageBean.format) && selectFormat == CIImageFormat.TPG){
                            //动图TPG跳过缓存加载时也需要asGif
                            view_imageinfo.setData(request.getUrl(), "GIF");
                        } else {
                            view_imageinfo.setData(request.getUrl(), selectFormat.getFormat().toUpperCase());
                        }

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