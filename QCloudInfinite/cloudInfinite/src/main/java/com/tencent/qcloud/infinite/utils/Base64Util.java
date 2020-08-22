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

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {
    /**
     * 获取base64编码字符串
     *
     * @param content 原始字符串
     * @return base64编码字符串
     * @throws UnsupportedEncodingException If the named charset is not supported
     */
    public static String getBase64(String content) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        return Base64.encodeToString(content.getBytes("utf-8"), Base64.NO_WRAP); // NO_WARP
    }

    /**
     * 获取URL安全的base64编码字符串<br>
     * 1、将普通 BASE64 编码结果中的加号（+）替换成连接号（-）
     * 2、将编码结果中的正斜线（/）替换成下划线（_）
     * 3、保留编码结果中末尾的全部等号（=）
     *
     * @param content 原始字符串
     * @return URL安全的base64编码字符串
     * @throws UnsupportedEncodingException If the named charset is not supported
     */
    public static String getUrlSafetyBase64(String content) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(content)) {
            return content;
        }

        String result = getBase64(content);
        result = result.replace("+", "-");
        result = result.replace("/", "_");
        return result;
    }
}
