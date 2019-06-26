package com.digian.example.moshicodegen

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * Created by Alex Forrester on 2019-04-23.
 *
 * LifecycleOwner created for tests to ensure LiveData emits
 */
class MoviesLifeCycleOwner  : LifecycleOwner {

    private val lifecycle = LifecycleRegistry(this)

    init {
       lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle
}