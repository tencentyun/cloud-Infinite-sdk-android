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

import com.tencent.qcloud.infinite.sample.avif.AVIFFrescoActivity;
import com.tencent.qcloud.infinite.sample.avif.AVIFGlideActivity;
import com.tencent.qcloud.infinite.sample.avif.AvifDecodeActivity;
import com.tencent.qcloud.infinite.sample.avif.AvifFrescoMemoryActivity;
import com.tencent.qcloud.infinite.sample.avif.AvifGifActivity;
import com.tencent.qcloud.infinite.sample.avif.AvifGlideMemoryActivity;
import com.tencent.qcloud.infinite.sample.avif.SuperLargeImageAvifActivity;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;

public class AvifDecoderActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_avif_decoder);

        findViewById(R.id.btn_avif_glide).setOnClickListener(this);
        findViewById(R.id.btn_avif_memory_glide).setOnClickListener(this);
        findViewById(R.id.btn_avif_fresco).setOnClickListener(this);
        findViewById(R.id.btn_avif_memory_fresco).setOnClickListener(this);
        findViewById(R.id.btn_gif).setOnClickListener(this);
        findViewById(R.id.btn_super_large_image).setOnClickListener(this);
        findViewById(R.id.btn_decode).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_avif_glide:
                startActivity(new Intent(this, AVIFGlideActivity.class));
                break;
            case R.id.btn_avif_memory_glide:
                startActivity(new Intent(this, AvifGlideMemoryActivity.class));
                break;
            case R.id.btn_avif_fresco:
                startActivity(new Intent(this, AVIFFrescoActivity.class));
                break;
            case R.id.btn_avif_memory_fresco:
                startActivity(new Intent(this, AvifFrescoMemoryActivity.class));
                break;
            case R.id.btn_gif:
                startActivity(new Intent(this, AvifGifActivity.class));
                break;
            case R.id.btn_decode:
                startActivity(new Intent(this, AvifDecodeActivity.class));
                break;
            case R.id.btn_super_large_image:
                startActivity(new Intent(this, SuperLargeImageAvifActivity.class));
                break;
        }
    }
}