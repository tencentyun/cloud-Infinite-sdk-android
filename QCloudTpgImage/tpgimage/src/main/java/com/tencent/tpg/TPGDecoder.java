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

package com.tencent.tpg;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * TPG解码器
 */
public class TPGDecoder {
	public static final int FORMAT_RGB = 1;
	public static final int FORMAT_BGR = 2;
	public static final int FORMAT_RGBA = 3;
	public static final int FORMAT_BGRA = 4;
	public static final int FORMAT_NV21 = 5;
	public static final int FORMAT_RGBA_BLEND_ALPHA = 6; //RGB*alpha

	public static final int IMAGE_MODE_Normal = 0;
	public static final int IMAGE_MODE_EncodeAlpha = 1;
	public static final int IMAGE_MODE_BlendAlpha = 2;
	public static final int IMAGE_MODE_Animation = 3;
	public static final int IMAGE_MODE_AnimationWithAlpha = 4;

	public static final int TPG_STATUS_OK = 0;
	public static final int TPG_STATUS_OUT_OF_MEMORY         =  1;
	public static final int TPG_STATUS_INVALID_PARAM         =  2;
	public static final int TPG_STATUS_BITSTREAM_ERROR       =  3;
	public static final int TPG_STATUS_UNSUPPORTED_FEATURE   =  4;
	public static final int TPG_STATUS_SUSPENDED             =  5;
	public static final int TPG_STATUS_USER_ABORT            =  6;
	public static final int TPG_STATUS_NOT_ENOUGH_DATA       =  7;
	public static final int TPG_STATUS_INIT_ERROR       		=  8;

	public final static int MAX_LAYER_NUM = 3;
	public final static int MAX_STREAM_SIZE = 1000000;


	public static class TPGFeature {
		int width;
		int height;
		int headerSize;
		int imageMode;
		int frameCount;
		int version;
	}

	public static class TPGOutFrame {
		int[] pOutBuf;
		int bufsize;
		int dstWidth;
		int dstHeight;
		int fmt;
		int delayTime;
	}


	private int imageWidth;
	private int imageHeight;
	private int mhDec;
	private TPGFeature mFeatureInfo;

	public int parseHeader(byte[] stream) {
		mFeatureInfo = new TPGFeature();
		return ParseHeader(stream, mFeatureInfo);
	}

	public int parseHeader(String tpg_path) {
		mFeatureInfo = new TPGFeature();
		return ParseHeader2(tpg_path, mFeatureInfo);
	}

	public int getTPGType() {
		return mFeatureInfo.imageMode;
	}

	public int getWidth() {
		return mFeatureInfo.width;
	}

	public int getHeight() {
		return mFeatureInfo.height;
	}

	public int getFrameCount() {
		return mFeatureInfo.frameCount;
	}

	public int getVersion() {
		return GetVersion();
	}

	public byte[] getAddtionalInfo(byte[] stream, int id) {
		return GetAdditionalInfo(mhDec, stream, id);
	}

	public byte[] getAddtionalInfo2(int id) {
		return GetAdditionalInfo2(mhDec, id);
	}

	public int startDecode(byte[] stream) {
		mhDec = CreateDecoder(stream);

		if (mhDec == 0) {
			return TPG_STATUS_INVALID_PARAM;
		}
		return TPG_STATUS_OK;
	}

	public int startDecode2(String path) {
		mhDec = CreateDecoder2(path);

		if (mhDec == 0) {
			return TPG_STATUS_INVALID_PARAM;
		}
		return TPG_STATUS_OK;
	}

	public void closeDecode() {
		CloseDecoder(mhDec);
		mhDec = 0;
	}

	public void closeDecode2() {
		CloseDecoder2(mhDec);
		mhDec = 0;
	}

	public int getDelayTime(byte[] stream, int index) {

		return GetDelayTime(mhDec, stream, index);
	}

	public int getDelayTime2(int index) {

		return GetDelayTime2(mhDec, index);
	}

