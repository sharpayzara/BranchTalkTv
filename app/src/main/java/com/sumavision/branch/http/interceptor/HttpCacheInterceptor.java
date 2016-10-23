package com.sumavision.branch.http.interceptor;

import com.sumavision.branch.utils.BaseApp;
import com.sumavision.branch.utils.NetworkUtil;
import com.jiongbull.jlog.JLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 云端响应头拦截器，用来配置缓存策略
 * Dangerous interceptor that rewrites the server's cache-control header.
 */

public class HttpCacheInterceptor implements Interceptor {
    private static Map<String,String> map = new HashMap<String,String>();
    //设缓存有效期为两天
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        JLog.e(String.format("Sending request %s by %s%n%s",
                request.url(), request.method(), request.headers()));
        Response originalResponse = chain.proceed(request);
        if (NetworkUtil.isConnectedByState(BaseApp.getContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build();
        } else {
            return originalResponse.newBuilder().header("Cache-Control",
                    "public, only-if-cached," + CACHE_STALE_SEC)
                    .removeHeader("Pragma").build();
        }

    }

}
