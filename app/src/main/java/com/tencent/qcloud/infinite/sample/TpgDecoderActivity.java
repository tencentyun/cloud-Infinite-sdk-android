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
import com.tencent.qcloud.infinite.sample.tpg.SuperLargeImageTpgActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgDecodeActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgFrescoActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgFrescoImageListActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgFrescoMemoryActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgFrescoOriginalImageRetryActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgGifActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgGlideActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgGlideImageListActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgGlideMemoryActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgGlideOriginalImageRetryActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgGlideSmallImageListActivity;
import com.tencent.qcloud.infinite.sample.tpg.TpgGlideVsWebPActivity;

public class TpgDecoderActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tpg_decoder);

        findViewById(R.id.btn_tpg_glide).setOnClickListener(this);
        findViewById(R.id.btn_tpg_memory_glide).setOnClickListener(this);
        findViewById(R.id.btn_tpg_webp_glide).setOnClickListener(this);
        findViewById(R.id.btn_tpg_image_list_glide).setOnClickListener(this);
        findViewById(R.id.btn_tpg_fresco).setOnClickListener(this);
        findViewById(R.id.btn_tpg_memory_fresco).setOnClickListener(this);
        findViewById(R.id.btn_tpg_image_list_fresco).setOnClickListener(this);
        findViewById(R.id.btn_gif).setOnClickListener(this);
        findViewById(R.id.btn_super_large_image).setOnClickListener(this);
        findViewById(R.id.btn_decode).setOnClickListener(this);
        findViewById(R.id.btn_glide_original_image_retry).setOnClickListener(this);
        findViewById(R.id.btn_fresco_original_image_retry).setOnClickListener(this);
        findViewById(R.id.btn_small_image_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tpg_glide:
                startActivity(new Intent(this, TpgGlideActivity.class));
                break;
            case R.id.btn_tpg_memory_glide:
                startActivity(new Intent(this, TpgGlideMemoryActivity.class));
                break;
            case R.id.btn_tpg_webp_glide:
                startActivity(new Intent(this, TpgGlideVsWebPActivity.class));
                break;
            case R.id.btn_tpg_image_list_glide:
                startActivity(new Intent(this, TpgGlideImageListActivity.class));
                break;
            case R.id.btn_tpg_fresco:
                startActivity(new Intent(this, TpgFrescoActivity.class));
                break;
            case R.id.btn_tpg_memory_fresco:
                startActivity(new Intent(this, TpgFrescoMemoryActivity.class));
                break;
            case R.id.btn_tpg_image_list_fresco:
                startActivity(new Intent(this, TpgFrescoImageListActivity.class));
                break;
            case R.id.btn_gif:
                startActivity(new Intent(this, TpgGifActivity.class));
                break;
            case R.id.btn_decode:
                startActivity(new Intent(this, TpgDecodeActivity.class));
                break;
            case R.id.btn_super_large_image:
                startActivity(new Intent(this, SuperLargeImageTpgActivity.class));
                break;
            case R.id.btn_glide_original_image_retry:
                startActivity(new Intent(this, TpgGlideOriginalImageRetryActivity.class));
                break;
            case R.id.btn_fresco_original_image_retry:
                startActivity(new Intent(this, TpgFrescoOriginalImageRetryActivity.class));
                break;
            case R.id.btn_small_image_list:
                startActivity(new Intent(this, TpgGlideSmallImageListActivity.class));
                break;
        }
    }
}