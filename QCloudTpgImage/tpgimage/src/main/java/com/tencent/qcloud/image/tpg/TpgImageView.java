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

package com.tencent.qcloud.image.tpg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import java.net.URI;

/**
 * TPG图片控件<br>
 * 继承自ImageView，支持字节数组、文件、DrawableRes、Assets类型图片加载
 */
@SuppressLint("AppCompatCustomView")
public class TpgImageView extends ImageView {
    private static final String TAG = "TPGImageView";

    public TpgImageView(Context context) {
        super(context);
    }

    public TpgImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TpgImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置字节数据图片
     *
     * @param bytes 字节数据
     */
    public void setImageWithData(@NonNull byte[] bytes) {
        TpgImageLoader.displayWithData(this, bytes);
    }

    /**
     * 设置字节数据图片
     *
     * @param bytes       字节数据
     * @param placeholder 占位资源ID
     */
    public void setImageWithData(@NonNull byte[] bytes, @DrawableRes int placeholder) {
        TpgImageLoader.displayWithData(this, bytes, placeholder);
    }

    /**
     * 设置文件图片
     * @param fileUri 文件Uri
     */
    public void setImageWithFileUri(@NonNull URI fileUri) {
        TpgImageLoader.displayWithFileUri(this, fileUri);
    }

    /**
     * 设置文件图片
     * @param fileUri 文件Uri
     * @param placeholder 占位资源ID
     */
    public void setImageWithFileUri(@NonNull URI fileUri, @DrawableRes int placeholder) {
        TpgImageLoader.displayWithFileUri(this, fileUri, placeholder);
    }

    /**
     * 设置资源图片
     *
     * @param resource 资源ID
     */
    public void setImageWithResource(@RawRes @DrawableRes int resource) {
        TpgImageLoader.displayWithResource(this, resource);
    }

    /**
     * 设置资源图片
     *
     * @param resource    资源ID
     * @param placeholder 占位资源ID
     */
    public void setImageWithResource(@DrawableRes int resource, @DrawableRes int placeholder) {
        TpgImageLoader.displayWithResource(this, resource, placeholder);
    }

    /**
     * 设置assets图片
     *
     * @param assetsName assets文件名称
     */
    public void setImageWithAssets(@NonNull String assetsName) {
        TpgImageLoader.displayWithAssets(this, assetsName);
    }

    /**
     * 设置assets图片
     *
     * @param assetsName  assets文件名称
     * @param placeholder 占位资源ID
     */
    public void setImageWithAssets(@NonNull String assetsName, @DrawableRes int placeholder) {
        TpgImageLoader.displayWithAssets(this, assetsName, placeholder);
    }
}
