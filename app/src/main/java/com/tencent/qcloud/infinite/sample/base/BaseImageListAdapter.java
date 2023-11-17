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

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 顶部图片列表数据适配器
 */
public class BaseImageListAdapter extends RecyclerView.Adapter<BaseImageListAdapter.MyViewHolder> {
    private List<ImageBean> mImageList;
    private OnClickListener mListener;

    public BaseImageListAdapter(List<ImageBean> imageList) {
        mImageList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(Utils.dip2px(parent.getContext(), 80), Utils.dip2px(parent.getContext(), 60));
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundColor(Color.parseColor("#e4e4e4"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && v.getTag() != null) {
                    ImageBean product = (ImageBean) v.getTag();
                    mListener.onClick(product);
                }
            }
        });
        return new MyViewHolder(imageView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ImageBean image = mImageList.get(position);

        if (image == null) return;

        Glide.with(holder.itemView.getContext())
                .load(image.url.toString())
                .into(holder.iv_image);

        holder.itemView.setTag(image);
    }

    @Override
    public int getItemCount() {
        if (mImageList != null) {
            return mImageList.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;

        MyViewHolder(ImageView view) {
            super(view);
            iv_image = view;
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public interface OnClickListener {
        void onClick(ImageBean image);
    }
}
