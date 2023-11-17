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

package com.tencent.qcloud.infinite.sample.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.infinite.sample.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 顶部图片列表封装，供每个页面使用
 */
public class BaseImageListView extends RecyclerView {
    private static final String TAG = "ImageListView";

    private final int ALL = 0;
    private final int STATICL = 1;
    private final int GIF = 2;

    private OnClickListener mListener;

    public BaseImageListView(@NonNull Context context) {
        this(context, null);
    }

    public BaseImageListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseImageListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BaseImageListView);
        int dataType = typedArray.getInt(R.styleable.BaseImageListView_dataType, 0);
        typedArray.recycle();

        //创建默认的线性横向LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(linearLayoutManager);
        this.setHasFixedSize(true);
        this.addItemDecoration(new BaseImageListDecoration());

        initData(dataType);
    }

    private void initData(int dataType) {
        final ArrayList<ImageBean> data = new ArrayList<>();
        try {
            if (dataType == ALL) {
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1024742413534494720.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/horizontal.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/vertical.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/01.gif"), "GIF"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1017132305849778176.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default02.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default03.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default04.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/26_2.gif"), "GIF"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/32_6.gif"), "GIF"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default05.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1023292959778406400.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default06.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default07.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default08.png"), "PNG"));
            } else if (dataType == STATICL) {
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1017132305849778176.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/1024742413534494720.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default01.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default02.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default03.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default04.jpg"), "JPG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default05.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default06.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default07.png"), "PNG"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default08.png"), "PNG"));
            } else if (dataType == GIF) {
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/01.gif"), "GIF"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/26_2.gif"), "GIF"));
                data.add(new ImageBean(new URL("https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/big_gif/32_6.gif"), "GIF"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        BaseImageListAdapter adapter = new BaseImageListAdapter(data);
        adapter.setOnClickListener(new BaseImageListAdapter.OnClickListener() {
            @Override
            public void onClick(ImageBean image) {
                if (mListener != null) {
                    mListener.onClick(image);
                }
            }
        });
        this.setAdapter(adapter);

        //触发第一项自动onClick
        this.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onClick(data.get(0));
                }
            }
        });
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public interface OnClickListener {
        void onClick(ImageBean image);
    }

}
