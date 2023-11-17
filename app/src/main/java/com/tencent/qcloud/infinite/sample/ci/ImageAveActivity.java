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
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageAveActivity extends BaseActivity {
    private RecyclerView rv_imagelist;
    private ImageAveListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_ave);

        Switch switch_ave = findViewById(R.id.switch_ave);
        switch_ave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                setData(isChecked);
            }
        });

        rv_imagelist = findViewById(R.id.rv_imagelist);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_imagelist.setLayoutManager(staggeredGridLayoutManager);
        rv_imagelist.setHasFixedSize(true);

        setData(switch_ave.isChecked());
    }

    @SuppressLint("DefaultLocale")
    public void setData(boolean isChecked) {
        //填充测试数据
        if (mAdapter == null) {
            ArrayList<ImageBean> list = new ArrayList<>(16);
            try {
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default01.jpg"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default02.jpg"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default03.jpg"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default04.jpg"), "JPG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default05.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default06.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default07.png"), "PNG"));
                list.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default08.png"), "PNG"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            mAdapter = new ImageAveListAdapter(list);
            mAdapter.setAveIsChecked(isChecked);
            rv_imagelist.setAdapter(mAdapter);
        } else {
            mAdapter.setAveIsChecked(isChecked);
            mAdapter.notifyDataSetChanged();
        }
    }
}