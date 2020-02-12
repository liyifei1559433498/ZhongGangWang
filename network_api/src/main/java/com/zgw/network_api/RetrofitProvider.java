package com.zgw.network_api;


import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zgw.base.util.Constants;
import com.zgw.network_api.converters.StringConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import static com.zgw.network_api.OkHttpConfig.DEFAULT_CONFIG;


public class RetrofitProvider {
    public static String baseUrl = BuildConfig.DEBUG
            ? Constants.MAIN_URL
            : Constants.TEST_URl;
    private OkHttpConfig config = DEFAULT_CONFIG;
    private static Map<String, Retrofit> retrofitMap = new HashMap<>();
    private static Map<String, OkHttpClient> clientMap = new HashMap<>();

    public void setUrl(String url) {
        baseUrl = url;
    }

    private static OkHttpClient provideOkHttpClient(String baseUrl, OkHttpConfig config) {
        checkBaseUrl(baseUrl);
        if (clientMap.get(baseUrl) != null) {
            return clientMap.get(baseUrl);
        }
        OkHttpClient.Builder builder = provideOkHttp().newBuilder();
        builder.readTimeout(60, TimeUnit.SECONDS);
        if (null != config) {
            config.configHttps(new OkHttpClient.Builder());
        }
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();
        clientMap.put(baseUrl, client);

        return client;
    }


    private static Retrofit provideRetrofit(String baseUrl, OkHttpConfig config) {
        checkBaseUrl(baseUrl);

        if (retrofitMap.get(baseUrl) != null) {
            return retrofitMap.get(baseUrl);
        }

        if (config == null) {
            config = DEFAULT_CONFIG;
        }
        Gson gson = config.configGson();
        gson = EmptyUtils.isEmpty(gson) ?
                DEFAULT_CONFIG.configGson() : gson;

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkHttpClient(baseUrl, config))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new StringConverterFactory(gson));

        Retrofit retrofit = builder.build();

        retrofitMap.put(baseUrl, retrofit);

        return retrofit;
    }


    private static void checkBaseUrl(String baseUrl) {
        if (EmptyUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
    }

    public static <S> S getService(Class<S> service) {
        //Log.d("getService", "getService: "+baseUrl);
        return provideRetrofit(baseUrl, null).create(service);
    }

    public static <S> S getService(Class<S> service, OkHttpConfig config) {
        return provideRetrofit(baseUrl, config).create(service);
    }

    public static <S> S getService(String baseUrl, Class<S> service) {
        return provideRetrofit(baseUrl, null).create(service);
    }

    public <S> S getService(String baseUrl, OkHttpConfig config, Class<S> service) {
        return provideRetrofit(baseUrl, config).create(service);
    }

    public static void clearCache() {
        retrofitMap.clear();
        clientMap.clear();
    }

    public static Map<String, Retrofit> getRetrofitMap() {
        return retrofitMap;
    }

    public static Map<String, OkHttpClient> getClientMap() {
        return clientMap;
    }

    public static OkHttpClient provideOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS);
        return builder.build();
    }


}
