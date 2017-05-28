package com.explod.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExplodServiceBuilder {

    public static ExplodServiceBuilder begin(@NonNull Context context) {
        return new ExplodServiceBuilder(context.getApplicationContext());
    }

    private static final String DEFAULT_CACHE_DIRECTORY = "exapi";

    private static final long DEFAULT_CACHE_SIZE = 50 * 1024 * 1024; // 50M

    @NonNull
    private final Context mContext;

    @NonNull
    private ApiEndpoint mApiEndpoint = ApiEndpoint.LIVE;

    @NonNull
    private Scheduler mScheduler = Schedulers.io();

    @Nullable
    private TokenProvider mTokenProvider;

    @Nullable
    private HeadersProvider mHeadersProvider;

    @Nullable
    private InternetConnectivityService mInternetConnectivityService;

    @Nullable
    private DeviceNameService mDeviceNameService;

    @Nullable
    private File mCacheDirectory;

    private boolean mEnableLogging = false;

    private long mCacheSize = DEFAULT_CACHE_SIZE;

    private ExplodServiceBuilder(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mCacheDirectory = new File(mContext.getCacheDir(), DEFAULT_CACHE_DIRECTORY);
    }

    @NonNull
    public ExplodServiceBuilder endpoint(@NonNull ApiEndpoint endpoint) {
        mApiEndpoint = endpoint;
        return this;
    }

    @NonNull
    public ExplodServiceBuilder scheduler(@NonNull Scheduler scheduler) {
        mScheduler = scheduler;
        return this;
    }

    @NonNull
    public ExplodServiceBuilder tokenProvider(@Nullable TokenProvider provider) {
        mTokenProvider = provider;
        return this;
    }

    @NonNull
    public ExplodServiceBuilder headersProvider(@Nullable HeadersProvider provider) {
        mHeadersProvider = provider;
        return this;
    }

    @NonNull
    public ExplodServiceBuilder internetConnectivityService(@NonNull InternetConnectivityService service) {
        mInternetConnectivityService = service;
        return this;
    }

    @NonNull
    public ExplodServiceBuilder deviceNameService(@NonNull DeviceNameService service) {
        mDeviceNameService = service;
        return this;
    }

    @NonNull
    public ExplodServiceBuilder logging(boolean enabled) {
        mEnableLogging = enabled;
        return this;
    }

    @NonNull
    public ExplodServiceBuilder cache(@Nullable File dir, long size) {
        mCacheDirectory = dir;
        mCacheSize = size;
        return this;
    }

    @NonNull
    public ExplodService build() {
        InternetConnectivityService ics = createInternetConnectivityService();
        DeviceNameService dns = createDeviceNameService();
        ExplodApi api = createExplodApi();
        return new ExplodServiceImpl(api, ics, dns);
    }

    @NonNull
    private InternetConnectivityService createInternetConnectivityService() {
        if (mInternetConnectivityService == null) {
            if (mApiEndpoint == ApiEndpoint.MOCK) {
                return new MockInternetConnectivityService();
            } else {
                return new InternetConnectivityServiceImpl(mContext);
            }
        }
        return mInternetConnectivityService;
    }

    @NonNull
    private DeviceNameService createDeviceNameService() {
        if (mDeviceNameService == null) {
            if (mApiEndpoint == ApiEndpoint.MOCK) {
                return new MockDeviceNameService();
            } else {
                return new DeviceNameServiceImpl(mContext);
            }
        }
        return mDeviceNameService;
    }

    @NonNull
    private ExplodApi createExplodApi() {
        if (mApiEndpoint == ApiEndpoint.MOCK) {
            return new MockExplodApi(mScheduler, mEnableLogging);
        } else {
            return createRealExplodApi();
        }
    }

    @NonNull
    private ExplodApi createRealExplodApi() {
        return new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(mScheduler))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mApiEndpoint.getUrl())
            .client(createOkHttpClient())
            .build()
            .create(ExplodApi.class);
    }

    @NonNull
    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (mTokenProvider != null || mHeadersProvider != null) {
            builder.addInterceptor(new HeadersInterceptor(mTokenProvider, mHeadersProvider));
        }

        if (mCacheDirectory != null && mCacheSize > 0) {
            Cache cache = new Cache(mCacheDirectory, mCacheSize);
            builder.cache(cache);
        }

        if (mEnableLogging) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        return builder.build();
    }


    private static class HeadersInterceptor implements Interceptor {

        private static final String HEADER_AUTH = "Authorization";

        private static final String HEADER_AUTH_BEARER_PREFIX = "Bearer ";

        @Nullable
        private final TokenProvider mTokenProvider;

        @Nullable
        private final HeadersProvider mHeadersProvider;

        private HeadersInterceptor(@Nullable TokenProvider tokenProvider, @Nullable HeadersProvider headersProvider) {
            mTokenProvider = tokenProvider;
            mHeadersProvider = headersProvider;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder();

            if (mTokenProvider != null) {
                String token = mTokenProvider.getToken();
                if (token != null && token.length() > 0) {
                    builder.addHeader(HEADER_AUTH, HEADER_AUTH_BEARER_PREFIX + token);
                }
            }

            if (mHeadersProvider != null) {
                Map<String, String> extraHeaders = mHeadersProvider.getAdditionalHeaders();
                if (extraHeaders != null) {
                    for (Map.Entry<String, String> header : extraHeaders.entrySet()) {
                        builder.addHeader(header.getKey(), header.getValue());
                    }
                }
            }

            return chain.proceed(builder.build());

        }
    }

}
