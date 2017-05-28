package com.explod.api;

import android.support.annotation.NonNull;

import io.reactivex.Single;


class MockDeviceNameService implements DeviceNameService {

    @NonNull
    @Override
    public Single<String> getDeviceName() {
        return Single.just("Test Device 5000");
    }

}
