package com.explod.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jaredrummler.android.device.DeviceName;

import io.reactivex.Single;

class DeviceNameServiceImpl implements DeviceNameService {

    @NonNull
    private final Context mContext;

    DeviceNameServiceImpl(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public Single<String> getDeviceName() {
        return Single.fromPublisher(s -> DeviceName.with(mContext).request((info, e) -> {
            if (e != null) {
                s.onError(e);
            } else {
                s.onNext(info.marketName);
                s.onComplete();
            }
        }));
    }

}
