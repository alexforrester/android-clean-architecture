package com.digian.clean.features.movies.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Alex Forrester on 11/04/2019.
 */

@Parcelize
data class GenreEntity(val id: Int, val name: String) : Parcelable