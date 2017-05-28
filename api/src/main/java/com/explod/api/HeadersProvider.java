package com.explod.api;

import android.support.annotation.Nullable;

import java.util.Map;

public interface HeadersProvider {

    @Nullable
    Map<String, String> getAdditionalHeaders();

}
