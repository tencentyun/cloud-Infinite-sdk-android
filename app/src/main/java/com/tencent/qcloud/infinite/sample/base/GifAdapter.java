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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tencent.qcloud.infinite.sample.R;

import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.MyViewHolder> {
    private List<ImageBean> mImageList;

    public GifAdapter(List<ImageBean> imageList) {
        mImageList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imageave_image, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ImageBean image = mImageList.get(position);

        if (image == null) return;

        Glide.with(holder.itemView.getContext())
                .load(image.url.toString())
                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.iv_image);
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

        MyViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
        }
    }
}
