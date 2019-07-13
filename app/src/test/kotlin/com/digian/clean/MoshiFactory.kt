package com.digian.clean

import com.digian.clean.features.movies.data.adapters.GenreAdapter
import com.squareup.moshi.Moshi

/**
 * Created by Alex Forrester on 2019-07-13.
 */
internal object MoshiFactory {

    val moshi: Moshi = Moshi.Builder()
        .add(GenreAdapter())
        .build()

}