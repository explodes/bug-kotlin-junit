package com.explod.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.ResponseBody;


class MockExplodApi implements ExplodApi {

    private static final String TAG = MockExplodApi.class.getSimpleName();

    @Nullable
    private final Scheduler mScheduler;

    private final boolean mEnableLogging;

    public MockExplodApi() {
        this(null, false);
    }

    public MockExplodApi(@Nullable Scheduler scheduler, boolean enableLogging) {
        mScheduler = scheduler;
        mEnableLogging = enableLogging;
    }

    @NonNull
    private <T> Single<T> network(@NonNull String endpoint, @NonNull T response) {
        Single<T> sing = Single.just(response);
        if (mScheduler != null) {
            sing = sing.subscribeOn(mScheduler);
        }
        if (mEnableLogging) {
            sing = sing.map(r -> {
                Log.d(TAG, endpoint);
                return r;
            });
        }
        return sing;
    }

    @NonNull
    @Override
    public Single<UserCreated> createUser(@NonNull CreateUser request) {
        UserCreated response = new UserCreated(request.getUsername(), request.getEmail(), new Date());
        return network("POST /user", response);
    }

    @NonNull
    @Override
    public Single<Token> login(@NonNull Login request) {
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.DAY_OF_MONTH, 7);
        Token response = new Token(expiry.getTime(), "invalid-for-real-use", "Android", "testDevice", BuildConfig.VERSION_NAME);
        return network("POST /login", response);
    }

    @NonNull
    @Override
    public Single<Exists> getUserUsernameExists(@NonNull String username) {
        Exists response = new Exists(false);
        return network("GET /user/username/exists/" + username, response);
    }

    @NonNull
    @Override
    public Single<Exists> getUserEmailExists(@NonNull String email) {
        Exists response = new Exists(false);
        return network("GET /user/email/exists/" + email, response);
    }

    @NonNull
    @Override
    public Single<Profile> getUserProfile() {
        Calendar created = Calendar.getInstance();
        created.add(Calendar.MONTH, -1);
        Profile response = new Profile("mockerton@example.com", "mockerton@example.com", "Mocky McMockerton", created.getTime());
        return network("GET /profile", response);
    }


    @NonNull
    @Override
    public Single<Lease> createUploadLease(@NonNull String uploadUuid) {
        String name = "MockFoo.mp4";
        String id = "mockId";
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.HOUR, 2);
        Lease response = new Lease(id, name, expiry.getTime(), "/download/" + id + "/" + name);
        return network("POST /lease/" + uploadUuid, response);
    }

    @NonNull
    @Override
    public Single<ResponseBody> getDownloadByLease(@NonNull String leaseId, @NonNull String uploadName) {
        ResponseBody body = ResponseBody.create(MediaType.parse("text/plain"), "hello world!");
        return network("GET /download/" + leaseId + "/" + uploadName, body);
    }

}
