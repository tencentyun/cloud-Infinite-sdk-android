package com.tencent.tpg;

public class Utils {

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

}
