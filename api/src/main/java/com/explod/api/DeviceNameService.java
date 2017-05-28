package com.explod.api;

import android.support.annotation.NonNull;

import io.reactivex.Single;

public interface DeviceNameService {

    @NonNull
    Single<String> getDeviceName();

}
