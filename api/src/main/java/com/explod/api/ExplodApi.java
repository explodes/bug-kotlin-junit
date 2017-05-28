package com.explod.api;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExplodApi {

    /* User */

    @POST("/user")
    @NonNull
    Single<UserCreated> createUser(@Body @NonNull CreateUser request);

    @POST("/login")
    @NonNull
    Single<Token> login(@Body @NonNull Login request);

    @GET("/user/username/exists/{username}")
    @NonNull
    Single<Exists> getUserUsernameExists(@Path("username") @NonNull String username);

    @GET("/user/username/exists/{email}")
    @NonNull
    Single<Exists> getUserEmailExists(@Path("email") @NonNull String email);

    @GET("/profile")
    @NonNull
    Single<Profile> getUserProfile();

    /* Upload */

    @POST("/lease/{uuid}")
    @NonNull
    Single<Lease> createUploadLease(@Path("uuid") @NonNull String uploadUuid);

    @POST("/download/{leaseId}/{uploadName}")
    @NonNull
    Single<ResponseBody> getDownloadByLease(@Path("leaseId") @NonNull String leaseId, @Path("uploadName") @NonNull String uploadName);

}
