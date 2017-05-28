package com.explod.logging;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import meta.BaseRoboTest;

public class LoggerTest extends BaseRoboTest {

    private static final String TAG = "TEST";
    private static final String MESSAGE = "test message";

    @Test
    public void init() throws Exception {
        Logger.init(RuntimeEnvironment.application);
    }

    @Test
    public void d() throws Exception {
        Logger.d(TAG, MESSAGE);
    }

    @Test
    public void d_withNullThrowable() throws Exception {
        Logger.d(TAG, MESSAGE, null);
    }

    @Test
    public void d_withThrowable() throws Exception {
        Logger.d(TAG, MESSAGE, new Exception(MESSAGE));
    }

    @Test
    public void e() throws Exception {
        Logger.e(TAG, MESSAGE);
    }

    @Test
    public void e_withNullThrowable() throws Exception {
        Logger.e(TAG, MESSAGE, null);
    }

    @Test
    public void e_withThrowable() throws Exception {
        Logger.e(TAG, MESSAGE, new Exception(MESSAGE));
    }

    @Test
    public void track() throws Exception {
        Logger.track(TAG, MESSAGE);
    }

    @Test
    public void track_withNullThrowable() throws Exception {
        Logger.track(TAG, MESSAGE, null);
    }

    @Test
    public void track_withThrowable() throws Exception {
        Logger.track(TAG, MESSAGE, new Exception(MESSAGE));
    }


}