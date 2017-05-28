package com.explod.api;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import okhttp3.ResponseBody;

public interface ExplodService {
    
    /* User */

    @NonNull
    Single<UserCreated> createUser(@NonNull String username, @NonNull String email, @NonNull String fullName, @NonNull String password);

    @NonNull
    Single<Token> login(@NonNull String username, @NonNull String password);

    @NonNull
    Single<Boolean> getUserUsernameExists(@NonNull String username);

    @NonNull
    Single<Boolean> getUserEmailExists(@NonNull String email);

    @NonNull
    Single<Profile> getUserProfile();
    
    /* Upload */

    @NonNull
    Single<Lease> createUploadLease(@NonNull String uploadUuid);

    @NonNull
    Single<ResponseBody> getDownloadByLease(@NonNull String leaseId, @NonNull String uploadName);


}
