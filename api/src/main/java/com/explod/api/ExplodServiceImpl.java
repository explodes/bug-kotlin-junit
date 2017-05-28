package com.explod.api;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import io.reactivex.Single;
import okhttp3.ResponseBody;

class ExplodServiceImpl implements ExplodService {

	private static final String SOURCE = "Android";
	private static final String SOURCE_VERSION = BuildConfig.VERSION_NAME;

	@NonNull
	private final ExplodApi mExplodApi;

	@NonNull
	private final InternetConnectivityService mInternetConnectivityService;

	@NonNull
	private final DeviceNameService mDeviceNameService;

	ExplodServiceImpl(@NonNull ExplodApi explodApi, @NonNull InternetConnectivityService internetConnectivityService, @NonNull DeviceNameService deviceNameService) {
		mExplodApi = explodApi;
		mInternetConnectivityService = internetConnectivityService;
		mDeviceNameService = deviceNameService;
	}

    /* User */

	@NonNull
	@Override
	public Single<UserCreated> createUser(@NonNull String username, @NonNull String email, @NonNull String fullName, @NonNull String password) {
		return onConnectedOrError()
			.flatMap(ok -> mExplodApi.createUser(new CreateUser(username, email, fullName, password)));
	}

	@Override
	@NonNull
	public Single<Token> login(@NonNull String username, @NonNull String password) {
		return onConnectedOrError()
			.flatMap(connected -> mDeviceNameService.getDeviceName())
			.flatMap(deviceName -> mExplodApi.login(new Login(username, password, SOURCE, deviceName, SOURCE_VERSION)));
	}

	@NonNull
	@Override
	public Single<Boolean> getUserUsernameExists(@NonNull String username) {
		return onConnectedOrError()
			.flatMap(connected -> mExplodApi.getUserUsernameExists(username))
			.map(Exists::getExists);
	}

	@NonNull
	@Override
	public Single<Boolean> getUserEmailExists(@NonNull String email) {
		return onConnectedOrError()
			.flatMap(connected -> mExplodApi.getUserEmailExists(email))
			.map(Exists::getExists);
	}

	@NonNull
	@Override
	public Single<Profile> getUserProfile() {
		return onConnectedOrError()
			.flatMap(connected -> mExplodApi.getUserProfile());
	}

    /* Upload */

	@NonNull
	@Override
	public Single<Lease> createUploadLease(@NonNull String uploadUuid) {
		return onConnectedOrError()
			.flatMap(connected -> mExplodApi.createUploadLease(uploadUuid));
	}

	@NonNull
	@Override
	public Single<ResponseBody> getDownloadByLease(@NonNull String leaseId, @NonNull String uploadName) {
		return onConnectedOrError()
			.flatMap(connected -> mExplodApi.getDownloadByLease(leaseId, uploadName));
	}

    /* Implementation details */

	/**
	 * Create a Single that emits a boolean when online, and emits a {@link OfflineException} when
	 * not connected to the internet
	 */
	@NonNull
	@VisibleForTesting
	Single<Boolean> onConnectedOrError() {
		return mInternetConnectivityService.connectionObservable()
			.take(1)
			.singleOrError()
			.flatMap(connected -> connected ? Single.just(Boolean.TRUE) : Single.error(new OfflineException()));
	}

}
