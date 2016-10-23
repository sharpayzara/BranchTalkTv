package com.sumavision.branch.http;

import com.sumavision.branch.http.interceptor.HttpCacheInterceptor;
import com.sumavision.branch.http.interceptor.LoggingInterceptor;
import com.sumavision.branch.model.entity.decor.BaseData;
import com.sumavision.branch.utils.BaseApp;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
/**
 *  desc 获取retrofit实例
 *  @author  yangjh
 *  created at  16-5-23 上午10:50
 */
public class SumaClient<E extends BaseData>{
    private static Map<String,String> map = new HashMap<String,String>();
    private static HashMap<String,Object> retrofitMap = new HashMap<>();
    private static Object sumaRetrofit;
    protected static final Object monitor = new Object();
    private static Retrofit retrofit;
    public static OkHttpClient okHttpClient;
    private static File cacheFile;
    private static String host = "http://mobile.tvfan.cn:8080/mepg-api/";
    private SumaClient(){
    }

    private static void initRetrofitClient(){
        initOkHttpClient();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)

                .build();
    }

    private static void initOkHttpClient() {
        if (okHttpClient == null) {
            // 因为BaseUrl不同所以这里Retrofit不为静态，但是OkHttpClient配置是一样的,静态创建一次即可
            cacheFile = new File(BaseApp.getContext().getCacheDir(),
                    "HttpCache"); // 指定缓存路径
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 200); // 指定缓存大小200Mb
            // 云端响应头拦截器，用来配置缓存策略
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(new StethoInterceptor())//chrome工具调试的中间件
                    .addInterceptor(new HttpCacheInterceptor())
                    .addInterceptor(new LoggingInterceptor())
                    //.addInterceptor(new HttpLoggingInterceptor())
                    .cache(cache)
                    .build();
        }
    }

    public static <T> T getRetrofitInstance(Class<T> cls) {
        synchronized (monitor) {

            if (sumaRetrofit == null || !retrofitMap.containsKey(cls.getName())) {
                initRetrofitClient();
                sumaRetrofit = retrofit.create(cls);
                retrofitMap.put(cls.getName(),sumaRetrofit);
            }else{
                return (T)retrofitMap.get(cls.getName());
            }
            return (T) sumaRetrofit;
        }
    }


    public static <T extends BaseData> Subscription subscribe(Callable<Observable<T>> callable, final Action1<T> action, final Action1<Throwable> throwableAction, final Class cls) {
        Subscription subscription = null;
        try {
            final Observable<T> observable = callable.call();
            subscription = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(action ,throwableAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscription;
    }

}
