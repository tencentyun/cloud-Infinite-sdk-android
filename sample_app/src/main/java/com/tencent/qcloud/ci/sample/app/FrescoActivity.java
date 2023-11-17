package com.tencent.qcloud.ci.sample.app;

import com.tencent.qcloud.ci.sample.app.base.BaseActivity;

/**
 * <p>
 * Created by jordanqin on 2023/5/17 14:38.
 * Copyright 2010-2020 Tencent Cloud. All Rights Reserved.
 */
public class FrescoActivity extends BaseActivity {
//    private static final String TAG = "TpgFrescoOriginalRetry";
//
//    private SimpleDraweeView simpleDraweeView;
//    private EditText et_image_url;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tpg_fresco_original_image);
//
//        simpleDraweeView = findViewById(R.id.imageView);
//        et_image_url = findViewById(R.id.et_image_url);
//
////        String test = "https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default01.jpg?imageMogr2/format/avif/thumbnail/150x50/quality/30";
//        String test = "https://mobile-ut-1253960454.cos.ap-guangzhou.myqcloud.com/ci_image_sdk/default01.jpg?imageMogr2/format/tpg|imageMogr2/thumbnail/150x50|imageMogr2/quality/30";
////        String test = "https://i4.hoopchina.com.cn/hupuapp/bbs/111111/thread_111111_20220527134517_s_10151_w_196_h_172_66941.jpg?imageMogr2/format/avif/thumbnail/450x%3E/quality/30/ignore-error/1";
//        et_image_url.setText(test);
//
//        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadImg();
//            }
//        });
//    }
//
//    private void loadImg(){
//        String imgUrl = et_image_url.getText().toString();
//        Log.d(TAG, "原始图片URL："+imgUrl);
//
//        // 创建一个GenericDraweeHierarchy对象
//        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
//                // 设置占位图片
//                .setPlaceholderImage(R.drawable.ic_launcher_background)
//                // 设置错误图片
//                .setFailureImage(R.drawable.ic_launcher_background)
//                .build();
//        simpleDraweeView.setHierarchy(hierarchy);
//
//        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imgUrl))
//                .disableDiskCache()
//                .disableMemoryCache();
//        ImageRequest request = imageRequestBuilder.build();
//
//        BaseControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>() {
//            @Override
//            public void onFinalImageSet(
//                    String id,
//                    @Nullable ImageInfo imageInfo,
//                    @Nullable Animatable anim) {
//                // 其他业务onFinalImageSet
//            }
//
//            @Override
//            public void onFailure(String id, Throwable throwable) {
//                // 其他业务onFailure
//            }
//        };
//
//        PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder()
//                .setOldController(simpleDraweeView.getController())
//                .setControllerListener(controllerListener)
//                .setAutoPlayAnimations(true)
//                .setImageRequest(request);
//
//        // 原图兜底监听器
//        OriginalImageRetryControllerListener originalImageRetry = new OriginalImageRetryControllerListener(simpleDraweeView, imageRequestBuilder, draweeControllerBuilder);
//        originalImageRetry.setOriginalImageRetryCallback(new FrescoOriginalImageRetryCallback() {
//            @Nullable
//            @Override
//            public String buildOriginalImageUrl(String urlStr) {
//                // 使用默认的原图格式
//                return null;
//            }
//
//            @Override
//            public void onFailureBeforeRetry(Uri uri, String id, Throwable throwable) {
//                // tpg加载失败在这里上报，统计原图兜底次数和tpg解码异常信息(不影响真正的图片加载失败率)
//                Log.d(TAG, "TPG onLoadFailed："+ uri.toString());
//                if (throwable != null) {
//                    Log.d(TAG, throwable.getMessage());
//                    throwable.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Uri uri, String id, Throwable throwable) {
//                //真正的加载失败在这里上报(影响真正的图片加载成功失败率)
//                Log.d(TAG, "Image onLoadFailed："+ uri.toString());
//                if (throwable != null) {
//                    Log.d(TAG, throwable.getMessage());
//                    throwable.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFinalImageSet(Uri uri, String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
//                //真正的加载成功在这里上报(影响真正的图片加载成功失败率)
//                Log.d(TAG, "Image onLoadSuccess："+ uri.toString());
//            }
//        });
//
//        PipelineDraweeController controller = (PipelineDraweeController) draweeControllerBuilder.build();
//        controller.addControllerListener(originalImageRetry);
//        simpleDraweeView.setController(controller);
//    }
}
