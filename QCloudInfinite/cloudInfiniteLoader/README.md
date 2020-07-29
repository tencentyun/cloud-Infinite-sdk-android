# cloudInfiniteLoader
**一个基于cloud-infinite实现的简易ImageLoader**

cloud-infinite-loader只是简易的封装了基础的图片下载，没有缓存、生命周期等图片库功能，不建议直接在项目中使用，推荐使用Glide等成熟的图片库

~~~
implementation 'com.tencent.qcloud::cloud-infinite-loader:1.0.0'
~~~
<br/>

1. 加入INTERNET权限
~~~
<uses-permission android:name="android.permission.INTERNET" />
~~~

2. 代码加载
~~~
//创建数据万象操作器
CloudInfinite cloudInfinite = new CloudInfinite();
//根据原始URL和要进行的操作CITransformation，生成图片加载请求CIImageLoadRequest
cloudInfinite.requestWithBaseUrl(image.url, new CITransformation().format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeAcceptHeader), neCloudInfiniteCallback() {
    @Override
    public void onImageLoadRequest(@NonNull CIImageLoadRequest request) {
        //使用CIImageLoader加载TPG图片
        CIImageLoader ciImageLoader = new CIImageLoader();
        ciImageLoader.loadData(request, new CIImageLoadDataCallBack() {
            @Override
            public void onLoadData(byte[] bytes) {
                TpgImageLoader.displayWithData(imageview, bytes);
            }

            @Override
            public void onFailure(QCloudClientException clientExceptionQCloudServiceException serviceException) {

            }
        });
    }
});
~~~