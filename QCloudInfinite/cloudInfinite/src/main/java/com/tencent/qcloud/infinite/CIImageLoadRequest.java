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

package com.tencent.qcloud.infinite;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片请求
 */
public class CIImageLoadRequest {
    private URL url;
    private Map<String, List<String>> header = new LinkedHashMap<>();;

    public CIImageLoadRequest(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setHeaders(Map<String, List<String>> headers){
        if(headers != null){
            this.header.putAll(headers);
        }
    }

    public void addHeader(String key, String value){
        List<String> values;
        if(header.containsKey(key)){
            values = header.get(key);
        }else {
            values = new ArrayList<String>();
        }
        values.add(value);
        header.put(key, values);
    }

    public Map<String, List<String>> getHeaders() {
        return header;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CIImageLoadRequest)) return false;
        CIImageLoadRequest that = (CIImageLoadRequest) o;
        return getUrl().equals(that.getUrl()) &&
                header.equals(that.header);
    }

    @Override
    public int hashCode() {
        int hashCode = getUrl().hashCode();
        hashCode = 31 * hashCode + getHeaders().hashCode();
        return hashCode;
    }
}
