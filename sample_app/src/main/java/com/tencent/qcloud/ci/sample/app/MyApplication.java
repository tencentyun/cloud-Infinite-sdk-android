package com.tencent.qcloud.ci.sample.app;

import android.app.Application;
import android.content.Context;

/**
 * <p>
 * Created by jordanqin on 2023/2/24 11:16.
 * Copyright 2010-2020 Tencent Cloud. All Rights Reserved.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        frescoInit(this);
    }

    private void frescoInit(Context context) {
//        // 解码器配置
//        ImageDecoderConfig imageDecoderConfig = new ImageDecoderConfig.Builder()
//                .addDecodingCapability(
//                        TpgFormatChecker.TPG,
//                        new TpgFormatChecker(),
//                        new FrescoTpgDecoder())
//                .addDecodingCapability(
//                        TpgAnimatedFormatChecker.TPGAnimated,
//                        new TpgAnimatedFormatChecker(),
//                        new FrescoTpgAnimatedDecoder())
//                // 配置 AVIF 静态解码器
////                .addDecodingCapability(
////                        AvifFormatChecker.AVIF,
////                        new AvifFormatChecker(),
////                        new FrescoAvifDecoder())
////                // 配置 AVIF 动图解码器
////                .addDecodingCapability(
////                        AvisFormatChecker.AVIS,
////                        new AvisFormatChecker(),
////                        new FrescoAvisDecoder())
//                .build();
//
//        ImagePipelineConfig config = QCloudImagePipelineConfigFactory
//                .newBuilder(context)
//                .setImageDecoderConfig(imageDecoderConfig)
//                .build();
//
//        // 初始化 Fresco
//        Fresco.initialize(context, config);
    }
}
