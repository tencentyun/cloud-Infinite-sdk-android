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

package com.tencent.qcloud.infinite.utils;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * url工具类
 */
public class UrlUtil {
    /**
     * 给某个URL 拼接参数
     */
    public static String attachGetParams(@NonNull String url, @NonNull String key, @NonNull String value) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }

        return attachGetParams(url, key + "=" + value);
    }

    /**
     * 给某个URL 拼接参数
     */
    public static String attachGetParams(String url, Map<String, String> params) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        return attachGetParams(url, paramsToString(params));
    }

    public static String attachGetParams(String url, String content) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }

        String connectSymbol = "";
        int questionMarkIndex = url.indexOf("?");
        //没有"?"
        if (questionMarkIndex == -1) {
            connectSymbol = "?";
        } else {
            //已经使用"&"结尾
            if (!url.endsWith("&")) {
                connectSymbol = "&";
            }
        }
        url = url + connectSymbol + content;

        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }
        if (url.endsWith("?")) {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }


    /**
     * 用于拼接URL后面的参数
     */
    public static String paramsToString(Map<String, String> urlParams) {
        if (urlParams == null || urlParams.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String key : urlParams.keySet()) {
            String value = urlParams.get(key);
            sb.append(key);
            sb.append("=");
            sb.append(value == null ? "" : value);
            sb.append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }


    public static Map<String, String> parseUrlToParams(String url) {
        if (TextUtils.isEmpty(url) || !url.contains("?")) {
            return null;
        }
        return parseContentToParams(url.substring(url.indexOf("?") + 1, url.length()));
    }


    /**
     * 解析URL中的参数
     *
     * @param content 要解析的URL中的参数（?后面的字符串部分）
     */
    public static Map<String, String> parseContentToParams(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }

        int index = content.indexOf("?");
        content = index == -1 ? content : content.substring(index + 1, content.length());

        Map<String, String> params = new HashMap<>();
        String[] pairs = content.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx == -1) {
                params.put(pair, "");
            } else {
                try {
                    String key = pair.substring(0, idx);
                    String value = pair.substring(idx + 1);
                    if (!TextUtils.isEmpty(value)) {
                        params.put(key, Uri.decode(value));
                    }
                } catch (StringIndexOutOfBoundsException e) {
                }
            }
        }
        return params;
    }

    /**
     * 删除url中指定的参数
     *
     * @param url  url
     * @param keys 指定key的集合
     */
    public static String removeParams(String url, String[] keys) {
        String reg = null;
        for (int i = 0; i < keys.length; i++) {
            reg = "(?<=[\\?&])" + keys[i] + "=[^&]*&?";
            url = url.replaceAll(reg, "");
        }
        url = url.replaceAll("&+$", "");
        return url;
    }
}
