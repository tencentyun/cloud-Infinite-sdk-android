package com.tencent.tpg;

import android.graphics.Bitmap;
import android.util.Log;

import static com.tencent.tpg.Utils.TPG_STATUS_OK;

/**
 * TPG解码器<br>
 * XXX方法使用byte[]作为数据源<br>
 * XXX2方法使用文件路径作为数据源
 */
public class TPGDecoder {
	public static final String TAG = "TPGDemo";
	public class TPGFeature {
		int width;
		int height;
		int headerSize;
		int imageMode;
		int frameCount;
		int version;
	}

	public class TPGOutFrame {
		int[] pOutBuf;
		int bufsize;
		int dstWidth;
		int dstHeight;
		int fmt;
		int delayTime;

	}

	public class TPGVersionNum {
		protected int num;
		protected String str;
	}

	private int imageWidth;
	private int imageHeight;
	private long mhDec;
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

	public int getVersion(TPGVersionNum hObj) {
		return GetVersion(hObj);
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
			return Utils.TPG_STATUS_INVALID_PARAM;
		}
		return TPG_STATUS_OK;
	}

	public int startDecode2(String path) {
		mhDec = CreateDecoder2(path);

		if (mhDec == 0) {
			return Utils.TPG_STATUS_INVALID_PARAM;
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
		tpgOutFrame.fmt = Utils.FORMAT_BGRA;
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
		Log.e(TAG, "iamge width: " + mFeatureInfo.width + ", image height: " + mFeatureInfo.height);
		tpgOutFrame.fmt = Utils.FORMAT_BGRA;
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
	 * @param dstWidth 目标宽度
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
        Log.e(TAG, "image width: " + imageWidth + ", image height: " + imageHeight);
		dstHeight = (int) ((double) imageHeight / (double) imageWidth * dstWidth);

		if (dstWidth > imageWidth || dstHeight > imageHeight) {
			dstWidth = imageWidth;
			dstHeight = imageHeight;
		}
		// System.out.println("createBitmap start!" + imageWidth*imageHeight*4);
		bm = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
		if(info.imageMode== Utils.IMAGE_MODE_Normal)
		{
			bm.setHasAlpha(false);
		}

		// System.out.println("createBitmap end!");
		if (null == bm) {
			System.out.println("no memory!");
		}
		int delayTime = 0;
		if (DecodeImageToBitmap2(mhDec, 0, bm, delayTime) > 0) {
			System.out.println("decode image error!");
			Log.e(TAG, "decode image error!!");
		}


		//TPGVersionNum foo = new TPGVersionNum();

		//GetVersion(foo);
		//System.out.println("version num:"+foo.num+"version str:"+foo.str);
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

		long hDec = CreateDecoder(stream);

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

	// interface using bitstream
	private native long CreateDecoder(byte[] pStream);

	private native int DecodeImage(long hObj, byte[] pStream, int index,
			TPGOutFrame tpgOutFrame);

	private native int DecodeImageToBitmap(long hObj, byte[] pStream, int index,
			Bitmap bitmap, Integer delayTime);

	private native void CloseDecoder(long hObj);

	private native int ParseHeader(byte[] pStream, TPGFeature info);

	private native int GetDelayTime(long hObj, byte[] pStream, int index);

	private native byte[] GetAdditionalInfo(long hObj, byte[] pStream, int id);

	// interface using file path as input
	private native long CreateDecoder2(String tpg_path);

	private native int DecodeImage2(long hObj, int index, TPGOutFrame tpgOutFrame);

	private native int DecodeImageToBitmap2(long hObj, int index, Bitmap bitmap,
			Integer delayTime);

	private native void CloseDecoder2(long hObj);

	private native int ParseHeader2(String tpgpath, TPGFeature info);

	private native int GetDelayTime2(long hObj, int index);

	private native byte[] GetAdditionalInfo2(long hObj, int id);

	private native int GetVersion(TPGVersionNum hObj);

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
