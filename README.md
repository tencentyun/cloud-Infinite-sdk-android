# CloudInfinite sdk for android

### 相关资源

* SDK源码以及demo 请参考： [CloudInfinite Android SDK](https://github.com/tencentyun/cloud-Infinite-sdk-android.git)
* SDK接口与参数文档请参考： [数据万象 SDK API](https://cloud.tencent.com/document/product/460/36540)
* 云闪图片分发介绍请参考：[云闪图片分发](https://cloud.tencent.com/solution/image-delivery)
* TPG功能介绍：[TPG](https://cloud.tencent.com/document/product/460/43680)
* SDK更新日志请参考：[更新日志](#更新日志)

## 文档概览

* [快速入门](#1)
* [图片转换](#2)
* [加载TPG图片](#3)
* [与Glide配合使用](#4)


<div id="1">
</div>

## 快速入门

为了开发者更方便快速地使用数据万象的基础图片处理功能和 CDN 的云闪图片分发功能，我们提供以下4个模块，开发者可根据具体需求进行选择。

|模块|功能
|:--:|:--
|cloud-infinite（默认模块）|该模块包含数据万象的基础图片处理操作，并支持各个操作能够相互组合，构建 URL 进行网络请求。
|cloud-infinite-loader |使用 CIImageLoadRequest 实例，请求网络图片并返回图片 data 数据。
|TPG|解码 TPG 格式图片并显示，可用于显示普通图片，也可用于 TPG 图。
|cloud-infinite-glide|依赖于 glide、cloud-infinite 模块，提供了数据万象基础图片处理功能。


#### 步骤1：安装 cloud-infinite SDK

cloud-infinite 模块主要功能如下：

- 包含数据万象的基础图片处理操作，分别有图片缩放、剪裁、旋转、格式转换、质量变换、高斯模糊、锐化、水印、获取图片主题色等。
- 将每一个操作封装为一个 CITransform，而且支持各个基础操作可以组合使用，并将组合好的操作集合构建出可以直接进行网络请求的 URL。

**使用 Gradle 集成**

```
 implementation 'com.tencent.qcloud:cloud-infinite:1.2.0'			
```

#### 步骤2：开始使用

**构建 CIImageLoadRequest 实例**
1. 在使用 CloudInfinite 构建具有数据万象功能的图片 URL 时，首先需要实例化 CloudInfinite 类。
```
CloudInfinite cloudInfinite = new CloudInfinite();
```
2. 实例化图片转换类 CITransformation，并设置相关操作，这里以 TPG 为例。更多功能请参见 [基础图片处理](https://cloud.tencent.com/document/product/460/47736)。
```
CITransformation transform = new CITransformation();
transform.format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeUrlFooter)
```
3. 使用 CloudInfinite 实例构建具有万象功能的图片 URL。
 - 同步方式构建
```
CIImageLoadRequest request = cloudInfinite.requestWithBaseUrlSync(objectUrl, transform);
// 图片url
URL imageURL = request.getUrl();
// header参数
Map<String, List<String>> heaers = request.getHeaders();
```
 - 异步方式构建
```
cloudInfinite.requestWithBaseUrl(objectUrl, transform, new CloudInfiniteCallback() {
        @Override
        public void onImageLoadRequest(@NonNull final CIImageLoadRequest request) {
            // 图片url
            URL imageURL = request.getUrl();
            // header参数
            Map<String, List<String>> heaers = request.getHeaders();
        }
    });
```

>?仅在进行图片格式转换并且 options 设置为 LoadTypeAcceptHeader 时，CIImageLoadRequest 的 header 参数为有效值。

#### 步骤3：加载图片

构建成功 CIImageLoadRequest 实例后，使用 cloud-infinite-loader SDK 进行图片加载。
1. 集成 cloud-infinite-loader SDK。
```
implementation 'com.tencent.qcloud:cloud-infinite-loader:1.2.0'
```
2. 加入 INTERNET 权限。
```
<uses-permission android:name="android.permission.INTERNET" />
```
3. 加载图片。
 - 直接加载 ImageView
```
CIImageLoader ciImageLoader = new CIImageLoader();
ciImageLoader.display(request, imageview);
```
 - 请求得到图片数据
```
CIImageLoader ciImageLoader = new CIImageLoader();
ciImageLoader.loadData(request, new CIImageLoadDataCallBack() {
    @Override
    public void onLoadData(byte[] bytes) {
        //伪代码给 imageview 设置 bytes 数据
        //imageview.setBytes(bytes);
    }

    @Override
    public void onFailure(QCloudClientException clientException, QCloudServiceException serviceException) {

    }
});
```


<div id="2">
</div>


## 图片转换

数据万象提供两种方式进行图片处理：图片自适应处理 ResponsiveTransformation 和自定义转换处理 CITransformation。

>?
>体积限制：处理图片原图大小不超过20MB、宽高不超过30000像素且总像素不超过1亿像素，处理结果图宽高设置不超过9999像素；针对动图，原图宽 x 高 x 帧数不超过1亿像素。

### 图片自适应处理 ResponsiveTransformation

图片自适应处理包括了格式转换（根据系统版本自动变换格式）和缩放（根据图片控件尺寸以及 ScaleType 进行缩放），相关说明如下。
1. 自适应加载 ResponsiveTransformation。
2. 根据当前 imageView 控件尺寸和 ScaleType 自动调整图片大小。
3. 根据系统版本自动调整最优图片格式，Android 4.3以下加载原格式，Android 4.3（包含4.3）到 Android 10.0（不包含10.0）加载 WEBP 格式，Android 10.0及以上加载 HEIC 格式。
```
    ResponsiveTransformation transformation = new ResponsiveTransformation(imageview);
```

### 自定义转换处理 CITransformation

cloud-infinite SDK 中的 CITransformation 类封装了数据万象的基础图片处理功能其中包含如下功能：

- [缩放](#ci_suofang)
- [裁剪](#ci_caijian)
- [旋转](#ci_xuanzhuan)
- [格式转换](#ci_geshizhuanhuan)
- [质量变换](#ci_zhiliangbianhuan)
- [高斯模糊](#ci_gaosimohua)
- [锐化](#ci_ruihua)
- [水印](#ci_shuiyin)
- [去除图片元信息](#ci_yuanxinxi)
- [组合操作](#ci_zuhe)

在使用数据万象基础图片处理功能时首先实例化 CITransformation 类，下面所有操作一致，不再重复说明。

```
CITransformation transform = new CITransformation();
```

<div id="ci_suofang"></div>


#### 缩放

>?API 文档请参见 [缩放功能接口](https://cloud.tencent.com/document/product/460/36540)。

##### 按百分比缩放

```plaintext
//指定图片的宽为原图的 50%，高度不变
transform.thumbnailByWidthScale(50);
//指定图片的高为原图的 50%，宽度不变
transform.thumbnailByHeightScale(50);
//指定图片的宽高为原图的 50%
transform.thumbnailByScale(50);
```

##### 指定宽高缩放

```plaintext
//指定目标图片宽度为50，高度等比压缩
transform.thumbnailByWidth(50);
//指定目标图片高度为50，宽度等比压缩
transform.thumbnailByHeight(50);
//限定缩略图的宽度和高度的最小值分别为50和50，进行等比缩放
transform.thumbnailByMinWH(50, 50);
//限定缩略图的宽度和高度的最大值分别为50和50，进行等比缩放
transform.thumbnailByMaxWH(50, 50);
//忽略原图宽高比例，指定图片宽度为50，高度为50 ，强行缩放图片，可能导致目标图片变形
transform.thumbnailByWH(50, 50);
```

##### 等比缩放

限制像素缩放，缩放后的图像，总像素数量不超过指定数量。

```
//等比缩放图片，缩放后的图像，总像素数量不超过1000
transform.thumbnailByPixel(1000);
```

<div id="ci_caijian"></div>


#### 裁剪

>?API 文档请参见 [裁剪功能接口](https://cloud.tencent.com/document/product/460/36541)。

##### 普通裁剪

```plaintext
// 指定目标图片宽度、高度、相对于图片左上顶点水平向右偏移、相对于图片左上顶点水平向下偏移进行裁剪
transform.cut(100, 300, 30, 30);
```

##### 内切圆裁剪

radius 是内切圆的半径，取值范围为大于0且小于原图最小边一半的整数。内切圆的圆心为图片的中心。图片格式为 GIF 时，不支持该操作。

```
// 指定半径100为例
transform.iradius(100);
```

##### 圆角裁剪

radius 为图片圆角边缘的半径，取值范围为大于0且小于原图最小边一半的整数。圆角与原图边缘相切。图片格式为 GIF 时，不支持该操作。

```
// 指定圆角半径100为例
transform.rradius(100);
```

##### 缩放裁剪 

>?方位图说明，请参见 [gravity 介绍](https://cloud.tencent.com/document/product/460/36541#.E4.B9.9D.E5.AE.AB.E6.A0.BC.E6.96.B9.E4.BD.8D.E5.9B.BE)。

```
//按照指定目标宽度和高度进行缩放裁剪
transform.cropByWH(100, 100);
//按照指定目标宽度和高度进行缩放裁剪，指定操作的起点位置
transform.cropByWH(100, 100, CIGravity.CENTER);
//按照指定目标宽度进行缩放裁剪
transform.cropByWidth(100);
//按照指定目标宽度进行缩放裁剪，指定操作的起点位置
transform.cropByWidth(100, CIGravity.CENTER);
//按照指定目标高度进行缩放裁剪
transform.cropByHeight(100);
//按照指定目标高度进行缩放裁剪，指定操作的起点位置
transform.cropByHeight(100, CIGravity.CENTER);
```

##### 人脸智能裁剪

基于图片中的人脸位置进行缩放裁剪。目标图片的宽度为 Width、高度为 Height。

``` 
// 裁剪人脸并宽高指定100缩放为例；
transform.scrop(100, 100);
```

<div id="ci_xuanzhuan"></div>


#### 旋转

>?API 文档请参见 [旋转功能接口](https://cloud.tencent.com/document/product/460/36542)。

##### 普通旋转

图片顺时针旋转角度，取值范围0 - 360 ，默认不旋转。

```
// 以旋转45度为例；
transform.rotate(45);
```

##### 自适应旋转

根据原图 EXIF 信息将图片自适应旋转回正。

```
transform.rotate();
```

<div id="ci_geshizhuanhuan"></div>


#### 格式转换

>?API 文档请参见 [格式转换接口](https://cloud.tencent.com/document/product/460/36543)。

目标缩略图的图片格式可为：TPG，JPG，BMP，GIF，PNG，HEIC，YJPEG 等，其中 YJPEG 为数据万象针对 JPEG 格式进行的优化，本质为 JPG 格式；缺省为原图格式。

```
// 以转换为 JPEG 为例
 transform.format(CIImageFormat.JPEG, CIImageLoadOptions.LoadTypeUrlFooter);
```

**CIImageLoadOptions**

```
// 加载类型一 带 accept 头部 accept:image/xxx
LoadTypeAcceptHeader,

// 加载类型二 在URL后面拼接 imageMogr2/format/xxx
LoadTypeUrlFooter,
```

>?
>
>- 使用 WEBP 格式，需要在 Android 4.3及以上。
>- 使用 HEIC 格式，需要在 Android 10及以上。
>- 不支持将 GIF 格式图片转换为 HEIF 格式。
>- 当指定为 LoadTypeAcceptHeader 方式传参时，并且组合了其他的转换则 header 失效，并且在 sdk 内部自动转换为 footer 的方式。

在使用图片格式转换时，如果需要转为 TPG 格式，则需要依赖 tpg SDK，如下所示。
```
    implementation 'com.tencent.qcloud:tpg:1.2.0'	
```

使用 TPG 时，安装时会自动包含 SO 库，建议在 Module 的 build.gradle 文件中使用 NDK 的“abiFilter”配置，设置支持的 SO 库架构。

```
defaultConfig {
    ndk {
       // 设置支持的 SO 库架构
        abiFilters 'armeabi' //, 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
    }
}
```

##### GIF 格式优化

只针对原图为 GIF 格式，对 GIF 图片格式进行的优化，降帧降颜色。
    FrameNumber=1，则按照默认帧数30处理，如果图片帧数大于该帧数则截取。
    FrameNumber 取值( 1,100 ]，则将图片压缩到指定帧数 （FrameNumber）。

```
transform.gifOptimization(50);
```

##### 输出渐进式 JPG 格式

该操作仅在输出图片格式为 JPG 格式时有效。如果输出非 JPG 图片格式，会忽略该操作。

```
transform.jpegInterlaceMode(true);
```

<div id="ci_zhiliangbianhuan"></div>


#### 质量变换


数据万象提供三种变换类型：绝对变换、相对变换、最低质量变换，可调节图片质量。API 文档请参见 [质量变换接口](https://cloud.tencent.com/document/product/460/36544)。


```
//设置图片的绝对质量，取值范围0 - 100 ，默认值为原图质量；取原图质量和指定质量的最小值
transform.quality(90);
// 图片的最低质量，取值范围0 - 100 ，设置结果图的质量参数最小值。
// 例如原图质量为85，将 lquality 设置为80后，处理结果图的图片质量为85。
// 例如原图质量为60，将 lquality 设置为80后，处理结果图的图片质量会被提升至80
transform.lowestQuality(90);
//设置图片的相对质量，取值范围0 - 100 ，数值以原图质量为标准。例如原图质量为80，将 rquality 设置为80后，得到处理结果图的图片质量为64（80x80%）
transform.relativelyQuality(80);
```

>?仅适用于 JPG 和 WEBP 格式的图片。

<div id="ci_gaosimohua"></div>


#### 高斯模糊

高斯模糊功能，模糊半径，取值范围为1 - 50；正态分布的标准差，必须大于0。API 文档请参见 [高斯模糊接口](https://cloud.tencent.com/document/product/460/36545)。


```
// 模糊半径20，正态分布的标准差20为例
transform.blur(20, 20);
```

>?图片格式为 GIF 时，不支持该操作。

<div id="ci_ruihua"><div>


#### 锐化


图片锐化功能，value 为锐化参数值，取值范围为10 - 300间的整数，推荐使用70。参数值越大，锐化效果越明显。API 文档请参见 [锐化接口](https://cloud.tencent.com/document/product/460/36546)。

```
// 以锐化值70为例
transform.sharpen(70);
```

<div id="ci_shuiyin"></div>


#### 水印

>?
>
>- API 文档请参见 [图片水印接口](https://cloud.tencent.com/document/product/460/6930) 和 [文字水印接口](https://cloud.tencent.com/document/product/460/6951)。
>- 方位图说明，请参见 [gravity](https://cloud.tencent.com/document/product/460/6951#1)。

##### 图片水印

```
WatermarkImageTransform watermarkImageTransform = new WatermarkImageTransform
        //水印图片地址
        .Builder(imageurl)
        //设置水印九宫格位置
        .setGravity(CIGravity.CENTER)
        //设置水印图适配功能，适用于水印图尺寸过大的场景（如水印墙）
        .setBlogo(1)
        //设置水平（横轴）边距
        .setDistanceX(10)
        //设置垂直（纵轴）边距
        .setDistanceY(10)
        .builder();
transform.watermarkImage(watermarkImageTransform);
```

>?指定的水印图片必须同时满足如下3个条件：
>1. 水印图片与源图片必须位于同一个存储桶下。
>2. URL 需使用 COS 域名（不能使用 CDN 加速域名，例如 examplebucket-1250000000.file.myqcloud.com/shuiyin_2.png 不可用 ），且需保证水印图可访问。如果水印图读取权限为私有，则需要携带有效签名）。
>3. URL 必须以`http://`开始，不能省略 HTTP 头，也不能填 HTTPS 头，例如 `examplebucket-1250000000.cos.ap-shanghai.myqcloud.com/shuiyin_2.png`，`https://examplebucket-1250000000.cos.ap-shanghai.myqcloud.com/shuiyin_2.png` 为非法的水印 URL。

##### 文字水印

```
WatermarkTextTransform watermarkImageTransform = new WatermarkTextTransform
        //水印内容
        .Builder("水印")
        //设置水印九宫格位置
        .setGravity(CIGravity.CENTER)
        //设置水印字体
        .setFont("tahoma.ttf")
        //设置水印文字字体大小
        .setFontSize(13)
        //设置水印字体颜色
        .setFill("#FF0000")
        //设置水印文字透明度
        .setDissolve(80)
        //设置水平（横轴）边距
        .setDistanceX(10)
        //设置垂直（纵轴）边距
        .setDistanceY(10)
        //打开平铺水印功能，可将文字水印平铺至整张图片
        .openBatch()
        //旋转水印
        .rotate(45)
        .builder();
transform.watermarkText(watermarkImageTransform);
```

<div id="ci_yuanxinxi"></div>


#### 去除图片元信息

腾讯云数据万象通过 imageMogr2 接口可去除图片元信息，包括 EXIF 信息。API 文档请参见 [去除图片元信息接口](https://cloud.tencent.com/document/product/460/36547)。

```
transform.strip();
```

<div id="ci_zuhe"></div>


#### 组合操作

Transform 支持多操作组合，并保持有序。

```
transform
        .thumbnailByScale(80)
        .iradius(100)
        .blur(20,20)
        .strip();
```

<div id="3">
</div>

## 加载TPG图片

### 安装 TPG SDK

```
implementation 'com.tencent.qcloud:tpg:1.2.0'    
```

安装时会自动包含 SO 库，建议在 Module 的 build.gradle 文件中使用 NDK 的“abiFilter”配置，设置支持的 SO 库架构。

```
defaultConfig {
    ndk {
        // 设置支持的 SO 库架构
        abiFilters 'armeabi' //, 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
    }
}
```

### 方式一：加载网络 TPG 图片

1. 集成 cloud-infinite SDK。
```
implementation 'com.tencent.qcloud:cloud-infinite:1.2.0'    
```
2. 在 cloud-infinite SDK 中构建出请求 TPG 格式图片的链接，然后与 [Glide 加载图片](https://cloud.tencent.com/document/product/460/47738)  配合使用，加载网络 TPG 图片。
```
// 实例化 CloudInfinite，用来构建请求图片请求连接；
CloudInfinite cloudInfinite = new CloudInfinite();
// 根据用户所选万象基础功能 options 进行 Transformation；
CITransformation transform = new CITransformation();
transform.format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeAcceptHeader);
// 构建图片 CIImageLoadRequest
CIImageLoadRequest request = cloudInfinite.requestWithBaseUrlSync(url, transform);
```

### 方式二：加载本地 TPG 图片

应用内置的资源，例如 assets、drawable、raw 等，使用 TPG 格式可以有利于减小安装包大小。

```
//加载 Assets 中的 TPG 图片
TpgImageLoader.displayWithAssets(imageview, assetsName);
//加载 Resource 中的 TPG 图片
TpgImageLoader.displayWithResource(imageview, R.drawable.tpg);
//加载本地文件中的 TPG 图片
TpgImageLoader.displayWithFileUri(imageview, fileUri);
```


<div id="4">
</div>

## 与Glide配合使用

### 安装 Glide

```
implementation 'com.github.bumptech.glide:glide:version'
```

### 基础图片处理

与 Glide 配合使用数据万象基础图片处理操作（除 TPG 相关功能外）。

1. 使用 CloudInfinite 和 CITransformation 构建 CIImageLoadRequest。
```
CloudInfinite cloudInfinite = new CloudInfinite();
CITransformation transform = new CITransformation();
transform.thumbnailByScale(50).iradius(60);
CIImageLoadRequest request = cloudInfinite.requestWithBaseUrlSync(url, transform);
```
2. 通过得到的 CIImageLoadRequest 获取 URL，使用 Glide 进行加载。
```
Glide.with(activity).load(request.getUrl().toString()).into(imageview);
```

### 使用数据万象 TPG 功能

安装 TPG SDK 和 cloud-infinite-glide SDK 以及 glide:compiler。
```
implementation 'com.tencent.qcloud:tpg:1.2.0'	
implementation 'com.tencent.qcloud:cloud-infinite-glide:1.2.0'	
annotationProcessor 'com.github.bumptech.glide:compiler:version' 
```
通过 AppGlideModule 注册相关解码器和 loader 实现相应功能。
```
 // 注册自定义 GlideModule
 // 开发者应该创建此类注册 CIImageRequestModelLoader 和 TpgDecoder、ByteBufferTpgGifDecoder<br>
 // 类库开发者可以继承 LibraryGlideModule 创建类似的注册类
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, Registry registry) {
        //注册支持 CIImageLoadRequest 的 loader
        registry.prepend(CIImageLoadRequest.class, InputStream.class, new CIImageRequestModelLoader.Factory());

        /*------------------解码器 开始-------------------------*/
        //注册 TPG 静态图片解码器
        registry.prepend(InputStream.class, Bitmap.class, new TpgDecoder(glide.getBitmapPool()));
        //注册 TPG 动图解码器
        ByteBufferTpgGifDecoder byteBufferTpgGifDecoder = new ByteBufferTpgGifDecoder(context, glide.getBitmapPool());
        registry.prepend(InputStream.class, GifDrawable.class, new StreamTpgGifDecoder(byteBufferTpgGifDecoder));
        registry.prepend(ByteBuffer.class, GifDrawable.class, byteBufferTpgGifDecoder);
        //注册主色解码器
        registry.prepend(InputStream.class, Bitmap.class, new ImageAveDecoder(glide.getBitmapPool()));
        /*------------------解码器 结束-------------------------*/
    }
}
```

#### 加载静态 TPG 图片

```
registry.prepend(InputStream.class, Bitmap.class, new TpgDecoder(glide.getBitmapPool()));
```

#### 加载动图 GIF 类型 TPG 图片

```
ByteBufferTpgGifDecoder byteBufferTpgGifDecoder = new ByteBufferTpgGifDecoder(context, glide.getBitmapPool());
registry.prepend(InputStream.class, GifDrawable.class, new StreamTpgGifDecoder(byteBufferTpgGifDecoder));
registry.prepend(ByteBuffer.class, GifDrawable.class, byteBufferTpgGifDecoder);
```

#### 使用图片主色预加载

```
//先在 AppGlideModule 中注册主色解码器
registry.prepend(InputStream.class, Bitmap.class, new ImageAveDecoder(glide.getBitmapPool()));

//使用 glide 的 thumbnail 进行主色预加载
Glide.with(context)
               .load(imageUrl)
               .thumbnail(CloudInfiniteGlide.getImageAveThumbnail(context, imageUrl))
               .into(imageview);
```

#### 直接加载 CIImageLoadRequest

目前仅用于格式转换时，header 传输目标图片格式的情况。

```
registry.prepend(CIImageLoadRequest.class, InputStream.class, new CIImageRequestModelLoader.Factory());
```



<div id="更新日志"></div>

## 更新日志

#### tpg Version 1.3.0
##### 2020-08-24
优化TPG解码内存占用

#### Version 1.2.0
##### 2020-08-22
支持数据万象图片基础操作

#### tpg-glide Version 1.1.0
##### 2020-08-05
Glide支持GIF动图格式TPG

#### tpg Version 1.0.1
##### 2020-07-30
TPG解码支持armeabi-v7a、arm64-v8a、x86、x86_64

#### Version 1.0.0
##### 2020-07-29
首次发布<br/>TPG图片加载功能

## 示例
完整示例请参考QCloudInfiniteSample示例工程

![](./screenshot/sample.png)

## 感谢
使用过程中如果您遇到了问题或者有更好的建议欢迎提 issue以及pull request

## License
~~~
Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
~~~



