package com.explod.logging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class Logger {

    public static void init(@NonNull Context context) {
        // no-op in debug builds
    }

    public static void d(@NonNull String tag, @NonNull String message) {
        d(tag, message, null);
    }

    public static void d(@NonNull String tag, @NonNull String message, @Nullable Throwable t) {
        if (t == null) {
            Log.d(tag, message);
        } else {
            Log.d(tag, message, t);
        }
    }

    public static void e(@NonNull String tag, @NonNull String message) {
        e(tag, message, null);
    }

    public static void e(@NonNull String tag, @NonNull String message, @Nullable Throwable t) {
        if (t == null) {
            Log.e(tag, message);
        } else {
            Log.e(tag, message, t);
        }
    }

    public static void track(@NonNull String tag, @NonNull String message) {
        track(tag, message, null);
    }

    public static void track(@NonNull String tag, @NonNull String message, @Nullable Throwable t) {
        if (t == null) {
            Log.i(tag, message);
        } else {
            Log.i(tag, message, t);
        }
    }

}
