# tpgimage
**TPG图片封装，方便用户直接使用TPG图片**
~~~
 implementation 'com.tencent.qcloud::tpg:1.0.1'			             	              
~~~
使用tpg时，安装时会自动包含SO库，建议在Module的build.gradle文件中使用NDK的“abiFilter”配置，设置支持的SO库架构
~~~
defaultConfig {
    ndk {
        // 设置支持的SO库架构
        abiFilters 'armeabi' //, 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
    }
~~~

<br/>

assets、drawable、row使用TPG图片有利于减小安装包大小
~~~
//加载Assets中TPG图片
TpgImageLoader.displayWithAssets(imageview, assetsName);
//加载Resource中TPG图片
TpgImageLoader.displayWithResource(imageview, R.drawable.tpg);
//加载本地文件中的TPG图片
TpgImageLoader.displayWithFileUri(imageview, fileUri);
~~~