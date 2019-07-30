package com.digian.clean.features.movies.presentation.IdlingResource
/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A very simple implementation of [IdlingResource].
 *
 *
 * Consider using CountingIdlingResource from espresso-contrib package if you use this class from
 * multiple threads or need to keep a count of pending operations.
 */

class SimpleIdlingResource : IdlingResource {

    @Volatile
    private var mCallback: IdlingResource.ResourceCallback? = null

    // Idleness is controlled with this boolean.
    private val mIsIdleNow = AtomicBoolean(true)

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        mCallback = callback
    }

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the [ResourceCallback].
     * @data isIdleNow false if there are pending operations, true if idle.
     */
    fun setIdleState(isIdleNow: Boolean) {
        mIsIdleNow.set(isIdleNow)
        if (isIdleNow && mCallback != null) {
            mCallback!!.onTransitionToIdle()
        }
    }
}