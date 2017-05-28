package com.explod.api;

import android.support.annotation.NonNull;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


class MockInternetConnectivityService implements InternetConnectivityService {

    @NonNull
    private final BehaviorSubject<Boolean> mConnectionSubject = BehaviorSubject.create();

    public MockInternetConnectivityService() {
        this(true);
    }

    public MockInternetConnectivityService(boolean connected) {
        setConnected(connected);
    }

    public void setConnected(boolean connected) {
        mConnectionSubject.onNext(connected);
    }

    @Override
    public boolean isConnected() {
        return mConnectionSubject.getValue();
    }

    @NonNull
    @Override
    public Observable<Boolean> connectionObservable() {
        return mConnectionSubject
            .distinctUntilChanged()
            .toFlowable(BackpressureStrategy.BUFFER)
            .toObservable();
    }

}
