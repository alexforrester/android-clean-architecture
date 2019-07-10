package com.digian.clean.core.data

import com.digian.clean.features.movies.data.adapters.GenreAdapter
import com.squareup.moshi.Moshi

internal object MoshiFactory {

    private val moshi: Moshi = Moshi.Builder()
        .add(GenreAdapter())
        .build()

    fun getInstance() = moshi
}