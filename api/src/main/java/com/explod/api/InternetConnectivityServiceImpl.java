/*
 * Copyright (c) 2017 SalesRabbit, Inc. All rights reserved.
 */

package com.explod.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


class InternetConnectivityServiceImpl implements InternetConnectivityService {

    @NonNull
    private final Context mContext;

    @NonNull
    private final BehaviorSubject<Boolean> mConnectionStatusSubject = BehaviorSubject.create();

    @NonNull
    private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(@NonNull Context context, @NonNull Intent intent) {
            updateStatus(context);
        }
    };

    public InternetConnectivityServiceImpl(@NonNull Context context) {
        mContext = context.getApplicationContext();
        updateStatus(context);
        registerReceiver(context);
    }

    /**
     * Update the network connection status monitored by this service
     *
     * @param context the context used to access the system's Connectivity Service
     */
    private void updateStatus(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean connected = activeNetwork != null && activeNetwork.isConnected();
        mConnectionStatusSubject.onNext(connected);
    }

    private void registerReceiver(@NonNull Context context) {
        mContext.registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @VisibleForTesting
    void unregisterReceiver() {
        mContext.unregisterReceiver(mConnectivityReceiver);
    }

    @Override
    public boolean isConnected() {
        return mConnectionStatusSubject.getValue();
    }

    @NonNull
    @Override
    public Observable<Boolean> connectionObservable() {
        return mConnectionStatusSubject.distinctUntilChanged();
    }
}
