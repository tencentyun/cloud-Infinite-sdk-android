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

package com.tencent.qcloud.infinite.sample.tpg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class TpgGlideSmallImageListActivity extends BaseActivity {
    private RecyclerView rv_imagelist;
    private TpgGlideSmallImageListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpg_memory);

        Switch switch_compress = findViewById(R.id.switch_compress);
        switch_compress.setVisibility(View.GONE);

        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TpgGlideSmallImageListActivity.this, TpgGlideSmallImageListActivity.class));
            }
        });

        rv_imagelist = findViewById(R.id.rv_imagelist);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL);
        rv_imagelist.setLayoutManager(staggeredGridLayoutManager);
        rv_imagelist.setHasFixedSize(true);

        setData();
    }

    @SuppressLint("DefaultLocale")
    public void setData() {
        //填充测试数据
        ArrayList<ImageBean> list = new ArrayList<>();
        try {
            Random random = new Random();
            for (int i=0;i<100; i++){
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1017132305849778176.png?imageMogr2/format/tpg&aaa="+random.nextInt(100)), "PNG"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (mAdapter == null) {
            mAdapter = new TpgGlideSmallImageListAdapter(list);
            rv_imagelist.setAdapter(mAdapter);
        } else {
            mAdapter.setImageList(list);
            mAdapter.notifyDataSetChanged();
        }
    }
}