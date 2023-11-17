package com.tencent.qcloud.infinite.sample.tpg;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.tencent.qcloud.image.decoder.glide.GlideOriginalImageRetry;
import com.tencent.qcloud.image.decoder.glide.GlideOriginalImageRetryCallback;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Created by jordanqin on 2023/5/17 14:38.
 * Copyright 2010-2020 Tencent Cloud. All Rights Reserved.
 */
public class TpgGlideOriginalImageRetryActivity extends BaseActivity {
    private static final String TAG = "TpgGlideOriginalRetry";

    private ImageView imageView;
    private EditText et_image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpg_glide_original_image);

        imageView = findViewById(R.id.imageView);
        et_image_url = findViewById(R.id.et_image_url);

//        String test = "https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default88.jpg?imageMogr2/format/avif|imageMogr2/thumbnail/150x50|imageMogr2/quality/30";
        String test = "https://i4.hoopchina.com.cn/hupuapp/bbs/111111/thread_111111_20220527134517_s_10151_w_196_h_172_66941.jpg?imageMogr2/format/avif/thumbnail/450x%3E/quality/30/ignore-error/1";
        et_image_url.setText(test);

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImg();
            }
        });
    }

    private void loadImg(){
        // 图片加载的Options
        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(true) //不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用磁盘缓存
                .centerCrop()
                .placeholder(R.drawable.arrow)
                .error(R.drawable.ic_launcher_background)
                .override(Target.SIZE_ORIGINAL);

        // 图片加载的监听器列表
        List<RequestListener<Drawable>> requestListeners = new ArrayList<>();
        requestListeners.add(new RequestListener<Drawable>() {
            @SuppressLint("DefaultLocale")
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                // 其他业务onLoadFailed
                return false;
            }

            @SuppressLint("DefaultLocale")
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                // 其他业务onResourceReady
                return false;
            }
        });

        // 原图兜底监听器
        GlideOriginalImageRetry.OriginalImageRetryRequestListener originalImageRetry = new GlideOriginalImageRetry.OriginalImageRetryRequestListener();
        originalImageRetry.setRequestOptions(requestOptions);
        originalImageRetry.setRequestListeners(requestListeners);
        originalImageRetry.setOriginalImageRetryCallback(new GlideOriginalImageRetryCallback() {
            @Nullable
            @Override
            public String buildOriginalImageUrl(String urlStr) {
                // 使用默认的原图格式
                return null;
            }

            @Override
            public void onFailureBeforeRetry(@Nullable GlideException e, Object model, Target<?> target, boolean isFirstResource) {
                // tpg加载失败在这里上报，统计原图兜底次数和tpg解码异常信息(不影响真正的图片加载失败率)
                Log.d(TAG, "TPG onLoadFailed："+ model);
                if (e != null) {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadFailed(@Nullable GlideException e, Object model, Target<?> target, boolean isFirstResource) {
                //真正的加载失败在这里上报(影响真正的图片加载成功失败率)
                Log.d(TAG, "Image onLoadFailed："+ model);
                if (e != null) {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onResourceReady(Object resource, Object model, Target<?> target, DataSource dataSource, boolean isFirstResource) {
                //真正的加载成功在这里上报(影响真正的图片加载成功失败率)
                Log.d(TAG, "Image onLoadSuccess："+ model);
            }
        });

        String imgUrl = et_image_url.getText().toString();
        Log.d(TAG, "原始图片URL："+imgUrl);

        // 加载图片
        RequestBuilder<Drawable> requestBuilder = Glide.with(this)
                .load(imgUrl)
                .apply(requestOptions)
                .addListener(originalImageRetry);
        for (RequestListener<Drawable> listener : requestListeners){
            requestBuilder.addListener(listener);
        }
        requestBuilder.into(imageView);

//        Glide.with(this).load(imgUrl).into(imageView);
    }
}
