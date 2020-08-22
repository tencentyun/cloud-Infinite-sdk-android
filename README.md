# CloudInfinite sdk for android

### 相关资源

SDK源码以及demo 请参考： [CloudInfinite Android SDK](https://github.com/tencentyun/cloud-Infinite-sdk-android.git)
SDK接口与参数文档请参考： [数据万象 SDK API](https://cloud.tencent.com/document/product/460/36540)
云闪图片分发介绍请参考：[云闪图片分发](https://cloud.tencent.com/solution/image-delivery)
TPG功能介绍：[TPG](https://cloud.tencent.com/document/product/460/43680)
SDK更新日志请参考：[更新日志](#changelog)

## 文档概览

* [快速入门](#1)
* [图片转换](#2)
* [加载TPG图片](#3)
* [与Glide配合使用](#4)


<div id="1">
</div>

## 快速入门

为了使开发者更方便快速是使用数据万象和云闪图片分发功能，我们提供了4个SDK，开发者可根据具体需求进行选择。

序号|模块|功能
--:|:--:|:--
1|cloud-infinite(默认模块)|该模块包含数据万象对图片的基础操作，并支持各个操作可以相互组合，并构建URL进行网络请求；
2|cloud-infinite-loader |使用CIImageLoadRequest实例，请求网络图片并返回图片data数据；
3|tpg|解码TPG格式图片并显示；即可用于显示普通图片，也可用于TPG图；
4|cloud-infinite-glide|依赖于glide、cloud-infinite模块，提供了数据万象图片基础操作功能；


cloud-infinite SDK主要功能：
* 包含数据万象的图片基础操作，主要有：图片缩放、剪裁、旋转、格式转换、质量变换、高斯模糊、锐化、水印、获取图片主题色等；
* 将每一个操作封装为一个CITransform，而且支持各个基础操作可以组合使用，并将组合好的操作集合构建出可以直接进行网络请求的URL;

#### 第一步 安装 cloud-infinite SDK

~~~
 implementation 'com.tencent.qcloud:cloud-infinite:1.2.0'			
~~~

#### 第二步 构建CIImageLoadRequest实例

1. 在使用CloudInfinite 构建具有数据万象功能的图片url时，首先需要实例化 CloudInfinite类。

```
CloudInfinite cloudInfinite = new CloudInfinite();
```

2. 实例化图片转换类 CITransformation，并设置相关操作，这里以TPG为例；更多功能见：[图片转换](#2)。

```
    CITransformation transform = CITransformation();
    transform.format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeUrlFooter)
```

3. 使用cloudInfinite 实例构建具有万象功能的图片url；
* 同步方式构建：

```
    CIImageLoadRequest request = cloudInfinite.requestWithBaseUrlSync(objectUrl, transform);
    // 图片url
    URL imageURL = request.getUrl();
    // header参数
    Map<String, List<String>> heaers = request.getHeaders();
```

* 异步方式构建

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
>？
> 仅在进行格式转换并且 options 设置为 LoadTypeAcceptHeader 时，CIImageLoadRequest 的 header 参数为有效值。

#### 构建成功CIImageLoadRequest 实例后，使用cloud-infinite-loader SDK进行图片加载

集成 cloud-infinite-loader SDK
~~~
implementation 'com.tencent.qcloud:cloud-infinite-loader:1.2.0'
~~~

加入INTERNET权限
~~~
<uses-permission android:name="android.permission.INTERNET" />
~~~

* 直接加载 ImageView
~~~
        CIImageLoader ciImageLoader = new CIImageLoader();
        ciImageLoader.display(request, imageview);
~~~

* 请求得到图片数据

~~~
        CIImageLoader ciImageLoader = new CIImageLoader();
        ciImageLoader.loadData(request, new CIImageLoadDataCallBack() {
            @Override
            public void onLoadData(byte[] bytes) {
                //伪代码 给imageview设置bytes数据
                //imageview.setBytes(bytes);
            }

            @Override
            public void onFailure(QCloudClientException clientExceptionQCloudServiceException serviceException) {

            }
        });
~~~


<div id="2">
</div>


## 图片转换

1. 自适应加载 ResponsiveTransformation
* 根据当前imageView控件尺寸和ScaleType自动调整图片大小；
* 根据系统版本自动调整最优图片格式，Android4.3以下加载原格式，Android4.3（包含4.3）到Android10（不包含10）加载webp格式，Android10及以上加载heic格式；

```
    ResponsiveTransformation transformation = new ResponsiveTransformation(imageview);
```

2. cloud-infinite SDK中的 CITransformation类封装了万象的图片转换功能，其中包含如下功能：


* [缩放](#ci_suofang)
* [裁剪](#ci_caijian)
* [旋转](#ci_xuanzhuan)
* [格式转换](#ci_geshizhuanhuan)
* [质量变换](#ci_zhiliangbianhuan)
* [高斯模糊](#ci_gaosimohua)
* [锐化](#ci_ruihua)
* [水印](#ci_shuiyin)
* [去除图片元信息](#ci_yuanxinxi)
* [组合操作](#ci_zuhe)


<div id="ci_suofang"><div>

### 一 缩放
相关链接：[缩放功能接口](https://cloud.tencent.com/document/product/460/36540)
在使用万象图片转换功能时首先实例化 CITransformation 类，下面所有操作一致，不再重复；
```
    CITransformation transform = new CITransformation();
```

* 图片百分比缩放

    ```
        //指定图片的宽为原图的 50%，高度不变
        transform.thumbnailByWidthScale(50);
        //指定图片的高为原图的 50%，宽度不变
        transform.thumbnailByHeightScale(50);
        //指定图片的宽高为原图的 50%
        transform.thumbnailByScale(50);
    ```

* 指定宽高缩放

    ```
        //指定目标图片宽度为 50，高度等比压缩
        transform.thumbnailByWidth(50);
        //指定目标图片高度为 50，宽度等比压缩
        transform.thumbnailByHeight(50);
        //限定缩略图的宽度和高度的最小值分别为 50 和 50，进行等比缩放
        transform.thumbnailByMinWH(50, 50);
        //限定缩略图的宽度和高度的最大值分别为 50 和 50，进行等比缩放
        transform.thumbnailByMaxWH(50, 50);
        //忽略原图宽高比例，指定图片宽度为 50，高度为 50 ，强行缩放图片，可能导致目标图片变形
        transform.thumbnailByWH(50, 50);
    ```

* 限制像素缩放-等比缩放图片，缩放后的图像，总像素数量不超过指定数量
    ```
        //等比缩放图片，缩放后的图像，总像素数量不超过 1000
        transform.thumbnailByPixel(1000);
    ```

<div id="ci_caijian"><div>

### 二 裁剪
相关链接：[裁剪功能接口](https://cloud.tencent.com/document/product/460/36541)

* 普通裁剪

    ```
        // 指定目标图片宽度、高度、相对于图片左上顶点水平向右偏移、相对于图片左上顶点水平向下偏移 进行裁剪1；
        transform.cut(100, 300, 30, 30);
    ```

* 内切圆裁剪功能，radius 是内切圆的半径，取值范围为大于0且小于原图最小边一半的整数。内切圆的圆心为图片的中心。图片格式为 gif 时，不支持该参数。
    ```
        // 指定半径为100 为例
        transform.iradius(100);
    ```
* 圆角裁剪功能，radius 为图片圆角边缘的半径，取值范围为大于0且小于原图最小边一半的整数。圆角与原图边缘相切。图片格式为 gif 时，不支持该参数。
    ```
        // 指定圆角半径 100为例
        transform.rradius(100);
    ```

* 缩放裁剪 
    相关链接：[gravity 介绍](https://cloud.tencent.com/document/product/460/36541#.E4.B9.9D.E5.AE.AB.E6.A0.BC.E6.96.B9.E4.BD.8D.E5.9B.BE)

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

* 人脸智能裁剪,基于图片中的人脸位置进行缩放裁剪。目标图片的宽度为 Width、高度为 Height。
    ``` 
        // 裁剪人脸并宽高指定100缩放为例；
        transform.scrop(100, 100);
    ```

<div id="ci_xuanzhuan"><div>

### 三 旋转
相关链接：[旋转功能接口](https://cloud.tencent.com/document/product/460/36542)

* 普通旋转：图片顺时针旋转角度，取值范围0 - 360 ，默认不旋转。
    ```
        // 以旋转45度为例；
        transform.rotate(45);
    ```

* 自适应旋转：根据原图 EXIF 信息将图片自适应旋转回正。
    ```
        transform.rotate();
    ```
    
<div id="ci_geshizhuanhuan"><div>

### 四 格式转换
相关链接：[格式转换接口](https://cloud.tencent.com/document/product/460/36543)
* 格式转换：目标缩略图的图片格式可为：tpg，jpg，bmp，gif，png，heic，yjpeg 等，其中 yjpeg 为数据万象针对 jpeg 格式进行的优化，本质为 jpg 格式；缺省为原图格式。

    ```
        // 以转为JPEG为例
         transform.format(CIImageFormat.JPEG, CIImageLoadOptions.LoadTypeUrlFooter);
    ```

    CIImageLoadOptions:
    ```
        // 加载类型一 带accept头部 accept:image/xxx
        LoadTypeAcceptHeader,
            
        // 加载类型二 在URL后面拼接 imageMogr2/format/xxx
        LoadTypeUrlFooter,
    ```

    >?
    > 使用webp格式，需要在android 4.0及以上。
    > 使用heic格式，需要在android 10及以上。

    >?
    > * 当指定为LoadTypeAcceptHeader 方式传参时，并且组合了其他的转换则header失效，并且在sdk内部自动转换为footer的方式
    > * 在使用图片格式转换时，如果需要转为TPG格式，则需要依赖 tpg SDK；
    
    ```
    implementation 'com.tencent.qcloud:tpg:1.2.0'	
    ```

    使用tpg时，安装时会自动包含SO库，建议在Module的build.gradle文件中使用NDK的“abiFilter”配置，设置支持的SO库架构
    ~~~
    defaultConfig {
        ndk {
           // 设置支持的SO库架构
            abiFilters 'armeabi' //, 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
        }
    ~~~

* gif 格式优化： 只针对原图为 gif 格式，对 gif 图片格式进行的优化，降帧降颜色。
    FrameNumber=1，则按照默认帧数30处理，如果图片帧数大于该帧数则截取。
    FrameNumber 取值( 1,100 ]，则将图片压缩到指定帧数 （FrameNumber）。

    ```
        transform.gifOptimization(50);
    ```

* 输出为渐进式 jpg 格式
Mode 可为0或1。0：表示不开启渐进式；1：表示开启渐进式。该参数仅在输出图片格式为 jpg 格式时有效。如果输出非 jpg 图片格式，会忽略该参数，默认值0。

    ```
        transform.jpegInterlaceMode(true);
    ```
<div id="ci_zhiliangbianhuan"><div>

### 五 质量变换
相关链接：[质量变换接口](https://cloud.tencent.com/document/product/460/36544)
* 图片质量进行调节，数据万象提供三种变换类型：绝对变换、相对变换、最低质量变换

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

    >？
    > 仅适用于 jpg 和 webp 格式的图片

<div id="ci_gaosimohua"><div>

### 六 高斯模糊
相关链接：[高斯模糊接口](https://cloud.tencent.com/document/product/460/36545)
* 高斯模糊功能
模糊半径，取值范围为1 - 50;
正态分布的标准差，必须大于0;
    ```
        // 模糊半径20 ,正态分布的标准差20 为例
        transform.blur(20, 20);
    ```
    >?
    > 图片格式为 gif 时，不支持该操作。

<div id="ci_ruihua"><div>

### 七 锐化
相关链接：[锐化接口](https://cloud.tencent.com/document/product/460/36546)
* 图片锐化功能，value 为锐化参数值，取值范围为10 - 300间的整数。参数值越大，锐化效果越明显。（推荐使用70）
    ```
        // 以锐化值70 为例
        transform.sharpen(70);
    ```
    
<div id="ci_shuiyin"><div>

### 八 水印

相关链接：[gravity](https://cloud.tencent.com/document/product/460/6951#1)
相关链接：[图片水印接口](https://cloud.tencent.com/document/product/460/6930)
相关链接：[文字水印接口](https://cloud.tencent.com/document/product/460/6951)

* #### 图片水印
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

    >？
    > 指定的水印图片必须同时满足如下3个条件：
    > 1、水印图片与源图片必须位于同一个存储桶下。
    > 2、URL 需使用 COS 域名（不能使用 CDN 加速域名，例如 examplebucket-1250000000.file.myqcloud.com/shuiyin_2.png 不可用 ），且需保证水印图可访问
        如果水印图读取权限为私有，则需要携带有效签名）。
    > 3、URL 必须以http://开始，不能省略 HTTP 头，也不能填 HTTPS 头，例如examplebucket-1250000000.cos.ap-shanghai.myqcloud.com/shuiyin_2.png，
        https://examplebucket-1250000000.cos.ap-shanghai.myqcloud.com/shuiyin_2.png 为非法的水印 URL。

* #### 文字水印

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

<div id="ci_yuanxinxi"><div>

### 九 去除图片元信息
相关链接：[去除图片元信息接口](https://cloud.tencent.com/document/product/460/36547)
* 腾讯云数据万象去除图片元信息，包括 exif 信息。
    ```
        transform.strip();
    ```

<div id="ci_zuhe"><div>

### 十 组合操作
* transform支持多操作组合，并保持有序。
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

安装tpg SDK
~~~
implementation 'com.tencent.qcloud:tpg:1.2.0'	
~~~

安装时会自动包含SO库，建议在Module的build.gradle文件中使用NDK的“abiFilter”配置，设置支持的SO库架构
~~~
defaultConfig {
    ndk {
        // 设置支持的SO库架构
        abiFilters 'armeabi' //, 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
    }
~~~

### 方式一 加载网络TPG图片

1. 集成 cloud-infinite SDK
    ```
    implementation 'com.tencent.qcloud:cloud-infinite:1.2.0'	
    ```

2. 在cloud-infinite SDK中构建出请求TPG格式图片的链接，然后[与Glide配合使用](#4)加载网络TPG图片

    ```
    // 实例化CloudInfinite，用来构建请求图片请求连接；
    CloudInfinite cloudInfinite = new CloudInfinite();

    // 根据用户所选万象基础功能options 进行Transformation；
    CITransformation transform = new CITransformation();
    transform.format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeAcceptHeader);

    // 构建图片CIImageLoadRequest
    CIImageLoadRequest request = cloudInfinite.requestWithBaseUrlSync(url, transform);
    ```

### 方式二 加载本地TPG图片
    assets、drawable、raw使用TPG图片有利于减小安装包大小
~~~
    //加载Assets中TPG图片
    TpgImageLoader.displayWithAssets(imageview, assetsName);
    //加载Resource中TPG图片
    TpgImageLoader.displayWithResource(imageview, R.drawable.tpg);
    //加载本地文件中的TPG图片
    TpgImageLoader.displayWithFileUri(imageview, fileUri);
~~~

<div id="4">
</div>

## 与Glide配合使用

安装glide
~~~
implementation 'com.github.bumptech.glide:glide:version'
~~~

* #### 与glide 配合使用数据万象图片基础操作（除TPG相关功能外）；

1. 使用CloudInfinite和CITransformation构建CIImageLoadRequest
    ```
        CloudInfinite cloudInfinite = new CloudInfinite();
        CITransformation transform = new CITransformation();
        transform.thumbnailByScale(50).iradius(60);
        CIImageLoadRequest request = cloudInfinite.requestWithBaseUrlSync(url, transform);
    ```

2. 通过得到的CIImageLoadRequest获取URL，使用glide进行加载

    ```
    Glide.with(activity).load(request.getUrl().toString()).into(imageview);
    ```

* ### 与glide 配合使用数据万象TPG功能；

安装tpg和cloud-infinite-glide SDK以及glide:compiler
~~~
    implementation 'com.tencent.qcloud:tpg:1.2.0'	
    implementation 'com.tencent.qcloud:cloud-infinite-glide:1.2.0'	
    annotationProcessor 'com.github.bumptech.glide:compiler:version'    
~~~

通过AppGlideModule注册相关解码器和loader实现相应功能
~~~
 // 注册自定义GlideModule
 // 开发者应该创建此类注册CIImageRequestModelLoader和TpgDecoder、ByteBufferTpgGifDecoder<br>
 // 类库开发者可以继承LibraryGlideModule创建类似的注册类
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, Registry registry) {
        //注册支持CIImageLoadRequest的loader
        registry.prepend(CIImageLoadRequest.class, InputStream.class, new CIImageRequestModelLoader.Factory());

        /*------------------解码器 开始-------------------------*/
        //注册TPG静态图片解码器
        registry.prepend(InputStream.class, Bitmap.class, new TpgDecoder(glide.getBitmapPool()));
        //注册TPG动图解码器
        ByteBufferTpgGifDecoder byteBufferTpgGifDecoder = new ByteBufferTpgGifDecoder(context, glide.getBitmapPool());
        registry.prepend(InputStream.class, GifDrawable.class, new StreamTpgGifDecoder(byteBufferTpgGifDecoder));
        registry.prepend(ByteBuffer.class, GifDrawable.class, byteBufferTpgGifDecoder);
        //注册主色解码器
        registry.prepend(InputStream.class, Bitmap.class, new ImageAveDecoder(glide.getBitmapPool()));
        /*------------------解码器 结束-------------------------*/
    }
}
~~~

##### 一 加载静态TPG图片

~~~
    registry.prepend(InputStream.class, Bitmap.class, new TpgDecoder(glide.getBitmapPool()));
~~~

##### 二 加载动图GIF类型TPG图片 
~~~
    registry.prepend(ByteBuffer.class, GifDrawable.class, new ByteBufferTpgGifDecoder(context, glide.getBitmapPool()));
~~~


##### 三 使用图片主色预加载
~~~
    //先在AppGlideModule中注册主色解码器
    registry.prepend(InputStream.class, Bitmap.class, new ImageAveDecoder(glide.getBitmapPool()));

    //使用glide的thumbnail进行主色预加载
    Glide.with(context)
                    .load(imageUrl)
                    .thumbnail(CloudInfiniteGlide.getImageAveThumbnail(context, imageUrl))
                    .into(imageview);
~~~

##### 四 直接加载 CIImageLoadRequest，目前仅用于格式转换时header传输目标图片格式的情况
~~~
    registry.prepend(CIImageLoadRequest.class, InputStream.class, new CIImageRequestModelLoader.Factory());
~~~

<div id="changelog"></div>

## 更新日志

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



