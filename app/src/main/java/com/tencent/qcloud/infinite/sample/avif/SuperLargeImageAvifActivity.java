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

package com.tencent.qcloud.infinite.sample.avif;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.tencent.qcloud.image.avif.subsampling.AvifSubsamplingImageDecoder;
import com.tencent.qcloud.image.avif.subsampling.AvifSubsamplingImageRegionDecoder;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;

public class SuperLargeImageAvifActivity extends BaseActivity {
    private SubsamplingScaleImageView imageView;
    private static final int FILE_SELECTOR_CODE = 890;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_large_image);

        imageView = findViewById(R.id.imageView);

        imageView.setBitmapDecoderClass(AvifSubsamplingImageDecoder.class);
        imageView.setRegionDecoderClass(AvifSubsamplingImageRegionDecoder.class);

        // 设置普通图片解码器
//              imageView.setBitmapDecoderClass(SkiaImageDecoder.class);
//              imageView.setRegionDecoderClass(SkiaImageRegionDecoder.class);

        imageView.setImage(ImageSource.asset("super_large_image.avif"));
//                imageView.setImage(ImageSource.resource(R.raw.a8_3));

//                File file = new File(getFilesDir(), "8_3.avif");
//                imageView.setImage(ImageSource.uri(file.getAbsolutePath()));

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent, FILE_SELECTOR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECTOR_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                imageView.setImage(ImageSource.uri(uri));
            }
        }
    }
}
