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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.DecoderMemoryFrescoListAdapter;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AvifFrescoMemoryActivity extends BaseActivity {
    private RecyclerView rv_imagelist;
    private DecoderMemoryFrescoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpg_memory);

        Switch switch_compress = findViewById(R.id.switch_compress);
        switch_compress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                setData(isChecked);
            }
        });

        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AvifFrescoMemoryActivity.this, AvifFrescoMemoryActivity.class));
            }
        });

        rv_imagelist = findViewById(R.id.rv_imagelist);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_imagelist.setLayoutManager(staggeredGridLayoutManager);
        rv_imagelist.setHasFixedSize(true);

        setData(switch_compress.isChecked());
    }

    @SuppressLint("DefaultLocale")
    public void setData(boolean isTpg) {
        //填充测试数据
        ArrayList<ImageBean> list = new ArrayList<>();
        try {
            if(isTpg){
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/21_3.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/24_9.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/8_3.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/26_2.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1017132305849778176.png?imageMogr2/format/avif"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1024742413534494720.png?imageMogr2/format/avif"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default01.jpg?imageMogr2/format/avif"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default02.jpg?imageMogr2/format/avif"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default03.jpg?imageMogr2/format/avif"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default04.jpg?imageMogr2/format/avif"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default05.png?imageMogr2/format/avif"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default06.png?imageMogr2/format/avif"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default07.png?imageMogr2/format/avif"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default08.png?imageMogr2/format/avif"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/32_6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/14_6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_2.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_9.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/21_3.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/24_9.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/8_3.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/26_2.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/32_6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/14_6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_2.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_9.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/21_3.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/24_9.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/8_3.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/26_2.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/32_6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/14_6.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_2.gif?imageMogr2/format/avif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_9.gif?imageMogr2/format/avif"), "GIF"));
            } else {
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/21_3.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/24_9.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/8_3.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/26_2.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1017132305849778176.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1024742413534494720.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default01.jpg"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default02.jpg"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default03.jpg"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default04.jpg"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default05.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default06.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default07.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default08.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/32_6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/14_6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_2.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_9.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/21_3.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/24_9.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/8_3.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/26_2.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/32_6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/14_6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_2.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_9.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/21_3.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/24_9.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/8_3.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/26_2.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/32_6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/14_6.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_2.gif"), "GIF"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/5_9.gif"), "GIF"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (mAdapter == null) {
            mAdapter = new DecoderMemoryFrescoListAdapter(list);
            rv_imagelist.setAdapter(mAdapter);
        } else {
            mAdapter.setImageList(list);
            mAdapter.notifyDataSetChanged();
        }
    }
}