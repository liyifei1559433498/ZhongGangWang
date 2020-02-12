package com.zgw.network_api.interceptors;



import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetInterceptor implements Interceptor {
    public static final int CACHE_NO_NET_TIME = 3600;
    public static final int CACHE_HAVE_NET_TIME = 90;
    int noNetCacheTime = CACHE_NO_NET_TIME;
    int cacheTime = CACHE_HAVE_NET_TIME;

    public NetInterceptor() {
    }
    /**
     *
     * @param noNetCacheTime 没有网络时,从缓存中读取时间
     * @param cacheTime 有网络,在多久时间内获取缓存
     */
    public NetInterceptor(int noNetCacheTime, int cacheTime) {
        this.noNetCacheTime = noNetCacheTime;
        this.cacheTime = cacheTime;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        Response originalResponse = chain.proceed(request);
            //有网络，缓存时间短
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + noNetCacheTime)
                    .build();

    }
}