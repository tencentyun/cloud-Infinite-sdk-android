# tpgimage_glide
**TPG图片的glide插件，方便在glide中直接使用TPG**

~~~
implementation 'com.tencent.qcloud::tpg-glide:1.0.0'
~~~

使用tpg\-glide时，安装时会自动包含SO库，建议在Module的build.gradle文件中使用NDK的“abiFilter”配置，设置支持的SO库架构
~~~
defaultConfig {
    ndk {
        // 设置支持的SO库架构
        abiFilters 'armeabi' //, 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
    }
~~~

<br/>

1. 创建AppGlideModule，注册CIImageRequestModelLoader和TpgDecoder
~~~
/**
 * 注册自定义GlideModule<br>
 * 开发者应该创建此类注册CIImageRequestModelLoader和TpgDecoder<br>
 * 类库开发者可以继承LibraryGlideModule创建类似的注册类
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, Registry registry) {
        //注册支持CIImageLoadRequest的loader
        registry.prepend(CIImageLoadRequest.class, InputStream.class, new CIImageRequestModelLoader.Factory());
        //注册TPG图片解码器
        registry.prepend(InputStream.class, Bitmap.class, new TpgDecoder(glide.getBitmapPool()));
    }
}
~~~

3. 使用Glide加载图片请求
~~~
//创建数据万象操作器
CloudInfinite cloudInfinite = new CloudInfinite();
//根据原始URL和要进行的操作CITransformation，生成图片加载请求CIImageLoadRequest
cloudInfinite.requestWithBaseUrl(image.url, new CITransformation().format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeAcceptHeader), neCloudInfiniteCallback() {
    @Override
    public void onImageLoadRequest(@NonNull CIImageLoadRequest request) {
        //使用glide加载CIImageLoadRequest
        Glide.with(activity).load(request).into(imageview);
    }
});
~~~