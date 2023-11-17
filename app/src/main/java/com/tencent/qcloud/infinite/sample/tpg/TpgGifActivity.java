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

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.GifAdapter;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TpgGifActivity extends BaseActivity {
    private RecyclerView rv_imagelist;
    private GifAdapter mAdapter;

    private static final boolean isTPG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        findViewById(R.id.btn_goto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TpgGifActivity.this, TpgGifActivity.class));
            }
        });

        rv_imagelist = findViewById(R.id.rv_imagelist);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_imagelist.setLayoutManager(staggeredGridLayoutManager);
        rv_imagelist.setHasFixedSize(true);
        rv_imagelist.setItemViewCacheSize(0);

        if (isTPG) {
            setTPGData();
        } else {
            setGifData();
        }
    }

    @SuppressLint("DefaultLocale")
    public void setTPGData() {
        //填充测试数据
        ArrayList<ImageBean> list = new ArrayList<>(16);
        try {
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/dingdang.gif?imageMogr2/format/tpg"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif1.gif?imageMogr2/format/tpg"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif2.gif?imageMogr2/format/tpg"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif3.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif4.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif5.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/10.gif?imageMogr2/format/tpg"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/11.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/12.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/13.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/20.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/21.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/22.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/23.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/24.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/25.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/26.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/27.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/28.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/29.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/30.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/dingdang.gif?imageMogr2/format/tpg"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif1.gif?imageMogr2/format/tpg"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif2.gif?imageMogr2/format/tpg"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif3.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif4.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif5.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/10.gif?imageMogr2/format/tpg"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/11.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/12.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/13.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/20.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/21.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/22.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/23.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/24.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/25.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/26.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/27.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/28.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/29.gif?imageMogr2/format/tpg"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/30.gif?imageMogr2/format/tpg"), "PNG"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mAdapter = new GifAdapter(list);
        rv_imagelist.setAdapter(mAdapter);
    }

    public void setGifData() {
        //填充测试数据
        ArrayList<ImageBean> list = new ArrayList<>(16);
        try {
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/dingdang.gif"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif1.gif"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif2.gif"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif3.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif4.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/gif5.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/10.gif"), "JPG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/11.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/12.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/13.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/20.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/21.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/22.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/23.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/24.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/25.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/26.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/27.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/28.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/29.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/30.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/14.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/15.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/16.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/17.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/18.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/19.gif"), "PNG"));

            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/31.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/32.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/33.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/34.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/35.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/36.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/37.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/38.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/39.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/40.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/41.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/42.gif"), "PNG"));
            list.add(new ImageBean(new URL("https://tpg-1253653367.file.myqcloud.com/43.gif"), "PNG"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mAdapter = new GifAdapter(list);
        rv_imagelist.setAdapter(mAdapter);
    }
}