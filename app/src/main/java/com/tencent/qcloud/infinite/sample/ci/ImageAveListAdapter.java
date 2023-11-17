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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.infinite.glide.CloudInfiniteGlide;
import com.tencent.qcloud.infinite.loader.CIImageAveColorCallBack;
import com.tencent.qcloud.infinite.loader.CIImageLoader;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

import java.util.List;

public class ImageAveListAdapter extends RecyclerView.Adapter<ImageAveListAdapter.MyViewHolder> {
    private List<ImageBean> mImageList;
    private boolean aveIsChecked = true;

    public ImageAveListAdapter(List<ImageBean> imageList) {
        mImageList = imageList;
    }

    public void setAveIsChecked(boolean aveIsChecked) {
        this.aveIsChecked = aveIsChecked;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imageave, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ImageBean image = mImageList.get(position);

        if (image == null) return;

        if (aveIsChecked) {
            //通过glide.thumbnail设置主色预加载
            Glide.with(holder.itemView.getContext())
                    .load(image.url.toString())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .thumbnail(CloudInfiniteGlide.getImageAveThumbnail(holder.itemView.getContext(), image.url.toString()))
                    .into(holder.iv_image);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(image.url.toString())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.iv_image);
        }


        if (aveIsChecked) {
            holder.tv_ave.setVisibility(View.VISIBLE);

            //通过CIImageLoader获取色值，用于其他控件背景色设置等
            CIImageLoader ciImageLoader = new CIImageLoader();
            ciImageLoader.getImageAveColor(image.url, new CIImageAveColorCallBack() {
                @Override
                public void onImageAveColor(final String aveColor) {
                    holder.tv_ave.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.tv_ave.setBackgroundColor(Color.parseColor(aveColor));
                            holder.tv_ave.setText("主色：" + aveColor);
                        }
                    });
                }

                @Override
                public void onFailure(QCloudClientException clientException, QCloudServiceException serviceException) {
                    clientException.printStackTrace();
                    serviceException.printStackTrace();
                    holder.tv_ave.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.tv_ave.setVisibility(View.GONE);
                        }
                    });
                }
            });
        } else {
            holder.tv_ave.setVisibility(View.GONE);
        }
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
        TextView tv_ave;

        MyViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
            tv_ave = view.findViewById(R.id.tv_ave);
        }
    }
}
