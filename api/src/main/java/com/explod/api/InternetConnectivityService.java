/*
 * Copyright (c) 2017 SalesRabbit, Inc. All rights reserved.
 */

package com.explod.api;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

public interface InternetConnectivityService {

    boolean isConnected();

    @NonNull
    Observable<Boolean> connectionObservable();

}
