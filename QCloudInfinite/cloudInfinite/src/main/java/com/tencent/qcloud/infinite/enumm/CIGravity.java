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

package com.tencent.qcloud.infinite.enumm;

/**
 * 九宫格方位<br>
 * 九宫格方位图可为图片的多种操作提供位置参考,通过 gravity 参数选定各区域后位移操作会以相应远点为参照<br>
 * API 文档：<a href="https://cloud.tencent.com/document/product/460/36541#.E4.B9.9D.E5.AE.AB.E6.A0.BC.E6.96.B9.E4.BD.8D.E5.9B.BE">九宫格方位图.</a>
 */
public enum CIGravity {
    NORTHWEST("northwest"),
    NORTH("north"),
    NORTHEAST("northeast"),
    WEST("west"),
    CENTER("center"),
    EAST("east"),
    SOUTHWEST("southwest"),
    SOUTH("south"),
    SOUTHEAST("southeast");

    private String gravity;

    CIGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getGravity() {
        return gravity;
    }
}