	public int decodeOneFrame(byte[] stream, int index, int[] outData,
                              Bitmap bm, int[] delayTime) {

		int res = 0;
		TPGOutFrame tpgOutFrame = new TPGOutFrame();
		tpgOutFrame.pOutBuf = outData;
		tpgOutFrame.dstWidth = mFeatureInfo.width;
		tpgOutFrame.dstHeight = mFeatureInfo.height;
		tpgOutFrame.fmt = FORMAT_BGRA;
		if (DecodeImage(mhDec, stream, index, tpgOutFrame) > 0) {
			System.out.println("decode error: ");
		}
		delayTime[0] = tpgOutFrame.delayTime;

		bm.setPixels(outData, 0, mFeatureInfo.width, 0, 0, mFeatureInfo.width,
				mFeatureInfo.height);

		return res;
	}

	public int decodeOneFrame2(int index, int[] outData, Bitmap bm,
			int[] delayTime) {

		int res = 0;
		TPGOutFrame tpgOutFrame = new TPGOutFrame();
		tpgOutFrame.pOutBuf = outData;
		tpgOutFrame.dstWidth = mFeatureInfo.width;
		tpgOutFrame.dstHeight = mFeatureInfo.height;
		tpgOutFrame.fmt = FORMAT_BGRA;
		if (DecodeImage2(mhDec, index, tpgOutFrame) > 0) {
			System.out.println("decode error: ");
		}
		delayTime[0] = tpgOutFrame.delayTime;

		bm.setPixels(outData, 0, mFeatureInfo.width, 0, 0, mFeatureInfo.width,
				mFeatureInfo.height);

		return res;
	}

	/**
	 * 通过字节数组解码图片<br>
	 * 按照图片自身宽高解码为bitmap
	 * @param stream 字节数组
	 * @param format 图片格式
	 * @return 解码后的bitmap
	 */
	public Bitmap decodeTPG(byte[] stream, int format) {
		Bitmap bm = null;

		TPGFeature info = new TPGFeature();

		int res = ParseHeader(stream, info);

		if (res != TPG_STATUS_OK) {
			return null;
		}
		mhDec = CreateDecoder(stream);

		if (mhDec == 0) {
			return null;
		}

		imageWidth = info.width;
		imageHeight = info.height;

		bm = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888);

		int delayTime = 0;
		if (DecodeImageToBitmap(mhDec, stream, 0, bm, delayTime) > 0) {

		}

		CloseDecoder(mhDec);
		mhDec = 0;

