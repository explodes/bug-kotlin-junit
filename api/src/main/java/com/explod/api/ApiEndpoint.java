package com.explod.api;

import android.support.annotation.NonNull;

public enum ApiEndpoint {
    LIVE("http://api.example.com"),
    MOCK("mock://api.example.com");

    @NonNull
    private final String mUrl;

    ApiEndpoint(@NonNull String url) {
        mUrl = url;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }

}
