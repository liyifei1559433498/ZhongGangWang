package com.zgw.network_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;

public abstract class OkHttpConfig {
    /**
     * 为网络请求添加进度管理器
     * @param builder
     */
    public abstract void configHttps(OkHttpClient.Builder builder);

    public Gson configGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }


    public static final OkHttpConfig DEFAULT_CONFIG = new OkHttpConfig() {
        @Override
        public void configHttps(OkHttpClient.Builder builder) {}
    };


}