		return bm;
	}

	/**
	 * 通过字节数组解码图片<br>
	 * 可以指定宽度，用来等比例获取比原图宽高小的bitmap
	 * @param stream 字节数组
	 * @param format 图片格式
	 * @return 解码后的bitmap
	 */
	public Bitmap decodeTPG(byte[] stream, int format, int dstWidth) {
		Bitmap bm = null;

		int dstHeight = 0;

		TPGFeature info = new TPGFeature();

		int res = ParseHeader(stream, info);

		if (res != TPG_STATUS_OK) {
			return null;
		}
		mhDec = CreateDecoder(stream);

		if (mhDec == 0) {
			return null;
		}

		imageWidth = info.width;
		imageHeight = info.height;

		dstHeight = (int) ((double) imageHeight / (double) imageWidth * dstWidth);

		if (dstWidth > imageWidth || dstHeight > imageHeight) {
			dstWidth = imageWidth;
			dstHeight = imageHeight;
		}

		bm = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);

		int delayTime = 0;
		if (DecodeImageToBitmap(mhDec, stream, 0, bm, delayTime) > 0) {

		}

		CloseDecoder(mhDec);
		mhDec = 0;

		return bm;
	}

	public Bitmap decodeTPG2(String tpgPath, int format, int dstWidth) {
		Bitmap bm = null;

		int dstHeight = 0;

		TPGFeature info = new TPGFeature();

		int res = ParseHeader2(tpgPath, info);

		if (res != TPG_STATUS_OK) {
			return null;
		}
		mhDec = CreateDecoder2(tpgPath);

		if (mhDec == 0) {
			return null;
		}

		imageWidth = info.width;
		imageHeight = info.height;

		dstHeight = (int) ((double) imageHeight / (double) imageWidth * dstWidth);

		if (dstWidth > imageWidth || dstHeight > imageHeight) {
			dstWidth = imageWidth;
			dstHeight = imageHeight;
		}
		// System.out.println("createBitmap start!" + imageWidth*imageHeight*4);
		bm = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
		// System.out.println("createBitmap end!");
		if (null == bm) {
			System.out.println("no memory!");
		}
		int delayTime = 0;
		if (DecodeImageToBitmap2(mhDec, 0, bm, delayTime) > 0) {
			System.out.println("decode image error!");
		}

		CloseDecoder2(mhDec);
		mhDec = 0;

		return bm;
	}

	public Bitmap decodeTPG2PNG(byte[] stream, int format, int dstWidth) {
		Bitmap bm = null;

		int dstHeight = 0;

		TPGFeature info = new TPGFeature();

		int res = ParseHeader(stream, info);

		if (res != TPG_STATUS_OK) {
			return null;
		}

		int hDec = CreateDecoder(stream);

		if (hDec == 0) {
			return null;
		}

		imageWidth = info.width;
		imageHeight = info.height;

		dstHeight = (int) ((double) imageHeight / (double) imageWidth * dstWidth);

		if (dstWidth > imageWidth || dstHeight > imageHeight) {
			dstWidth = imageWidth;
			dstHeight = imageHeight;
		}
		int[] outData = new int[dstWidth * dstHeight];
		// bm = Bitmap.createBitmap(dstWidth, dstHeight,
		// Bitmap.Config.ARGB_8888);

		TPGOutFrame tpgOutFrame = new TPGOutFrame();
		tpgOutFrame.pOutBuf = outData;
		tpgOutFrame.dstWidth = dstWidth;
		tpgOutFrame.dstHeight = dstHeight;
		tpgOutFrame.fmt = format;

		if (DecodeImage(hDec, stream, 0, tpgOutFrame) > 0) {
		}

		bm = Bitmap.createBitmap(outData, 0, dstWidth, dstWidth, dstHeight,
				Bitmap.Config.ARGB_8888);

		CloseDecoder(hDec);

		return bm;
	}

	//---------------------------native start---------------------------
	// interface using bitstream
	private native int CreateDecoder(byte[] pStream);

	private native int DecodeImage(int hObj, byte[] pStream, int index,
			TPGOutFrame tpgOutFrame);

	private native int DecodeImageToBitmap(int hObj, byte[] pStream, int index,
                                           Bitmap bitmap, Integer delayTime);

	private native void CloseDecoder(int hObj);

	private native int ParseHeader(byte[] pStream, TPGFeature info);

	private native int GetDelayTime(int hObj, byte[] pStream, int index);

	private native byte[] GetAdditionalInfo(int hObj, byte[] pStream, int id);

	// interface using file path as input
	private native int CreateDecoder2(String tpg_path);

	private native int DecodeImage2(int hObj, int index, TPGOutFrame tpgOutFrame);

	private native int DecodeImageToBitmap2(int hObj, int index, Bitmap bitmap,
			Integer delayTime);

	private native void CloseDecoder2(int hObj);

	private native int ParseHeader2(String tpgpath, TPGFeature info);

	private native int GetDelayTime2(int hObj, int index);

	private native byte[] GetAdditionalInfo2(int hObj, int id);

	private native int GetVersion();
	//------------------------------native end-----------------------------

	static {
		try {
			Log.v("cxx", "loadLibrary[00] TPGDecoder");
			System.loadLibrary("TPGDecoder");
			Log.v("cxx", "loadLibrary[99] TPGDecoder");
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
	}
}
