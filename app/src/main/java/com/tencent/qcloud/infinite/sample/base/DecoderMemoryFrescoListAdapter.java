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
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tencent.qcloud.image.decoder.fresco.FrescoOriginalImageRetryCallback;
import com.tencent.qcloud.image.decoder.fresco.OriginalImageRetryControllerListener;
import com.tencent.qcloud.infinite.sample.R;

import java.util.List;

public class DecoderMemoryFrescoListAdapter extends RecyclerView.Adapter<DecoderMemoryFrescoListAdapter.MyViewHolder> {
    private static final String TAG = "FrescoListAdapter";

    private List<ImageBean> mImageList;

    public DecoderMemoryFrescoListAdapter(List<ImageBean> imageList) {
        mImageList = imageList;
    }

    public void setImageList(List<ImageBean> imageList){
        mImageList.clear();
        mImageList.addAll(imageList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_fresco, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ImageBean image = mImageList.get(position);

        if (image == null) return;

        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(image.url.toString()))
                .disableDiskCache()
                .disableMemoryCache();
        ImageRequest imageRequest = imageRequestBuilder.build();

        PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder()
                .setOldController(holder.iv_image.getController())
                .setAutoPlayAnimations(true)
                .setImageRequest(imageRequest);

        // 原图兜底监听器
        OriginalImageRetryControllerListener originalImageRetry = new OriginalImageRetryControllerListener(holder.iv_image, imageRequestBuilder, draweeControllerBuilder);
        originalImageRetry.setOriginalImageRetryCallback(new FrescoOriginalImageRetryCallback() {
            @Nullable
            @Override
            public String buildOriginalImageUrl(String urlStr) {
                // 使用默认的原图格式
                return null;
            }

            @Override
            public void onFailureBeforeRetry(Uri uri, String id, Throwable throwable) {
                // tpg加载失败在这里上报，统计原图兜底次数和tpg解码异常信息(不影响真正的图片加载失败率)
                Log.d(TAG, "TPG onLoadFailed："+ uri.toString());
                if (throwable != null) {
                    Log.d(TAG, throwable.getMessage());
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onFailure(Uri uri, String id, Throwable throwable) {
                //真正的加载失败在这里上报(影响真正的图片加载成功失败率)
                Log.d(TAG, "Image onLoadFailed："+ uri.toString());
                if (throwable != null) {
                    Log.d(TAG, throwable.getMessage());
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onFinalImageSet(Uri uri, String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                //真正的加载成功在这里上报(影响真正的图片加载成功失败率)
                Log.d(TAG, "Image onLoadSuccess："+ uri.toString());
            }
        });

        AbstractDraweeController controller = draweeControllerBuilder.build();
        controller.addControllerListener(originalImageRetry);
        holder.iv_image.setController(controller);
    }

    @Override
    public int getItemCount() {
        if (mImageList != null) {
            return mImageList.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_image;

        MyViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
        }
    }
}
