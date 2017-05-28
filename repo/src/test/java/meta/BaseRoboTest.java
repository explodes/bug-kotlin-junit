/*
 * Copyright (c) 2017 SalesRabbit, Inc. All rights reserved.
 */

package meta;

import android.content.Context;

import com.explod.repo.BuildConfig;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.Scheduler;

import static org.robolectric.Shadows.shadowOf;


/**
 * Base class for unit tests that use Android components.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class BaseRoboTest {

    /**
     * Allows mocking to work with Robolectric and Dex
     */
    @Before
    public void setUpDexcache() {
        System.setProperty("dexmaker.dexcache", RuntimeEnvironment.application.getCacheDir().getPath());
    }

    /**
     * Wait for the Main looper to catch up on all of its pending events.
     * <p>
     * Anything queued on the Main Looper will be executed.
     */
    protected static void waitForMainLooper() {
        Context context = RuntimeEnvironment.application;

        Scheduler scheduler = shadowOf(context.getMainLooper()).getScheduler();

        //noinspection StatementWithEmptyBody
        while (scheduler.advanceToLastPostedRunnable()) ;
    }

}
