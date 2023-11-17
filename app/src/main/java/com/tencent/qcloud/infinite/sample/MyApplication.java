package com.tencent.qcloud.infinite.sample;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ImageDecoderConfig;
import com.tencent.msdk.dns.DnsConfig;
import com.tencent.msdk.dns.MSDKDnsResolver;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.image.avif.fresco.AvifFormatChecker;
import com.tencent.qcloud.image.avif.fresco.AvisFormatChecker;
import com.tencent.qcloud.image.avif.fresco.FrescoAvifDecoder;
import com.tencent.qcloud.image.avif.fresco.FrescoAvisDecoder;
import com.tencent.qcloud.image.decoder.network.QCloudHttpConfig;
import com.tencent.qcloud.image.decoder.network.fresco.QCloudFrescoUriCallback;
import com.tencent.qcloud.image.decoder.network.fresco.QCloudImagePipelineConfigFactory;
import com.tencent.qcloud.image.tpg.fresco.FrescoTpgAnimatedDecoder;
import com.tencent.qcloud.image.tpg.fresco.FrescoTpgDecoder;
import com.tencent.qcloud.image.tpg.fresco.TpgAnimatedFormatChecker;
import com.tencent.qcloud.image.tpg.fresco.TpgFormatChecker;
import com.tencent.qcloud.track.QCloudTrackService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
        tencentHttpDnsInit();
        trackInit();
    }

    private void trackInit(){
        QCloudTrackService.init(this,
                "ci_image_sdk",
                "ap-guangzhou.cls.tencentcs.com",
                BuildConfig.CLS_SECRET_ID,
                BuildConfig.CLS_SECRET_KEY,
                "",
                BuildConfig.DEBUG, false
        );
    }

    private void frescoInit(Context context) {
        // 解码器配置
        ImageDecoderConfig imageDecoderConfig = new ImageDecoderConfig.Builder()
                .addDecodingCapability(
                        TpgFormatChecker.TPG,
                        new TpgFormatChecker(),
                        new FrescoTpgDecoder())
                .addDecodingCapability(
                        TpgAnimatedFormatChecker.TPGAnimated,
                        new TpgAnimatedFormatChecker(),
                        new FrescoTpgAnimatedDecoder())
                // 配置 AVIF 静态解码器
                .addDecodingCapability(
                        AvifFormatChecker.AVIF,
                        new AvifFormatChecker(),
                        new FrescoAvifDecoder())
                // 配置 AVIF 动图解码器
                .addDecodingCapability(
                        AvisFormatChecker.AVIS,
                        new AvisFormatChecker(),
                        new FrescoAvisDecoder())
                .build();

        // 配置 Image Pipeline
        //设置glide超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
//                .newBuilder(context, okHttpClient)
//                .setImageDecoderConfig(imageDecoderConfig)
//                .build();

        // 使用QCloudHttpUrlLoader加载GlideUrl
        QCloudHttpConfig qCloudHttpConfig = new QCloudHttpConfig.Builder()
                .setDebuggable(BuildConfig.DEBUG)
                .enableQuic(false)
                .setDnsFetch(new QCloudHttpClient.QCloudDnsFetch() {
                    @Override
                    public List<InetAddress> fetch(String hostname) throws UnknownHostException {
                        String ips = MSDKDnsResolver.getInstance().getAddrByName(hostname);
                        String[] ipArr = ips.split(";");
                        if (0 == ipArr.length) {
                            return Collections.emptyList();
                        }
                        List<InetAddress> inetAddressList = new ArrayList<>(ipArr.length);
                        for (String ip : ipArr) {
                            if ("0".equals(ip)) {
                                continue;
                            }
                            try {
                                InetAddress inetAddress = InetAddress.getByName(ip);
                                inetAddressList.add(inetAddress);
                            } catch (UnknownHostException ignored) {
                            }
                        }
                        return inetAddressList;
                    }
                })
                .builder();

        QCloudFrescoUriCallback callback = new QCloudFrescoUriCallback() {
            @Override
            public boolean handles(@NonNull Uri uri) {
                return uri.toString().contains("format/tpg");
            }
        };

        ImagePipelineConfig config = QCloudImagePipelineConfigFactory
//                .newBuilder(context)
                .newBuilder(context, qCloudHttpConfig, callback)
                .setImageDecoderConfig(imageDecoderConfig)
                .build();

        // 初始化 Fresco
        Fresco.initialize(context, config);
    }

    private void tencentHttpDnsInit() {
        DnsConfig dnsConfigBuilder = new DnsConfig.Builder()
                //（必填）dns 解析 id，即授权 id，腾讯云官网（https://console.cloud.tencent.com/httpdns）申请获得，用于域名解析鉴权
                .dnsId(BuildConfig.UT_DNS_ID)
                //（必填）dns 解析 key，即授权 id 对应的 key（加密密钥），在申请 SDK 后的邮箱里，腾讯云官网（https://console.cloud.tencent.com/httpdns）申请获得，用于域名解析鉴权
                .dnsKey(BuildConfig.UT_DNS_KEY)
                //（必填）Channel为desHttp()或aesHttp()时使用 119.29.29.98（默认填写这个就行），channel为https()时使用 119.29.29.99
                .dnsIp("119.29.29.98")
                //（可选）channel配置：基于 HTTP 请求的 DES 加密形式，默认为 desHttp()，另有 aesHttp()、https() 可选。（注意仅当选择 https 的 channel 需要选择 119.29.29.99 的dnsip并传入token，例如：.dnsIp('119.29.29.99').https().token('....') ）。
//                .desHttp()
                //（可选，选择 https channel 时进行设置）腾讯云官网（https://console.cloud.tencent.com/httpdns）申请获得，用于 HTTPS 校验。仅当选用https()时进行填写
//                .token("xxx")
                //（可选）日志粒度，如开启Debug打印则传入"Log.DEBUG"
                .logLevel(Log.DEBUG)
                //（可选）预解析域名，填写形式："baidu.com", "qq.com"，建议不要设置太多预解析域名，当前限制为最多 10 个域名。仅在初始化时触发。
//                .preLookupDomains("baidu.com", "qq.com")
                //（可选）解析缓存自动刷新, 以域名形式进行配置，填写形式："baidu.com", "qq.com"。配置的域名会在 TTL * 75% 时自动发起解析请求更新缓存，实现配置域名解析时始终命中缓存。此项建议不要设置太多域名，当前限制为最多 10 个域名。与预解析分开独立配置。
//                .persistentCacheDomains("baidu.com", "qq.com")
                // (可选) IP 优选，以 IpRankItem(hostname, port) 组成的 List 配置, port（可选）默认值为 8080。例如：IpRankItem("qq.com", 443)。sdk 会根据配置项进行 socket 连接测速情况对解析 IP 进行排序，IP 优选不阻塞当前解析，在下次解析时生效。当前限制为最多 10 项。
//                .ipRankItems(ipRankItemList)
                //（可选）手动指定网络栈支持情况，仅进行 IPv4 解析传 1，仅进行 IPv6 解析传 2，进行 IPv4、IPv6 双栈解析传 3。默认为根据客户端本地网络栈支持情况发起对应的解析请求。
//                .setCustomNetStack(3)
                //（可选）设置是否允许使用过期缓存，默认false，解析时先取未过期的缓存结果，不满足则等待解析请求完成后返回解析结果。
                // 设置为true时，会直接返回缓存的解析结果，没有缓存则返回0;0，用户可使用localdns（InetAddress）进行兜底。且在无缓存结果或缓存已过期时，会异步发起解析请求更新缓存。因异步API（getAddrByNameAsync，getAddrsByNameAsync）逻辑在回调中始终返回未过期的解析结果，设置为true时，异步API不可使用。建议使用同步API （getAddrByName，getAddrsByName）。
//                .setUseExpiredIpEnable(true)
                //（可选）设置是否启用本地缓存（Room），默认false
//                .setCachedIpEnable(true)
                //（可选）设置域名解析请求超时时间，默认为1000ms
//                .timeoutMills(1000)
                //（可选）是否开启解析异常上报，默认false，不上报
//                .enableReport(true)
                // 以build()结束
                .build();


        MSDKDnsResolver.getInstance().init(this, dnsConfigBuilder);
    }
}
