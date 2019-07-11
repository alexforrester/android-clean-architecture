package com.digian.clean.features.core.data

/**
 * Created by Alex Forrester on 04/04/2019.
 *
 * Credits to Yossi Segev -
 * See <a href="https://proandroiddev.com/a-guided-tour-inside-a-clean-architecture-code-base-48bb5cc9fc97</a>
 */
interface Mapper<in E, T> {
    fun mapFrom(from: E): T
}

