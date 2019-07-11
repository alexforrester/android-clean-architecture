package com.digian.clean.features.core.data

import com.digian.clean.features.movies.data.adapters.GenreAdapter
import com.squareup.moshi.Moshi


/**
 *
  Created by Alex Forrester on 2019-07-11.
 */
//TODO - Replace with Koin
internal object MoshiFactory {

    private val moshi: Moshi = Moshi.Builder()
        .add(GenreAdapter())
        .build()

    fun getInstance() = moshi
}