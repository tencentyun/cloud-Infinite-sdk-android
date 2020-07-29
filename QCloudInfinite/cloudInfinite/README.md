# cloudInfinite
**数据万象基础SDK** ：根据用户对图片的操作构建图片加载请求

~~~
 implementation 'com.tencent.qcloud::cloud-infinite:1.0.0'			
~~~

~~~
//创建数据万象操作器
CloudInfinite cloudInfinite = new CloudInfinite();
//根据原始URL和要进行的操作CITransformation，生成图片加载请求CIImageLoadRequest
cloudInfinite.requestWithBaseUrl(url, new CITransformation().format(CIImageFormat.TPG, CIImageLoadOptions.LoadTypeAcceptHeader), neCloudInfiniteCallback() {
    @Override
    public void onImageLoadRequest(@NonNull CIImageLoadRequest request) {
        //...
    }
});
~~~