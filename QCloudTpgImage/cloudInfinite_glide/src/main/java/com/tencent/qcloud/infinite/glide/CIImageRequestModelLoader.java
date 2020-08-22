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

package com.tencent.qcloud.infinite.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.tencent.qcloud.infinite.CIImageLoadRequest;

import java.io.InputStream;

/**
 * 万象图片请求加载器<br>
 * 基于BaseGlideUrlLoader，通过拼接URL和Header实现
 */
public class CIImageRequestModelLoader extends BaseGlideUrlLoader<CIImageLoadRequest> {
    protected CIImageRequestModelLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    @Override
    protected String getUrl(CIImageLoadRequest request, int width, int height, Options options) {
        return request.getUrl().toString();
    }

    @Nullable
    @Override
    protected Headers getHeaders(CIImageLoadRequest request, int width, int height, Options options) {
        if (request == null || request.getHeaders() == null)
            return super.getHeaders(request, width, height, options);

        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        for (String key : request.getHeaders().keySet()) {
            if (request.getHeaders().get(key) != null && request.getHeaders().get(key).size() > 0) {
                builder.addHeader(key, request.getHeaders().get(key).get(0));
            }
        }
        return builder.build();
    }

    @Override
    public boolean handles(@NonNull CIImageLoadRequest request) {
        return true;
    }

    public static final class Factory implements ModelLoaderFactory<CIImageLoadRequest, InputStream> {
        @Override
        public ModelLoader<CIImageLoadRequest, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new CIImageRequestModelLoader(multiFactory.build(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {
        }
    }
}
