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

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tencent.qcloud.infinite.CIImageLoadRequest;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {
    private List<ImageBean> mImageList;
    private OnClickListener mListener;

    public ImageListAdapter(List<ImageBean> imageList) {
        mImageList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_image, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && v.getTag() != null) {
                    ImageBean product = (ImageBean) v.getTag();
                    mListener.onClick(product);
                }
            }
        });
        return new MyViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ImageBean image = mImageList.get(position);

        if (image == null) return;

        Glide.with(holder.itemView.getContext())
                .load(new CIImageLoadRequest(image.url)).into(holder.iv_image);

        //获取并展示图片大小（仅做演示）
        new Thread(new Runnable() {
            @Override
            public void run() {
                int originalSize = 0;
                try {
                    URLConnection connection = image.url.openConnection();
                    originalSize = connection.getContentLength();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final int finalOriginalSize = originalSize;
                holder.tv_size.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.tv_size.setText(String.format("大小:%s", Utils.readableStorageSize(finalOriginalSize)));
                    }
                });

            }
        }).start();

        holder.tv_format.setText(image.format);
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
        TextView tv_format;
        TextView tv_size;

        MyViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
            tv_format = view.findViewById(R.id.tv_format);
            tv_size = view.findViewById(R.id.tv_size);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public interface OnClickListener {
        void onClick(ImageBean image);
    }
}
